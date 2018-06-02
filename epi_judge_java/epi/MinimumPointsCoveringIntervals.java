package epi;

import epi.test_framework.EpiTest;
import epi.test_framework.EpiUserType;
import epi.test_framework.GenericTestHandler;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MinimumPointsCoveringIntervals {
  @EpiUserType(ctorParams = {int.class, int.class})

  public static class Interval {
    public int left, right;

    public Interval(int l, int r) {
      this.left = l;
      this.right = r;
    }
  }

  @EpiTest(testfile = "points_covering_intervals.tsv")

  public static Integer findMinimumVisits(List<Interval> intervals) {
    int numVisits = 0;
    if (intervals==null || intervals.size()==0) return numVisits;
    Collections.sort(intervals, new Comparator<Interval>() {
      @Override
      public int compare(Interval o1, Interval o2) {
        return Integer.compare(o1.right, o2.right);
      }
    });

    int lastVisitTime = intervals.get(0).right;
    numVisits++;

    for (int i = 1; i < intervals.size(); i++) {
      if (intervals.get(i).left > lastVisitTime) {
        lastVisitTime = intervals.get(i).right;
        numVisits++;
      }
    }
    return numVisits;
  }

  public static void main(String[] args) {
    GenericTestHandler.executeTestsByAnnotation(
        new Object() {}.getClass().getEnclosingClass(), args);
  }
}
