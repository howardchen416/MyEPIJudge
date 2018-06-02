package epi;

import epi.test_framework.EpiTest;
import epi.test_framework.EpiUserType;
import epi.test_framework.GenericTestHandler;
import epi.test_framework.TestFailureException;
import epi.test_framework.TestTimer;

import java.util.*;

public class CopyPostingList {

    public static PostingListNode copyPostingsList(PostingListNode l) {
        if (l == null) {
            return null;
        }

        // Stage 1: Makes a copy of the original list without assigning the jump
        //          field, and creates the mapping for each node in the original
        //          list to the copied list.
        PostingListNode iter = l;
        while (iter != null) {
            PostingListNode newNode =
                    new PostingListNode(iter.order, iter.next, null);
            iter.next = newNode;
            iter = newNode.next;
        }

        // Stage 2: Assigns the jump field in the copied list.
        iter = l;
        while (iter != null) {
            if (iter.jump != null) {
                iter.next.jump = iter.jump.next;
            }
            iter = iter.next.next;
        }

        // Stage 3: Reverts the original list, and assigns the next field of
        //          the copied list.
        iter = l;
        PostingListNode newListHead = iter.next;
        while (iter.next != null) {
            PostingListNode temp = iter.next;
            iter.next = temp.next;
            iter = temp;
        }
        return newListHead;
    }


    @EpiUserType(ctorParams = {int.class, int.class})
    public static class SerializedNode {
        public int order;
        public int jumpIndex;

        public SerializedNode(int order, int jumpIndex) {
            this.order = order;
            this.jumpIndex = jumpIndex;
        }

    }

    public static PostingListNode
    createPostingList(List<SerializedNode> serialized) {
        Map<Integer, PostingListNode> keyMapping = new HashMap<>();
        PostingListNode head = null;
        for (int i = serialized.size() - 1; i >= 0; i--) {
            head = new PostingListNode(serialized.get(i).order, head, null);
            keyMapping.put(head.order, head);
        }
        PostingListNode listIt = head;
        for (SerializedNode x : serialized) {
            if (x.jumpIndex != -1) {
                listIt.jump = keyMapping.get(x.jumpIndex);
                if (listIt.jump == null) {
                    throw new RuntimeException("Jump index out of range");
                }
            }
        }

        return head;
    }

    public static void assertListsEqual(PostingListNode orig,
                                        PostingListNode copy)
            throws TestFailureException {
        Map<PostingListNode, PostingListNode> nodeMapping = new HashMap<>();
        PostingListNode oIt = orig;
        PostingListNode cIt = copy;

        while (oIt != null) {
            if (cIt == null) {
                throw new TestFailureException(
                        "Copied list has fewer nodes than the original");
            }
            if (oIt.order != cIt.order) {
                throw new TestFailureException("Order value mismatch");
            }
            nodeMapping.put(oIt, cIt);
            oIt = oIt.next;
            cIt = cIt.next;
        }

        if (cIt != null) {
            throw new TestFailureException(
                    "Copied list has more nodes than the original");
        }

        oIt = orig;
        cIt = copy;

        while (oIt != null) {
            if (nodeMapping.get(cIt) != null) {
                throw new TestFailureException(
                        "Copied list contains a node from the original list");
            }
            if (oIt.jump != null) {
                if (nodeMapping.get(oIt.jump) != cIt.jump) {
                    throw new TestFailureException(
                            "Jump link points to a different nodes in the copied list");
                }
            } else {
                if (cIt.jump != null) {
                    throw new TestFailureException(
                            "Jump link points to a different nodes in the copied list");
                }
            }
            oIt = oIt.next;
            cIt = cIt.next;
        }
    }

    @EpiTest(testfile = "copy_posting_list.tsv")
    public static void copyPostingsListWrapper(TestTimer timer,
                                               List<SerializedNode> l)
            throws TestFailureException {
        PostingListNode head = createPostingList(l);

        timer.start();
        PostingListNode copy = copyPostingsList(head);
        timer.stop();

        assertListsEqual(head, copy);
    }

    public static void main(String[] args) {
        GenericTestHandler.executeTestsByAnnotation(
                new Object() {
                }.getClass().getEnclosingClass(), args);
    }
}
