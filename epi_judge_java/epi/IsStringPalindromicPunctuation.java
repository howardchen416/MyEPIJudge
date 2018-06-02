package epi;

import epi.test_framework.EpiTest;
import epi.test_framework.GenericTestHandler;

public class IsStringPalindromicPunctuation {
  @EpiTest(testfile = "is_string_palindromic_punctuation.tsv")

  public static boolean isPalindrome(String s) {
    int i = 0;
    int j = s.length()-1;

    while (i<j) {
      while (!Character.isLetterOrDigit(s.charAt(i)) && i<j) i++;
      while (!Character.isLetterOrDigit(s.charAt(j)) && i<j) j--;
      if (Character.toLowerCase(s.charAt(i))!=Character.toLowerCase(s.charAt(j)))
        return false;
      i++; j--;
    }
    return true;
  }

  public static void main(String[] args) {
    GenericTestHandler.executeTestsByAnnotation(
        new Object() {}.getClass().getEnclosingClass(), args);
  }
}
