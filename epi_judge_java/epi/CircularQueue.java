package epi;

import epi.test_framework.EpiTest;
import epi.test_framework.EpiUserType;
import epi.test_framework.GenericTestHandler;
import epi.test_framework.TestFailureException;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

public class CircularQueue {


    public static class Queue {

        int head = 0;
        int tail = 0;
        int numElementsInQueue = 0;
        Integer[] elements;
        static final int SCALE_FACTOR = 2;

        public Queue(int capacity) {
            elements = new Integer[capacity];
        }

        public void enqueue(Integer x) {
            // make sure current size fits
            if (numElementsInQueue == elements.length) {
                // rotation
                Collections.rotate(Arrays.asList(elements), -head);
                head = 0;
                tail = numElementsInQueue;
                // expand
                elements = Arrays.copyOf(elements, elements.length * SCALE_FACTOR);
            }
            elements[tail] = x;
            tail = (tail+1) % elements.length;
            numElementsInQueue++;
            return;
        }

        public Integer dequeue() {
            if (numElementsInQueue == 0) throw new NoSuchElementException("");
            numElementsInQueue--;
            Integer ret = elements[head];
            head = (head+1) % elements.length;
            return ret;
        }

        public int size() {
            // Implement this placeholder.
            return numElementsInQueue;
        }
    }

    @EpiUserType(ctorParams = {String.class, int.class})
    public static class QueueOp {
        public String op;
        public int arg;

        public QueueOp(String op, int arg) {
            this.op = op;
            this.arg = arg;
        }

    }

    @EpiTest(testfile = "circular_queue.tsv")
    public static void queueTest(List<QueueOp> ops) throws TestFailureException {
        Queue q = new Queue(1);

        for (QueueOp op : ops) {
            switch (op.op) {
                case "Queue":
                    q = new Queue(op.arg);
                    break;
                case "enqueue":
                    q.enqueue(op.arg);
                    break;
                case "dequeue":
                    int result = q.dequeue();
                    if (result != op.arg) {
                        throw new TestFailureException("Dequeue: expected " +
                                String.valueOf(op.arg) + ", got " +
                                String.valueOf(result));
                    }
                    break;
                case "size":
                    int s = q.size();
                    if (s != op.arg) {
                        throw new TestFailureException("Size: expected " +
                                String.valueOf(op.arg) + ", got " +
                                String.valueOf(s));
                    }
                    break;
            }
        }
    }

    public static void main(String[] args) {
        GenericTestHandler.executeTestsByAnnotation(
                new Object() {
                }.getClass().getEnclosingClass(), args);
    }
}
