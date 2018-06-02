package epi;

import epi.test_framework.EpiTest;
import epi.test_framework.GenericTestHandler;

import java.util.Arrays;

public class BinomialCoefficients {
  @EpiTest(testfile = "binomial_coefficients.tsv")

  public static int computeBinomialCoefficient(int n, int k) {

    int[][] coef = new int[n+1][n+1];
    for (int i = 0; i <= n; i++) {
      int[] row = coef[i];
      Arrays.fill(row, -1);
    }

    return computeBinomialCoefficientHelper(n, k, coef);
  }

  private static int computeBinomialCoefficientHelper(int n, int k, int[][] coef) {
    // base condition(s)
    if (k==0 || n==k) return 1;
    //if ()
    if (coef[n][k]!=-1) return coef[n][k];

    coef[n][k] = computeBinomialCoefficientHelper(n - 1, k, coef) + computeBinomialCoefficientHelper(n - 1, k - 1, coef);

    return coef[n][k];

  }

  public static void main(String[] args) {
    GenericTestHandler.executeTestsByAnnotation(
        new Object() {}.getClass().getEnclosingClass(), args);
  }
}
