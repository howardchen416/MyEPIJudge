package epi;

import epi.test_framework.EpiTest;
import epi.test_framework.GenericTestHandler;
import epi.test_framework.TestFailureException;
import epi.test_framework.TestTimer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;

public class EvenOddArray {

    public static List<Integer> evenOdd(List<Integer> A) {
        // Implement this placeholder.
        ListIterator<Integer> evenIter = A.listIterator();
        ListIterator<Integer> oddIter = A.listIterator(A.size());
        while (evenIter.nextIndex() < oddIter.previousIndex()) {
            while (evenIter.hasNext() && evenIter.next() % 2 == 0) {
            }
            while (oddIter.hasPrevious() && oddIter.previous() % 2 == 1) {
            }
            if (evenIter.previousIndex() < oddIter.nextIndex()) { // swap elements
                Integer t = evenIter.previous();
                evenIter.set(oddIter.next());
                oddIter.set(t);
            }
        }
        return A;
    }

    @EpiTest(testfile = "even_odd_array.tsv")
    public static void evenOddWrapper(TestTimer timer, List<Integer> A)
            throws TestFailureException {
        List<Integer> before = new ArrayList<>(A);

        timer.start();
        evenOdd(A);
        timer.stop();

        boolean inOdd = false;
        for (int i = 0; i < A.size(); i++) {
            if (A.get(i) % 2 == 0) {
                if (inOdd) {
                    throw new TestFailureException("Even elements appear in odd part");
                }
            } else {
                inOdd = true;
            }
        }
        List<Integer> after = new ArrayList<>(A);
        Collections.sort(before);
        Collections.sort(after);
        if (!before.equals(after)) {
            throw new TestFailureException("Elements mismatch");
        }
    }

    public static void main(String[] args) {
        GenericTestHandler.executeTestsByAnnotation(
                new Object() {
                }.getClass().getEnclosingClass(), args);
    }
}
