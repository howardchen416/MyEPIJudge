package epi;

import epi.test_framework.EpiTest;
import epi.test_framework.EpiUserType;
import epi.test_framework.GenericTestHandler;
import epi.test_framework.TestFailureException;

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

public class StackWithMax {

  public static class MaxWithCount {
    int max;
    int count;

    public MaxWithCount(int max, int count) {
      this.max = max;
      this.count = count;
    }
  }

  public static class Stack {

    Deque<MaxWithCount> maxWithCount = new LinkedList<>();
    Deque<Integer> stack = new LinkedList<>();

    public boolean empty() {
      return stack.isEmpty();
    }

    public Integer max() {
      if (empty())
        throw new IllegalStateException("");
      return maxWithCount.peekFirst().max;
    }

    public Integer pop() {
      int r = stack.pollFirst();
      if (maxWithCount.isEmpty())
        throw new IllegalStateException();
      if (maxWithCount.peekFirst().max == r) {
        MaxWithCount mwc = maxWithCount.pollFirst();
        if (mwc.count>1) {
          mwc.count--;
          maxWithCount.offerFirst(mwc);
        }
      }
      return r;
    }

    public void push(Integer x) {
      stack.offerFirst(x);
      int xi = x;
      if (maxWithCount.isEmpty()) {
        maxWithCount.offerFirst(new MaxWithCount(x, 1));
        return;
      }
      MaxWithCount mwc = maxWithCount.peekFirst();
      if (mwc.max == x)
        mwc.count++;
      else if (mwc.max < x)
        maxWithCount.offerFirst(new MaxWithCount(x, 1));
      return;
    }
  }

  @EpiUserType(ctorParams = {String.class, int.class})
  public static class StackOp {
    public String op;
    public int arg;

    public StackOp(String op, int arg) {
      this.op = op;
      this.arg = arg;
    }
  }

  @EpiTest(testfile = "stack_with_max.tsv")
  public static void stackTest(List<StackOp> ops) throws TestFailureException {
    try {
      Stack s = new Stack();
      int result;
      for (StackOp op : ops) {
        switch (op.op) {
        case "Stack":
          s = new Stack();
          break;
        case "push":
          s.push(op.arg);
          break;
        case "pop":
          result = s.pop();
          if (result != op.arg) {
            throw new TestFailureException("Pop: expected " +
                                           String.valueOf(op.arg) + ", got " +
                                           String.valueOf(result));
          }
          break;
        case "max":
          result = s.max();
          if (result != op.arg) {
            throw new TestFailureException("Max: expected " +
                                           String.valueOf(op.arg) + ", got " +
                                           String.valueOf(result));
          }
          break;
        case "empty":
          result = s.empty() ? 1 : 0;
          if (result != op.arg) {
            throw new TestFailureException("Empty: expected " +
                                           String.valueOf(op.arg) + ", got " +
                                           String.valueOf(s));
          }
          break;
        default:
          throw new RuntimeException("Unsupported stack operation: " + op.op);
        }
      }
    } catch (NoSuchElementException e) {
      throw new TestFailureException("Unexpected NoSuchElement exception");
    }
  }

  public static void main(String[] args) {
    GenericTestHandler.executeTestsByAnnotation(
        new Object() {}.getClass().getEnclosingClass(), args);
  }
}
