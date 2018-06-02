package epi;

import epi.test_framework.EpiTest;
import epi.test_framework.GenericTestHandler;

import java.util.HashMap;
import java.util.Map;

public class IsAnonymousLetterConstructible {
  @EpiTest(testfile = "is_anonymous_letter_constructible.tsv")

  public static boolean isLetterConstructibleFromMagazine(String letterText,
                                                          String magazineText) {
    Map<Character, Integer> charMap = new HashMap<>();
    // magazine round
    char[] m = magazineText.toCharArray();
    for (char c : m) {
      if (!charMap.containsKey(c)) charMap.put(c, 0);
      charMap.put(c, charMap.get(c)+1);
    }

    // letter round
    char[] l = letterText.toCharArray();
    for (char c : l) {
      if (!charMap.containsKey(c)) return false;
      if (charMap.get(c)==0) return false;
      charMap.put(c, charMap.get(c)-1);
    }

    return true;
  }

  public static void main(String[] args) {
    GenericTestHandler.executeTestsByAnnotation(
        new Object() {}.getClass().getEnclosingClass(), args);
  }
}
