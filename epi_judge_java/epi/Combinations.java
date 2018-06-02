package epi;

import epi.test_framework.EpiTest;
import epi.test_framework.EpiTestComparator;
import epi.test_framework.LexicographicalListComparator;
import epi.test_framework.GenericTestHandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.BiPredicate;

public class Combinations {
    @EpiTest(testfile = "combinations.tsv")

    public static List<List<Integer>> combinations(int n, int k) {

        List<List<Integer>> r = new ArrayList<>();
        composeSubset(n, k, 1, new ArrayList<Integer>(), r);

        return r;
    }

    public static void composeSubset(int n, int k, int offset, List<Integer> partialSubset, List<List<Integer>> results) {
        if (partialSubset.size()==k) { // done
            results.add(new ArrayList<Integer>(partialSubset));
            return;
        }

        int numRemaining = k - partialSubset.size();
        for (int i = offset; (i <= n) && (i <= n - numRemaining + 1); i++) {
            partialSubset.add(i); // the colPlacement List holds a partial placement for trial
            composeSubset(n, k, offset + 1, partialSubset, results);
            partialSubset.remove(partialSubset.size() - 1);
        }

        return;
    }

    @EpiTestComparator
    public static BiPredicate<List<List<Integer>>,
            List<List<Integer>>> comp = (expected, result) -> {
        if (result == null) {
            return false;
        }
        expected.sort(new LexicographicalListComparator<>());
        result.sort(new LexicographicalListComparator<>());
        return expected.equals(result);
    };

    public static void main(String[] args) {
        GenericTestHandler.executeTestsByAnnotation(
                new Object() {
                }.getClass().getEnclosingClass(), args);
    }
}
