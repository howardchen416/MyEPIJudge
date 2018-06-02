package epi;

import epi.test_framework.EpiTest;
import epi.test_framework.GenericTestHandler;

import java.util.Iterator;
import java.util.List;

public class MajorityElement {

  public static String majoritySearch(Iterator<String> stream) {

    int count = 1;
    String candidate = stream.next();
    while (stream.hasNext()) {
      String next =stream.next();
      if (!candidate.equalsIgnoreCase(next)) {
        count--;
        if (count==0) {
          candidate = next;
          count=1;
        }
      }
      else {
        count++;
      }
    }
    return candidate;
  }

  @EpiTest(testfile = "majority_element.tsv")
  public static String majoritySearchWrapper(List<String> stream) {
    return majoritySearch(stream.iterator());
  }

  public static void main(String[] args) {
    GenericTestHandler.executeTestsByAnnotation(
        new Object() {}.getClass().getEnclosingClass(), args);
  }
}
