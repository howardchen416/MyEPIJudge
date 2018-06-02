package epi;

import epi.test_framework.EpiTest;
import epi.test_framework.GenericTestHandler;

public class SubstringMatch {
  @EpiTest(testfile = "substring_match.tsv")

  // Returns the index of the first character of the substring if found, -1
  // otherwise.
  public static int rabinKarp(String t, String s) {
    int BASE = 26;
    int powerS = 1;
    int sH = 0;
    int tH = 0;

    if (t.length()<s.length()) return -1;

    for (int i = 0; i < s.length(); i++) {
      powerS = (i>0) ? powerS*BASE : 1;
      sH = sH * BASE + s.charAt(i);
      tH = tH * BASE + t.charAt(i);
    }

    for (int i = s.length(); i < t.length(); i++) {
      if (sH==tH && t.substring(i - s.length(), i).equals(s)) {
        return i - s.length();
      }
      else {
        tH -= t.charAt(i - s.length()) * powerS;
        tH = tH*BASE + t.charAt(i);
      }
    }

    if (sH==tH && t.substring(t.length()-s.length()).equals(s)) {
      return t.length()-s.length();
    }

    return -1;
  }

  public static void main(String[] args) {
    GenericTestHandler.executeTestsByAnnotation(
        new Object() {}.getClass().getEnclosingClass(), args);
  }
}
