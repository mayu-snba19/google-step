// ALEX_COMMENT:  This class has two jobs:
 //              doubly-linked list for the items
 //              hash-table for the nodes
 //  You could have a nicer implementation if you had two classes,
  //            one for each job above, 
  //            along with a master class that uses the other 2 classes.
  //   Then you can re-use each class individually
public class List {
  Cell head;
  Cell table[];
  int cnt = 0;
  int MAX_SIZE = 10;

  public List() {
    // ALEX_COMMENT:  is there really a need to use space for the "special"  head cell?
    //                 (note the strings "headurl" and "headpage" are not part of the data)
    //                 This decision started with your design.
    head = new Cell("headurl", "headpage");
    head.next = head;
    head.prev = head;
    table = new Cell[100];
  }

  void insert(String url, String page) {
    Cell c = getCell(url);
    if (c == null) {
      c = new Cell(url, page); // 新しく作成する
      insertTable(url, c);
      if (cnt >= MAX_SIZE)
        removeLast();
      cnt++;
    } else {
      // 既にあるものを削除する
      Cell right = c.next;
      c.prev.next = right;
      right.prev = c.prev;
    }
    // 先頭に挿入する
    head.next.prev = c;
    c.next = head.next;
    head.next = c;
    c.prev = head;
  }

  // 一番後ろの要素を削除する
  void removeLast() {
    Cell c = head.prev;
    removeTable(c.url);
    head.prev.prev.next = head;
    head.prev = head.prev.prev;
    cnt--;
  }

  // ハッシュ関数
  int hash(String s) {
    int total = 0;
    int mod = 100;
    for (int i = 0; i < s.length(); i++) {
      total += s.charAt(i);
    }
    return total % mod;
  }

  // ハッシュテーブルからCellを取り出す(見つからなかった場合null)
  Cell getCell(String s) {
    int hashValue = hash(s);
    Cell c = null;
    boolean isFind = false;
    // ALEX_COMMENT:  isFind (abvoe) was not used, apparently
    
    // ハッシュテーブルを確認する
    for (int i = hashValue; i < table.length; i++) {
      if (table[i] == null) break; //これ以降は見つからないので止める
      if (table[i] != null && s.equals(table[i].url)) {
        c = table[i];
        break;
      }
    }
    return c;
  }

  // Cellをハッシュテーブルに入れる
  void insertTable(String s, Cell c) {
    int hashValue = hash(s);
    boolean isInsert = false;

    // ハッシュ値より後ろの空いている場所を調べる
    for (int i = hashValue; i < table.length; i++) {
      if (table[i] == null) {
        table[i] = c;
        isInsert = true;
        break;
      }
    }
    // 後半に空いている場所が見つからなければ、先頭からハッシュ値までを調べる
    if (!isInsert) {
      for (int i = 0; i < hashValue; i++) {
        if (table[i] == null) {
          table[i] = c;
          break;
        }
      }
    }
    // ALEX_COMMENT:  not handling the case where the table is full 
    //                (rare, especially with limited dictionary, for sure)
  }

  // ハッシュテーブルからCellを削除
  void removeTable(String s) {
    int hashValue = hash(s);
    boolean isRemove = false;

    //ハッシュ値より後ろを調べる
    for (int i = hashValue; i < table.length; i++) {
      if (table[i] != null && s.equals(table[i].url)) {
        table[i] = null;
        isRemove = true;
      } else if (table[i] == null)
        break;
    }

    //後半から見つからなければ、先頭からハッシュ値までを調べる
    if (!isRemove) {
      for (int i = 0; i < hashValue; i++) {
        if (table[i] != null && s.equals(table[i].url)) {
          table[i] = null;
        } else if (table[i] == null)
          break;
      }
    }
  }

  // 表示
  public String toString() {
    StringBuilder sb = new StringBuilder();
    for (Cell p = head.next; p != head; p = p.next) {
      sb.append("[" + p.url + "," + p.page + "]");
    }
    return sb.toString();
  }
}
