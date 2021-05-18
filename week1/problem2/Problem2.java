import java.util.*;
import java.io.*;

class Counter {
  char c[] = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u',
      'v', 'w', 'x', 'y', 'z' };
  int count[];

  //単語に含まれるアルファベットの個数を数える
  public int[] counter(String s) {
    count = new int[26];
    for (int i = 0; i < s.length(); i++) {
      for (int j = 0; j < 26; j++) {
        if (s.charAt(i) == c[j])
          count[j]++;
      }
    }
    return count;
  }
}

public class Problem2 {

  static String filename;

  public static void main(String[] args) {

    if (args.length == 0) {
      System.out.println("ファイル名を指定してください。入力例 : java Problem2 small");
      System.exit(0);
    }
    filename = args[0];
    solve();

  }

  static void solve() {

    ArrayList<String> targets = new ArrayList<String>(); //small.txtなどから与えられた文字列を格納
    File file = new File(filename + ".txt");
    ArrayList<String> ans = new ArrayList<String>();
    int charScore[] = {1, 3, 2, 2, 1, 3, 3, 1, 1, 4, 4, 2, 2, 1, 1, 3, 4, 1, 1, 1, 2, 3, 3, 4, 3, 4};

    try {
      BufferedReader br = new BufferedReader(new FileReader(file));
      while (br.ready()) {
        String s = br.readLine();
        targets.add(s);
      }
      br.close();
    } catch (Exception e) {
      // ALEX_COMMENT:  same as exercise one: exception hidden, not handled
      System.out.println(e);
    }

    Counter c = new Counter();

    for (String target : targets) {
      int[] targetCnt = c.counter(target);
      int maxScore=0;
      String nowAns="";

      //辞書ファイルからアナグラムを見つける
      try {
        BufferedReader br = new BufferedReader(new FileReader("words.txt"));
        while (br.ready()) {
          String s = br.readLine();
          int nowScore=0;
          boolean boo=true;

          if(s.length()*4<maxScore) continue;
          int[] wordCnt = c.counter(s);

          for (int i = 0; i < 26; i++) {
            //この単語は作れないのでスキップ
            if (targetCnt[i] < wordCnt[i]){
              boo=false;
              break;
            }
            nowScore+=wordCnt[i]*charScore[i];
          }
          
          
          //高いスコアのものに更新
          // ALEX_COMMENT: just confirm please (during group discussion):  will this read
          //               the entire dictionary to find the highest score?
          if(boo && nowScore>maxScore){
            maxScore=nowScore;
            nowAns=s;
          }
        }
        ans.add(nowAns);
        br.close();
      } catch (Exception e) {
        System.out.println(e);
      }
    }

    Iterator iterator = ans.iterator();
    while (iterator.hasNext()) {
      System.out.println(iterator.next());
    }
  }
}
