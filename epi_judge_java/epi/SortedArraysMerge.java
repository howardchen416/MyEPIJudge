package epi;

import epi.test_framework.EpiTest;
import epi.test_framework.GenericTestHandler;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class SortedArraysMerge {

  @EpiTest(testfile = "sorted_arrays_merge.tsv")

  public static List<Integer>
  mergeSortedArrays(List<List<Integer>> sortedArrays) {

    int numSortedArrays = sortedArrays.size();
    PriorityQueue<HeadEntry> h = new PriorityQueue<>(numSortedArrays, new Comparator<HeadEntry>() {
      @Override
      public int compare(HeadEntry o1, HeadEntry o2) {
        return Integer.compare(o1.value, o2.value);
      }
    });

    for (int i = 0; i < numSortedArrays; i++) { // heap does not have to have all 500 entries
      List<Integer> c = sortedArrays.get(i);
      if (c.size()>0) { // not empty
        h.offer(new HeadEntry(c.get(0), 0, i));
      } else { // empty or at capacity
       // h.offer(null);
      }
    }

    List<Integer> r = new ArrayList<>();
    HeadEntry he;
    while ((he = h.poll()) != null) {
      r.add(he.value);
      // replenish h with a HeadEntry from the same sorted array
      List<Integer> a = sortedArrays.get(he.arrayNumber);
      if (he.position<a.size()-1) h.add(new HeadEntry(a.get(he.position+1), he.position+1, he.arrayNumber));
    }
    return r;
  }

  private static class HeadEntry {
    Integer value;
    int position;
    int arrayNumber;

    public HeadEntry(Integer v, int p, int a) {
      this.value = v;
      this.position = p;
      this.arrayNumber = a;
    }


  }


  public static void main(String[] args) {
    GenericTestHandler.executeTestsByAnnotation(
        new Object() {}.getClass().getEnclosingClass(), args);
  }
}
