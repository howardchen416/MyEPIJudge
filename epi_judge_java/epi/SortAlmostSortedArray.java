package epi;

import epi.test_framework.EpiTest;
import epi.test_framework.GenericTestHandler;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.PriorityQueue;

public class SortAlmostSortedArray {

  public static List<Integer>
  sortApproximatelySortedData(Iterator<Integer> sequence, int k) {
    PriorityQueue<Integer> h = new PriorityQueue<>(k);
    List<Integer> r = new ArrayList<>();

    // prep heap
    for (int i = 0; i < k && sequence.hasNext(); i++) {
      h.offer(sequence.next());
    }

    // operate
    while (sequence.hasNext()) {
      h.offer(sequence.next());
      r.add(h.poll());
      //System.out.print(h.poll());
    }

    // empty heap
    while (!h.isEmpty()) {
      r.add(h.poll());
      //System.out.print(h.poll());
    }

    return r;
  }

  @EpiTest(testfile = "sort_almost_sorted_array.tsv")
  public static List<Integer>
  sortApproximatelySortedDataWrapper(List<Integer> sequence, int k) {
    return sortApproximatelySortedData(sequence.iterator(), k);
  }

  public static void main(String[] args) {
    GenericTestHandler.executeTestsByAnnotation(
        new Object() {}.getClass().getEnclosingClass(), args);
  }
}
