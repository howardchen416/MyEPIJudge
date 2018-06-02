package epi;

import epi.test_framework.EpiTest;
import epi.test_framework.GenericTestHandler;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class LongestContainedInterval {
  @EpiTest(testfile = "longest_contained_interval.tsv")

  public static int longestContainedRange(List<Integer> A) {
    if (A==null || A.size()==0) return 0;
    if (A.size()==1) return 1;
    Set<Integer> s = new HashSet<>(A);
    int maxSetSize = 0;
    for (Integer i : A) {
      if (A.contains(i)) {
        int setSize = 1;
        s.remove(i);
        // expand on both sides of i
        int j = i;
        while (s.contains(j+=1)) {
          s.remove(j);
          setSize++;
        }
        int k = i;
        while (s.contains(k-=1)) {
          s.remove(k);
          setSize++;
        }
        maxSetSize = Math.max(maxSetSize, setSize);
      }
      // no need to do anything if the set does not contain current element, means a contained set with the element had been processed previously
    }

    return maxSetSize;
  }

  public static void main(String[] args) {
    GenericTestHandler.executeTestsByAnnotation(
        new Object() {}.getClass().getEnclosingClass(), args);
  }
}
