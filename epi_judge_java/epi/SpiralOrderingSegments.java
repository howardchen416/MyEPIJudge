package epi;

import epi.test_framework.EpiTest;
import epi.test_framework.GenericTestHandler;

import java.util.ArrayList;
import java.util.List;

public class SpiralOrderingSegments {
    @EpiTest(testfile = "spiral_ordering_segments.tsv")

    public static List<Integer> matrixInSpiralOrder(List<List<Integer>> squareMatrix) {
        List<Integer> r = new ArrayList<>();

        for (int offset = 0; offset < Math.ceil(0.5 * squareMatrix.size()); offset++) {
            matrixInSpiralOrderHelper(squareMatrix, offset, r);
        }

        return r;
    }

    public static void matrixInSpiralOrderHelper(List<List<Integer>> squareMatrix,
                                                 int offset, List<Integer> r) {
        // special case
        if (2 * offset == squareMatrix.size() - 1) {
            r.add(squareMatrix.get(offset).get(offset));
            return;
        }

        for (int j = offset; j < squareMatrix.size() - offset - 1; j++) {
            r.add(squareMatrix.get(offset).get(j));
        }

        for (int i = offset; i < squareMatrix.size() - offset - 1; i++) {
            r.add(squareMatrix.get(i).get(squareMatrix.size() - offset - 1));
        }

        for (int j = squareMatrix.size() - offset - 1; j > offset; j--) {
            r.add(squareMatrix.get(squareMatrix.size() - offset - 1).get(j));
        }

        for (int i = squareMatrix.size() - offset - 1; i > offset; i--) {
            r.add(squareMatrix.get(i).get(offset));
        }

        return;
    }

    public static void main(String[] args) {
        GenericTestHandler.executeTestsByAnnotation(
                new Object() {
                }.getClass().getEnclosingClass(), args);
    }
}
