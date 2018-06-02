package epi;

import epi.test_framework.EpiTest;
import epi.test_framework.GenericTestHandler;
import epi.test_framework.TestFailureException;
import epi.test_framework.TestTimer;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class IsArrayDominated {
  @EpiTest(testfile = "is_array_dominated.tsv")
  public static void
  validPlacementExistsWrapper(TestTimer timer, List<Integer> team0,
                              List<Integer> team1, boolean expected01,
                              boolean expected10) throws TestFailureException {
    Team t0 = new Team(team0);
    Team t1 = new Team(team1);

    timer.start();
    if (Team.validPlacementExists(t0, t1) != expected01 ||
        Team.validPlacementExists(t1, t0) != expected10) {
      throw new TestFailureException("");
    }
  }

  public static void main(String[] args) {
    GenericTestHandler.executeTestsByAnnotation(
        new Object() {}.getClass().getEnclosingClass(), args);
  }
}

class Team {
  private static class Player implements Comparable<Player> {
    public Integer height;

    public Player(Integer h) { height = h; }

    @Override
    public int compareTo(Player that) {
      return Integer.compare(height, that.height);
    }
  }

  public Team(List<Integer> height) {
    players =
        height.stream().map(h -> new Player(h)).collect(Collectors.toList());
  }

  // Checks if team0 can be placed in front of team1.
  public static boolean validPlacementExists(Team team0, Team team1) {
    // special condition(s)
    if (team0==null || team0.players.size()==0 || team1==null || team1.players.size()==0 || team0.players.size()!=team1.players.size()) return false;

    // sort each team by height
    orderTeamOfPlayers(team0);
    orderTeamOfPlayers(team1);

    // compare each player in sorted order
    int teamSize = team0.players.size();
    for (int i = 0; i < teamSize; i++) {
      if (team0.players.get(i).compareTo(team1.players.get(i))>=0)
        return false;
    }
    return true;
  }

  public static void orderTeamOfPlayers(Team team) {
    Collections.sort(team.players);
    return;
  }


  private List<Player> players;
}
