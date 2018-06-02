package epi;

import epi.test_framework.EpiTest;
import epi.test_framework.GenericTestHandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CountInversions {
  @EpiTest(testfile = "count_inversions.tsv")

  public static int countInversions(List<Integer> A) {
    return countInversionsInSubarray(A, 0, A.size());
  }

  public static int countInversionsInSubarray(List<Integer> A, int start, int end) {
    int inversionCount = 0;
    // base condition
    if (start+1 >= end)
      return 0;

    int mid = start + ((end - start)/2);
    inversionCount += countInversionsInSubarray(A, start, mid);
    inversionCount += countInversionsInSubarray(A, mid, end);
    inversionCount += mergeSortedSubarraysAndCountInversions(A, start, mid, end);

    return inversionCount;
  }

  public static int mergeSortedSubarraysAndCountInversions(List<Integer> A, int start, int mid, int end) {
    List<Integer> sortedA = new ArrayList<>();
    int i = start;
    int j = mid;
    int inversionCount = 0;
    while (i < mid && j < end) {
      if (Integer.compare(A.get(i), A.get(j)) <= 0) {
        sortedA.add(A.get(i++));
      } else {
        inversionCount += (mid - i);
        sortedA.add(A.get(j++));
      }
    }
    while (i < mid) sortedA.add(A.get(i++));
    while (j < end) sortedA.add(A.get(j++));
    //sortedA.addAll(A.subList(i, mid));
    //sortedA.addAll(A.subList(j, end));

    for (Integer e : sortedA) {
      A.set(start++, e);
    }
    return inversionCount;
  }

  public static void main(String[] args) {
    GenericTestHandler.executeTestsByAnnotation(
        new Object() {}.getClass().getEnclosingClass(), args);
  }
}
