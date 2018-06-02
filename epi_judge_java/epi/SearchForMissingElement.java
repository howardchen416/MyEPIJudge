package epi;

import epi.test_framework.EpiTest;
import epi.test_framework.EpiUserType;
import epi.test_framework.GenericTestHandler;

import java.util.List;

public class SearchForMissingElement {
  @EpiUserType(ctorParams = {Integer.class, Integer.class})

  public static class DuplicateAndMissing {
    public Integer duplicate;
    public Integer missing;

    public DuplicateAndMissing(Integer duplicate, Integer missing) {
      this.duplicate = duplicate;
      this.missing = missing;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) {
        return true;
      }
      if (o == null || getClass() != o.getClass()) {
        return false;
      }

      DuplicateAndMissing that = (DuplicateAndMissing)o;

      if (!duplicate.equals(that.duplicate)) {
        return false;
      }
      return missing.equals(that.missing);
    }

    @Override
    public int hashCode() {
      int result = duplicate.hashCode();
      result = 31 * result + missing.hashCode();
      return result;
    }

    @Override
    public String toString() {
      return "duplicate: " + duplicate + ", missing: " + missing;
    }
  }

  @EpiTest(testfile = "find_missing_and_duplicate.tsv")

  public static DuplicateAndMissing findDuplicateMissing(List<Integer> A) {
    int missXorDup = 0;
    for (int i = 0; i < A.size(); i++) {
      missXorDup ^= i ^ A.get(i);
    }
    int differingBit = missXorDup & (~(missXorDup - 1));
    int missOrDup = 0;
    for (int i = 0; i < A.size(); i++) {
      if ((i & differingBit) != 0) {
        missOrDup ^= i;
      }
      if ((A.get(i) & differingBit) != 0) {
        missOrDup ^= A.get(i);
      }
    }
    for (int i = 0; i < A.size(); i++) {
      if (A.get(i).compareTo(missOrDup)==0) {
        return new DuplicateAndMissing(missOrDup, missXorDup ^ missOrDup);
      }
    }
    return new DuplicateAndMissing(missXorDup ^ missOrDup, missOrDup);
  }

  public static void main(String[] args) {
    GenericTestHandler.executeTestsByAnnotation(
        new Object() {}.getClass().getEnclosingClass(), args);
  }
}
