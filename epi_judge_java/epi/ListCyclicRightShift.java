package epi;

import epi.test_framework.EpiTest;
import epi.test_framework.GenericTestHandler;

public class ListCyclicRightShift {
  @EpiTest(testfile = "list_cyclic_right_shift.tsv")

  public static ListNode<Integer> cyclicallyRightShiftList(ListNode<Integer> L,
                                                           int k) {
    // special cases
    if (L==null || L.next==null) return L;

    // find length and tail
    ListNode<Integer> tail = L;
    int len = 1;
    while (tail.next!=null) {
      tail = tail.next;
      len++;
    }

    k%=len;
    if (k == 0) return L;

    // connect tail with head
    tail.next = L;
    int stepsTpShift = len - k;
    while (stepsTpShift-- > 0) tail = tail.next;
    ListNode<Integer> newHead = tail.next;
    tail.next = null;

    return newHead;
  }

  public static void main(String[] args) {
    GenericTestHandler.executeTestsByAnnotation(
        new Object() {}.getClass().getEnclosingClass(), args);
  }
}
