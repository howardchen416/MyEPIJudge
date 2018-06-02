package epi;

import epi.test_framework.EpiTest;
import epi.test_framework.GenericTestHandler;

public class PathSum {
  @EpiTest(testfile = "path_sum.tsv")

  public static boolean hasPathSum(BinaryTreeNode<Integer> tree,
                                   int remainingWeight) {
    return hasPathSumHelper(tree, remainingWeight);
  }

  public static boolean hasPathSumHelper(BinaryTreeNode<Integer> tree, int remainingWeight) {
    if (tree==null) return false;

    remainingWeight -= tree.data;

    if (tree.left==null && tree.right==null) { // leaf node
      return (remainingWeight==0);
    }

    //
    return hasPathSumHelper(tree.left, remainingWeight) || hasPathSumHelper(tree.right, remainingWeight);
  }

  public static void main(String[] args) {
    GenericTestHandler.executeTestsByAnnotation(
        new Object() {}.getClass().getEnclosingClass(), args);
  }
}
