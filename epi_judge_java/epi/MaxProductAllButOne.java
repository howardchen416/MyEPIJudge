package epi;

import epi.test_framework.EpiTest;
import epi.test_framework.GenericTestHandler;

import java.util.List;

public class MaxProductAllButOne {
  @EpiTest(testfile = "max_product_all_but_one.tsv")

  public static int findBiggestProductNMinusOneProduct(List<Integer> A) {
    int leastNegativeIdx = -1;
    int greatestNegativeIdx = -1;
    int leastNonNegativeIdx = -1;
    int numNegatives = 0;
    for (int i = 0; i < A.size(); i++) {
      if (A.get(i)<0) {
        numNegatives++;
        if (leastNegativeIdx==-1 || A.get(leastNegativeIdx) < A.get(i))
          leastNegativeIdx = i;
        if (greatestNegativeIdx==-1 || A.get(greatestNegativeIdx) < A.get(i))
          greatestNegativeIdx = i;
      }
      else {
        if (leastNonNegativeIdx==-1 || A.get(leastNonNegativeIdx) > A.get(i))
          leastNonNegativeIdx = i;
      }
    }

    //
    int skipIdx = (numNegatives%2==1) ? leastNegativeIdx : (leastNonNegativeIdx!=-1) ? leastNonNegativeIdx : greatestNegativeIdx;

    int product = 1;
    for (int i = 0; i < A.size(); i++) {
      if (i!=skipIdx) product *= A.get(i);
    }

    return product;
  }

  public static void main(String[] args) {
    GenericTestHandler.executeTestsByAnnotation(
        new Object() {}.getClass().getEnclosingClass(), args);
  }
}
