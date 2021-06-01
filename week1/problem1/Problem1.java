/**
 * 与えられた文字列のAnagramを辞書ファイルから探して返すプログラム
 */

import java.io.*;
import java.util.*;

class Pair {
  String word;
  String sortword;

  public Pair(String word, String sortword) {
    this.word = word;
    this.sortword = sortword;
  }
}

public class Problem1 {

  static int MAX = 84093;
  static String arr1[];
  static String arr2[];

  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    //newwords.txtが存在すればデータを読み取り、なければ新たに辞書を作成する。
    File newwordsFile = new File("newwords.txt");
    if(!newwordsFile.exists()) makeNewDictionary(newwordsFile);

    arr1= new String[MAX];
    arr2 = new String[MAX];

    try {
      BufferedReader br=new BufferedReader(new FileReader(newwordsFile));
      int i=0;

      //ソート済み辞書ファイルからデータを全て読む。
      while(br.ready()){
        String s=br.readLine();
        arr1[i]=s.split(" ")[0]; //ソート済
        arr2[i]=s.split(" ")[1]; //ソート前
        i++;
      }
      br.close();
    } catch (Exception e) {
      // ALEX_COMMENT:  if there's an exception here, do you really want to continue with the rest of the processing?
      e.printStackTrace();
      System.exit(1);
    }

    //検索対象
    String[]targets={"yamada","maeda","sato","suzuki","pokemon","anime","ant","sparrow","friend"};

    //アナグラムがあれば表示
    for(String target:targets){
      String ans=binarySearch(target);
      if(!ans.equals(target) && !ans.equals("")) System.out.println(target+" "+ans);
    }

  }

  //ソート済みの辞書を作成する
  static void makeNewDictionary(File newwordsFile){
    try{
      //辞書ファイル(words.txt)からデータを読み取る
      File wordsFile=new File("words.txt");
      BufferedReader br=new BufferedReader(new FileReader(wordsFile));

      //<ソート後の文字列, ソート前の文字列>をセットでリストに追加する
      ArrayList<Pair> arr = new ArrayList<Pair>();

      for (int i = 0; i < MAX; i++) {
        //文字列をchar型の配列に直してソート
        String word = br.readLine();
        char[] c = word.toCharArray();
        Arrays.sort(c);

        //ソートしたchar配列をStringに直してリストに追加
        String sortword = new String(c);
        arr.add(new Pair(word, sortword));
      }
      //ソート済みの文字列を比較して全体もソートする
      arr.sort((a, b) -> a.sortword.compareTo(b.sortword));

      //ファイルに一行ずつ書き込む
      BufferedWriter bw=new BufferedWriter(new FileWriter(newwordsFile));
      for(Pair p:arr){
        bw.write(p.sortword + " " + p.word);
        bw.newLine();
      }
      br.close();
      bw.close();

    }catch(IOException e){
      // ALEX_COMMENT:  Great that you thought of catching the exception - 
      //   but you probably want to convey that to the caller via the interface.
      //    Currently, caller does not know.
      e.printStackTrace();
      System.exit(1);
    }
  }

  static String binarySearch(String target) {
    int left = 0;
    int right = MAX;
    String ans="";

    //検索対象文字列をソート
    char[] c = target.toCharArray();
    Arrays.sort(c);
    target = new String(c);

    //二分探索
    while (right - left > 1) {
      int middle = (left + right) / 2;
      int compare = arr1[middle].compareTo(target);
      if (compare > 0) {
        right = middle;
      } else if (compare == 0) {
        ans=arr2[middle];
        break;
      } else {
        left = middle;
      }
    }
    return ans;
  }

}
