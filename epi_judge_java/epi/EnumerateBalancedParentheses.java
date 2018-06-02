package epi;

import epi.test_framework.EpiTest;
import epi.test_framework.EpiTestComparator;
import epi.test_framework.GenericTestHandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.BiPredicate;

public class EnumerateBalancedParentheses {
    @EpiTest(testfile = "enumerate_balanced_parentheses.tsv")

    public static List<String> generateBalancedParentheses(int numPairs) {

        List<String> r = new ArrayList<>();
        directedGenerateBalancedParentheses(numPairs, numPairs, "", r);
        return r;
    }

    public static void directedGenerateBalancedParentheses(int i, int j, String str, List<String> r) {
        if (i == 0 && j == 0) {
            r.add(new String(str));
            return;
        }
        if (i > 0) {
            directedGenerateBalancedParentheses(i - 1, j, str + "(", r);
        }
        if (i < j) {
            directedGenerateBalancedParentheses(i, j - 1, str + ")", r);
        }
        return;
    }

    @EpiTestComparator
    public static BiPredicate<List<String>, List<String>> comp =
            (expected, result) -> {
                if (result == null) {
                    return false;
                }
                Collections.sort(expected);
                Collections.sort(result);
                return expected.equals(result);
            };

    public static void main(String[] args) {
        GenericTestHandler.executeTestsByAnnotation(
                new Object() {
                }.getClass().getEnclosingClass(), args);
    }
}
