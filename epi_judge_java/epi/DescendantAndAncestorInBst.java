package epi;

import epi.test_framework.BinaryTreeUtils;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTestHandler;
import epi.test_framework.TestTimer;

public class DescendantAndAncestorInBst {

  public static boolean
  pairIncludesAncestorAndDescendantOfM(BstNode<Integer> possibleAncOrDesc0,
                                       BstNode<Integer> possibleAncOrDesc1,
                                       BstNode<Integer> middle) {
    // phase 1 - traverse from both nodes downward, checking whether if we hit middle
    BstNode<Integer> i0 = possibleAncOrDesc0;
    BstNode<Integer> i1 = possibleAncOrDesc1;
    while (i0 != middle && i1 != middle &&
            i0 != possibleAncOrDesc1 && i1 != possibleAncOrDesc0 &&
            (i0 != null || i1 != null)) {
      if (i0 != null) {
        if (i0 != middle) i0 = (Integer.compare(i0.data, middle.data)>0) ? i0.left : i0.right;
        else break;
      }
      if (i1 != null) {
        if (i1 != middle) i1 = (Integer.compare(i1.data, middle.data)>0) ? i1.left : i1.right;
        else break;
      }
    }

    if (i0 == possibleAncOrDesc1 || i1 == possibleAncOrDesc0 || (i0 == null && i1 == null)) return false;

    return (i0==middle) ? findTarget(i0, possibleAncOrDesc1) : findTarget(i1, possibleAncOrDesc0);
  }

  private static boolean findTarget(BstNode<Integer> from, BstNode<Integer> target) {
    // 2nd phase  - traverse from middle downward, checking if we hit the target
    while (from!=null && Integer.compare(from.data, target.data)!=0) {
      if (Integer.compare(from.data, target.data)>0) from = from.left;
      else from = from.right;
    }

    return (from==null) ? false : true;
  }

  @EpiTest(testfile = "descendant_and_ancestor_in_bst.tsv")
  public static boolean pairIncludesAncestorAndDescendantOfMWrapper(
      TestTimer timer, BstNode<Integer> tree, int possibleAncOrDesc0,
      int possibleAncOrDesc1, int middle) {
    BstNode<Integer> candidate0 =
        BinaryTreeUtils.mustFindNode(tree, possibleAncOrDesc0);
    BstNode<Integer> candidate1 =
        BinaryTreeUtils.mustFindNode(tree, possibleAncOrDesc1);
    BstNode<Integer> middleNode = BinaryTreeUtils.mustFindNode(tree, middle);

    timer.start();
    boolean result = pairIncludesAncestorAndDescendantOfM(
        candidate0, candidate1, middleNode);
    timer.stop();
    return result;
  }

  public static void main(String[] args) {
    GenericTestHandler.executeTestsByAnnotation(
        new Object() {}.getClass().getEnclosingClass(), args);
  }
}
