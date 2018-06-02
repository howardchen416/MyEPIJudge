package epi;

import epi.test_framework.EpiTest;
import epi.test_framework.GenericTestHandler;

public class PrimitiveDivide {
  @EpiTest(testfile = "primitive_divide.tsv")
  public static int divide(int x, int y) {
    long xl = (long) x;
    long yl = (long) y;
    int power = 32;
    long yPower = y << power;
    int result = 0;
    while (x >= y) {
      while (yPower>x) {
        yPower>>>=1;
        power--;
      }
      if (power>=0) {
        x -= yPower;
        result += 1<<power;
      }
    }

    return result;
  }

  public static void main(String[] args) {
    GenericTestHandler.executeTestsByAnnotation(
        new Object() {}.getClass().getEnclosingClass(), args);
  }
}
