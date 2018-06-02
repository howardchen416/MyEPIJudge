package epi;

import epi.test_framework.EpiTest;
import epi.test_framework.GenericTestHandler;

import java.util.Arrays;

public class LevenshteinDistance {
    @EpiTest(testfile = "levenshtein_distance.tsv")

    public static int levenshteinDistance(String A, String B) {
        int[][] d = new int[A.length()][B.length()];
        for (int i = 0; i < A.length(); i++) {
            int[] row = d[i];
            Arrays.fill(row, -1);
        }

        return calculateLevenshteinDistance(A, A.length() - 1, B, B.length() - 1, d);
    }

    public static int calculateLevenshteinDistance(String A, int posA, String B, int posB, int[][] d) {
        // base condition
        if (posA < 0) {
            return posB +1;
        }
        else if (posB <0) {
            return posA + 1;
        }

        if (d[posA][posB]==-1) {
            if (A.charAt(posA) == B.charAt(posB))
                d[posA][posB] = calculateLevenshteinDistance(A, posA - 1, B, posB - 1, d);
            else {
                d[posA][posB] = 1 + Math.min(calculateLevenshteinDistance(A, posA - 1, B, posB - 1, d),
                        Math.min(calculateLevenshteinDistance(A, posA - 1, B, posB, d),
                        calculateLevenshteinDistance(A, posA, B, posB - 1, d))
                );
            }
        }

        return d[posA][posB];
    }

    public static void main(String[] args) {
        GenericTestHandler.executeTestsByAnnotation(
                new Object() {
                }.getClass().getEnclosingClass(), args);
    }
}
