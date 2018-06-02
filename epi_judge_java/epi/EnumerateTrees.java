package epi;

import epi.test_framework.EpiTest;
import epi.test_framework.LexicographicalListComparator;
import epi.test_framework.GenericTestHandler;
import epi.test_framework.TestTimer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

public class EnumerateTrees {

  public static List<BinaryTreeNode<Integer>> generateAllBinaryTrees(int numNodes) {

    List<BinaryTreeNode<Integer>> r = new ArrayList<>();
    if (numNodes==0) {
      r.add(null);
      return r;
    }

    for (int lts = 0; lts <= numNodes-1; lts++) {
      int rts = numNodes - 1 - lts;
      List<BinaryTreeNode<Integer>> leftSubtrees = generateAllBinaryTrees(lts);
      List<BinaryTreeNode<Integer>> rightSubtrees = generateAllBinaryTrees(rts);
      for (int i = 0; i < leftSubtrees.size(); i++) {
        for (int j = 0; j < rightSubtrees.size(); j++) {
          r.add(new BinaryTreeNode<Integer>(0, leftSubtrees.get(i), rightSubtrees.get(j)));
        }
      }
    }

    return r;
  }

  public static List<Integer> serializeStructure(BinaryTreeNode<Integer> tree) {
    List<Integer> result = new ArrayList<>();
    Stack<BinaryTreeNode<Integer>> stack = new Stack<>();
    stack.push(tree);
    while (!stack.empty()) {
      BinaryTreeNode<Integer> p = stack.pop();
      result.add(p == null ? 0 : 1);
      if (p != null) {
        stack.push(p.left);
        stack.push(p.right);
      }
    }
    return result;
  }

  @EpiTest(testfile = "enumerate_trees.tsv")
  public static List<List<Integer>>
  generateAllBinaryTreesWrapper(TestTimer timer, int numNodes) {
    timer.start();
    List<BinaryTreeNode<Integer>> result = generateAllBinaryTrees(numNodes);
    timer.stop();

    List<List<Integer>> serialized = new ArrayList<>();
    for (BinaryTreeNode<Integer> x : result) {
      serialized.add(serializeStructure(x));
    }
    serialized.sort(new LexicographicalListComparator<>());
    return serialized;
  }

  public static void main(String[] args) {
    GenericTestHandler.executeTestsByAnnotation(
        new Object() {}.getClass().getEnclosingClass(), args);
  }
}
