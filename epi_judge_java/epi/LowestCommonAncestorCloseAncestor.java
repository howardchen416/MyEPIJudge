package epi;

import epi.test_framework.BinaryTreeUtils;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTestHandler;
import epi.test_framework.TestFailureException;
import epi.test_framework.TestTimer;

public class LowestCommonAncestorCloseAncestor {

  public static class NumNodesWithSubroot {
    int numNodes;
    BinaryTree<Integer> subRoot;

    public NumNodesWithSubroot(int numNodes, BinaryTree<Integer> subRoot) {
      this.numNodes = numNodes;
      this.subRoot = subRoot;
    }
  }

  public static BinaryTree<Integer> findRoot(BinaryTree<Integer> node) {
    // base condition
    if (node==null) return null;
    while (node.parent!=null) {
      node = node.parent;
    }
    return node;
  }

  public static int findDepth(BinaryTree<Integer> root, BinaryTree<Integer> node) {
    int r = 0;
    while (node!=root) {
      node = node.parent;
      r++;
    }
    return r;
  }

  public static BinaryTree<Integer> LCA(BinaryTree<Integer> node0,
                                        BinaryTree<Integer> node1) {
    BinaryTree<Integer> root = findRoot(node0);
    int d0 = findDepth(root, node0);
    int d1 = findDepth(root, node1);
    if (d0>d1) {
      int c = d0 - d1;
      while (c-- > 0) {
        node0 = node0.parent;
      }
    }
    else {
      int c = d1 - d0;
      while (c-- > 0) {
        node1 = node1.parent;
      }
    }
    // at same depth
    while (node0!=null && node1!=null && node0 != node1) {
      node0 = node0.parent;
      node1 = node1.parent;
    }
    return node0;
  }

  @EpiTest(testfile = "lowest_common_ancestor.tsv")
  public static int lcaWrapper(TestTimer timer, BinaryTree<Integer> tree,
                               Integer key1, int key2)
      throws TestFailureException {
    timer.start();
    BinaryTree<Integer> result = LCA(BinaryTreeUtils.mustFindNode(tree, key1),
                                     BinaryTreeUtils.mustFindNode(tree, key2));
    timer.stop();

    if (result == null) {
      throw new TestFailureException("Result can not be null");
    }
    return result.data;
  }

  public static void main(String[] args) {
    GenericTestHandler.executeTestsByAnnotation(
        new Object() {}.getClass().getEnclosingClass(), args);
  }
}
