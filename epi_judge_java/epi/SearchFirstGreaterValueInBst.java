package epi;

import epi.test_framework.EpiTest;
import epi.test_framework.GenericTestHandler;

public class SearchFirstGreaterValueInBst {

  public static BstNode<Integer> findFirstGreaterThanK(BstNode<Integer> tree,
                                                       Integer k) {
    // special condition(s)
    if (tree==null || (tree.left==null && tree.right==null)) return null;

    BstNode<Integer> iter = tree;
    BstNode<Integer> bestSoFar = null;
    while (iter!=null) {
      if(iter.data>k) {
        bestSoFar = iter;
        iter = iter.left;
      } else {
        iter = iter.right;
      }
    }
    return bestSoFar;
  }


  @EpiTest(testfile = "search_first_greater_value_in_bst.tsv")
  public static int findFirstGreaterThanKWrapper(BstNode<Integer> tree,
                                                 Integer k) {
    BstNode<Integer> result = findFirstGreaterThanK(tree, k);
    return result != null ? result.data : -1;
  }

  public static void main(String[] args) {
    GenericTestHandler.executeTestsByAnnotation(
        new Object() {}.getClass().getEnclosingClass(), args);
  }
}
