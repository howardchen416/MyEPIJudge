package epi;

import epi.test_framework.EpiTest;
import epi.test_framework.GenericTestHandler;
import epi.test_framework.TestTimer;

public class DeleteFromList {

  // Delete the node immediately following aNode. Assumes aNode is not a tail.
  public static void deleteList(ListNode<Integer> aNode) {
    ListNode<Integer> currNode = aNode;
    ListNode<Integer> lastDifferent = currNode;
    do {
      if (currNode.next!=null) {
        if (currNode.data != currNode.next.data) {
          if (lastDifferent!=currNode) {
            lastDifferent.next=currNode.next;
          }
          // advance lastDifferent
          lastDifferent = currNode.next;
        }
      }
      currNode = currNode.next; // advance
    } while (currNode!=null);
    return;
  }

  public static void main(String[] args) {
    GenericTestHandler.executeTestsByAnnotation(
        new Object() {}.getClass().getEnclosingClass(), args);
  }
}
