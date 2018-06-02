package epi;

import epi.test_framework.EpiTest;
import epi.test_framework.EpiTestComparator;
import epi.test_framework.GenericTestHandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.BiPredicate;

public class KLargestValuesInBst {
  @EpiTest(testfile = "k_largest_values_in_bst.tsv")

  public static List<Integer> findKLargestInBst(BstNode<Integer> tree, int k) {
    List<Integer> r = new ArrayList<>();
    findKLargestInBstHelper(tree, r, k);

    return r;
  }

  public static void findKLargestInBstHelper(BstNode<Integer> tree, List<Integer> r, int k) {
    // reverse in-order traversal, right subtree first
    if (tree!=null && r.size()<k) {
      findKLargestInBstHelper(tree.right, r, k);
      if (r.size()<k) {
        r.add(tree.data);
        findKLargestInBstHelper(tree.left, r, k);
      }
    }
    return;
  }

  @EpiTestComparator
  public static BiPredicate<List<Integer>, List<Integer>> comp =
      (expected, result) -> {
    if (result == null) {
      return false;
    }
    Collections.sort(expected);
    Collections.sort(result);
    return expected.equals(result);
  };

  public static void main(String[] args) {
    GenericTestHandler.executeTestsByAnnotation(
        new Object() {}.getClass().getEnclosingClass(), args);
  }
}
