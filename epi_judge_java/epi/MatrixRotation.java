package epi;

import epi.test_framework.EpiTest;
import epi.test_framework.GenericTestHandler;

import java.util.List;

public class MatrixRotation {

  public static void rotateMatrix(List<List<Integer>> squareMatrix) {

    for (int i = 0; i < Math.ceil(0.5 * squareMatrix.size()); i++) { // layer
      for (int j = i; j < squareMatrix.size() - 1 - i; j++) {
        int t1 = squareMatrix.get(squareMatrix.size() - 1 - j).get(i);
        int t2 = squareMatrix.get(squareMatrix.size() - 1 - i).get(squareMatrix.size() - 1 - j);
        int t3 = squareMatrix.get(j).get(squareMatrix.size() - 1 - i);
        int t4 = squareMatrix.get(i).get(j);

        squareMatrix.get(i).set(j, t1);
        squareMatrix.get(j).set(squareMatrix.size() - 1 - i, t4);
        squareMatrix.get(squareMatrix.size() - 1 - i).set(squareMatrix.size() - 1 - j, t3);
        squareMatrix.get(squareMatrix.size() - 1 - j).set(i, t2);
      }
    }
    return;
  }

  @EpiTest(testfile = "matrix_rotation.tsv")
  public static List<List<Integer>>
  rotateMatrixWrapper(List<List<Integer>> squareMatrix) {
    rotateMatrix(squareMatrix);
    return squareMatrix;
  }

  public static void main(String[] args) {
    GenericTestHandler.executeTestsByAnnotation(
        new Object() {}.getClass().getEnclosingClass(), args);
  }
}
