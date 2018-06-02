package epi;

import epi.test_framework.EpiTest;
import epi.test_framework.GenericTestHandler;

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

public class LargestRectangleUnderSkyline {
  @EpiTest(testfile = "largest_rectangle_under_skyline.tsv")

  public static int calculateLargestRectangle(List<Integer> heights) {
    Deque<Integer> pillarIndices = new LinkedList<>();
    int maxRectangleArea = 0;
    for (int i = 0; i <= heights.size(); ++i) {
      if (!pillarIndices.isEmpty() && i < heights.size() && heights.get(i).equals(heights.get(pillarIndices.peekFirst()))) {
// Replace earlier building with same height by current building. This
// ensures the later buildings have the correct left endpoint.
        pillarIndices.removeFirst();
        pillarIndices.addFirst(i);
      }
// By iterating to heights.size() instead of heights.size() - 1, we can
// uniformly handle the computation for rectangle area here.
      while (!pillarIndices.isEmpty()
              && isNewPillarOrReachEnd(heights, i, pillarIndices.peekFirst())) {
        int height = heights.get(pillarIndices.removeFirst());
        int width
                = pillarIndices.isEmpty() ? i : i - pillarIndices.peekFirst() - 1;
        maxRectangleArea = Math.max(maxRectangleArea, height * width);
      }
      pillarIndices.addFirst(i);
    }
    return maxRectangleArea;
  }

  private static boolean isNewPillarOrReachEnd(List<Integer> heights,
                                               int currldx , int lastPillarldx) {
    return currldx < heights.size()
            ? heights.get(currldx) < heights.get(lastPillarldx)
            : true;
  }

  public static void main(String[] args) {
    GenericTestHandler.executeTestsByAnnotation(
        new Object() {}.getClass().getEnclosingClass(), args);
  }
}
