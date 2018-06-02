package epi;

import epi.test_framework.EpiTest;
import epi.test_framework.GenericTestHandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MaximumSubarrayInCircularArray {
  @EpiTest(testfile = "maximum_subarray_in_circular_array.tsv")

  public static int maxSubarraySumInCircular(List<Integer> A) {
    return Math.max(maxSubarraySumNonCircular(A), maxSubarraySumCircular(A));
  }

  public static int maxSubarraySumNonCircular(List<Integer> A) {
    int maxSoFar = 0;
    int maxSum = 0;
    for (int i = 0; i < A.size(); i++) {
      maxSoFar = Math.max(A.get(i), A.get(i) + maxSoFar);
      maxSum = Math.max(maxSum, maxSoFar);
    }
    return maxSum;
  }

  public static int maxSubarraySumCircular(List<Integer> A) {
    List<Integer> maxPrefixSum = new ArrayList<>();
    maxPrefixSum.add(A.get(0));
    int prefixSum = A.get(0);
    for (int i = 1; i < A.size(); i++) {
      prefixSum += A.get(i);
      maxPrefixSum.add(Math.max(maxPrefixSum.get(maxPrefixSum.size()-1), prefixSum));
    }
    List<Integer> maxPostfixSum = new ArrayList<>(Collections.nCopies(A.size(), 0));
    int postfixSum = 0;
    for (int i = A.size()-2; i >= 0 ; i--) {
      postfixSum += A.get(i+1);
      maxPostfixSum.set(i, Math.max(maxPostfixSum.get(i+1), postfixSum));
    }
    int maxSum = Integer.MIN_VALUE;
    for (int i = 0; i < A.size(); i++) {
      maxSum = Math.max(maxSum, maxPrefixSum.get(i) + maxPostfixSum.get(i));
    }
    return maxSum;
  }

  public static void main(String[] args) {
    GenericTestHandler.executeTestsByAnnotation(
        new Object() {}.getClass().getEnclosingClass(), args);
  }
}
