package epi;

import epi.test_framework.EpiTest;
import epi.test_framework.GenericTestHandler;

public class IsTreeSymmetric {
  @EpiTest(testfile = "is_tree_symmetric.tsv")

  public static boolean isSymmetric(BinaryTreeNode<Integer> tree) {
    return (tree==null || checkSymmetry(tree.left, tree.right));
  }

  public static boolean checkSymmetry(BinaryTreeNode<Integer> lTree, BinaryTreeNode<Integer> rTree) {
    // base condition
    if (lTree==null && rTree==null) return true;
    if ((lTree==null && rTree!=null) || (lTree!=null && rTree==null) || (lTree!=null && rTree!=null && lTree.data!=rTree.data)) return false;

    if (!checkSymmetry(lTree.left, rTree.right)) return false;
    if (!checkSymmetry(lTree.right, rTree.left)) return false;

    return (lTree.data==rTree.data);
  }

  public static void main(String[] args) {
    GenericTestHandler.executeTestsByAnnotation(
        new Object() {}.getClass().getEnclosingClass(), args);
  }
}
