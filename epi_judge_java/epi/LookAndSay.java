package epi;

import epi.test_framework.EpiTest;
import epi.test_framework.GenericTestHandler;

public class LookAndSay {
  @EpiTest(testfile = "look_and_say.tsv")

  public static String lookAndSay(int n) {
    String s = "1";
    for (int i = 1; i < n; i++) {
      s = nextStr(s);
    }
    return s;
  }

  public static String nextStr(String s) {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < s.length(); i++) {
      int cnt = 1;
      while (i + 1 < s.length() && s.charAt(i) == s.charAt(i + 1)) {
        cnt++;
        i++;
      }
      sb.append(cnt);
      sb.append(s.charAt(i - 1));
    }
    return sb.toString();
  }

  public static void main(String[] args) {
    GenericTestHandler.executeTestsByAnnotation(
        new Object() {}.getClass().getEnclosingClass(), args);
  }
}
