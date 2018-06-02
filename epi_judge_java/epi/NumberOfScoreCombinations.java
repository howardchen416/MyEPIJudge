package epi;

import epi.test_framework.EpiTest;
import epi.test_framework.GenericTestHandler;

import java.util.ArrayList;
import java.util.List;

public class NumberOfScoreCombinations {
  @EpiTest(testfile = "number_of_score_combinations.tsv")


  public static int numCombinationsForFinalScore(int finalScore, List<Integer> individualPlayScores) {

    int[][] n = new int[individualPlayScores.size()][];
    for (int i = 0; i < individualPlayScores.size(); i++) {
      n[i] = new int[finalScore+1];
      n[i][0] = 1;
    }

    for (int i = 0; i < individualPlayScores.size(); i++) {
      for (int j = 1; j <= finalScore ; j++) {
        int withoutThisPlay = (i<1) ? 0 : n[i-1][j];
        int withThisPlay = (j >= individualPlayScores.get(i)) ? n[i][j - individualPlayScores.get(i)] : 0;
        n[i][j] = withoutThisPlay + withThisPlay;
      }
    }

    return n[individualPlayScores.size()-1][finalScore];

  }

  // Recursion solution
/*  public static int numCombinationsForFinalScore(int finalScore,
                               List<Integer> individualPlayScores) {
    List<List<Integer>> result = new ArrayList<>();
    numCombinationsForFinalScore(0, finalScore, individualPlayScores, new ArrayList<Integer>(), result);

    return result.size();
  }

  public static void numCombinationsForFinalScore(int recurseLevel, int remainingNumberToFill, List<Integer> individualPlayScores, List<Integer> partialPlayCombination, List<List<Integer>> result) {
    // base condition
    if (remainingNumberToFill==0) {
      result.add(partialPlayCombination);
      return;
    }
    else if (remainingNumberToFill<0) {
      return;
    }
    else if (recurseLevel>=individualPlayScores.size()) {
      return;
    }

    int stepSize = individualPlayScores.get(recurseLevel);
    for (int i = 0; stepSize * i <= remainingNumberToFill; i++) {
      partialPlayCombination.add(i);
      numCombinationsForFinalScore(recurseLevel+1, remainingNumberToFill-i*stepSize, individualPlayScores, partialPlayCombination, result);
      partialPlayCombination.remove(partialPlayCombination.size()-1);
    }

    return;
  }*/

  public static void main(String[] args) {
    GenericTestHandler.executeTestsByAnnotation(
        new Object() {}.getClass().getEnclosingClass(), args);
  }
}
