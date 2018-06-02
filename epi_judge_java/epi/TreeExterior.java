package epi;

import epi.test_framework.EpiTest;
import epi.test_framework.GenericTestHandler;
import epi.test_framework.TestFailureException;
import epi.test_framework.TestTimer;

import java.util.ArrayList;
import java.util.List;

public class TreeExterior {

  public static List<BinaryTreeNode<Integer>> exteriorBinaryTree(BinaryTreeNode<Integer> tree) {
    List<BinaryTreeNode<Integer>> r = new ArrayList<>();
    if (tree!=null) {
        r.add(tree);
        exteriorBinaryTreeHelperLeft(tree.left, true, r);
        exteriorBinaryTreeHelperRight(tree.right, true, r);
    }
    return r;
  }

  public static List<BinaryTreeNode<Integer>> exteriorBinaryTreeHelperLeft(BinaryTreeNode<Integer> subtree, boolean isBoundary, List<BinaryTreeNode<Integer>> r) {
    // base condition
    if (subtree==null) return r;

    if (isBoundary || isLeaf(subtree))
      r.add(subtree);

    exteriorBinaryTreeHelperLeft(subtree.left, isBoundary, r);
    exteriorBinaryTreeHelperLeft(subtree.right, isBoundary && subtree.left==null, r);

    return r;
  }

  public static List<BinaryTreeNode<Integer>> exteriorBinaryTreeHelperRight(BinaryTreeNode<Integer> subtree, boolean isBoundary, List<BinaryTreeNode<Integer>> r) {
    // base condition
    if (subtree==null) return r;

    exteriorBinaryTreeHelperRight(subtree.left, isBoundary && subtree.right==null, r);
    exteriorBinaryTreeHelperRight(subtree.right, isBoundary, r);

    if (isBoundary || isLeaf(subtree))
      r.add(subtree);

    return r;
  }

  public static boolean isLeaf(BinaryTreeNode<Integer> node) {
    return (node.left==null && node.right==null);
  }

  private static List<Integer> createOutputList(List<BinaryTreeNode<Integer>> L)
      throws TestFailureException {
    if (L.contains(null)) {
      throw new TestFailureException("Resulting list contains null");
    }
    List<Integer> output = new ArrayList<>();
    for (BinaryTreeNode<Integer> l : L) {
      output.add(l.data);
    }
    return output;
  }

  @EpiTest(testfile = "tree_exterior.tsv")
  public static List<Integer>
  exteriorBinaryTreeWrapper(TestTimer timer, BinaryTreeNode<Integer> tree)
      throws TestFailureException {
    timer.start();
    List<BinaryTreeNode<Integer>> l = exteriorBinaryTree(tree);
    timer.stop();
    return createOutputList(l);
  }

  public static void main(String[] args) {
    GenericTestHandler.executeTestsByAnnotation(
        new Object() {}.getClass().getEnclosingClass(), args);
  }
}
