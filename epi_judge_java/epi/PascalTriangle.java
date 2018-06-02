package epi;

import epi.test_framework.EpiTest;
import epi.test_framework.GenericTestHandler;

import java.util.ArrayList;
import java.util.List;

public class PascalTriangle {
  @EpiTest(testfile = "pascal_triangle.tsv")
  public static List<List<Integer>> generatePascalTriangle(int numRows) {
    List<List<Integer>> r = new ArrayList<>();
    for (int i = 0; i < numRows; i++) {
      List<Integer> currRow = new ArrayList<>();
      for (int j = 0; j <= i; j++) {
        currRow.add(j, (j==0 || j==i) ? 1 : r.get(i-1).get(j-1)+r.get(i-1).get(j));
      }
      r.add(currRow);
    }
    return r;
  }

  public static void main(String[] args) {
    GenericTestHandler.executeTestsByAnnotation(
        new Object() {}.getClass().getEnclosingClass(), args);
  }
}
