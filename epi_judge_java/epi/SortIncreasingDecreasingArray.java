package epi;

import epi.test_framework.EpiTest;
import epi.test_framework.GenericTestHandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SortIncreasingDecreasingArray {
  @EpiTest(testfile = "sort_increasing_decreasing_array.tsv")

  public static List<Integer> sortKIncreasingDecreasingArray(List<Integer> A) {
    List<List<Integer>> arrays = new ArrayList<>();
    boolean increasing = true;
    int startIdx = 0;
    for (int i = 1; i <= A.size() ; i++) {
      if (i==A.size() || // finished last nmber
              (increasing && A.get(i)<=A.get(i-1)) ||
              (!increasing && A.get(i-1)<=A.get(i))) {
        List<Integer> sa = A.subList(startIdx, i);
        if (!increasing) Collections.reverse(sa);
        arrays.add(sa);
        // flip increasing
        increasing ^= true;
        startIdx=i;
      }
    }
    return SortedArraysMerge.mergeSortedArrays(arrays);
  }

  public static void main(String[] args) {
    GenericTestHandler.executeTestsByAnnotation(
        new Object() {}.getClass().getEnclosingClass(), args);
  }
}
