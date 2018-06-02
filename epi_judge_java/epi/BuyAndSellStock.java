package epi;

import epi.test_framework.EpiTest;
import epi.test_framework.GenericTestHandler;

import java.util.List;

public class BuyAndSellStock {
  @EpiTest(testfile = "buy_and_sell_stock.tsv")
  public static double computeMaxProfit(List<Double> prices) {
    double minPrice = Integer.MAX_VALUE;
    double maxProfit = 0.0D;
    for (int i = 0; i < prices.size(); i++) {
      minPrice = Math.min(minPrice, prices.get(i));
      maxProfit = Math.max(maxProfit, prices.get(i) - minPrice);
    }
    return maxProfit;
  }

  public static void main(String[] args) {
    GenericTestHandler.executeTestsByAnnotation(
        new Object() {}.getClass().getEnclosingClass(), args);
  }
}
