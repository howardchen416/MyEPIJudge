package epi;

import epi.test_framework.EpiTest;
import epi.test_framework.GenericTestHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StringDecompositionsIntoDictionaryWords {
  @EpiTest(testfile = "string_decompositions_into_dictionary_words.tsv")

  public static List<Integer> findAllSubstrings(String s, List<String> words) {
    List<Integer> r = new ArrayList<>();

    // special cases
    if (s==null || s.length()<words.size()*words.get(0).length()) return r;

    // create and populate WordToFrequency map
    Map<String, Integer> m = new HashMap<>();
    for (String word : words) {
      if (!m.containsKey(word)) m.put(word, 0);
      m.put(word, m.get(word)+1);
    }

    // incremental test of substring starting from position i
    for (int i = 0; i < s.length()-words.size()*words.get(0).length()+1; i++) {
      if (isValidCase(s, words, i, m)) {
        r.add(i);
      }
    }

    return r;
  }

  public static boolean isValidCase(String str, List<String> words, int start, Map<String, Integer> m ) {
    Map<String, Integer> cStF = new HashMap<>();
    int wS = words.get(0).length();
    for (int i = 0; i < words.size(); i++) {
      String cW = str.substring(start+i*wS, start+(i+1)*wS);
      if (m.containsKey(cW)) {
        if (!cStF.containsKey(cW)) cStF.put(cW, 0);
        cStF.put(cW, cStF.get(cW)+1);
        if (cStF.get(cW)>m.get(cW)) return false; // str has too many occurrences of cW
      } else {
        return false;  // str has a sub word that is not found in words
      }
    }
    return true;
  }

  public static void main(String[] args) {
    GenericTestHandler.executeTestsByAnnotation(
        new Object() {}.getClass().getEnclosingClass(), args);
  }
}
