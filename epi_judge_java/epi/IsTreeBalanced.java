package epi;

import epi.test_framework.EpiTest;
import epi.test_framework.GenericTestHandler;

public class IsTreeBalanced {

  public static class BalanceStatusWithHeight {
    int height;
    boolean balanced;

    public BalanceStatusWithHeight(int height, boolean balanced) {
      this.height = height;
      this.balanced = balanced;
    }
  }

  @EpiTest(testfile = "is_tree_balanced.tsv")

  public static boolean isBalanced(BinaryTreeNode<Integer> tree) {
    BalanceStatusWithHeight res = checkBalanced(tree);
    return res.balanced;
  }

  public static BalanceStatusWithHeight checkBalanced(BinaryTreeNode<Integer> tree) {
    // base condition
    if (tree==null)
      return new BalanceStatusWithHeight(-1, true);

    BalanceStatusWithHeight ls = checkBalanced(tree.left);
    if (!ls.balanced)
      return ls;

    BalanceStatusWithHeight rs = checkBalanced(tree.right);
    if (!rs.balanced)
      return rs;

    boolean balanced = Math.abs(ls.height - rs.height) <= 1;
    int height = Math.max(ls.height, rs.height) + 1;

    return new BalanceStatusWithHeight(height, balanced);
  }

  public static void main(String[] args) {
    GenericTestHandler.executeTestsByAnnotation(
        new Object() {}.getClass().getEnclosingClass(), args);
  }
}
