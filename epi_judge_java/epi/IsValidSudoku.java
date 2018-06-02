package epi;

import epi.test_framework.EpiTest;
import epi.test_framework.GenericTestHandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class IsValidSudoku {
  @EpiTest(testfile = "is_valid_sudoku.tsv")

  // Check if a partially filled matrix has any conflicts.
  public static boolean isValidSudoku(List<List<Integer>> partialAssignment) {
    // cols
    for (int i = 0; i < partialAssignment.size(); i++) {
      if (hasDup(partialAssignment, i, i+1, 0, partialAssignment.size()))
        return false;
    }

    // rows
    for (int i = 0; i < partialAssignment.size(); i++) {
      if (hasDup(partialAssignment, 0, partialAssignment.size(), i, i+1))
        return false;
    }

    // regions
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        if (hasDup(partialAssignment, 3*i, 3*i+3, 3*j, 3*j+3))
          return false;
      }
    }

    return true;
  }

  public static boolean hasDup(List<List<Integer>> a, int sr, int er, int sc, int ec) {
    List<Boolean> l = new ArrayList<>(Collections.nCopies(a.size()+1, false));
    for (int i = sr; i <er ; i++) {
      for (int j = sc; j < ec; j++) {
        if (a.get(i).get(j)!=0 && l.get(a.get(i).get(j))==true) {
          return true;
        }
        else l.set(a.get(i).get(j), true);
      }
    }
    return false;
  }

  public static void main(String[] args) {
    GenericTestHandler.executeTestsByAnnotation(
        new Object() {}.getClass().getEnclosingClass(), args);
  }
}
