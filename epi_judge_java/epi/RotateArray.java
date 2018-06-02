package epi;

import epi.test_framework.EpiTest;
import epi.test_framework.GenericTestHandler;
import epi.test_framework.TestTimer;

import java.util.ArrayList;
import java.util.List;

public class RotateArray {

  public static void rotateArray(int rotateAmount, List<Integer> A) {
    rotateAmount %= A.size();
    if (rotateAmount==0) return;

    int numRotations = GCD(A.size(), rotateAmount);
    int seqLength = A.size() / numRotations;

    for (int i = 0; i < numRotations; i++) {
      rotate(A, rotateAmount, i, seqLength);
    }

    return;
  }

  public static void rotate(List<Integer> A, int k, int offset, int seqLength) {
    int t = A.get(offset);
    for (int i = seqLength-1; i >=1; i--) {
      A.set(((i+1)*k + offset) % A.size(), A.get((i*k + offset) % A.size()));
    }
    A.set((k + offset) % A.size(), t);
  }

  public static int GCD(int x, int y) {
    return (y==0 || x==y) ? x : (x>y) ? GCD(y, x % y) : GCD(x, y % x);
  }

  @EpiTest(testfile = "rotate_array.tsv")
  public static List<Integer>
  rotateArrayWrapper(TestTimer timer, List<Integer> A, int rotateAmount) {
    List<Integer> aCopy = new ArrayList<>(A);
    timer.start();
    rotateArray(rotateAmount, aCopy);
    timer.stop();
    return aCopy;
  }

  public static void main(String[] args) {
    GenericTestHandler.executeTestsByAnnotation(
        new Object() {}.getClass().getEnclosingClass(), args);
  }
}
