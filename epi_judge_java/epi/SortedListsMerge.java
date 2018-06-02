package epi;

import epi.test_framework.EpiTest;
import epi.test_framework.GenericTestHandler;

public class SortedListsMerge {
  @EpiTest(testfile = "sorted_lists_merge.tsv")
  //@include
  public static ListNode<Integer> mergeTwoSortedLists(ListNode<Integer> L1,
                                                      ListNode<Integer> L2) {
    ListNode<Integer> d = new ListNode<>(0, null);
    ListNode<Integer> c = d;
    ListNode<Integer> p1 = L1;
    ListNode<Integer> p2 = L2;

    while (p1!=null && p2!=null) {
      if (p1.data <= p2.data) {
        c.next = p1;
        p1 = p1.next;
      } else {
        c.next = p2;
        p2 = p2.next;
      }
      c = c.next;
    }
    if (p1==null) c.next = p2;
    else c.next = p1;

    return d.next;
  }

  public static void main(String[] args) {
    GenericTestHandler.executeTestsByAnnotation(
        new Object() {}.getClass().getEnclosingClass(), args);
  }
}
