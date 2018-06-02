package epi;

import epi.test_framework.EpiTest;
import epi.test_framework.GenericTestHandler;

import java.util.Arrays;

public class NumberOfTraversalsStaircase {
  @EpiTest(testfile = "number_of_traversals_staircase.tsv")

  public static int numberOfWaysToTop(int top, int maximumStep) {
    int[] m = new int[top+1];
    Arrays.fill(m, -1);

    return numberOfWaysToTopHelper(top, maximumStep, m);

  }

  public static int numberOfWaysToTopHelper(int top, int maximumStep, int[] m) {
    //
    if (m[top]!=-1) return m[top];
    if (top <= 1) {
      m[top] = 1;
      return m[top];
    }

    int sum = 0;
    for (int i = 1; (i <= maximumStep) && (top - i >= 0); i++) {
      sum += numberOfWaysToTopHelper(top - i, maximumStep, m);
    }
    m[top] = sum;

    return m[top];
  }

  public static void main(String[] args) {
    GenericTestHandler.executeTestsByAnnotation(
        new Object() {}.getClass().getEnclosingClass(), args);
  }
}
