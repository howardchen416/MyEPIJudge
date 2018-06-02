package epi;

import epi.test_framework.EpiTest;
import epi.test_framework.GenericTestHandler;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

public class TreePreorder {
  @EpiTest(testfile = "tree_preorder.tsv")

  public static List<Integer> preorderTraversal(BinaryTreeNode<Integer> tree) {

    Deque<BinaryTreeNode<Integer>> st = new LinkedList<BinaryTreeNode<Integer>>();
    List<Integer> r = new ArrayList<>();
    if (tree!=null) st.addFirst(tree);
    while (!st.isEmpty()) {
      BinaryTreeNode<Integer> c = st.removeFirst();
      r.add(c.data);
      if (c.right!=null) st.addFirst(c.right);
      if (c.left!=null) st.addFirst(c.left);
    }
    return r;
  }

  public static void main(String[] args) {
    GenericTestHandler.executeTestsByAnnotation(
        new Object() {}.getClass().getEnclosingClass(), args);
  }
}
