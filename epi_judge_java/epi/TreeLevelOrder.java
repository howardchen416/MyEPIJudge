package epi;

import epi.test_framework.EpiTest;
import epi.test_framework.GenericTestHandler;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class TreeLevelOrder {
  @EpiTest(testfile = "tree_level_order.tsv")

  public static List<List<Integer>> binaryTreeDepthOrder(BinaryTreeNode<Integer> tree) {
    Queue<BinaryTreeNode<Integer>> nodeQueue = new LinkedList<BinaryTreeNode<Integer>>();
    nodeQueue.add(tree);
    int numToProcAtCurrLvl = nodeQueue.size();
    List<List<Integer>> res = new ArrayList<>();
    List<Integer> currLevelData = new ArrayList<>();

    while (!nodeQueue.isEmpty()) {
      BinaryTreeNode<Integer> currTreeNode = nodeQueue.poll();
      --numToProcAtCurrLvl;
      if (currTreeNode!=null) {
        currLevelData.add(currTreeNode.data);
        nodeQueue.add(currTreeNode.left);
        nodeQueue.add(currTreeNode.right);
      }
      if (numToProcAtCurrLvl==0) { // complete processing for one level, moving on to the next level
        numToProcAtCurrLvl = nodeQueue.size();
        if (currLevelData!=null && currLevelData.size()>0) {
          res.add(currLevelData);
          currLevelData = new ArrayList<>();
        }
      }
    }

    return res;
  }

  public static void main(String[] args) {
    GenericTestHandler.executeTestsByAnnotation(
        new Object() {}.getClass().getEnclosingClass(), args);
  }
}
