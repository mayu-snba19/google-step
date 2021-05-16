import java.util.Random;

public class Main {
  public static void main(String[] args) {
    List list = new List();
    Random rand=new Random();
    for(int i=0;i<20;i++){
      String pageStr="url"+String.valueOf(rand.nextInt(20));
      list.insert(pageStr,"page");
      System.out.println(list+" "+pageStr);
    }
  }
}
