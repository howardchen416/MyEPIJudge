package epi;

import epi.test_framework.EpiTest;
import epi.test_framework.GenericTestHandler;

public class RemoveDuplicatesFromSortedList {
    @EpiTest(testfile = "remove_duplicates_from_sorted_list.tsv")

    public static ListNode<Integer> removeDuplicates(ListNode<Integer> L) {
        if (L==null) return L;
        ListNode<Integer> iter = L;
        ListNode<Integer> nextDistinct = iter.next;
        while (nextDistinct!=null) {
            while (nextDistinct!=null && iter.data==nextDistinct.data) nextDistinct = nextDistinct.next;
            iter.next = nextDistinct;
            iter = nextDistinct;
        }
        return L;
    }

    public static void main(String[] args) {
        GenericTestHandler.executeTestsByAnnotation(
                new Object() {
                }.getClass().getEnclosingClass(), args);
    }
}
