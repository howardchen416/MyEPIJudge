package epi;

import epi.test_framework.EpiTest;
import epi.test_framework.GenericTestHandler;

public class Gcd {
  @EpiTest(testfile = "gcd.tsv")

  public static long GCD(long x, long y) {
    return (y==0) ? x : GCD(y, x%y);
  }

  public static void main(String[] args) {
    GenericTestHandler.executeTestsByAnnotation(
        new Object() {}.getClass().getEnclosingClass(), args);
  }
}
