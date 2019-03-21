import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Objects;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

/**
 * tester
 */

public class Tester {
  public static void main(String[] args) {

    boolean quit = false;
    Scanner kb = new Scanner(System.in);

    while (!quit) {
      System.out.println("Enter a Selection: ");
      System.out.println("1) Solve a random puzzle");
      System.out.println("2) Input a puzzle to solve");
      System.out.println("3) Solve 1000 puzzles from puzzles.txt");
      System.out.println("4) run cases and output to CSV");
      System.out.println("5) quit");

      int choice = kb.nextInt();

      switch (choice) {
      case 1:
        solveOneRandom();
        break;
      case 2:
        solveFromInput(kb);
        break;
      case 3:
        solveFromFile();
        break;
      case 4:
        solveMany();
        break;
      case 5:
        quit = true;
        break;
      default:
        break;
      }
    }
  }

  public static void solveOneRandom() {
    int[] puzzle = Generator.random();
    System.out.println(Node.print_puzzle(puzzle));
    Solution solved = Solver.solve(puzzle);
    if(solved != null) {
      solved.print();
    }
  }

  public static void solveFromInput(Scanner kb) {

    // prompt for input
    boolean valid = false;
    while(!valid) {
      System.out.println("Enter puzzle:");
      String in = kb.nextLine();
      try {
        int[] puzzle = Generator.genPuzzle(in);
        Solution solved = Solver.solve(puzzle);
        if(solved != null) {
          solved.print();
        }
      } catch (Exception e) {
        System.out.println("bad input. try again.");
      }
    }
  }

  public static void solveFromFile() {
    String path = "puzzles.txt";
    try {
      File file = new File(path);
      Scanner in = new Scanner(file);

      int count = 1;

      while (in.hasNext()) {

        String line = in.nextLine();
        count++;

        int[] puzzle = Generator.genPuzzle(line);
        Solution solved = Solver.solve(puzzle);
        System.out.printf("depth: %4d, cost: %6d nodes, time: %dms %n", solved.getDepth(), solved.getCost(), solved.getRunTime());

      }
      in.close();
    } catch (FileNotFoundException e) {
      System.out.println("file " + path + " not found.");
      return;
    }
  }

  public static void solveMany() {
    int count = 1000;

    try (PrintWriter writer = new PrintWriter(new File("test_" + count + ".csv"))) {

      StringBuilder sb = new StringBuilder();
      writer.write(String.format("depth, cost, time%n"));
      for (int i = 0; i < count; i++) {
        int[] puzzle = Generator.random();
        Solution solved = Solver.solve(puzzle);
        if(solved == null) {
          count ++;
        } else {
          System.out.printf("%4d: depth: %4d, cost: %6d, time: %d %n", i, solved.getDepth(), solved.getCost(), solved.getRunTime());
          String out = String.format(
            "%d,%d,%d%n", 
            solved.getDepth(), 
            solved.getCost(),
            (int) solved.getRunTime()
            );
          writer.write(out);
        }
      }
      writer.close();

      System.out.println("done!");

    } catch (FileNotFoundException e) {
      System.out.println(e.getMessage());
    }
  }
}