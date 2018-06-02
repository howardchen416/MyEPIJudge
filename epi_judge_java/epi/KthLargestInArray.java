package epi;

import epi.test_framework.EpiTest;
import epi.test_framework.GenericTestHandler;

import java.util.Collections;
import java.util.List;
import java.util.Random;

public class KthLargestInArray {
  // The numbering starts from one, i.e., if A = [3,1,-1,2] then
  // findKthLargest(A, 1) returns 3, findKthLargest(A, 2) returns 2,
  // findKthLargest(A, 3) returns 1, and findKthLargest(A, 4) returns -1.
  @EpiTest(testfile = "kth_largest_in_array.tsv")
  public static int findKthLargest(int k, List<Integer> A) {
    return findKthLargestHelper(k, A);
  }

  public static int findKthLargestHelper(int k, List<Integer> A) {
    Random rand = new Random();
    int left = 0, right = A.size()-1;
    while (left <= right) {
      int pivotIdx = rand.nextInt(right - left + 1) + left;
      int newPivotIndex = pivot(A, left, right, pivotIdx);

      if (newPivotIndex == k-1)
        return A.get(newPivotIndex);
      else if (newPivotIndex > k-1) {
        right = newPivotIndex-1;
      }
      else { // newPivotIndex < k-1
        left = newPivotIndex+1;
      }
    }
    return 0;
  }

  private static int pivot(List<Integer> A, int left, int right, int pivotIdx) {
    Integer pivotValue = A.get(pivotIdx);
    Collections.swap(A, pivotIdx, right);
    int writeIdx = left;
    for (int i = left; i < right; i++) {
      if (Integer.compare(A.get(i), pivotValue)>0) {
        Collections.swap(A, i, writeIdx++);
      }
    }
    // swap the pivot back
    Collections.swap(A, writeIdx, right);
    return writeIdx;
  }

  public static void main(String[] args) {
    GenericTestHandler.executeTestsByAnnotation(
        new Object() {}.getClass().getEnclosingClass(), args);
  }
}
