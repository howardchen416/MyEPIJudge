package epi;

import epi.test_framework.EpiTest;
import epi.test_framework.GenericTestHandler;

import java.util.Arrays;
import java.util.List;

public class PickingUpCoins {
  @EpiTest(testfile = "picking_up_coins.tsv")

  public static int pickUpCoins(List<Integer> coins) {
    int[][] R = new int[coins.size()][coins.size()];
    for (int i = 0; i < coins.size(); i++) {
      Arrays.fill(R[i], -1);
    }
    return pickUpCoinsHelper(coins, 0, coins.size()-1, R);
  }

  public static int pickUpCoinsHelper(List<Integer> coins, int i, int j, int[][] R) {
    // base condition
    if (i>j) {
      return 0;
    }

    if (R[i][j]==-1) {
      R[i][j] = Math.max(coins.get(i) + Math.min(pickUpCoinsHelper(coins, i+2, j, R), pickUpCoinsHelper(coins, i+1, j-1, R)),
              coins.get(j) + Math.min(pickUpCoinsHelper(coins, i+1, j-1, R), pickUpCoinsHelper(coins, i, j-2, R)));
    }
    return R[i][j];
  }


  public static void main(String[] args) {
    GenericTestHandler.executeTestsByAnnotation(
        new Object() {}.getClass().getEnclosingClass(), args);
  }
}
