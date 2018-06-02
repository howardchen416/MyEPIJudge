package epi;

import epi.test_framework.EpiTest;
import epi.test_framework.GenericTestHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NearestRepeatedEntries {
  @EpiTest(testfile = "nearest_repeated_entries.tsv")

  public static int findNearestRepetition(List<String> paragraph) {
    Map<String, Integer> m = new HashMap<>();
    int minDist = Integer.MAX_VALUE;
    for (int i = 0; i < paragraph.size(); i++) {
      if (!m.containsKey(paragraph.get(i))) m.put(paragraph.get(i), i);
      else {
        minDist = Math.min(minDist, i-m.get(paragraph.get(i)));
        m.put(paragraph.get(i), i);
      }
    }
    if (minDist==Integer.MAX_VALUE) minDist=-1;
    return minDist;
  }

  public static void main(String[] args) {
    GenericTestHandler.executeTestsByAnnotation(
        new Object() {}.getClass().getEnclosingClass(), args);
  }
}
