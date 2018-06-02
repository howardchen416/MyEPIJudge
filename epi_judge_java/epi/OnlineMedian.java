package epi;

import epi.test_framework.EpiTest;
import epi.test_framework.GenericTestHandler;

import java.util.*;

public class OnlineMedian {

  public static List<Double> onlineMedian(Iterator<Integer> sequence) {
    PriorityQueue<Integer> minHeap = new PriorityQueue<>();
    PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Collections.reverseOrder());
    List<Double> r = new ArrayList<>();

    while (sequence.hasNext()) {
      Integer i = sequence.next();

      if (minHeap.isEmpty()) {
        minHeap.offer(i);
      } else {
        if (i > minHeap.peek()) minHeap.add(i);
        else maxHeap.add(i);
      }

      // check for balance
      if (minHeap.size()>maxHeap.size()+1) {
        maxHeap.add(minHeap.remove());
      } else if (maxHeap.size()>minHeap.size()) {
        minHeap.add(maxHeap.remove());
      }

      if (minHeap.size()>maxHeap.size()) r.add(minHeap.peek() * 1.0D);
      else r.add((minHeap.peek() + maxHeap.peek()) * 1.0D / 2.0D);

    }

    return r;
  }

  @EpiTest(testfile = "online_median.tsv")
  public static List<Double> onlineMedianWrapper(List<Integer> sequence) {
    return onlineMedian(sequence.iterator());
  }

  public static void main(String[] args) {
    GenericTestHandler.executeTestsByAnnotation(
        new Object() {}.getClass().getEnclosingClass(), args);
  }
}
