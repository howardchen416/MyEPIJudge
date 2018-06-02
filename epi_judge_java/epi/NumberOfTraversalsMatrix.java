package epi;

import epi.test_framework.EpiTest;
import epi.test_framework.GenericTestHandler;

import java.util.Arrays;

public class NumberOfTraversalsMatrix {
  @EpiTest(testfile = "number_of_traversals_matrix.tsv")

  public static int numberOfWays(int n, int m) {
    // Implement this placeholder.
    int[][] ways = new int[n][m];
    return calculate(n-1, m-1, ways);

  }

  public static int calculate(int i, int j, int[][] ways) {
    // base condition(s)
    if (i==0 || j==0) return 1;
    if (ways[i][j]==0) {  // new entry
      ways[i][j] = calculate(i-1, j, ways) + calculate(i, j-1, ways);
    }
    return ways[i][j];
  }

  public static void main(String[] args) {
    GenericTestHandler.executeTestsByAnnotation(
        new Object() {}.getClass().getEnclosingClass(), args);
  }
}
