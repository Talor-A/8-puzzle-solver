import java.util.ArrayList;
import java.util.Arrays;

public class Node implements Comparable<Node> {
  Node parent;

  int[] puzzle;

  int h;
  int g;

  public Node(int[] puzzle) {
    this(puzzle, null);
  }
  public Node(int[] puzzle, Node parent) {
    this.puzzle = puzzle;
    this.parent = parent;
    this.h = getH();
    // this.h = manhattanDistance();
    // this.n = getN();

    if (this.parent == null) {
      this.g = 0;
    } else {
      this.g = this.parent.g + 1;
    }
  }

  public int getF() {
    return h+g;
  }

  /**
   * Heuristic 1:
   * h = the number of misplaced tiles in the puzzle
   */
  public int getH() {
    int h = 0;
    for(int i = 0; i < puzzle.length; i++) {
      int current = puzzle[i];

      if(current != 0 && current != i) {
        h++;
      }
    }
    return h;
  }
  /**
   * Use these formulas:
   * 
   * d = destination index
   * c = current tile's index
   * dist = x + y
   * 
   * x = |d%3 - c%3|
   * y = | ((d - d%3) - (c - c%3)) / 3 |
   */
  public int manhattanDistance() {
    int total = 0;
    for (int curr = 0; curr < puzzle.length; curr++) {
      int dest = puzzle[curr];
      if (dest != 0) { // do not count distance of blank tile
        int x = Math.abs((dest % 3) - (curr %3));
        int y = Math.abs(((dest - dest % 3) - (curr - curr % 3)) / 3);

        total += (x+y);
      }
    }

    return total;
  }
  public int getG() {
    return this.g;
  }
  public int getN() {
    int inversions = 0;

    // if the tile directly preceding us is greater than us, increment.
    for(int i=0; i < puzzle.length; i++) {
      for(int j = i+1; j < puzzle.length; j++) {
        int curr = puzzle[i];
        int test = puzzle[j];

        if(curr != 0 && test != 0 && curr > test) {
          inversions++;
        }
      }
    }
    // System.out.println(inversions);
    return inversions % 2; 
  }

  @Override
  public int compareTo(Node n) {
    return Integer.valueOf(getF()).compareTo(Integer.valueOf(n.getF()));
  }


  public ArrayList<Node> getChildren() {
    ArrayList<Node> children = new ArrayList<>();

    // find the position of the blank tile, which we will move other tiles into
    int zeroPos = indexOf(0);

    // These are all the possible neighbor indices of a blank tile.
    // They may be negative, and will be filtered out below.
    int[] neighborPositions = getNeighborPos(zeroPos);

    for (int neighborPos : neighborPositions) {
      if (neighborPos >= 0 && neighborPos < 9) {
        int[] childPuzzle = Node.moveTile(puzzle, zeroPos, neighborPos);
        children.add(new Node(childPuzzle, this));
        // System.out.println("added child");
      }
    }

    return children;
  }
  public static int[] moveTile(int[] puzzle, int i, int j) {
    int[] arr = puzzle.clone();
    int tmp = arr[i];

    arr[i] = arr[j];
    arr[j] = tmp;

    return arr;
  }
  public int indexOf(int digit) {
    for(int i=0; i < puzzle.length; i++) {
      if(puzzle[i] == digit ) return i;
    }
    throw new Error(" digit not found in indexOf method");
  }

  @Override
  public String toString() {
    // String out = "%n";
    // for (int i = 0; i < puzzle.length; i++) {

    //   out = out + puzzle[i] + " ";
    //   if (i == 2 || i == 5)
    //     out += "%n";
    // }
    // out += "%nN: " + getN() + ", H: " + getH() + ", G: " + getG();


    // return String.format(out);
    return Node.print_puzzle(puzzle);
  }
  @Override
  public int hashCode() {
    return Arrays.hashCode(puzzle);
  }
  // Overriding equals() to compare two Complex objects 
  @Override
  public boolean equals(Object o) { 

      // If the object is compared with itself then return true   
      if (o == this) { 
          return true; 
      } 

      /* Check if o is an instance of Complex or not 
        "null instanceof [type]" also returns false */
      if (!(o instanceof Node)){ 
          return false; 
      } 
        
      // typecast o to Complex so that we can compare data members  
      Node other = (Node) o; 
        
      // Compare the data members and return accordingly  
      return Arrays.equals(other.puzzle, puzzle);
  } 
  public static String print_puzzle(int[] puzzle) {
    String out = "[ ";
    for (int i : puzzle) {
      out = out + i + " ";
    }
    out += "]";
    return out;
  }
  public int[] getNeighborPos(int zeroPos) {
    // 0 1 2
    // 3 4 5
    // 6 7 8
    switch (zeroPos) {
      case 0: return new int[]{ 1, 3 };
      case 1: return new int[]{ 0, 2, 4};
      case 2: return new int[]{ 1, 5 };
      case 3: return new int[]{ 0, 4, 6 };
      case 4: return new int[]{ 1, 3, 5, 7};
      case 5: return new int[]{ 2, 4, 8 };
      case 6: return new int[]{ 3, 7 };
      case 7: return new int[]{ 6, 4, 8 };
      case 8: return new int[]{ 7, 5 };
      default: return new int[]{};
    }
  }
}