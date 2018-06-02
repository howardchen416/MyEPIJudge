package epi;

import epi.test_framework.EpiTest;
import epi.test_framework.GenericTestHandler;

import java.util.Collections;
import java.util.List;

public class FindSalaryThreshold {
  @EpiTest(testfile = "find_salary_threshold.tsv")

  public static double findSalaryCap(int targetPayroll,
                                     List<Integer> currentSalaries) {
    int uSS = 0;
    Collections.sort(currentSalaries); // important - this is essential!!!
    for (int i = 0; i < currentSalaries.size(); i++) {
      int aSS = currentSalaries.get(i)*(currentSalaries.size()-i);
      if ((uSS+aSS)>=targetPayroll) {
        return (double)(targetPayroll-uSS)/(currentSalaries.size()-i);
      }
      uSS += currentSalaries.get(i);
    }
    return -1.0d; // no solution possible
  }

  public static void main(String[] args) {
    GenericTestHandler.executeTestsByAnnotation(
        new Object() {}.getClass().getEnclosingClass(), args);
  }
}
