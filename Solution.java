public class Solution {
  Node node;
  private int generatedNodes = 0;
  private int depth = 0;

  private long startTime;
  private long endTime;

  public Solution() {}

  public void startTiming() {
    this.startTime = System.currentTimeMillis();
  }

  public void stopTiming() {
    this.endTime = System.currentTimeMillis();
  }

  public long getRunTime() {
    return this.endTime - this.startTime;
  }

  public void increment() {
    this.generatedNodes++;
  }
  public int getCost() {
    return this.generatedNodes;
  }

  public void setNode(Node node) {
    this.node = node;
    this.depth = node.getG();
  }
  public int getDepth() {
    return this.depth;
  }
  public void print() {
    print_parent(node);
    System.out.printf("depth: %4d, cost: %6d nodes, time: %dms %n", getDepth(), getCost(), getRunTime());
  }


  // def print_parent(node):
  // if(node is not None):
  //   print_parent(node.parent)
  //   print(node)

  private void print_parent(Node node) {
    if(node != null) {
      print_parent(node.parent);
      System.out.println(node);
    }
  }
}