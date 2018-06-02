package epi;

import epi.test_framework.EpiTest;
import epi.test_framework.GenericTestHandler;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class IsTreeABst {

/*  public static boolean isBinaryTreeBST(BinaryTreeNode<Integer> tree) {
    // boundary condition(s)
    if (tree==null || (tree.left==null && tree.right==null)) return true;

    return isBinaryTreeBSTHelper(tree, Integer.MIN_VALUE, Integer.MAX_VALUE);
  }

  public static boolean isBinaryTreeBSTHelper(BinaryTreeNode<Integer> tree, int leftBound, int rightBound) {
    if (tree==null) return true;
    if (Integer.compare(tree.data, leftBound)<0 || Integer.compare(tree.data, rightBound)>0) return false;

    return isBinaryTreeBSTHelper(tree.left, leftBound, tree.data) && isBinaryTreeBSTHelper(tree.right, tree.data, rightBound);
  }*/

  public static class QueueEntry {
    BinaryTreeNode<Integer> treeNode;
    int leftBound;
    int rightBound;

    public QueueEntry(BinaryTreeNode<Integer> treeNode, int leftBound, int rightBound) {
      this.treeNode = treeNode;
      this.leftBound = leftBound;
      this.rightBound = rightBound;
    }
  }

  @EpiTest(testfile = "is_tree_a_bst.tsv")
  public static boolean isBinaryTreeBST(BinaryTreeNode<Integer> tree) {
    // boundary condition(s)
    if (tree==null || (tree.left==null && tree.right==null)) return true;

    // set up a Queue
    Queue<QueueEntry> q = new LinkedList<>();

    // push in root first as a seed
    q.offer(new QueueEntry(tree, Integer.MIN_VALUE, Integer.MAX_VALUE));

    // loop for processing entries in queue
    while(!q.isEmpty()) {
      QueueEntry qe = q.poll();

      if (qe!=null && qe.treeNode!=null) {
        if (qe.treeNode.data < qe.leftBound || qe.treeNode.data > qe.rightBound) return false;
        q.offer(new QueueEntry(qe.treeNode.left, qe.leftBound, qe.treeNode.data));
        q.offer(new QueueEntry(qe.treeNode.right, qe.treeNode.data, qe.rightBound));
      }
    }

    return true;
  }

  public static void main(String[] args) {
    GenericTestHandler.executeTestsByAnnotation(
        new Object() {}.getClass().getEnclosingClass(), args);
  }
}
