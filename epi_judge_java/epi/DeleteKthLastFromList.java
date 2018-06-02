package epi;

import epi.test_framework.EpiTest;
import epi.test_framework.GenericTestHandler;

public class DeleteKthLastFromList {
  @EpiTest(testfile = "delete_kth_last_from_list.tsv")

  // Assumes L has at least k nodes, deletes the k-th last node in L.
  public static ListNode<Integer> removeKthLast(ListNode<Integer> L, int k) {
    if (k<=0) return L;
    ListNode<Integer> d = new ListNode<Integer>(0, L);
    ListNode<Integer> p1 = d.next;
    for (int i = 0; i < k; i++) {
      p1 = p1.next;
    }

    ListNode<Integer> p2 = d;
    while (p1!=null) {
      p1 = p1.next;
      p2 = p2.next;
    }
    p2.next = p2.next.next;
   // p2.next = p2.next.next;
    return d.next;
  }

  public static void main(String[] args) {
    GenericTestHandler.executeTestsByAnnotation(
        new Object() {}.getClass().getEnclosingClass(), args);
  }
}
