package epi;

import epi.test_framework.EpiTest;
import epi.test_framework.EpiUserType;
import epi.test_framework.GenericTestHandler;
import epi.test_framework.TestTimer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class IntervalsUnion {

  public static class Interval implements Comparable<Interval> {
    public Endpoint left = new Endpoint();
    public Endpoint right = new Endpoint();

    private static class Endpoint {
      public boolean isClosed;
      public int val;
    }

    @Override
    public int compareTo(Interval o) {
      int r = Integer.compare(this.left.val, o.left.val);
      return (r!=0) ? r : (this.left.isClosed && !o.left.isClosed) ? -1 : (!this.left.isClosed && o.left.isClosed) ? 1 : 0;
    }

    @Override
    public int hashCode() {
      return Objects.hash(this.left.val, this.left.isClosed);
    }

    @Override
    public boolean equals(Object obj) {
      if (obj==null || !(obj instanceof Interval))
        return false;
      if (this == obj)
        return true;
      Interval that = (Interval) obj;
      return (this.left.val==that.left.val) && (this.left.isClosed==that.left.isClosed);
    }
  }

  public static List<Interval> unionOfIntervals(List<Interval> intervals) {
    // special condition(s)
    if (intervals.size()==0) return intervals;

    Collections.sort(intervals);
    List<Interval> r = new ArrayList<>();
    Interval curr = intervals.get(0);
    for (int i = 1; i < intervals.size(); i++) {
      if (curr.right.val > intervals.get(i).left.val ||
              (curr.right.val == intervals.get(i).left.val &&
                      (curr.right.isClosed || intervals.get(i).left.isClosed))) {
          if (curr.right.val < intervals.get(i).right.val ||
                  (curr.right.val == intervals.get(i).right.val && intervals.get(i).right.isClosed))
            curr.right = intervals.get(i).right;
      }
      else {
        r.add(curr);
        curr = intervals.get(i);
      }
    }
    r.add(curr);
    return r;
  }

  @EpiUserType(
      ctorParams = {int.class, boolean.class, int.class, boolean.class})
  public static class FlatInterval {
    int leftVal;
    boolean leftIsClosed;
    int rightVal;
    boolean rightIsClosed;

    public FlatInterval(int leftVal, boolean leftIsClosed, int rightVal,
                        boolean rightIsClosed) {
      this.leftVal = leftVal;
      this.leftIsClosed = leftIsClosed;
      this.rightVal = rightVal;
      this.rightIsClosed = rightIsClosed;
    }

    public FlatInterval(Interval i) {
      if (i != null) {
        leftVal = i.left.val;
        leftIsClosed = i.left.isClosed;
        rightVal = i.right.val;
        rightIsClosed = i.right.isClosed;
      }
    }

    public Interval toInterval() {
      Interval i = new Interval();
      i.left.val = leftVal;
      i.left.isClosed = leftIsClosed;
      i.right.val = rightVal;
      i.right.isClosed = rightIsClosed;
      return i;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) {
        return true;
      }
      if (o == null || getClass() != o.getClass()) {
        return false;
      }

      FlatInterval that = (FlatInterval)o;

      if (leftVal != that.leftVal) {
        return false;
      }
      if (leftIsClosed != that.leftIsClosed) {
        return false;
      }
      if (rightVal != that.rightVal) {
        return false;
      }
      return rightIsClosed == that.rightIsClosed;
    }

    @Override
    public int hashCode() {
      int result = leftVal;
      result = 31 * result + (leftIsClosed ? 1 : 0);
      result = 31 * result + rightVal;
      result = 31 * result + (rightIsClosed ? 1 : 0);
      return result;
    }

    @Override
    public String toString() {
      return "" + (leftIsClosed ? "<" : "(") + leftVal + ", " + rightVal +
          (rightIsClosed ? ">" : ")");
    }
  }

  @EpiTest(testfile = "intervals_union.tsv")
  public static List<FlatInterval>
  unionIntervalWrapper(TestTimer timer, List<FlatInterval> intervals) {
    List<Interval> casted = new ArrayList<>(intervals.size());
    for (FlatInterval in : intervals) {
      casted.add(in.toInterval());
    }

    timer.start();
    List<Interval> result = unionOfIntervals(casted);
    timer.stop();

    intervals = new ArrayList<>(result.size());
    for (Interval i : result) {
      intervals.add(new FlatInterval(i));
    }
    return intervals;
  }

  public static void main(String[] args) {
    GenericTestHandler.executeTestsByAnnotation(
        new Object() {}.getClass().getEnclosingClass(), args);
  }
}
