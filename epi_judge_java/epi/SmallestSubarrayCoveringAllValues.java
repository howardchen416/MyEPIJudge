package epi;

import epi.test_framework.EpiTest;
import epi.test_framework.GenericTestHandler;
import epi.test_framework.TestFailureException;
import epi.test_framework.TestTimer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SmallestSubarrayCoveringAllValues {

  public static class Subarray {
    // Represent subarray by starting and ending indices, inclusive.
    public Integer start;
    public Integer end;

    public Subarray(Integer start, Integer end) {
      this.start = start;
      this.end = end;
    }
  }

  public static Subarray
  findSmallestSequentiallyCoveringSubset(List<String> paragraph,
                                         List<String> keywords) {
    Subarray result = new Subarray(0, paragraph.size());
    Map<String, Integer> keywordToIdx = new HashMap<>(keywords.size());
    List<Integer> lastOccurence = new ArrayList<>(keywords.size());
    List<Integer> shortestDistanceCovering = new ArrayList<>();

    for (int i = 0; i < keywords.size(); i++) {
      keywordToIdx.put(keywords.get(i), i);
      lastOccurence.add(-1);
      shortestDistanceCovering.add(Integer.MAX_VALUE);
    }

    for (int i = 0; i < paragraph.size(); i++) {
      if (keywordToIdx.containsKey(paragraph.get(i))) {
        int idx = keywordToIdx.get(paragraph.get(i));
        // base condition
        if (idx==0) {
          shortestDistanceCovering.set(0, 1);
        }
        //else if (Integer.compare(shortestDistanceCovering.get(idx-1), Integer.MAX_VALUE)<0) {
        else if (Integer.compare(lastOccurence.get(idx-1), -1) > 0) {
          int distanceToLastKeyword = i - lastOccurence.get(idx-1);
          shortestDistanceCovering.set(idx, distanceToLastKeyword + shortestDistanceCovering.get(idx-1));
          if (idx==keywords.size()-1) {
            if (result.end - result.start + 1 > shortestDistanceCovering.get(idx)) {
              result.end = i;
              result.start = i - shortestDistanceCovering.get(idx) + 1;
            }
          }
        }
        lastOccurence.set(idx, i);
        // if last keyword
      }
    }
    return result;
  }

  @EpiTest(testfile = "subsequence_cover.tsv")
  public static int findSmallestSequentiallyCoveringSubsetWrapper(
      TestTimer timer, List<String> paragraph, List<String> keywords)
      throws TestFailureException {
    timer.start();
    Subarray result =
        findSmallestSequentiallyCoveringSubset(paragraph, keywords);
    timer.stop();

    int kwIdx = 0;
    if (result.start < 0) {
      throw new TestFailureException("Subarray start index is negative");
    }
    int paraIdx = result.start;

    while (kwIdx < keywords.size()) {
      if (paraIdx >= paragraph.size()) {
        throw new TestFailureException(
            "Not all keywords are in the generated subarray");
      }
      if (paraIdx >= paragraph.size()) {
        throw new TestFailureException("Subarray end index exceeds array size");
      }
      if (paragraph.get(paraIdx).equals(keywords.get(kwIdx))) {
        kwIdx++;
      }
      paraIdx++;
    }
    return result.end - result.start + 1;
  }

  public static void main(String[] args) {
    GenericTestHandler.executeTestsByAnnotation(
        new Object() {}.getClass().getEnclosingClass(), args);
  }
}
