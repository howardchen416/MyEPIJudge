package epi;

import epi.test_framework.BinaryTreeUtils;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTestHandler;
import epi.test_framework.TestFailureException;
import epi.test_framework.TestTimer;

import java.util.List;

public class BstFromSortedArray {

  public static BstNode<Integer>
  buildMinHeightBSTFromSortedArray(List<Integer> A) {

    return buildMinHeightBSTFromSortedArrayHelper(A, 0, A.size());
  }

  public static BstNode<Integer>
  buildMinHeightBSTFromSortedArrayHelper(List<Integer> A, int start, int end) {
    // base condition
    if (start==end) return null;
    if (start+1==end) return new BstNode<Integer>(A.get(start), null, null);

    int middle = start + ((end - start)/2);

    return new BstNode<Integer>(A.get(middle),
            buildMinHeightBSTFromSortedArrayHelper(A, start, middle),
            buildMinHeightBSTFromSortedArrayHelper(A, middle + 1, end));
  }


  @EpiTest(testfile = "bst_from_sorted_array.tsv")
  public static int buildMinHeightBSTFromSortedArrayWrapper(TestTimer timer,
                                                            List<Integer> A)
      throws TestFailureException {
    timer.start();
    BstNode<Integer> result = buildMinHeightBSTFromSortedArray(A);
    timer.stop();

    if (!BinaryTreeUtils.generateInorder(result).equals(A)) {
      throw new TestFailureException(
          "Result binary tree mismatches input array");
    }

    return BinaryTreeUtils.binaryTreeHeight(result);
  }

  public static void main(String[] args) {
    GenericTestHandler.executeTestsByAnnotation(
        new Object() {}.getClass().getEnclosingClass(), args);
  }
}
