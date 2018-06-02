package epi;

import epi.test_framework.EpiTest;
import epi.test_framework.GenericTestHandler;

import java.util.List;

public class SearchFirstKey {
  @EpiTest(testfile = "search_first_key.tsv")

  public static int searchFirstOfK(List<Integer> A, int k) {
    int start = 0, end = A.size()-1;
    int result = -1;
    while (start<=end) {
      int mid = start + (end - start)/2;
      if (A.get(mid)>k) {
        end = mid-1;
      } else if(A.get(mid)==k) {
        result = mid;
        end = mid-1;
      } else {
        start = mid+1;
      }
    }
    return result;
  }

  public static void main(String[] args) {
    GenericTestHandler.executeTestsByAnnotation(
        new Object() {}.getClass().getEnclosingClass(), args);
  }
}
