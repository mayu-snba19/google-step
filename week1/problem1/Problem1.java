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

    File newwordsFile = new File("newwords.txt");
    if(!newwordsFile.exists()) makeNewDictionary(newwordsFile);

    arr1= new String[MAX];
    arr2 = new String[MAX];
    try {
      BufferedReader br=new BufferedReader(new FileReader(newwordsFile));
      int i=0;
      while(br.ready()){
        String s=br.readLine();
        arr1[i]=s.split(" ")[0];
        arr2[i]=s.split(" ")[1];
        i++;
      }
      br.close();
    } catch (Exception e) {
      e.printStackTrace();
    }

    //検索対象
    String[]targets={"yamada","maeda","sato","suzuki","pokemon","anime","ant","sparrow","friend"};

    for(String target:targets){
      String ans=binarySearch(target);
      if(!ans.equals(target) && !ans.equals("")) System.out.println(target+" "+ans);
    }
  }

  //ソート済みの辞書を作成する
  static void makeNewDictionary(File newwordsFile){
    try{
      File wordsFile=new File("words.txt");
      BufferedReader br=new BufferedReader(new FileReader(wordsFile));
      ArrayList<Pair> arr = new ArrayList<Pair>();

      for (int i = 0; i < MAX; i++) {
        String word = br.readLine();
        char[] c = word.toCharArray();
        Arrays.sort(c);
        String sortword = new String(c);
        arr.add(new Pair(word, sortword));
      }
      arr.sort((a, b) -> a.sortword.compareTo(b.sortword));
      BufferedWriter bw=new BufferedWriter(new FileWriter(newwordsFile));
      for(Pair p:arr){
        bw.write(p.sortword + " " + p.word);
        bw.newLine();
      }
      br.close();
      bw.close();

    }catch(IOException e){
      e.printStackTrace();
    }
  }

  static String binarySearch(String target) {
    int left = 0;
    int right = MAX;
    String ans="";

    //文字列をソート
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
