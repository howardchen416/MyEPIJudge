package epi;

import epi.test_framework.EpiTest;
import epi.test_framework.GenericTestHandler;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

public class InsertOperatorsInString {
  @EpiTest(testfile = "insert_operators_in_string.tsv")

  public static boolean expressionSynthesis(List<Integer> digits, int target) {
    List<Integer> operands = new ArrayList<>();
    List<Character> operators = new ArrayList<>();
    return expressionSynthesisHelper(digits, target, 0, 0, operands, operators);
  }

  private static boolean expressionSynthesisHelper(List<Integer> digits, int target,
                                                   int currentTerm, int offset,
                                                   List<Integer> operands,
                                                   List<Character> operators) {
    currentTerm = currentTerm*10 + digits.get(offset);
    // base condition
    if (offset == digits.size()-1) {
      operands.add(currentTerm);
      if (evaluate(operands, operators)==target)
        return true;
      operands.remove(operands.size()-1);
      return false;
    }
    // no operator
    if (expressionSynthesisHelper(digits, target, currentTerm, offset+1, operands, operators))
      return true;

    // "*" operator
    operands.add(currentTerm);
    operators.add('*');
    if (expressionSynthesisHelper(digits, target, 0, offset+1, operands, operators))
      return true;
    operands.remove(operands.size()-1);
    operators.remove(operators.size()-1);

    // "+" operator
    operands.add(currentTerm);
    if (evaluate(operands, operators) + remainingInt(digits, offset) >= target) {
      operators.add('+');
      if (expressionSynthesisHelper(digits, target, 0, offset + 1, operands, operators))
        return true;
      operators.remove(operators.size() - 1);
    }
    operands.remove(operands.size() - 1);

    return false;
  }

  private static int remainingInt(List<Integer> digits, int idx) {
    int result = 0;
    for (int i = idx; i < digits.size(); i++) {
      result = result*10 + digits.get(i);
    }
    return result;
  }

  private static int evaluate(List<Integer> operands, List<Character> operators) {
    int operandIdx = 0;
    Deque<Integer> intermediateOperands = new LinkedList<>();
    intermediateOperands.addFirst(operands.get(operandIdx++));
    // "*" pass
    for (int i = 0; i < operators.size(); i++) {
      if (operators.get(i)=='*') {
        intermediateOperands.addFirst(intermediateOperands.pollFirst() * operands.get(operandIdx++));
      }
      else {
        intermediateOperands.addFirst(operands.get(operandIdx++));
      }
    }

    // "+" pass
    int result =0;
    while (!intermediateOperands.isEmpty())
      result += intermediateOperands.pollFirst();

    return result;
  }

  public static void main(String[] args) {
    GenericTestHandler.executeTestsByAnnotation(
        new Object() {}.getClass().getEnclosingClass(), args);
  }
}
