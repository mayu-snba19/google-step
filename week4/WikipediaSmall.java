import java.io.*;
import java.util.*;

public class WikipediaSmall {
  static Map<String, String> pages = new TreeMap<String, String>();;
  static Map<String, Set<String>> links = new TreeMap<String, Set<String>>();;
  private static WikipediaSmall singleton = new WikipediaSmall();

  private WikipediaSmall(){
    inputFiles();
  }

  public static WikipediaSmall getInstance(){
    return singleton;
  }

  public static void main(String[] args) {
    WikipediaSmall wiki = WikipediaSmall.getInstance();

    String startPosi = wiki.getID("Google");
    String goalPosi = wiki.getID("セグウェイ");
    ArrayList<String> answers = wiki.getPath(startPosi, goalPosi);
    wiki.printPath(answers);

  }

  // ファイル読み込み
  private void inputFiles() {
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
  String getID(String name) {
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
  ArrayList<String> getPath(String startPosi, String goalPosi) {
    boolean isFind = false; // 辿り着けるかどうか

     // [東京(キー), 奈良・京都・大阪・東京(始点からキーまでの最短経路)]のように経路を保存する
    Map<String, ArrayList<String>> dir = new TreeMap<String, ArrayList<String>>();

    Deque<String> queue = new ArrayDeque<String>();
    queue.add(startPosi);

    // 始点の経路を記録
    {
      ArrayList<String> tmp = new ArrayList<String>();
      tmp.add(startPosi);
      dir.put(startPosi, tmp);
    }

    while (!queue.isEmpty()) {
      String s = queue.poll();
      if (s.equals(goalPosi)) {
        isFind = true;
        break;
      }
      
      // ALEX_COMMENT:  link.get(s) is called twice below - that is unnecessary.
      if (links.get(s) == null) continue;
      for (String current : links.get(s)) {
        if (!dir.containsKey(current)) { //既にこのノードに訪れたことがあれば、経路が記録されている。
          queue.add(current);
          ArrayList<String> dirCurrent = new ArrayList<String>(dir.get(s));
          dirCurrent.add(current);
          dir.put(current, dirCurrent);
        }
      }
    }
    if (!isFind) {
      System.out.println("経路が見つかりませんでした");
      System.exit(0);
    }
    return dir.get(goalPosi);
  }

  void printPath(ArrayList<String>paths){
    for (String path : paths) {
      System.out.print(pages.get(path) + " ");
    }
    System.out.println();
  }

}
