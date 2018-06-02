package epi;

import epi.test_framework.EpiTest;
import epi.test_framework.GenericTestHandler;

import java.util.List;

public class MaxWaterTrappable {
  @EpiTest(testfile = "max_water_trappable.tsv")

  public static int calculateTrappingWater(List<Integer> heights) {
    int max = maxIdx(heights);
    // left
    int sum = 0;
    int left = 0;
    for (int i = 1; i < max; i++) {
      if (heights.get(i)>=heights.get(left))
        left = i;
      else
        sum += heights.get(left) - heights.get(i);
    }
    // right
    int right = heights.size()-1;
    for (int i = heights.size()-2; i > max ; i--) {
      if (heights.get(i)>=heights.get(right))
        right = i;
      else
        sum += heights.get(right) - heights.get(i);
    }

    return sum;
  }

  private static int maxIdx(List<Integer> heights) {
    int maxIdx = 0;
    int max = 0;
    for (int i = 0; i < heights.size(); i++) {
      if (heights.get(i) > max) {
        max = heights.get(i);
        maxIdx = i;
      }
    }
    return maxIdx;
  }

  public static void main(String[] args) {
    GenericTestHandler.executeTestsByAnnotation(
        new Object() {}.getClass().getEnclosingClass(), args);
  }
}
