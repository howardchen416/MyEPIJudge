package epi;

import epi.test_framework.EpiTest;
import epi.test_framework.GenericTestHandler;

import java.util.List;

public class IntAsArrayIncrement {
  @EpiTest(testfile = "int_as_array_increment.tsv")
  public static List<Integer> plusOne(List<Integer> A) {
    // Implement this placeholder.
    int n = A.size()-1;
    A.set(n, A.get(n)+1);
    for (int i = n; i >= 1; i--) {
      if (A.get(i)==10) {
        A.set(i, 0);
        // carry
        A.set(i-1,A.get(i-1)+1);
      }
    }
    if (A.get(0)==10) {
      A.set(0, 0);
      A.add(0, new Integer(1));
    }
    return A;
  }

  public static void main(String[] args) {
    GenericTestHandler.executeTestsByAnnotation(
        new Object() {}.getClass().getEnclosingClass(), args);
  }
}
