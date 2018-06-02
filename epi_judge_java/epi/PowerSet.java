package epi;

import epi.test_framework.EpiTest;
import epi.test_framework.EpiTestComparator;
import epi.test_framework.LexicographicalListComparator;
import epi.test_framework.GenericTestHandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.BiPredicate;

import static java.lang.Math.log;

public class PowerSet {
  @EpiTest(testfile = "power_set.tsv")

  public static List<List<Integer>> generatePowerSet(List<Integer> inputSet) {
    List<List<Integer>> powerSet = new ArrayList<>();
    double LOG2 = log(2.0);
    for (int i = 0; i < (1<<inputSet.size()); i++) {
      List<Integer> localResult = new ArrayList<>();
      int c = i;
      while (c!=0) {
        int posn = (int) (Math.log(c & ~(c-1))/LOG2);
        localResult.add(inputSet.get(posn));
        c &= (c-1);
      }
      powerSet.add(localResult);
    }
    return powerSet;
  }

  @EpiTestComparator
      public static BiPredicate < List<List<Integer>>,
      List < List<Integer>>> comp = (expected, result) -> {
    if (result == null) {
      return false;
    }
    for (List<Integer> l : expected) {
      Collections.sort(l);
    }
    expected.sort(new LexicographicalListComparator<>());
    for (List<Integer> l : result) {
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
