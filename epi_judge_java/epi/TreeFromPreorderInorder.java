package epi;

import epi.test_framework.EpiTest;
import epi.test_framework.GenericTestHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TreeFromPreorderInorder {
  @EpiTest(testfile = "tree_from_preorder_inorder.tsv")

  public static BinaryTreeNode<Integer>
  binaryTreeFromPreorderInorder(List<Integer> po, List<Integer> io) {

    Map<Integer, Integer> m = new HashMap<>();
    for (int i = 0; i < io.size(); i++) {
      m.put(io.get(i), i);
    }
    return binaryTreeFromPreorderInorderHelper(po, 0, io.size(), 0, m);
  }

  public static BinaryTreeNode<Integer> binaryTreeFromPreorderInorderHelper(final List<Integer> po, int ios, int ioe, int pos, final Map<Integer, Integer> m) {
    // base condition
    if (ios >= ioe)
      return null;

    int ioIdx = m.get(po.get(pos));
    int leftSubtreeSize = ioIdx - ios;

    return new BinaryTreeNode<Integer>(
            po.get(pos),
            binaryTreeFromPreorderInorderHelper(po, ios, ios+leftSubtreeSize, pos+1, m),
            binaryTreeFromPreorderInorderHelper(po, ioIdx+1, ioe, pos+1+leftSubtreeSize, m));
  }

  public static void main(String[] args) {
    GenericTestHandler.executeTestsByAnnotation(
        new Object() {}.getClass().getEnclosingClass(), args);
  }
}
