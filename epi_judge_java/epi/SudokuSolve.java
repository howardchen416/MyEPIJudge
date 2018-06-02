package epi;

import epi.test_framework.EpiTest;
import epi.test_framework.GenericTestHandler;
import epi.test_framework.TestFailureException;
import epi.test_framework.TestTimer;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class SudokuSolve {

  public static boolean solveSudoku(List<List<Integer>> partialAssignment) {
    return solvePartialSudoku(0, 0, partialAssignment);
  }

  public static int EMPTY_ENTRY = 0;

  public static boolean solvePartialSudoku(int i, int j, List<List<Integer>> partialAssignment) {
    if (i==partialAssignment.size()) { // start a new col
      i = 0;
      if (++j == partialAssignment.size()) return true;
    }

    if (partialAssignment.get(i).get(j)!=EMPTY_ENTRY) {
      return solvePartialSudoku(i+1, j, partialAssignment);
    }

    for (int k = 1; k <= partialAssignment.size(); k++) {
      if (isValidAssignment(partialAssignment, i, j, k)) {
        partialAssignment.get(i).set(j, k);
        if (solvePartialSudoku(i+1, j, partialAssignment))
          return true;
      }
    }
    // dead end, back track by unseting val
    partialAssignment.get(i).set(j, EMPTY_ENTRY);
    return false;
  }

  public static boolean isValidAssignment(List<List<Integer>> partialAssignment, int i, int j, int val) {
    // check column
    for (List<Integer> row : partialAssignment) {
      if (row.get(j) == val) return false;
    }

    // check row
    for (int k = 0; k < partialAssignment.size(); k++) {
      List<Integer> row = partialAssignment.get(i);
      if (row.get(k) == val) return false;
    }

    // check region
    int X = i / 3;
    int Y = j / 3;
    for (int k = 0; k < 3; k++) {
      for (int l = 0; l < 3; l++) {
        if (partialAssignment.get(X * 3 + k).get(Y * 3 + l)==val) return false;
      }
    }
    return true;
  }

  @EpiTest(testfile = "sudoku_solve.tsv")
  public static void solveSudokuWrapper(TestTimer timer,
                                        List<List<Integer>> partialAssignment)
      throws TestFailureException {
    List<List<Integer>> solved = new ArrayList<>();
    for (List<Integer> row : partialAssignment) {
      List<Integer> copy = new ArrayList<>();
      copy.addAll(row);
      solved.add(copy);
    }

    timer.start();
    solveSudoku(solved);
    timer.stop();

    if (partialAssignment.size() != solved.size()) {
      throw new TestFailureException(
          "Initial cell assignment has been changed");
    }

    for (int i = 0; i < partialAssignment.size(); i++) {
      List<Integer> br = partialAssignment.get(i);
      List<Integer> sr = solved.get(i);
      if (br.size() != sr.size()) {
        throw new TestFailureException(
            "Initial cell assignment has been changed");
      }
      for (int j = 0; j < br.size(); j++)
        if (br.get(j) != 0 && !Objects.equals(br.get(j), sr.get(j)))
          throw new TestFailureException(
              "Initial cell assignment has been changed");
    }

    int blockSize = (int)Math.sqrt(solved.size());
    for (int i = 0; i < solved.size(); i++) {
      assertUniqueSeq(solved.get(i));
      assertUniqueSeq(gatherColumn(solved, i));
      assertUniqueSeq(gatherSquareBlock(solved, blockSize, i));
    }
  }

  private static void assertUniqueSeq(List<Integer> seq)
      throws TestFailureException {
    Set<Integer> seen = new HashSet<>();
    for (Integer x : seq) {
      if (x == 0) {
        throw new TestFailureException("Cell left uninitialized");
      }
      if (x < 0 || x > seq.size()) {
        throw new TestFailureException("Cell value out of range");
      }
      if (seen.contains(x)) {
        throw new TestFailureException("Duplicate value in section");
      }
      seen.add(x);
    }
  }

  private static List<Integer> gatherColumn(List<List<Integer>> data, int i) {
    List<Integer> result = new ArrayList<>();
    for (List<Integer> row : data) {
      result.add(row.get(i));
    }
    return result;
  }

  private static List<Integer> gatherSquareBlock(List<List<Integer>> data,
                                                 int blockSize, int n) {
    List<Integer> result = new ArrayList<>();
    int blockX = n % blockSize;
    int blockY = n / blockSize;
    for (int i = blockX * blockSize; i < (blockX + 1) * blockSize; i++) {
      for (int j = blockY * blockSize; j < (blockY + 1) * blockSize; j++) {
        result.add(data.get(i).get(j));
      }
    }

    return result;
  }

  public static void main(String[] args) {
    GenericTestHandler.executeTestsByAnnotation(
        new Object() {}.getClass().getEnclosingClass(), args);
  }
}
