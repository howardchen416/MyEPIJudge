package epi;

import epi.test_framework.EpiTest;
import epi.test_framework.EpiUserType;
import epi.test_framework.GenericTestHandler;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class DefectiveJugs {
  @EpiUserType(ctorParams = {int.class, int.class})

  private static class VolRange {
    int low;
    int high;

    public VolRange(int low, int high) {
      this.low = low;
      this.high = high;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      VolRange volRange = (VolRange) o;
      return low == volRange.low &&
              high == volRange.high;
    }

    @Override
    public int hashCode() {

      return Objects.hash(low, high);
    }
  }

  public static class Jug {
    public int low, high;

    public Jug() {}

    public Jug(int low, int high) {
      this.low = low;
      this.high = high;
    }
  }

  @EpiTest(testfile = "defective_jugs.tsv")

  public static boolean checkFeasible(List<Jug> jugs, int L, int H) {
    return checkFeasibleHelper(jugs, L, H, new HashSet<VolRange>());
  }

  public static boolean checkFeasibleHelper(List<Jug> jugs, int L, int H, Set<VolRange> failed) {
    // base condition
    if (L > H || failed.contains(new VolRange(L, H)) || (L<0 && H<0))
      return false;

    for (Jug jug : jugs) {
      if ((L <= jug.low && jug.high <= H) ||
              checkFeasibleHelper(jugs, L - jug.low, H - jug.high, failed))
        return true;
    }
    failed.add(new VolRange(L, H));
    return false;
  }


  public static void main(String[] args) {
    GenericTestHandler.executeTestsByAnnotation(
        new Object() {}.getClass().getEnclosingClass(), args);
  }
}
