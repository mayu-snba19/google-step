import java.util.*;
import java.util.function.*;
import java.util.stream.Collectors;
import java.io.*;

class Counter {
  char c[] = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'm', 'n', 'l', 'o', 'p', 'q', 'r', 's', 't', 'u',
      'v', 'w', 'x', 'y', 'z' };
  int count[];

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

    ArrayList<String> targets = new ArrayList<String>();
    File file = new File(filename + ".txt");
    Set<String> ans = new HashSet<String>();

    try {
      BufferedReader br = new BufferedReader(new FileReader(file));
      int cnt=0;
      while (br.ready() && cnt<=1000) {
        String s = br.readLine();
        cnt++;
        targets.add(s);
      }
      br.close();
    } catch (Exception e) {
      System.out.println(e);
    }

    Counter c = new Counter();
    int len=Math.min(targets.get(0).length(), 7);

    for (String target : targets) {
      int[] targetCnt = c.counter(target);
      try {
        BufferedReader br = new BufferedReader(new FileReader("words.txt"));
        while (br.ready()) {
          String s = br.readLine();
          int[] wordCnt = c.counter(s);
          boolean boo = true;
          int cnt=0;
          for (int i = 0; i < 26; i++) {
            if (targetCnt[i] < wordCnt[i]) boo = false;
            cnt+=wordCnt[i];
          }
          if (boo && cnt>=len)
            ans.add(s);
        }
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
