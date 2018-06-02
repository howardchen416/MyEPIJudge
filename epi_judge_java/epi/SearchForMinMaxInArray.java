package epi;

import epi.test_framework.EpiTest;
import epi.test_framework.EpiUserType;
import epi.test_framework.GenericTestHandler;

import java.util.List;

public class SearchForMinMaxInArray {
  @EpiUserType(ctorParams = {Integer.class, Integer.class})

  public static class MinMax {
    public Integer smallest;
    public Integer largest;

    public MinMax(Integer smallest, Integer largest) {
      this.smallest = smallest;
      this.largest = largest;
    }

    private static MinMax minMax(Integer a, Integer b) {
      return Integer.compare(b, a) < 0 ? new MinMax(b, a) : new MinMax(a, b);
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) {
        return true;
      }
      if (o == null || getClass() != o.getClass()) {
        return false;
      }

      MinMax minMax = (MinMax)o;

      if (!smallest.equals(minMax.smallest)) {
        return false;
      }
      return largest.equals(minMax.largest);
    }

    @Override
    public String toString() {
      return "min: " + smallest + ", max: " + largest;
    }
  }

  @EpiTest(testfile = "search_for_min_max_in_array.tsv")

  public static MinMax findMinMax(List<Integer> A) {
    MinMax gMinMax = new MinMax(Integer.MAX_VALUE, Integer.MIN_VALUE);
    for (int i = 0; i < A.size(); i+=2) {
      MinMax mm = MinMax.minMax(A.get(i), A.get(i+1));
      gMinMax = new MinMax(Math.min(gMinMax.smallest, mm.smallest), Math.max(gMinMax.largest, mm.largest));
    }
    if (A.size()%2==1) {
      gMinMax = new MinMax(Math.min(gMinMax.smallest, A.get(A.size()-1)), Math.max(gMinMax.largest, A.get(A.size()-1)));
    }
    return gMinMax;
  }

  public static void main(String[] args) {
    GenericTestHandler.executeTestsByAnnotation(
        new Object() {}.getClass().getEnclosingClass(), args);
  }
}
