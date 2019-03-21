import java.util.Random;

public class Generator {

  public static int[] random() {
    int[] arr = { 0, 1, 2, 3, 4, 5, 6, 7, 8 };

    shuffleArray(arr);

    return arr;
  }
  public static int[] randomSolvable() {
    while(true) {
      int[] arr = { 0, 1, 2, 3, 4, 5, 6, 7, 8 };
      Node node = new Node(arr);
      shuffleArray(arr);
      if(node.getN() == 0) return arr;
    }
  }

  public static int[] genPuzzle(String str) {
    
    int[] arr = new int[9];
    
    String[] strs = str.split(" ");

    for (int i=0; i<arr.length; i++) {
      arr[i] = Integer.valueOf(strs[i]);
    }
    return arr;
  }

  // fisher-yates shuffle
  private static void shuffleArray(int[] array) {
    int index, temp;
    Random random = new Random();
    for (int i = array.length - 1; i > 0; i--) {
      index = random.nextInt(i + 1);
      temp = array[index];
      array[index] = array[i];
      array[i] = temp;
    }
  }
}