package epi;

import epi.test_framework.EpiTest;
import epi.test_framework.GenericTestHandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class IntAsArrayMultiply {
  @EpiTest(testfile = "int_as_array_multiply.tsv")
  public static List<Integer> multiply(List<Integer> num1, List<Integer> num2) {
    // speical case 1 - 0 multiplier
    if ((num1.size()==1 && num1.get(0)==0) || (num2.size()==1 && num2.get(0)==0)) {
      return new ArrayList<Integer>(Collections.nCopies(1, 0));
    }
    // extract sign
    int sign = 1;
    if (num1.get(0)<0) { num1.set(0, num1.get(0)*-1); sign*=-1; }
    if (num2.get(0)<0) { num2.set(0, num2.get(0)*-1); sign*=-1; }

    List<Integer> result = new ArrayList<>(Collections.nCopies(num1.size()+num2.size(), 0));
    for (int i = num2.size()-1; i >= 0; i--) {
      for (int j = num1.size()-1; j >= 0 ; j--) {
        result.set(i+j+1, result.get(i+j+1) + num1.get(j)*num2.get(i));
      }
    }
    for (int i = num1.size()+num2.size()-1; i > 0; i--) {
      // calculate carry
      int carry = result.get(i)/10;
      result.set(i, result.get(i)%10);
      // apply carry
      result.set(i-1, result.get(i-1) + carry);
    }
    // special case 2 - no leading digit
    if (result.get(0)==0)
      result=result.subList(1,result.size());

    // restore sign
    result.set(0, result.get(0)*sign);

    return result;
  }

  public static void main(String[] args) {
    GenericTestHandler.executeTestsByAnnotation(
        new Object() {}.getClass().getEnclosingClass(), args);
  }
}
