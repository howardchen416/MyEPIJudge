package epi;

import epi.test_framework.EpiTest;
import epi.test_framework.GenericTestHandler;

import java.util.*;

public class IsStringInMatrix {


    private static class Attempt {
        int x;
        int y;
        int data;

        public Attempt(int x, int y, int data) {
            this.x = x;
            this.y = y;
            this.data = data;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Attempt Attempt = (Attempt) o;
            return x == Attempt.x &&
                    y == Attempt.y &&
                    data == Attempt.data;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y, data);
        }
    }

    @EpiTest(testfile = "is_string_in_matrix.tsv")
    public static boolean isPatternContainedInGrid(List<List<Integer>> grid,
                                                   List<Integer> pattern) {
        // Implement this placeholder.
        Set<Attempt> previouslyFailedAttempts = new HashSet<>();
        for (int i = 0; i < grid.size(); i++) {
            for (int j = 0; j < grid.get(i).size(); j++) {
                if (patternContainedInGridTester(grid, pattern, previouslyFailedAttempts, i, j, 0))
                    return true;
            }
        }
        return false;
    }

    public static boolean patternContainedInGridTester(List<List<Integer>> grid,
                                                       List<Integer> pattern, Set<Attempt> previouslyFailedAttempts, int x, int y, int offset) {
        // base condition(s)
        if (offset == pattern.size())
            return true;

        if (x<0 || x>=grid.size() || y<0 || y>=grid.get(0).size() ||
                previouslyFailedAttempts.contains(new Attempt(x, y, offset)))
            return false;

        if (grid.get(x).get(y) == pattern.get(offset) &&
                (patternContainedInGridTester(grid, pattern, previouslyFailedAttempts, x + 1, y, offset+1) ||
                        patternContainedInGridTester(grid, pattern, previouslyFailedAttempts, x - 1, y, offset+1) ||
                        patternContainedInGridTester(grid, pattern, previouslyFailedAttempts, x, y + 1, offset+1) ||
                        patternContainedInGridTester(grid, pattern, previouslyFailedAttempts, x, y - 1, offset+1)))
            return true;

        previouslyFailedAttempts.add(new Attempt(x, y, offset));
        return false;
    }

    public static void main(String[] args) {
        GenericTestHandler.executeTestsByAnnotation(
                new Object() {
                }.getClass().getEnclosingClass(), args);
    }
}
