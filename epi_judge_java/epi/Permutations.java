package epi;

import epi.test_framework.EpiTest;
import epi.test_framework.EpiTestComparator;
import epi.test_framework.LexicographicalListComparator;
import epi.test_framework.GenericTestHandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.BiPredicate;

public class Permutations {
  @EpiTest(testfile = "permutations.tsv")

  public static List<List<Integer>> permutations(List<Integer> A) {

    List<List<Integer>> r = new ArrayList<>();
    permuteForPositionN(A, 0, r);

    return r;
  }

  public static void permuteForPositionN(List<Integer> A, int n, List<List<Integer>> r) {
    if (n == A.size()-1) { // permutation is complete
      r.add(new ArrayList<Integer>(A));
      return;
    }
    // permute for pth position
    for (int i = n; i < A.size(); i++) {
      Collections.swap(A, i, n);
      permuteForPositionN(A, n+1, r);
      Collections.swap(A, i, n);
    }

    return;
  }



  @EpiTestComparator
      public static BiPredicate < List<List<Integer>>,
      List < List<Integer>>> comp = (expected, result) -> {
    if (result == null) {
      return false;
    }
    for (List<Integer> l : expected) {
      Collections.sort(l);
    }
    expected.sort(new LexicographicalListComparator<>());
    for (List<Integer> l : result) {
      Collections.sort(l);
    }
    result.sort(new LexicographicalListComparator<>());
    return expected.equals(result);
  };

  public static void main(String[] args) {
    GenericTestHandler.executeTestsByAnnotation(
        new Object() {}.getClass().getEnclosingClass(), args);
  }
}
