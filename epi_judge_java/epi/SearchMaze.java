package epi;

import epi.test_framework.EpiTest;
import epi.test_framework.EpiUserType;
import epi.test_framework.GenericTestHandler;
import epi.test_framework.TestFailureException;

import java.util.ArrayList;
import java.util.List;

public class SearchMaze {
  @EpiUserType(ctorParams = {int.class, int.class})

  public static class Coordinate {
    public int x, y;

    public Coordinate(int x, int y) {
      this.x = x;
      this.y = y;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) {
        return true;
      }

      if (o == null || getClass() != o.getClass()) {
        return false;
      }

      Coordinate that = (Coordinate)o;
      if (x != that.x || y != that.y) {
        return false;
      }
      return true;
    }
  }

  public static enum Color { WHITE, BLACK }

  public static List<Coordinate> searchMaze(List<List<Integer>> maze,
                                            Coordinate s, Coordinate e) {

    List<Coordinate> path = new ArrayList<>();
    path.add(s);
    maze.get(s.x).set(s.y, Color.BLACK.ordinal());

    if (!searchMazeHelper(maze, s, e, path)) {
      path.remove(path.size()-1);
    }

    return path;
  }

  private static boolean searchMazeHelper(List<List<Integer>> maze,
                                       Coordinate c, Coordinate e, List<Coordinate> path) {
    // base condition
    if (c==null) return false;
    if (c==e || c.equals(e)) return true;

    int[][] dirs = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

    for (int[] dir : dirs) {
      Coordinate nextCord = new Coordinate(c.x + dir[0], c.y + dir[1]);
      if (pathElementIsFeasible(maze, nextCord)) {
        path.add(nextCord);
        maze.get(nextCord.x).set(nextCord.y, Color.BLACK.ordinal());
        if (searchMazeHelper(maze, nextCord, e, path)) {
          return true;
        }
        path.remove(path.size() - 1);
      }
    }
    return false;
  }

  public static boolean pathElementIsFeasible(List<List<Integer>> maze, Coordinate cur) {
    return (0 <= cur.x) && (cur.x < maze.size()) && (0 <= cur.y) &&
            (cur.y < maze.get(0).size()) && (maze.get(cur.x).get(cur.y) == Color.WHITE.ordinal());
  }

  @EpiTest(testfile = "search_maze.tsv")
  public static boolean searchMazeWrapper(List<List<Integer>> maze,
                                          Coordinate s, Coordinate e)
      throws TestFailureException {
    List<List<Integer>> colored = new ArrayList<>();
    for (List<Integer> col : maze) {
      List<Integer> tmp = new ArrayList<>();
      for (Integer i : col) {
        tmp.add(i);
      }
      colored.add(tmp);
    }
    List<Coordinate> path = searchMaze(colored, s, e);
    if (path.isEmpty()) {
      return s.equals(e);
    }

    if (!path.get(0).equals(s) || !path.get(path.size() - 1).equals(e)) {
      throw new TestFailureException(
          "Path doesn't lay between start and end points");
    }

    for (int i = 1; i < path.size(); i++) {
      if (!pathElementIsFeasible(maze, path.get(i))) {
        throw new TestFailureException("Path contains invalid segments");
      }
    }

    return true;
  }

  public static void main(String[] args) {
    GenericTestHandler.executeTestsByAnnotation(
        new Object() {}.getClass().getEnclosingClass(), args);
  }
}
