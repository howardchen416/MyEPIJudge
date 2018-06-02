package epi;

import epi.test_framework.EpiTest;
import epi.test_framework.GenericTestHandler;

import java.util.Iterator;
import java.util.List;

public class BstFromPreorder {
  @EpiTest(testfile = "bst_from_preorder.tsv")

  public static BstNode<Integer>
  rebuildBSTFromPreorder(List<Integer> preorderSequence) {
    // special condition(s)
    if (preorderSequence==null || preorderSequence.size()==0) return null;
    if (preorderSequence.size()==1) return new BstNode<Integer>(preorderSequence.get(0), null, null);

    //
    return rebuildBSTFromPreorderHelper(preorderSequence, 0, preorderSequence.size());

  }

  public static BstNode<Integer>
  rebuildBSTFromPreorderHelper(List<Integer> preorderSequence, int start, int end) {
    // base condition - very important!!!
    if (start==end) return null;
    //if (start+1==end) return new BstNode(preorderSequence.get(start), null, null);

    int inversionPoint = start+1;
    while ((inversionPoint < end) && Integer.compare(preorderSequence.get(inversionPoint), preorderSequence.get(start))<0) {
      inversionPoint++;
    }
    BstNode<Integer> l = rebuildBSTFromPreorderHelper(preorderSequence, start+1, inversionPoint);
    BstNode<Integer> r = rebuildBSTFromPreorderHelper(preorderSequence, inversionPoint, end);

    return new BstNode(preorderSequence.get(start), l, r);
  }

  public static void main(String[] args) {
    GenericTestHandler.executeTestsByAnnotation(
        new Object() {}.getClass().getEnclosingClass(), args);
  }
}
