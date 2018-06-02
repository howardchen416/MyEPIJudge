package epi;

import epi.test_framework.EpiTest;
import epi.test_framework.GenericTestHandler;
import epi.test_framework.TestFailureException;
import epi.test_framework.TestTimer;

public class DoTerminatedListsOverlap {

  public static ListNode<Integer>
  overlappingNoCycleLists(ListNode<Integer> l0, ListNode<Integer> l1) {
    int len0 = length(l0);
    int len1 = length(l1);
    ListNode<Integer> iter0 = l0;
    ListNode<Integer> iter1 = l1;
    if (len0>len1) {
      for (int i = 0; i < len0-len1; i++) {
        iter0= iter0.next;
      }
    } else {
      for (int i = 0; i < len1-len0; i++) {
        iter1= iter1.next;
      }
    }
    while (iter1!=iter0) {
      iter0 = iter0.next;
      iter1 = iter1.next;
    }
    return iter0;
  }

  public static ListNode<Integer> advanceListByK(int k, ListNode<Integer> L) {
    ListNode<Integer> p = L;
    for (int i = 0; i < k; i++) {
      p = p.next;
    }
    return p;
  }

  public static int length(ListNode<Integer> L) {
    ListNode<Integer> p = L;
    if (p==null) return 0;
    int l = 1;
    while (p.next!=null) {
      l++;
      p = p.next;
    }
    return l;
  }


    @EpiTest(testfile = "do_terminated_lists_overlap.tsv")
  public static void
  overlappingNoCycleListsWrapper(TestTimer timer, ListNode<Integer> l0,
                                 ListNode<Integer> l1, ListNode<Integer> common)
      throws TestFailureException {
    if (common != null) {
      if (l0 != null) {
        ListNode<Integer> i = l0;
        while (i.next != null) {
          i = i.next;
        }
        i.next = common;
      } else {
        l0 = common;
      }

      if (l1 != null) {
        ListNode<Integer> i = l1;
        while (i.next != null) {
          i = i.next;
        }
        i.next = common;
      } else {
        l1 = common;
      }
    }

    timer.start();
    ListNode<Integer> result = overlappingNoCycleLists(l0, l1);
    timer.stop();

    if (result != common) {
      throw new TestFailureException("Invalid result");
    }
  }

  public static void main(String[] args) {
    GenericTestHandler.executeTestsByAnnotation(
        new Object() {}.getClass().getEnclosingClass(), args);
  }
}
