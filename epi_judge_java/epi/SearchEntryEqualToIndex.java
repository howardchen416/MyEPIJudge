package epi;

import epi.test_framework.EpiTest;
import epi.test_framework.GenericTestHandler;
import epi.test_framework.TestFailureException;
import epi.test_framework.TestTimer;

import java.util.List;

public class SearchEntryEqualToIndex {

  public static int searchEntryEqualToItsIndex(List<Integer> A) {
    // variation of binary seach
    int res = -1;
    int start=0, end=A.size()-1;
    while (start<=end) {
      int mid = start + (end-start)/2;
      int diff = A.get(mid) - mid;
      // search for diff == 0;
      if (diff>0) {
        end = mid-1;
      } else if (diff == 0) {
        return mid;
      } else {
        start = mid+1;
      }
    }
    return res;
  }

  @EpiTest(testfile = "binary_search_ai=i.tsv")
  public static void searchEntryEqualToItsIndexWrapper(TestTimer timer,
                                                       List<Integer> A)
      throws TestFailureException {
    timer.start();
    int result = searchEntryEqualToItsIndex(A);
    timer.stop();

    if (result != -1) {
      if (A.get(result) != result) {
        throw new TestFailureException("Entry does not equal to its index");
      }
    } else {
      for (int i = 0; i < A.size(); ++i) {
        if (A.get(i) == i) {
          throw new TestFailureException(
              "There are entries which equal to its index");
        }
      }
    }
  }

  public static void main(String[] args) {
    GenericTestHandler.executeTestsByAnnotation(
        new Object() {}.getClass().getEnclosingClass(), args);
  }
}
