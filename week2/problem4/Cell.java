public class Cell {
  Cell prev;
  Cell next;
  String url;
  String page;

  public Cell(String url, String page) {
    this.url = url;
    this.page = page;
  }
}
