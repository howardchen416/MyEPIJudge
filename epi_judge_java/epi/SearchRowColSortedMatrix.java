package epi;

import epi.test_framework.EpiTest;
import epi.test_framework.GenericTestHandler;

import java.util.List;

public class SearchRowColSortedMatrix {
  @EpiTest(testfile = "search_row_col_sorted_matrix.tsv")

  public static boolean matrixSearch(List<List<Integer>> A, int x) {
    int row = 0;
    int col = A.get(0).size()-1;
    while (row<A.size() && col>=0) {
      if (A.get(row).get(col)==x) return true;
      else if (A.get(row).get(col)<x) row++;
      else col--;
    }
    return false;
  }

  public static void main(String[] args) {
    GenericTestHandler.executeTestsByAnnotation(
        new Object() {}.getClass().getEnclosingClass(), args);
  }
}
