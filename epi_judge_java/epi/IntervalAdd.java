package epi;

import epi.test_framework.EpiTest;
import epi.test_framework.EpiUserType;
import epi.test_framework.GenericTestHandler;

import java.util.ArrayList;
import java.util.List;

public class IntervalAdd {
  @EpiUserType(ctorParams = {int.class, int.class})

  public static class Interval {
    public int left, right;

    public Interval(int l, int r) {
      this.left = l;
      this.right = r;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) {
        return true;
      }
      if (o == null || getClass() != o.getClass()) {
        return false;
      }

      Interval interval = (Interval)o;

      if (left != interval.left) {
        return false;
      }
      return right == interval.right;
    }

    @Override
    public String toString() {
      return "[" + left + ", " + right + "]";
    }
  }

  @EpiTest(testfile = "interval_add.tsv")

  public static List<Interval> addInterval(List<Interval> disjointIntervals,
                                           Interval newInterval) {
    List<Interval> r = new ArrayList<>();
    // special condition(s)
    if (disjointIntervals==null || disjointIntervals.size()==0) { r.add(newInterval); return r; }

    // process disjoint and sorted intervals before newInterval
    int i = 0;
    while ((i < disjointIntervals.size()) && (disjointIntervals.get(i).right < newInterval.left)) {
      r.add(disjointIntervals.get(i++));
    }

    // process intervals that touches newInterval
    while ((i < disjointIntervals.size()) && (disjointIntervals.get(i).left <= newInterval.right)) {
      newInterval = new Interval(Math.min(newInterval.left, disjointIntervals.get(i).left),
              Math.max(newInterval.right, disjointIntervals.get(i).right));
      i++;
    }
    r.add(newInterval);

    // process intervals after the newInterval
    while (i < disjointIntervals.size()) {
      r.add(disjointIntervals.get(i++));
    }

    return r;
  }

  public static void main(String[] args) {
    GenericTestHandler.executeTestsByAnnotation(
        new Object() {}.getClass().getEnclosingClass(), args);
  }
}
