package epi;

import epi.test_framework.EpiTest;
import epi.test_framework.GenericTestHandler;

import java.util.*;

public class LongestSubarrayWithDistinctValues {
  @EpiTest(testfile = "longest_subarray_with_distinct_values.tsv")

  public static int longestSubarrayWithDistinctEntries(List<Integer> A) {
    Map<Integer, Integer> map = new HashMap<>();
    int longestSubarrayStartingIdx = 0;
    int r = 0;

    for (int i = 0; i < A.size(); i++) {
      Integer v = map.put(A.get(i), i);

      if (v!=null) {
        if (v>=longestSubarrayStartingIdx) {
          r = Math.max(r, i - longestSubarrayStartingIdx);
          longestSubarrayStartingIdx = v + 1;
        }
      }
    }
    r = Math.max(r, A.size() - longestSubarrayStartingIdx);
    return r;
/*    Map<Integer, Integer> m = new HashMap<>();
    if (A==null || A.size()==0) return 0;
    if (A.size()==1) return 1;
    int res = 0;
    for (int l = 0, r = 0; r < A.size(); r++) {
      if (m.containsKey(A.get(r))) { // Math.max dictates whether to advance l
        l=Math.max(m.get(A.get(r))+1, l);
      }
      res = Math.max(res, r-l+1);  // res needs to be re-calculates every time
      m.put(A.get(r), r);  // always update the map with each step
    }
    return res;*/
  }

  public static void main(String[] args) {
    GenericTestHandler.executeTestsByAnnotation(
        new Object() {}.getClass().getEnclosingClass(), args);
  }
}
