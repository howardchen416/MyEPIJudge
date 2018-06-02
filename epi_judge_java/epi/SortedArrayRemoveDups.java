package epi;

import epi.test_framework.EpiTest;
import epi.test_framework.GenericTestHandler;
import epi.test_framework.TestTimer;

import java.util.List;
import java.util.ListIterator;

public class SortedArrayRemoveDups {
  // Returns the number of valid entries after deletion.
  public static int deleteDuplicates(List<Integer> A) {
    if (A.size()==0) return 0;
    int writeIndex = 1;
    for (int i = 1; i < A.size(); i++) {
      if (A.get(writeIndex-1)<A.get(i))
        A.set(writeIndex++, A.get(i));
    }
    return writeIndex;
  }

  @EpiTest(testfile = "sorted_array_remove_dups.tsv")
  public static List<Integer> deleteDuplicatesWrapper(TestTimer timer,
                                                      List<Integer> A) {
    timer.start();
    int end = deleteDuplicates(A);
    timer.stop();

    return A.subList(0, end);
  }

  public static void main(String[] args) {
    GenericTestHandler.executeTestsByAnnotation(
        new Object() {}.getClass().getEnclosingClass(), args);
  }
}
