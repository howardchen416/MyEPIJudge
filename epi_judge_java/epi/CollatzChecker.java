package epi;

import epi.test_framework.EpiTest;
import epi.test_framework.GenericTestHandler;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class CollatzChecker {
  @EpiTest(testfile = "collatz_checker.tsv")

  public static boolean testCollatzConjecture(int n) {
    Set<Integer> verifiedNumbers = new HashSet<>();
    verifiedNumbers.add(1);
    verifiedNumbers.add(2);

    Set<Integer> sequence = new HashSet<>();
    for (int i = 3; i <= n; i += 2) {
      int j = i;
      sequence.clear();
      while (j >= i || j%2!=0) {
        if (sequence.contains(j)) return false; // loop found
        sequence.add(j);
        if (verifiedNumbers.contains(j)) break;
        j = nextNumber(j);
      }
      verifiedNumbers.addAll(sequence);
    }
    return true;
  }

  public static int nextNumber(int curr) {
    if (curr%2==0) return curr/2;
    else return curr*3+1;
  }

  public static void main(String[] args) {
    GenericTestHandler.executeTestsByAnnotation(
        new Object() {}.getClass().getEnclosingClass(), args);
  }
}
