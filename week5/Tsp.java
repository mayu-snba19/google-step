import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.*;

public class Tsp {

  ArrayList<City> cities = new ArrayList<City>();
  int fileId;

  public Tsp(int fileId) {
    this.fileId = fileId;
  }

  void run() {
    System.out.println("Start the File " + fileId + " program.");
    inputFile();
    solveGreedy();
    sortCities();
    outputFile();
    System.out.println("End the File " + fileId + " program.\n");
  }

  // File input
  void inputFile() {
    try {
      File file = new File("input_" + fileId + ".csv");
      // ALEX_COMMENT:  great use of BufferedReader!
      BufferedReader br = new BufferedReader(new FileReader(file));
      br.readLine();
      int index = 0;
      while (br.ready()) {
        String s[] = br.readLine().split(",");
        double x = Double.parseDouble(s[0]);
        double y = Double.parseDouble(s[1]);
        cities.add(new City(x, y, index));
        index++;
      }
      br.close();
    } catch (Exception e) {
      System.err.println("Failed to load the file.");
      System.exit(1);
    }
  }

  // Calculate the distance between two points.
  double distance(City c1, City c2) {
    return Math.sqrt(Math.pow(c1.x - c2.x, 2) + Math.pow(c1.y - c2.y, 2));
  }

  // Remove intersections.
  void sortCities() {
    boolean isSwap = true;
    while (isSwap) {
      isSwap = false;
      for (int i = 0; i < cities.size() - 1; i++) {
        for (int j = i + 1; j < cities.size() - 1; j++) {
          // ALEX_COMMENT:  it would be good to get documentation explaining
          //                why the logic below works.  This is a good place
          //                to demonstrate with diagrams.
          City a = cities.get(i);
          City b = cities.get(i + 1);
          City c = cities.get(j);
          City d = cities.get(j + 1);
          if (distance(a, c) + distance(b, d) < distance(a, b) + distance(c, d)) {
            for (int k = 0; k < (j - i) / 2; k++) {
              Collections.swap(cities, i + 1 + k, j - k);
            }
            isSwap = true;
          }
        }
      }
      // The only exception is the last part.
      for (int i = 0; i < cities.size() - 1; i++) {
        City a = cities.get(i);
        City b = cities.get(i + 1);
        City c = cities.get(cities.size() - 1);
        City d = cities.get(0);
        if (distance(a, c) + distance(b, d) < distance(a, b) + distance(c, d)) {
          for (int k = 0; k < (cities.size() - 1 - i) / 2; k++) {
            Collections.swap(cities, (i + 1 + k) % cities.size(), cities.size() - 1 - k);
          }
          isSwap = true;
        }
      }
    }
  }

  // The closest city will be the next destination.
  void solveGreedy() {
    ArrayList<City> route = new ArrayList<City>();
    ArrayList<City> unVisited = new ArrayList<City>(cities);
    City head = unVisited.remove(0);
    route.add(head);
    while (!unVisited.isEmpty()) {
      City a = route.get(route.size() - 1);
      City next = null;
      double minDistance = Double.MAX_VALUE; // ALEX_COMMENT: This is good.  But using the first element would be best
      for (City c : unVisited) {
        if (distance(a, c) < minDistance) {
          minDistance = distance(a, c);
          next = c;
        }
      }
      route.add(next);
      unVisited.remove(next);
    }
    cities = route;
  }

  // File output
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
