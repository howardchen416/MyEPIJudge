package epi;

import epi.test_framework.EpiTest;
import epi.test_framework.GenericTestHandler;

import java.util.List;

public class RefuelingSchedule {

  @EpiTest(testfile = "refueling_schedule.tsv")
  // gallons[i] is the amount of gas in city i, and distances[i] is the distance
  // city i to the next city.
  public static int findAmpleCity(List<Integer> gallons,
                                  List<Integer> distances) {
    int minDistance = Integer.MAX_VALUE;
    int minCity = 0;
    int cumulativeDistance = 0;
    for (int i = 0; i < gallons.size(); i++) {
      cumulativeDistance += (gallons.get(i) * 20 - distances.get(i));
      if (cumulativeDistance < minDistance) {
        minDistance = cumulativeDistance;
        minCity = (i+1) % gallons.size();
      }
    }
    return minCity;
  }

  public static void main(String[] args) {
    GenericTestHandler.executeTestsByAnnotation(
        new Object() {}.getClass().getEnclosingClass(), args);
  }
}
