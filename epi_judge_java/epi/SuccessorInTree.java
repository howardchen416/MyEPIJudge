package epi;

import epi.test_framework.BinaryTreeUtils;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTestHandler;
import epi.test_framework.TestTimer;

public class SuccessorInTree {

  public static BinaryTree<Integer> findSuccessor(BinaryTree<Integer> node) {
    // special condition(s)
    if (node==null) return null;

    BinaryTree<Integer> iter;
    // try right child first
    if (node.right!=null) {
      iter = node.right;
      while (iter.left!=null) iter = iter.left;
      //return iter;
    }
    else { // no right child, find ancestor
      iter = node;
      while (iter.parent!=null && iter==iter.parent.right) {
        iter = iter.parent;
      }
      iter = iter.parent;
    }
    return iter;
  }

  @EpiTest(testfile = "successor_in_tree.tsv")
  public static int
  findSuccessorWrapper(TestTimer timer, BinaryTree<Integer> tree, int nodeIdx) {
    BinaryTree<Integer> n = BinaryTreeUtils.mustFindNode(tree, nodeIdx);

    timer.start();
    BinaryTree<Integer> result = findSuccessor(n);
    timer.stop();

    return result == null ? -1 : result.data;
  }

  public static void main(String[] args) {
    GenericTestHandler.executeTestsByAnnotation(
        new Object() {}.getClass().getEnclosingClass(), args);
  }
}
