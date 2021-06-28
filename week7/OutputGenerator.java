
public class OutputGenerator {
  public static void main(String[] args) {
    int nodeNum[] = { 5, 8, 16, 64, 128, 512, 2048 };
    for (int i = 0; i < 7; i++) {
      Tsp tsp = new Tsp(i, nodeNum[i]);
      tsp.run();
    }
  }
}
