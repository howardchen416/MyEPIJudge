package epi;

import epi.test_framework.EpiTest;
import epi.test_framework.GenericTestHandler;

import java.util.List;

public class TwoSum {
  @EpiTest(testfile = "two_sum.tsv")

  public static boolean hasTwoSum(List<Integer> A, int t) {
    int l = 0;
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
