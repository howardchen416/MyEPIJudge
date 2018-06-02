package epi;

import epi.test_framework.EpiTest;
import epi.test_framework.GenericTestHandler;

import java.util.*;

public class LongestNondecreasingSubsequence {

    private static class valueAndIdx {
        int value;
        int idx;

        public valueAndIdx(int value, int idx) {
            this.value = value;
            this.idx = idx;
        }
    }

    @EpiTest(testfile = "longest_nondecreasing_subsequence.tsv")

    public static int longestNondecreasingSubsequenceLength(List<Integer> A) {
        Deque<valueAndIdx> stack = new LinkedList<>();
        Integer[] maxLen = new Integer[A.size()];
        Arrays.fill(maxLen, -1);
        maxLen[0] = 1;
        stack.addFirst(new valueAndIdx(A.get(0), 0));
        for (int i = 1; i < A.size(); i++) {
            while (!stack.isEmpty() && stack.peek().value>A.get(i)) {
                stack.poll();
            }
            maxLen[i] = (stack.isEmpty()) ? 1 : maxLen[stack.peek().idx] + 1;
            stack.addFirst(new valueAndIdx(A.get(i), i));
        }
        return Collections.max(Arrays.asList(maxLen));
    }

    public static void main(String[] args) {
        GenericTestHandler.executeTestsByAnnotation(
                new Object() {
                }.getClass().getEnclosingClass(), args);
    }
}
