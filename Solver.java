import java.util.PriorityQueue;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Arrays;
import java.util.Iterator;

public class Solver {

  public static Solution solve(int[] puzzle) {
  
    Solution solution = new Solution();
    solution.startTiming();

    Node root = new Node(puzzle, null);

    if (root.getN() == 1) {
      System.out.println("unsolvable");
      return null;
    }

    PriorityQueue<Node> frontier = new PriorityQueue<Node>();
    HashSet<Node> explored = new HashSet<Node>();

    frontier.add(root);

    while (!frontier.isEmpty()) {
      Node curr = frontier.poll();
      // System.out.println(curr.getF());

      // slow down execution, for testing
      // try {
      //   Thread.sleep(100);
      // } catch(Exception e) {}

      if (isGoal(curr.puzzle)) {
        solution.stopTiming();
        solution.setNode(curr);
        return solution;
      }
      explored.add(curr);
      
      ArrayList<Node> children = curr.getChildren();
      
      for (Node child : children) {
        solution.increment();
        if (!explored.contains(child) && !frontier.contains(child)) {
          frontier.add(child);
        } else {
          Iterator<Node> iter = frontier.iterator();
          boolean stop = false;
          while (iter.hasNext() && !stop) {
            Node test = iter.next();
            if (Arrays.equals(child.puzzle, test.puzzle)) {
              if (child.getF() < test.getF()) {
                frontier.remove(test);
                frontier.add(child);
                stop = true;
              }
            }
          }
        }
      }
    }
    return null;
  }

  /**
   * Returns true if puzzle has been solved.
   */
  public static boolean isGoal(int[] puzzle) {
    for (int i = 0; i < puzzle.length; i++) {
      if (i != puzzle[i])
        return false;
    }
    return true;
  }
}