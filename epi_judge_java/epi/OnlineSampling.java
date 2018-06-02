package epi;

import epi.test_framework.EpiTest;
import epi.test_framework.GenericTestHandler;
import epi.test_framework.RandomSequenceChecker;
import epi.test_framework.TestFailureException;
import epi.test_framework.TestTimer;

import java.util.*;

public class OnlineSampling {

    // Assumption: there are at least k elements in the stream.
    public static List<Integer> onlineRandomSample(Iterator<Integer> stream, int k) {
        List<Integer> samples = new ArrayList<>();

        for (int i = 0; i < k; i++) {
            samples.add(stream.next());
        }

        int itemsReadSoFar = k;
        Random gen = new Random();
        while (stream.hasNext()) {
            Integer n = stream.next();
            itemsReadSoFar++;
            int r = gen.nextInt(itemsReadSoFar);
            if (r < k) {
                samples.set(r, n);
            }
        }
        return samples;
    }

    private static boolean onlineRandomSampleRunner(TestTimer timer,
                                                    List<Integer> A, int k) {
        List<List<Integer>> results = new ArrayList<>();
        timer.start();
        for (int i = 0; i < 1000000; ++i) {
            results.add(onlineRandomSample(A.iterator(), k));
        }
        timer.stop();

        int totalPossibleOutcomes =
                RandomSequenceChecker.binomialCoefficient(A.size(), k);
        Collections.sort(A);
        List<List<Integer>> combinations = new ArrayList<>();
        for (int i = 0; i < RandomSequenceChecker.binomialCoefficient(A.size(), k);
             ++i) {
            combinations.add(
                    RandomSequenceChecker.computeCombinationIdx(A, A.size(), k, i));
        }
        List<Integer> sequence = new ArrayList<>();
        for (List<Integer> result : results) {
            Collections.sort(result);
            sequence.add(combinations.indexOf(result));
        }
        return RandomSequenceChecker.checkSequenceIsUniformlyRandom(
                sequence, totalPossibleOutcomes, 0.01);
    }

    @EpiTest(testfile = "online_sampling.tsv")
    public static void onlineRandomSampleWrapper(TestTimer timer,
                                                 List<Integer> stream, int k)
            throws TestFailureException {
        RandomSequenceChecker.runFuncWithRetries(
                () -> onlineRandomSampleRunner(timer, stream, k));
    }

    public static void main(String[] args) {
        GenericTestHandler.executeTestsByAnnotation(
                new Object() {
                }.getClass().getEnclosingClass(), args);
    }
}
