package epi;

import epi.test_framework.EpiTest;
import epi.test_framework.GenericTestHandler;

import java.util.Arrays;
import java.util.List;
import java.util.NavigableSet;
import java.util.TreeSet;

public class MinimumDistance3SortedArrays {

  public static class ArrayData implements Comparable<ArrayData> {
    public int val;
    public int idx;

    public ArrayData(int idx, int val) {
      this.val = val;
      this.idx = idx;
    }

    @Override
    public int compareTo(ArrayData o) {
      int result = Integer.compare(val, o.val);
      if (result == 0) {
        result = Integer.compare(idx, o.idx);
      }
      return result;
    }
  }

  @EpiTest(testfile = "minimum_distance_3_sorted_arrays.tsv")

  public static int findMinDistanceSortedArrays(List<List<Integer>> sortedArrays) {
    int minDistance = Integer.MAX_VALUE;
    int[] headIndexes = new int[sortedArrays.size()];
    NavigableSet<ArrayData> currentHeads = new TreeSet<>();
    for (int i = 0; i < sortedArrays.size(); i++) {
      currentHeads.add(new ArrayData(i, sortedArrays.get(i).get(0)));
    }
    while (true) {
      // calculate latest minDistance
      minDistance = Math.min(minDistance, sortedArrays.get(currentHeads.last().idx).get(headIndexes[currentHeads.last().idx])
       - sortedArrays.get(currentHeads.first().idx).get(headIndexes[currentHeads.first().idx]));
      // pop smallest
      ArrayData smallest = currentHeads.pollFirst();
      if (headIndexes[smallest.idx] < sortedArrays.get(smallest.idx).size()-1) {
        headIndexes[smallest.idx]++;
        currentHeads.add(new ArrayData(smallest.idx, sortedArrays.get(smallest.idx).get(headIndexes[smallest.idx])));
      }
      else break;
    }
    return minDistance;
  }

  public static void main(String[] args) {
    GenericTestHandler.executeTestsByAnnotation(
        new Object() {}.getClass().getEnclosingClass(), args);
  }
}
