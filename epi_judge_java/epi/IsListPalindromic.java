package epi;

import epi.test_framework.EpiTest;
import epi.test_framework.GenericTestHandler;

public class IsListPalindromic {
  @EpiTest(testfile = "is_list_palindromic.tsv")

  public static boolean isLinkedListAPalindrome(ListNode<Integer> L) {
    if (L==null || L.next==null) return true;

    // find 2nd half
    ListNode<Integer> slow = L;
    ListNode<Integer> fast = L;
    while (fast!=null && fast.next!=null) {
      slow = slow.next;
      fast = fast.next.next;
    }

    //
    ListNode<Integer> firstHalfIter = L;
    ListNode<Integer> secondHalfIter = reverseList(slow);
    if (firstHalfIter!=null && secondHalfIter!=null) {
      if (firstHalfIter.data!=secondHalfIter.data)
        return false;
      firstHalfIter = firstHalfIter.next;
      secondHalfIter = secondHalfIter.next;
    }
    return true;
  }

  public static ListNode<Integer> reverseList(ListNode<Integer> L) {
    ListNode<Integer> dh = new ListNode<Integer>(0, L);
    ListNode<Integer> iter = dh.next;
    while (iter.next!=null) {
      ListNode<Integer> t = iter.next;
      iter.next = t.next;
      t.next = dh.next;
      dh.next = t;
    }
    return dh.next;
  }

  public static void main(String[] args) {
    GenericTestHandler.executeTestsByAnnotation(
        new Object() {}.getClass().getEnclosingClass(), args);
  }
}
