/**
 * 与えられた文字列のAnagramを辞書ファイルから探して返すプログラム
 */

import java.util.*;

public class Problem1 {

  static int MAX = 84093;
  static String arr1[];
  static String arr2[];

  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);
    arr1= new String[MAX];
    arr2 = new String[MAX];

    for (int i = 0; i < MAX; i++) {
      arr1[i] = sc.next();
      arr2[i] = sc.next();
    }

    //検索対象
    String[]targets={"yamada","maeda","sato","suzuki","pokemon","anime","ant","sparrow","friend"};

    for(String target:targets){
      String ans=binarySearch(target);
      if(!ans.equals(target) && !ans.equals("")) System.out.println(target+" "+ans);
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
