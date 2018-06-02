package epi;

import epi.test_framework.EpiTest;
import epi.test_framework.GenericTestHandler;
import epi.test_framework.TestFailureException;

public class RunLengthCompression {

  public static String decoding(String s) {
    StringBuffer sb = new StringBuffer();
    int c = 0;
    for (int i = 0; i < s.length(); i++) {
      if (Character.isDigit(s.charAt(i))) {
        c *= 10;
        c += (int) s.charAt(i) - '0';
      } else {
        char ch = s.charAt(i);
        while (c>0) {
          c--;
          sb.append(ch);
        }
      }
    }
    return sb.toString();
  }

  public static String encoding(String s) {
    StringBuffer sb = new StringBuffer();
    int c = 1;
    for (int i = 1; i <= s.length(); i++) {
      if (i==s.length() || s.charAt(i)!=s.charAt(i-1)) {
        sb.append(c);
        sb.append(s.charAt(i-1));
        c=1;
      }
      else {
        c++;
      }
    }
    return sb.toString();
  }

  @EpiTest(testfile = "run_length_compression.tsv")
  public static void rleTester(String encoded, String decoded)
      throws TestFailureException {
    if (!decoding(encoded).equals(decoded)) {
      throw new TestFailureException("Decoding failed");
    }
    if (!encoding(decoded).equals(encoded)) {
      throw new TestFailureException("Encoding failed");
    }
  }

  public static void main(String[] args) {
    GenericTestHandler.executeTestsByAnnotation(
        new Object() {}.getClass().getEnclosingClass(), args);
  }
}
