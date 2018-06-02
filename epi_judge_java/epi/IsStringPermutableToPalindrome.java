package epi;

import epi.test_framework.EpiTest;
import epi.test_framework.GenericTestHandler;

import java.util.HashMap;
import java.util.Map;

public class IsStringPermutableToPalindrome {
  @EpiTest(testfile = "can_string_be_palindrome.tsv")

  public static boolean canFormPalindrome(String s) {
    Map<Character, Integer> m = new HashMap<>();

    char[] charAry = s.toCharArray();
    for (char c : charAry) {
      if (!m.containsKey(c)) {
        m.put(c, 0);
      }
      m.put(c, m.get(c)+1);
    }

    // all but one keys can have odd occurrences
    boolean seenOdd = false;
    for (Map.Entry<Character, Integer> e : m.entrySet()) {
      if (e!=null && e.getValue()%2==1) {
        if (seenOdd) return false;
        seenOdd = true;
      }
    }
    return true;
  }

  public static void main(String[] args) {
    GenericTestHandler.executeTestsByAnnotation(
        new Object() {}.getClass().getEnclosingClass(), args);
  }
}
