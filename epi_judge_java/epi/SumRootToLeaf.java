package epi;

import epi.test_framework.EpiTest;
import epi.test_framework.GenericTestHandler;

public class SumRootToLeaf {
  @EpiTest(testfile = "sum_root_to_leaf.tsv")

  public static int sumRootToLeaf(BinaryTreeNode<Integer> tree) {
    return sumRootToLeafHelper(tree, 0);
    //return 0;
  }

  public static int sumRootToLeafHelper(BinaryTreeNode<Integer> tree, int p) {
    if (tree==null) return 0;
    int c = p*2 + tree.data;
    // boundary condition - leaf node
    if (tree.left==null && tree.right==null) return c;
    return sumRootToLeafHelper(tree.left, c) + sumRootToLeafHelper(tree.right, c);
  }

  public static void main(String[] args) {
    GenericTestHandler.executeTestsByAnnotation(
        new Object() {}.getClass().getEnclosingClass(), args);
  }
}
