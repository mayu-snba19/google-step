import java.io.*;
import java.util.*;

public class WikipediaSmall {
  static Map<String, String> pages;
  static Map<String, Set<String>> links;

  public static void main(String[] args) {
    pages = new TreeMap<String, String>();
    links = new TreeMap<String, Set<String>>();

    inputFiles(); // ファイル読み込み

    // テストケースです。不要な時はコメントアウトしてください。
    runTests();

    String startPosi = getID("Google");
    String goalPosi = getID("アイスクリーム");
    ArrayList<String> answers = getPath(startPosi, goalPosi);
    for (String answer : answers) {
      System.out.print(pages.get(answer) + " ");
    }
    System.out.println();
  }

  // ファイル読み込み
  static void inputFiles() {
    try {
      File pageFile = new File("data/pages_small.txt");
      Scanner pageReader = new Scanner(pageFile);
      while (pageReader.hasNextLine()) {
        String[] page = pageReader.nextLine().split("\t", 0);
        // page[0]: id, page[1]: title
        pages.put(page[0], page[1]);
      }
      pageReader.close();

      File linkFile = new File("data/links_small.txt");
      Scanner linkReader = new Scanner(linkFile);
      while (linkReader.hasNextLine()) {
        String[] link = linkReader.nextLine().split("\t", 0);
        // link[0]: id (from), links[1]: id (to)
        if (!links.containsKey(link[0])) {
          links.put(link[0], new TreeSet<String>());
        }
        links.get(link[0]).add(link[1]);
      }
      linkReader.close();
    } catch (FileNotFoundException e) {
      System.out.println("File not found.");
      return;
    }
  }

  // ページIDを取得して返す
  static String getID(String name) {
    String id = null;
    for (Map.Entry<String, String> page : pages.entrySet()) {
      if (page.getValue().equals(name)) {
        id = page.getKey();
        break;
      }
    }
    if (id == null) {
      System.out.println("検索対象が見つかりませんでした");
      System.exit(0);
    }
    return id;
  }

  // スタート地点とゴール地点のIDを読み込んで、経路を返す
  static ArrayList<String> getPath(String startPosi, String goalPosi) {
    boolean isFind = false; // 辿り着けるかどうか
    Map<String, ArrayList<String>> dir = new TreeMap<String, ArrayList<String>>(); // 経路を保存する

    Deque<String> queue = new ArrayDeque<String>();
    queue.add(startPosi);
    {
      ArrayList<String> arr = new ArrayList<String>();
      arr.add(startPosi);
      dir.put(startPosi, arr);
    }

    while (!queue.isEmpty()) {
      String s = queue.poll();
      if (s.equals(goalPosi)) {
        isFind = true;
        break;
      }
      if (links.get(s) == null) continue;
      for (String ss : links.get(s)) {
        if (!dir.containsKey(ss)) {
          queue.add(ss); // キューに追加

          // 経路を記録
          ArrayList<String> arr = new ArrayList<String>(dir.get(s));
          arr.add(ss);
          dir.put(ss, arr);
        }
      }
    }
    if (!isFind) {
      System.out.println("経路が見つかりませんでした");
      System.exit(0);
    }
    return dir.get(goalPosi);
  }

  // テストを行う
  static void runTests() {
    System.out.println("------ test start! ------");
    test("Google", "セグウェイ", 2);
    test("ツキノワグマ", "環境", 3);
    test("Google", "Google", 1);
    test("国際宇宙ステーション", "国際宇宙ステーション", 1);
    test("ラーメン", "カレー", 2);
    test("ゲーム", "じゃんけん", 2);
    test("じゃんけん", "ゲーム", 1);
    System.out.println("------ test done. ------");
  }

  // テスト結果を表示する
  static void test(String startName, String goalName, int expectedDis) {
    String startPosi = getID(startName);
    String goalPosi = getID(goalName);
    ArrayList<String> answers = getPath(startPosi, goalPosi);
    if (answers.size() == expectedDis) {
      System.out.printf("PASS! %s -> %s\n", startName, goalName);
    } else {
      System.out.printf("FAIL! %s -> %s, distance should be %d but was %d. \n", startName, goalName, expectedDis, answers.size());
    }
  }
}
