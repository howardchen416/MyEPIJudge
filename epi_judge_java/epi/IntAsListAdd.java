package epi;

import epi.test_framework.EpiTest;
import epi.test_framework.GenericTestHandler;

public class IntAsListAdd {
  @EpiTest(testfile = "int_as_list_add.tsv")

  public static ListNode<Integer> addTwoNumbers(ListNode<Integer> L1,
                                                ListNode<Integer> L2) {
    // special condition(s)
    if (L1==null) return L2;
    if (L2==null) return L1;

    ListNode<Integer> d = new ListNode<Integer>(0, null);
    ListNode<Integer> iter = d;
    ListNode<Integer> lIter = L1;
    ListNode<Integer> rIter = L2;

    int carry = 0;
    while (lIter!=null || rIter!=null) {
      int sum = carry;
      if (lIter!=null) {
        sum += lIter.data;
        lIter = lIter.next;
      }
      if (rIter!=null) {
        sum += rIter.data;
        rIter = rIter.next;
      }
      iter.next = new ListNode<Integer>(sum%10, null);
      carry = sum/10;
      iter = iter.next;
    }
    // take care of most significant digit
    if (carry>0) {
      iter.next = new ListNode<Integer>(carry, null);
      //iter = iter.next;
    }
    return d.next;
  }

  public static void main(String[] args) {
    GenericTestHandler.executeTestsByAnnotation(
        new Object() {}.getClass().getEnclosingClass(), args);
  }
}
