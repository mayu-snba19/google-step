import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.Random;

public class Tsp {
  int fileId;
  int nodeNum;
  City cities[];
  Random random;
  double endTime = 50000;

  public Tsp(int fileId, int nodeNum) {
    this.fileId = fileId;
    this.nodeNum = nodeNum;
    cities = new City[nodeNum];
    random = new Random();
  }

  void run() {
    inputFile();
    solveGreedy(); // 貪欲法
    sortCities(); // 交差を無くす
    sa(); // 焼きなまし法
    sortCities(); // 交差を無くす
    outputFile();
    System.out.println("score : " + calcAllCost());
  }

  void setEndTime(double endTime) {
    this.endTime = endTime;
  }

  void inputFile() {
    try {
      File file = new File("input_" + fileId + ".csv");
      BufferedReader br = new BufferedReader(new FileReader(file));
      br.readLine();
      for (int index = 0; index < nodeNum; index++) {
        String s[] = br.readLine().split(",");
        double x = Double.parseDouble(s[0]);
        double y = Double.parseDouble(s[1]);
        cities[index] = new City(x, y, index);
      }
      br.close();
    } catch (Exception e) {
      System.err.println("Failed to load the file.");
      System.exit(1);
    }
  }

  double distance(City c1, City c2) {
    return Math.sqrt(Math.pow(c1.x - c2.x, 2) + Math.pow(c1.y - c2.y, 2));
  }

  void sortCities() {
    boolean isSwap = true;
    while (isSwap) {
      isSwap = false;
      for (int i = 0; i < nodeNum - 1; i++) {
        for (int j = i + 1; j < nodeNum - 1; j++) {
          City a = cities[i];
          City b = cities[i + 1];
          City c = cities[j];
          City d = cities[j + 1];
          double cost = calcCost(a, b, c, d);
          if (cost > 0.01) {
            for (int k = 0; k < (j - i) / 2; k++) {
              City tmp = cities[j - k];
              cities[j - k] = cities[i + 1 + k];
              cities[i + 1 + k] = tmp;
            }
            isSwap = true;
          }
        }
      }

      for (int i = 0; i < nodeNum - 1; i++) {
        City a = cities[i];
        City b = cities[i + 1];
        City c = cities[nodeNum - 1];
        City d = cities[0];
        double cost = calcCost(a, b, c, d);
        if (cost > 0.01) {
          for (int k = 0; k < (nodeNum - 1 - i) / 2; k++) {
            City tmp = cities[(i + 1 + k) % nodeNum];
            cities[(i + 1 + k) % nodeNum] = cities[nodeNum - 1 - k];
            cities[nodeNum - 1 - k] = tmp;
          }
          isSwap = true;
        }
      }
    }
  }

  double calcCost(City a, City b, City c, City d) {
    return distance(a, b) + distance(c, d) - distance(a, c) - distance(b, d);
  }

  // 現在の位置から最も近い頂点を次の行き先にする。
  void solveGreedy() {
    City route[] = new City[nodeNum];
    ArrayList<City> unVisited = new ArrayList<City>();
    for (int i = 0; i < nodeNum; i++) {
      unVisited.add(cities[i]);
    }
    City head = unVisited.remove(0);
    route[0] = head;
    for (int i = 1; i < nodeNum; i++) {
      City a = route[i - 1];
      City next = null;
      double minDistance = Double.MAX_VALUE;
      for (City c : unVisited) {
        if (distance(a, c) < minDistance) {
          minDistance = distance(a, c);
          next = c;
        }
      }
      route[i] = next;
      unVisited.remove(next);
    }
    cities = route;
  }

  // 焼きなまし法
  void sa() {
    for (int t = 1; t <= endTime; t++) {
      for (int cnt = 0; cnt < nodeNum; cnt++) {
        int i = random.nextInt(nodeNum - 1);
        int j = random.nextInt(nodeNum - 1);
        City a = cities[i];
        City b = cities[i + 1];
        City c = cities[j];
        City d = cities[j + 1];
        double cost = calcCost(a, b, c, d);
        double forceSwap = (endTime - t) / endTime;
        if (cost > 0.01 || forceSwap > random.nextDouble()) {
          for (int k = 0; k < (j - i) / 2; k++) {
            City tmp = cities[j - k];
            cities[j - k] = cities[i + 1 + k];
            cities[i + 1 + k] = tmp;
          }
        }
      }
    }
  }

  // スタート地点に戻ってくるまでの、全てのノード間の合計値
  double calcAllCost() {
    double allCost = 0;
    for (int i = 0; i < nodeNum - 1; i++) {
      allCost += distance(cities[i], cities[i + 1]);
    }
    allCost += distance(cities[nodeNum - 1], cities[0]);
    return allCost;
  }

  void outputFile() {
    try {
      File file = new File("output_" + fileId + ".csv");
      BufferedWriter bw = new BufferedWriter(new FileWriter(file));
      bw.write("index");
      bw.newLine();
      for (City city : cities) {
        bw.write(String.valueOf(city.index));
        bw.newLine();
      }
      bw.close();
    } catch (Exception e) {
      System.err.println(e);
      System.exit(1);
    }
  }
}
