import java.util.ArrayList;
import java.util.Scanner;

public class Test {
  public static void main(String[] args) {

    System.out.println("------ test start! ------");
    test("Google", "セグウェイ", 2);
    test("ツキノワグマ", "環境", 3);
    test("Google", "Google", 1);
    test("国際宇宙ステーション", "国際宇宙ステーション", 1);
    test("ラーメン", "カレー", 2);
    test("ゲーム", "じゃんけん", 2);
    test("じゃんけん", "ゲーム", 2);
    System.out.println("------ test done. ------");

    Scanner sc = new Scanner(System.in);
    while (true) {
      System.out.print("start: ");
      String startName = sc.next();
      System.out.print("goal: ");
      String goalName = sc.next();
      WikipediaSmall wiki = WikipediaSmall.getInstance();
      ArrayList<String> answers = wiki.getPath(wiki.getID(startName), wiki.getID(goalName));
      wiki.printPath(answers);
    }

  }

  // テスト結果を表示する
  static void test(String startName, String goalName, int expectedDis) {
    WikipediaSmall wiki = WikipediaSmall.getInstance();
    String startPosi = wiki.getID(startName);
    String goalPosi = wiki.getID(goalName);
    ArrayList<String> answers = wiki.getPath(startPosi, goalPosi);
    if (answers.size() == expectedDis) { // 予想通りの結果を得られた場合
      System.out.printf("PASS! %s -> %s\n", startName, goalName);
    } else {
      System.out.printf("FAIL! %s -> %s, distance should be %d but was %d. \n", startName, goalName, expectedDis,
          answers.size());
    }
  }
}
