package epi;

import epi.test_framework.EpiTest;
import epi.test_framework.EpiUserType;
import epi.test_framework.GenericTestHandler;

import java.util.Arrays;
import java.util.List;

public class Knapsack {
  @EpiUserType(ctorParams = {Integer.class, Integer.class})

  public static class Item {
    public Integer weight;
    public Integer value;

    public Item(Integer weight, Integer value) {
      this.weight = weight;
      this.value = value;
    }
  }

  @EpiTest(testfile = "knapsack.tsv")

  public static int optimumSubjectToCapacity(List<Item> items, int capacity) {
    int[][] values = new int[items.size()][capacity+1];
    for (int i = 0; i < items.size(); i++) {
      int[] row = values[i];
      Arrays.fill(row, -1);
    }
    return optimumSubjectToCapacityHelper(items, items.size()-1, capacity, values);
  }

  private static int optimumSubjectToCapacityHelper(List<Item> items, int k, int remainingCapacity, int[][] values) {
    // base condition
    if (k < 0)
      return 0;

    if (values[k][remainingCapacity]==-1) {
      int withItemK = (items.get(k).weight > remainingCapacity) ? 0 : (items.get(k).value +
              optimumSubjectToCapacityHelper(items, k - 1, remainingCapacity - items.get(k).weight, values));
      int withoutItemK = optimumSubjectToCapacityHelper(items, k - 1, remainingCapacity, values);
      values[k][remainingCapacity] = Math.max(withItemK, withoutItemK);
    }
    return values[k][remainingCapacity];


  }

  public static void main(String[] args) {
    GenericTestHandler.executeTestsByAnnotation(
        new Object() {}.getClass().getEnclosingClass(), args);
  }
}
