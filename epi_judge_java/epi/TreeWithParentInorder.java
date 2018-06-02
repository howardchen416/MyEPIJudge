package epi;

import epi.test_framework.EpiTest;
import epi.test_framework.GenericTestHandler;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

public class TreeWithParentInorder {
  @EpiTest(testfile = "tree_with_parent_inorder.tsv")

  public static List<Integer> inorderTraversal(BinaryTree<Integer> tree) {
    List<Integer> r = new ArrayList<>();
    BinaryTree<Integer> prev = null;
    BinaryTree<Integer> curr = tree;
    while (curr!=null) {
      BinaryTree<Integer> next;
      if (curr.parent == prev) {
        if (curr.left!=null) {
          next = curr.left;
        }
        else {
          r.add(curr.data);
          next = (curr.right==null) ? curr.parent : curr.right;
        }
      }
      else if (curr.left == prev) {       // case 2
        r.add(curr.data);
        next = (curr.right==null) ? curr.parent : curr.right;
      }
      else { // prev = current.right
        next = curr.parent;
      }
      prev = curr;
      curr = next;
    }

    return r;
  }

  public static void main(String[] args) {
    GenericTestHandler.executeTestsByAnnotation(
        new Object() {}.getClass().getEnclosingClass(), args);
  }
}
