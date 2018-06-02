package epi;

import epi.test_framework.EpiTest;
import epi.test_framework.RandomSequenceChecker;
import epi.test_framework.GenericTestHandler;
import epi.test_framework.TestFailureException;
import epi.test_framework.TestTimer;

import java.util.*;

public class NonuniformRandomNumber {

  public static int
  nonuniformRandomNumberGeneration(List<Integer> values,
                                   List<Double> probabilities) {
    List<Double> l = new ArrayList<>(probabilities.size()+1);
    l.add(0.0D);
    for (int i = 0; i < probabilities.size(); i++) {
      l.add(l.get(i)+probabilities.get(i));
    }
    Random rg = new Random();
    double r = rg.nextDouble();
    int p = Collections.binarySearch(l, r);

    return values.get(Math.abs(p)-2);
  }

  private static boolean
  nonuniformRandomNumberGenerationRunner(TestTimer timer, List<Integer> values,
                                         List<Double> probabilities) {
    int n = 1000000;
    List<Integer> results = new ArrayList<>(n);
    timer.start();
    for (int i = 0; i < n; ++i) {
      results.add(nonuniformRandomNumberGeneration(values, probabilities));
    }
    timer.stop();

    Map<Integer, Integer> counts = new HashMap<>();
    for (Integer result : results) {
      counts.put(result, counts.getOrDefault(result, 0) + 1);
    }
    for (int i = 0; i < values.size(); ++i) {
      final int v = values.get(i);
      final double p = probabilities.get(i);
      if (n * p < 50 || n * (1.0 - p) < 50) {
        continue;
      }
      final double sigma = Math.sqrt(n * p * (1.0 - p));
      if (Math.abs(counts.get(v) - (p * n)) > 5 * sigma) {
        return false;
      }
    }
    return true;
  }

  @EpiTest(testfile = "nonuniform_random_number.tsv")
  public static void
  nonuniformRandomNumberGenerationWrapper(TestTimer timer, List<Integer> values,
                                          List<Double> probabilities)
      throws TestFailureException {
    RandomSequenceChecker.runFuncWithRetries(
        ()
            -> nonuniformRandomNumberGenerationRunner(timer, values,
                                                      probabilities));
  }

  public static void main(String[] args) {
    GenericTestHandler.executeTestsByAnnotation(
        new Object() {}.getClass().getEnclosingClass(), args);
  }
}
