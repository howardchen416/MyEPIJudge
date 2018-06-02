package epi;

import epi.test_framework.EpiTest;
import epi.test_framework.GenericTestHandler;

import java.util.List;

public class SearchShiftedSortedArray {
  @EpiTest(testfile = "search_shifted_sorted_array.tsv")

  public static int searchSmallest(List<Integer> A) {
    // variation of binary search
    int start = 0, end = A.size()-1;
    while (start<=end) {

    }
    return 0;
  }

  public static void main(String[] args) {
    GenericTestHandler.executeTestsByAnnotation(
        new Object() {}.getClass().getEnclosingClass(), args);
  }
}
