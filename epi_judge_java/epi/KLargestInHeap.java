package epi;

import epi.test_framework.EpiTest;
import epi.test_framework.EpiTestComparator;
import epi.test_framework.GenericTestHandler;

import java.util.*;
import java.util.function.BiPredicate;

public class KLargestInHeap {

  private static class HeapEntry {
    int index;
    int value;

    public HeapEntry(int i, int v) {
      this.index = i;
      this.value = v;
    }
  }

  private static class HeapEntryComparator implements Comparator<HeapEntry> {
    @Override
    public int compare(HeapEntry o1, HeapEntry o2) {
      return Integer.compare(o2.value, o1.value);
    }

    public static final HeapEntryComparator s = new HeapEntryComparator();
  }

  @EpiTest(testfile = "k_largest_in_heap.tsv")

  public static List<Integer> kLargestInBinaryHeap(List<Integer> A, int k) {

    List<Integer> r = new ArrayList<>();
    PriorityQueue<HeapEntry> maxh = new PriorityQueue<>(k, HeapEntryComparator.s);

    maxh.offer(new HeapEntry(0, A.get(0)));
    for (int i = 0; i < k; i++) {
      // extract ith largest
      HeapEntry c = maxh.poll();
      if (c!=null) {
        r.add(c.value);
        if (2*c.index+1<A.size()) maxh.offer(new HeapEntry(2*c.index+1, A.get(2*c.index+1)));
        if (2*c.index+2<A.size()) maxh.offer(new HeapEntry(2*c.index+2, A.get(2*c.index+2)));
      }
    }
    return r;
  }

  @EpiTestComparator
  public static BiPredicate<List<Integer>, List<Integer>> comp =
      (expected, result) -> {
    if (result == null) {
      return false;
    }
    Collections.sort(expected);
    Collections.sort(result);
    return expected.equals(result);
  };

  public static void main(String[] args) {
    GenericTestHandler.executeTestsByAnnotation(
        new Object() {}.getClass().getEnclosingClass(), args);
  }
}
