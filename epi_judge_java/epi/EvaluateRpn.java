package epi;

import epi.test_framework.EpiTest;
import epi.test_framework.GenericTestHandler;

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

public class EvaluateRpn {
  @EpiTest(testfile = "evaluate_rpn.tsv")

  public static int eval(String expression) {
    //
    Deque<Integer> o = new LinkedList<>();
    String[] tokens = expression.split(",");
    for (String token : tokens) {
      if (token.length()==1 && "+-*/".contains(token)) {
        int y = o.pollFirst();
        int x = o.pollFirst();
        int r = 0;
        switch (token) {
          case "+" :
            r = x + y;
            break;
          case "-":
            r = x - y;
            break;
          case "*":
            r = x * y;
            break;
          case "/":
            r = x / y;
            break;
          default:
        }
        o.offerFirst(r);
      }
      else {
        o.offerFirst(Integer.valueOf(token));
      }
    }
    return o.pollFirst();
  }

  public static void main(String[] args) {
    GenericTestHandler.executeTestsByAnnotation(
        new Object() {}.getClass().getEnclosingClass(), args);
  }
}
