package epi;

import epi.test_framework.EpiTest;
import epi.test_framework.GenericTestHandler;

public class IntSquareRoot {
  @EpiTest(testfile = "int_square_root.tsv")

  public static int squareRoot(int k) {
    long left = 0;
    long right = k;
    if (k == 0 || k == 1) return k;
    while (left < right) {
      long m = left + ((right - left) / 2);
      long midSquared = m * m;
      if (midSquared <= k) {
        left = m + 1;
      } else if (midSquared > k) {
        right = m;
      }
      /*else {
        return (int) m;
      }*/
    }
    return (int) left-1;
  }

  public static void main(String[] args) {
    GenericTestHandler.executeTestsByAnnotation(
        new Object() {}.getClass().getEnclosingClass(), args);
  }
}
