package epi;

import epi.test_framework.EpiTest;
import epi.test_framework.GenericTestHandler;

import java.util.BitSet;
import java.util.Iterator;

public class AbsentValueArray {

  static int NUM_BUCKETS = 1<<16;

  @EpiTest(testfile = "absent_value_array.tsv")
  public static int findMissingElement(Iterable<Integer> stream) {
    // 1st pass - create bucket counts
    int[] ints = new int[NUM_BUCKETS];
    Iterator<Integer> iter = stream.iterator();
    while (iter.hasNext()) {
      Integer i = iter.next();
      int idx = i>>>16;
      ints[idx]++;
    }

    // 2nd pass
    for (int i = 0; i < NUM_BUCKETS; i++) {
      BitSet bs = new BitSet(NUM_BUCKETS);
      if (ints[i] < NUM_BUCKETS) { // incomplete bucket
        iter = stream.iterator();
        while (iter.hasNext()) {
          Integer ip = iter.next();
          int idx = ip >>> 16;
          if (i == idx) { // entries from same bucket
            bs.set(ip ^ (NUM_BUCKETS - 1));
          }
        }
        // find missing entry
        for (int j = 0; j < NUM_BUCKETS; j++) {
          if (!bs.get(j)) {
            return (i<<16 | j);
          }
        }
      }
    }

    return 0;
  }

  public static void main(String[] args) {
    GenericTestHandler.executeTestsByAnnotation(
        new Object() {}.getClass().getEnclosingClass(), args);
  }
}
