package epi;

import epi.test_framework.EpiTest;
import epi.test_framework.GenericTestHandler;
import epi.test_framework.TestHandler;

import java.util.List;

public class MaxSubmatrix {

    private static class MaxHW {
        public int h, w;

        public MaxHW(int h, int w) {
            this.h = h;
            this.w = w;
        }
    }

    @EpiTest(testfile = "max_submatrix.tsv")

    public static int maxRectangleSubmatrix(List<List<Boolean>> A) {
        //
        MaxHW[][] maxHWMatrix = new MaxHW[A.size()][A.get(0).size()];

        for (int i = A.size()-1; i>=0; i--) {
            for (int j = A.get(i).size()-1; j >= 0 ; j--) {
                maxHWMatrix[i][j] = A.get(i).get(j) ?
                        new MaxHW((i+1<A.size()) ? maxHWMatrix[i+1][j].h+1 : 1, (j+1<A.get(i).size()) ? maxHWMatrix[i][j+1].w+1 : 1) :
                        new MaxHW(0, 0);
            }
        }
        int maxRectangleSize = 0;
        for (int i = 0; i < A.size(); i++) {
            for (int j = 0; j < A.get(i).size(); j++) {
                if (A.get(i).get(j) && maxHWMatrix[i][j].h*maxHWMatrix[i][j].w>maxRectangleSize) {
                    int minWidth = Integer.MAX_VALUE;
                    for (int k = 0; k < maxHWMatrix[i][j].h; k++) {
                        minWidth = Math.min(minWidth, maxHWMatrix[i+k][j].w);
                        maxRectangleSize = Math.max(maxRectangleSize, (k+1)*minWidth);
                    }
                }
            }
        }
        return maxRectangleSize;
    }

    public static void main(String[] args) {
        GenericTestHandler.executeTestsByAnnotation(
                new Object() {
                }.getClass().getEnclosingClass(), args);
    }
}