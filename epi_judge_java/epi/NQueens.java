package epi;

import epi.test_framework.EpiTest;
import epi.test_framework.EpiTestComparator;
import epi.test_framework.LexicographicalListComparator;
import epi.test_framework.GenericTestHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiPredicate;

public class NQueens {
  @EpiTest(testfile = "n_queens.tsv")

  public static List<List<Integer>> nQueens(int n) {

    List<List<Integer>> r = new ArrayList<>();

    SolveNQueens(n, 0, new ArrayList<>(), r);

    return r;
  }

  public static void SolveNQueens(int n, int row, List<Integer> colPlacement, List<List<Integer>> results) {
    if (row==n) { // done
      results.add(new ArrayList<Integer>(colPlacement));
      return;
    }

    for (int i = 0; i < n; i++) {
      colPlacement.add(i); // the colPlacement List holds a partial placement for trial
      if (isLatestPlacementValid(colPlacement)) {
        SolveNQueens(n, row+1, colPlacement, results);
      }
      colPlacement.remove(colPlacement.size()-1);
    }

    return;
  }


  //
  public static boolean isLatestPlacementValid(List<Integer> p) {
    int position = p.size()-1;
    for (int i = 0; i < position; i++) {
      if ((p.get(position)==p.get(i)) || Math.abs(position - i) == Math.abs(p.get(position) - p.get(i))) return false;
    }
    return true;
  }

  @EpiTestComparator
      public static BiPredicate < List<List<Integer>>,
      List < List<Integer>>> comp = (expected, result) -> {
    if (result == null) {
      return false;
    }
    expected.sort(new LexicographicalListComparator<>());
    result.sort(new LexicographicalListComparator<>());
    return expected.equals(result);
  };

  public static void main(String[] args) {
    GenericTestHandler.executeTestsByAnnotation(
        new Object() {}.getClass().getEnclosingClass(), args);
  }
}
