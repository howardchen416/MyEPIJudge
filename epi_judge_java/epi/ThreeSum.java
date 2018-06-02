package epi;

import epi.test_framework.EpiTest;
import epi.test_framework.GenericTestHandler;

import java.util.Collections;
import java.util.List;

public class ThreeSum {
  @EpiTest(testfile = "three_sum.tsv")

  public static boolean hasThreeSum(List<Integer> A, int t) {
    Collections.sort(A);
    for (int i = 0; i < A.size(); i++) {
      if (hasTwoSumWithStartIdx(A, t-A.get(i), i+1))
        return true;
    }

    return false;
  }

  public static boolean hasTwoSumWithStartIdx(List<Integer> A, int t, int startIdx) {
    int l = startIdx;
    int r = A.size()-1;
    while (l<=r){
      if (Integer.compare(A.get(l)+A.get(r), t)==0) {
        return true;
      }
      else if (Integer.compare(A.get(l)+A.get(r), t)>0) {
        r--;
      }
      else {
        l++;
      }
    }
    return false;
  }

  public static void main(String[] args) {
    GenericTestHandler.executeTestsByAnnotation(
        new Object() {}.getClass().getEnclosingClass(), args);
  }
}
