package epi;

import epi.test_framework.EpiTest;
import epi.test_framework.GenericTestHandler;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

public class TreeInorder {
  @EpiTest(testfile = "tree_inorder.tsv")

  public static List<Integer> inorderTraversal(BinaryTreeNode<Integer> tree) {

    Deque<BinaryTreeNode<Integer>> stack = new LinkedList<>();
    List<Integer> r = new ArrayList<Integer>();
    BinaryTreeNode<Integer> curr = tree;

    while(curr!=null || !stack.isEmpty()) {
      if (curr!=null) {
        stack.addFirst(curr);
        curr = curr.left;
      } else { // curr==null
        curr = stack.pollFirst();
        r.add(curr.data);
        curr = curr.right;
      }
    }

    return r;

  }

  public static void main(String[] args) {
    GenericTestHandler.executeTestsByAnnotation(
        new Object() {}.getClass().getEnclosingClass(), args);
  }
}
