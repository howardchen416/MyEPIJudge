package epi;

import epi.test_framework.EpiTest;
import epi.test_framework.GenericTestHandler;

import java.util.*;

public class IntersectSortedArrays {
  @EpiTest(testfile = "intersect_sorted_arrays.tsv")

  public static List<Integer> intersectTwoSortedArrays(List<Integer> A,
                                                       List<Integer> B) {
    //List<Integer> r = new ArrayList<>();
    Set<Integer> r = new HashSet<>();

    // special conditions
    if (A==null || A.size()==0 || B==null || B.size()==0) return Collections.emptyList();
    if (A.equals(B)) return A;

    int i = 0, j = 0;
    while (i<A.size() && j<B.size()) {
      if (Integer.compare(A.get(i), B.get(j))==0) {
        r.add(A.get(i));
        i=advance(A, i);
        j=advance(B, j);
      } else if (Integer.compare(A.get(i), B.get(j))>0) {
        j=advance(B, j);
      } else { // (Integer.compare(A.get(i), B.get(j))<0)
        i=advance(A, i);;
      }
    }
    List<Integer> rr = new ArrayList<>(r);
    Collections.sort(rr);
    return rr;
  }

  private static int advance(List<Integer> l, int currIdx) { // need this routine due to data repetitions
   // do {
      currIdx++;
    //} while (currIdx<l.size() && Integer.compare(l.get(currIdx), l.get(currIdx-1))==0);
    return currIdx;
  }

  public static void main(String[] args) {
    GenericTestHandler.executeTestsByAnnotation(
        new Object() {}.getClass().getEnclosingClass(), args);
  }
}
