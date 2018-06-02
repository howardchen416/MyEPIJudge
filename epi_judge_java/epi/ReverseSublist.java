package epi;

import epi.test_framework.EpiTest;
import epi.test_framework.GenericTestHandler;

public class ReverseSublist {
  @EpiTest(testfile = "reverse_sublist.tsv")

  public static ListNode<Integer> reverseSublist(ListNode<Integer> L, int start,
                                                 int finish) {

    if (start==finish) return L;
    ListNode<Integer> d = new ListNode<>(0, L);
    ListNode<Integer> sh = d;
    ListNode<Integer> c = d.next;

    for (int i = 1; i < start; i++) {
      sh = c;
      c = c.next;
    }
    //ListNode<Integer> sh = c;

    ListNode<Integer> iter = sh.next;
    while (start++ < finish) {
      ListNode<Integer> t = iter.next;
      iter.next = t.next;
      t.next = sh.next;
      sh.next = t;
    }


    return d.next;
  }

  public static void main(String[] args) {
    GenericTestHandler.executeTestsByAnnotation(
        new Object() {}.getClass().getEnclosingClass(), args);
  }
}
