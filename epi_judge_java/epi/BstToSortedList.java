package epi;

import epi.test_framework.EpiTest;
import epi.test_framework.GenericTestHandler;
import epi.test_framework.TestFailureException;
import epi.test_framework.TestTimer;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

public class BstToSortedList {

    private static class HeadAndTail {
        BstNode<Integer> head;
        BstNode<Integer> tail;

        public HeadAndTail(BstNode<Integer> head, BstNode<Integer> tail) {
            this.head = head;
            this.tail = tail;
        }
    }

    public static BstNode<Integer> bstToDoublyLinkedList(BstNode<Integer> tree) {
        return bstToDoublyLinkedListHelper(tree).head;
    }

    private static HeadAndTail bstToDoublyLinkedListHelper(BstNode<Integer> tree) {
        if (tree == null) return new HeadAndTail(null, null);

        HeadAndTail left = bstToDoublyLinkedListHelper(tree.left);
        HeadAndTail right = bstToDoublyLinkedListHelper(tree.right);

        if (left.tail != null) left.tail.right = tree;
        tree.left = left.tail;
        if (right.head != null) right.head.left = tree;
        tree.right = right.head;

        return new HeadAndTail((left.head==null) ? tree : left.head,
                (right.tail==null) ? tree : right.tail);
    }

    @EpiTest(testfile = "bst_to_sorted_list.tsv")
    public static List<Integer>
    bstToDoublyLinkedListWrapper(TestTimer timer, BstNode<Integer> tree)
            throws TestFailureException {
        timer.start();
        BstNode<Integer> list = bstToDoublyLinkedList(tree);
        timer.stop();

        if (list != null && list.left != null)
            throw new TestFailureException(
                    "Function must return the head of the list. Left link must be null");
        List<Integer> v = new ArrayList<>();
        while (list != null) {
            v.add(list.data);
            if (list.right != null && list.right.left != list)
                throw new RuntimeException("List is ill-formed");
            list = list.right;
        }
        return v;
    }

    public static void main(String[] args) {
        GenericTestHandler.executeTestsByAnnotation(
                new Object() {
                }.getClass().getEnclosingClass(), args);
    }
}
