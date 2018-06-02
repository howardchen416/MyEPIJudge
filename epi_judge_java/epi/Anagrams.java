package epi;

import epi.test_framework.EpiTest;
import epi.test_framework.EpiTestComparator;
import epi.test_framework.LexicographicalListComparator;
import epi.test_framework.GenericTestHandler;

import java.util.*;
import java.util.function.BiPredicate;

public class Anagrams {
  @EpiTest(testfile = "anagrams.tsv")

  public static List<List<String>> findAnagrams(List<String> dictionary) {
    Map<String, List<String>> m = new HashMap<>();
    for (String s : dictionary) {
      char[] charAry = s.toCharArray();
      Arrays.sort(charAry);
      String sortedKey = new String(charAry);
      if (!m.containsKey(sortedKey)) {
        m.put(sortedKey, new ArrayList<String>());
      }
      m.get(sortedKey).add(s);
    }

    // only need anagram sets greater than 1
    List<List<String>> r = new ArrayList<List<String>>();
    for (Map.Entry<String,List<String>> k : m.entrySet()) {
      if (k.getValue()!=null && k.getValue().size()>1) {
        r.add(k.getValue());
      }
    }

    return r;
  }

  @EpiTestComparator
      public static BiPredicate < List<List<String>>,
      List < List<String>>> comp = (expected, result) -> {
    if (result == null) {
      return false;
    }
    for (List<String> l : expected) {
      Collections.sort(l);
    }
    expected.sort(new LexicographicalListComparator<>());
    for (List<String> l : result) {
      Collections.sort(l);
    }
    result.sort(new LexicographicalListComparator<>());
    return expected.equals(result);
  };

  public static void main(String[] args) {
    GenericTestHandler.executeTestsByAnnotation(
        new Object() {}.getClass().getEnclosingClass(), args);
  }
}
