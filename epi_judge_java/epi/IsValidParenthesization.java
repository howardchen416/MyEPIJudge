package epi;

import epi.test_framework.EpiTest;
import epi.test_framework.GenericTestHandler;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

public class IsValidParenthesization {
  @EpiTest(testfile = "is_valid_parenthesization.tsv")

  public static boolean isWellFormed(String s) {
    Deque<Character> q = new LinkedList<>();
    for (int i = 0; i < s.length(); i++) {
      if ("([{".contains(s.substring(i, i+1))) {
        q.offerFirst(s.charAt(i));
      }
      else {
        if (q.isEmpty()) return false;
        char c = q.pollFirst();
        switch (s.charAt(i)) {
          case (')'):
            if (c != '(') return false;
            break;
          case ('}'):
            if (c != '{') return false;
            break;
          case (']'):
            if (c != '[') return false;
            break;
        }
      }
    }
    return q.isEmpty();
  }

  public static void main(String[] args) {
    GenericTestHandler.executeTestsByAnnotation(
        new Object() {}.getClass().getEnclosingClass(), args);
  }
}
