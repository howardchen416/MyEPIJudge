package epi;

import epi.test_framework.EpiTest;
import epi.test_framework.GenericTestHandler;

import java.util.Collections;
import java.util.List;

public class NextPermutation {
  @EpiTest(testfile = "next_permutation.tsv")
  public static List<Integer> nextPermutation(List<Integer> perm) {
    int k = perm.size() - 2;
    while (k>=0 && perm.get(k)>=perm.get(k+1)) {
      --k;
    }
    if (k==-1) { // no next permutation possible
      return Collections.emptyList();
    }
    for (int i = perm.size()-1; i > k ; i--) {
      if (perm.get(i)>perm.get(k)) {
        Collections.swap(perm, k, i);
        break;
      }
    }
    // reverse suffix
    Collections.reverse(perm.subList(k+1, perm.size()));
    return perm;
  }

  public static void main(String[] args) {
    GenericTestHandler.executeTestsByAnnotation(
        new Object() {}.getClass().getEnclosingClass(), args);
  }
}
