package epi;

import epi.test_framework.EpiTest;
import epi.test_framework.GenericTestHandler;
import epi.test_framework.TestFailureException;
import epi.test_framework.TestTimer;

import java.util.*;

public class SmallestSubarrayCoveringSet {

    // Represent subarray by starting and ending indices, inclusive.
    private static class Subarray {
        public Integer start;
        public Integer end;

        public Subarray(Integer start, Integer end) {
            this.start = start;
            this.end = end;
        }
    }

    public static Subarray findSmallestSubarrayCoveringSet(List<String> paragraph,
                                                           Set<String> keywords) {
        Subarray sa = new Subarray(-1, -1);
        int keysToCoverCnt = keywords.size();
        Map<String, Integer> keysToCover = new HashMap<>();
        for (String key : keywords) {
            keysToCover.put(key, keysToCover.containsKey(key) ? keysToCover.get(key) + 1 : 1);
        }

        for (int l = 0, r = 0; r < paragraph.size(); r++) {
            if (keysToCoverCnt>0) {
                if (keysToCover.containsKey(paragraph.get(r))) {
                    keysToCover.put(paragraph.get(r), keysToCover.get(paragraph.get(r)) - 1);
                    if (keysToCover.get(paragraph.get(r))>=0) keysToCoverCnt--;
                }
            }

            while (keysToCoverCnt==0) {
                if (keysToCover.containsKey(paragraph.get(l))) {
                    keysToCover.put(paragraph.get(l), keysToCover.get(paragraph.get(l)) + 1);
                    if (keysToCover.get(paragraph.get(l))>0) {
                        keysToCoverCnt++;
                        if (r >= l) {
                            if ((sa.start == -1 && sa.end == -1) ||
                                    (sa.end - sa.start > r - l)) {
                                sa = new Subarray(l, r);
                            }
                        }
                    }

                }
                l++;
            }
        }

        return sa;
    }

    @EpiTest(testfile = "smallest_subarray_covering_set.tsv")
    public static int findSmallestSubarrayCoveringSetWrapper(
            TestTimer timer, List<String> paragraph, Set<String> keywords)
            throws TestFailureException {
        Set<String> copy = new HashSet<>(keywords);

        timer.start();
        Subarray result = findSmallestSubarrayCoveringSet(paragraph, keywords);
        timer.stop();

        if (result.start < 0 || result.start >= paragraph.size() ||
                result.end < 0 || result.end >= paragraph.size() ||
                result.start > result.end)
            throw new TestFailureException("Index out of range");

        for (int i = result.start; i <= result.end; i++)
            copy.remove(paragraph.get(i));

        if (!copy.isEmpty())
            throw new TestFailureException("Not all keywords are in the range");

        return result.end - result.start + 1;
    }

    public static void main(String[] args) {
        GenericTestHandler.executeTestsByAnnotation(
                new Object() {
                }.getClass().getEnclosingClass(), args);
    }
}
