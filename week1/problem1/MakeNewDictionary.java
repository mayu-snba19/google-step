
/**
 * ソート済みの辞書を生成する
 */

import java.util.*;

class Pair {
  String word;
  String sortword;

  public Pair(String word, String sortword) {
    this.word = word;
    this.sortword = sortword;
  }
}

public class MakeNewDictionary {
  static int MAX = 84093;

  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);
    ArrayList<Pair> arr = new ArrayList<Pair>();

    for (int i = 0; i < MAX; i++) {
      String word = sc.next();
      char[] c = word.toCharArray();
      Arrays.sort(c);
      String sortword = new String(c);
      arr.add(new Pair(word, sortword));
    }

    arr.sort((a, b) -> a.sortword.compareTo(b.sortword));
    arr.forEach(p -> System.out.println(p.sortword + " " + p.word));
  }
}
