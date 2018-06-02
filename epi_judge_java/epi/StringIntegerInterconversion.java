package epi;

import epi.test_framework.EpiTest;
import epi.test_framework.GenericTestHandler;
import epi.test_framework.TestFailureException;

public class StringIntegerInterconversion {

  public static String intToString(int x) {
    boolean isNegative = false;
    int largestInt = Integer.MAX_VALUE;
    if (x<0) {
      isNegative = true;
    }
    StringBuilder sb = new StringBuilder();
    do {
      sb.append((char) ('0' + Math.abs(x % 10)));
      x /= 10;
    } while (x!=0);
    if (isNegative) sb.append('-');
    sb.reverse();
    return sb.toString();
  }

  public static int stringToInt(String s) {
    int idx = 0;
    int result = 0;
    boolean isNegative = false;
    if (s.charAt(0)=='-') {
      isNegative = true;
      idx++;
    }
    while (idx<s.length()) {
      result = result * 10 + (int) (s.charAt(idx++) - '0');
    }
    if (isNegative) result *= -1;
    return result;
  }

  @EpiTest(testfile = "string_integer_interconversion.tsv")
  public static void wrapper(int x, String s) throws TestFailureException {
    if (!intToString(x).equals(s)) {
      throw new TestFailureException("Int to string conversion failed");
    }
    if (stringToInt(s) != x) {
      throw new TestFailureException("String to int conversion failed");
    }
  }

  public static void main(String[] args) {
    GenericTestHandler.executeTestsByAnnotation(
        new Object() {}.getClass().getEnclosingClass(), args);
  }
}
