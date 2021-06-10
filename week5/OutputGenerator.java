
public class OutputGenerator {
  public static void main(String[]args){
    for(int i=0;i<=6;i++){
      Tsp tsp=new Tsp(i);
      tsp.run();
    }
  }
}
