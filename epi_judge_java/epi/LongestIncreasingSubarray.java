package epi;

import epi.test_framework.EpiTest;
import epi.test_framework.GenericTestHandler;

import java.util.List;

public class LongestIncreasingSubarray {

  // Represent subarray by starting and ending indices, inclusive.
  private static class Subarray {
    public Integer start;
    public Integer end;

    public Subarray(Integer start, Integer end) {
      this.start = start;
      this.end = end;
    }
  }

  public static Subarray findLongestIncreasingSubarray(List<Integer> A) {
    Subarray result = null;
    int start = 0;
    int maxLength = 0;
    for (int i = 1; i <= A.size(); i++) {
      if (i==A.size() || Integer.compare(A.get(i), A.get(i-1))<=0) {
        if (maxLength < i - start) {
          maxLength = i - start;
          result = new Subarray(start, i-1);
        }
        start = i;
      }
    }
    return result;
  }

  @EpiTest(testfile = "longest_increasing_subarray.tsv")
  public static int findLongestIncreasingSubarrayWrapper(List<Integer> A) {
    Subarray result = findLongestIncreasingSubarray(A);
    return result.end - result.start + 1;
  }

  public static void main(String[] args) {
    GenericTestHandler.executeTestsByAnnotation(
        new Object() {}.getClass().getEnclosingClass(), args);
  }
}
