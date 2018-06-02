package epi;

import epi.test_framework.BinaryTreeUtils;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTestHandler;
import epi.test_framework.TestFailureException;
import epi.test_framework.TestTimer;

public class LowestCommonAncestor {

    private static class Status {
        int numNodes;
        BinaryTreeNode<Integer> ancestor;

        public Status(int numNodes, BinaryTreeNode<Integer> ancestor) {
            this.numNodes = numNodes;
            this.ancestor = ancestor;
        }
    }

    public static BinaryTreeNode<Integer> LCA(BinaryTreeNode<Integer> tree,
                                              BinaryTreeNode<Integer> node0,
                                              BinaryTreeNode<Integer> node1) {
        return LCAHelper(tree, node0, node1).ancestor;
    }

    public static Status LCAHelper(BinaryTreeNode<Integer> tree,
                                   BinaryTreeNode<Integer> node0,
                                   BinaryTreeNode<Integer> node1) {

        if (tree == null)
            return new Status(0, null);

        Status leftStatus = LCAHelper(tree.left, node0, node1);
        if (leftStatus.ancestor != null)
            return leftStatus;
        Status rightStatus = LCAHelper(tree.right, node0, node1);
        if (rightStatus.ancestor != null)
            return rightStatus;
        int numNodes = leftStatus.numNodes + rightStatus.numNodes + ((tree == node0) ? 1 : 0) + ((tree == node1) ? 1 : 0);
        return new Status(numNodes, (numNodes == 2) ? tree : null);
    }

    @EpiTest(testfile = "lowest_common_ancestor.tsv")
    public static int lcaWrapper(TestTimer timer, BinaryTreeNode<Integer> tree,
                                 Integer node0, int node1)
            throws TestFailureException {
        timer.start();
        BinaryTreeNode<Integer> result =
                LCA(tree, BinaryTreeUtils.mustFindNode(tree, node0),
                        BinaryTreeUtils.mustFindNode(tree, node1));
        timer.stop();

        if (result == null) {
            throw new TestFailureException("Result can not be null");
        }
        return result.data;
    }

    public static void main(String[] args) {
        GenericTestHandler.executeTestsByAnnotation(
                new Object() {
                }.getClass().getEnclosingClass(), args);
    }
}
