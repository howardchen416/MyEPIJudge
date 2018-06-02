package epi;

import epi.test_framework.EpiTest;
import epi.test_framework.GenericTestHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EvenOddListMerge {
  @EpiTest(testfile = "even_odd_list_merge.tsv")

  public static ListNode<Integer> evenOddMerge(ListNode<Integer> L) {
    // special situation
    if (L==null || L.next==null || L.next.next==null) return L;

    ListNode<Integer> evenDummyHead = new ListNode(0, null);
    ListNode<Integer> oddDummyHead = new ListNode(0, null);
    List<ListNode<Integer>> iters = Arrays.asList(evenDummyHead, oddDummyHead);
    int turn = 0;
    for (ListNode<Integer> i = L; i!=null; i=i.next) {
      iters.get(turn).next = i;
      iters.set(turn, iters.get(turn).next);
      turn ^= 1;
    }
    iters.get(0).next = oddDummyHead.next;
    iters.get(1).next = null;
    return evenDummyHead.next;
  }

  public static void main(String[] args) {
    GenericTestHandler.executeTestsByAnnotation(
        new Object() {}.getClass().getEnclosingClass(), args);
  }
}
