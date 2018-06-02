package epi;

import epi.test_framework.EpiTest;
import epi.test_framework.EpiTestComparator;
import epi.test_framework.LexicographicalListComparator;
import epi.test_framework.GenericTestHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiPredicate;

public class EnumeratePalindromicDecompositions {
  @EpiTest(testfile = "enumerate_palindromic_decompositions.tsv")

  public static List<List<String>> palindromeDecompositions(String input) {
    List<List<String>> r = new ArrayList<>();

    directedPalindromeDecompositions(input, 0, new ArrayList<String>(), r);

    return r;
  }

  public static void directedPalindromeDecompositions(String input, int offset, List<String> curr, List<List<String>> r) {
    //base condition
    if (offset==input.length()) {
      r.add(new ArrayList<String>(curr));
      return;
    }
    //
    for (int i = offset+1; i <= input.length(); i++) {
      String prefix = input.substring(offset, i);
      if (isPalindrome(prefix)) {
        curr.add(prefix);
        directedPalindromeDecompositions(input, i, curr, r);
        curr.remove(curr.size()-1);
      }
    }

    return;
  }

  public static boolean isPalindrome(String str) {
    for (int i = 0, j = str.length()-1; i < j; i++, j--) {
      if (str.charAt(i)!=str.charAt(j)) return false;
    }
    return true;
  }

  @EpiTestComparator
      public static BiPredicate < List<List<String>>,
      List < List<String>>> comp = (expected, result) -> {
    if (result == null) {
      return false;
    }
    expected.sort(new LexicographicalListComparator<>());
    result.sort(new LexicographicalListComparator<>());
    return expected.equals(result);
  };

  public static void main(String[] args) {
    GenericTestHandler.executeTestsByAnnotation(
        new Object() {}.getClass().getEnclosingClass(), args);
  }
}
