package epi;

import epi.test_framework.EpiTest;
import epi.test_framework.GenericTestHandler;

import java.util.Collections;
import java.util.List;

public class ApplyPermutation {
  public static void applyPermutation(List<Integer> perm, List<Integer> A) {
    for (int i = 0; i < perm.size(); i++) {
      int next = i;
      while (perm.get(next)>=0) {
        int temp = perm.get(next);
        Collections.swap(A, i, perm.get(next));
        perm.set(next, perm.get(next) - perm.size());
        next = temp;
      }
    }

    // restore

    return;
  }

  @EpiTest(testfile = "apply_permutation.tsv")
  public static List<Integer> applyPermutationWrapper(List<Integer> perm,
                                                      List<Integer> A) {
    applyPermutation(perm, A);
    return A;
  }

  public static void main(String[] args) {
    GenericTestHandler.executeTestsByAnnotation(
        new Object() {}.getClass().getEnclosingClass(), args);
  }
}
