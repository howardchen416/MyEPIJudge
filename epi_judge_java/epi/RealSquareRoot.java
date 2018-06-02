package epi;

import com.sun.xml.internal.bind.v2.model.core.ElementPropertyInfo;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTestHandler;

public class RealSquareRoot {
  @EpiTest(testfile = "real_square_root.tsv")

  public static double squareRoot(double x) {
    double left, right;
    if (x<1) {
      left=x;
      right=1;
    } else {
      left=1;
      right=x;
    }
    while (compare(left, right) == Ordering.SMALLER) {
      double mid = left + (right - left) / 2;
      double midSquared = mid * mid;
      if (compare(midSquared, x) == Ordering.LARGER) right = mid;
      else if (compare(midSquared, x) == Ordering.SMALLER) left = mid;
      else return mid;
    }
    return left;
  }

  private static enum Ordering { SMALLER, EQUAL, LARGER }

  private static Ordering compare(double d1, double d2) {
    double EPSILON = 0.00000000001;
    return ((d1-d2)/d2 < -EPSILON) ? Ordering.SMALLER : ((d1-d2)/d2 > EPSILON) ? Ordering.LARGER : Ordering.EQUAL;
  }

  public static void main(String[] args) {
    GenericTestHandler.executeTestsByAnnotation(
        new Object() {}.getClass().getEnclosingClass(), args);
  }
}
