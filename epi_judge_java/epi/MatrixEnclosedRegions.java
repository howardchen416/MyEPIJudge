package epi;

import epi.test_framework.EpiTest;
import epi.test_framework.GenericTestHandler;

import java.util.*;

public class MatrixEnclosedRegions {

  public static void fillSurroundedRegions(List<List<Character>> board) {
    if (board . isEmpty () ) {
      return ;
    }

    List<List <Boolean>> visited = new ArrayList<>(board.size ()) ;
    for (int i = 0; i < board.size (); ++i) {
      visited.add(new ArrayList (Collections.nCopies(board.get(i).size() , false))) ;
    }
    // Identifies the regions that are reachable via white path starting from
    // the first or last columns.
    for (int i = 0; i < board.size() ; ++i) {
      if (board.get(i).get(0) == 'W' && !visited.get(i).get(0)) {
        markBoundaryRegion(i , 0, board, visited);
      }
      if (board.get(i).get(board.get(i).size() - 1) == 'W' && !visited.get(i).get(board.get(i).size() - 1)) {
        markBoundaryRegion(i , board.get(i).size() - 1, board, visited);
      }
    }
    // Identifies the regions that are reachable via white path starting from
    // the first or last rows.
    for (int j = 0; j < board.get(0).size(); ++j) {
      if (board.get(0).get(j)== 'W' && !visited.get(0).get (j )) {
        markBoundaryRegion (0, j, board, visited);
      }
      if (board.get(board.size() - 1).get(j) == 'W' && !visited.get(board.size() - 1).get(j)) {
        markBoundaryRegion(board.size () - 1, j, board, visited);
      }
    }

    // Marks the surrounded white regions as black.
    for (int i = 1; i < board.size() - 1; ++i) {
      for (int j = 1; j < board.get(i).size() - 1; ++j){
        if (board.get(i).get(j)== 'W' && !visited.get(i).get(j)){
          board.get(i).set(j, 'B');
        }
      }
    }
  }

  private static class Coordinate {
    public Integer x;
    public Integer y;

    public Coordinate(Integer x, Integer y) {
      this.x = x;
      this.y = y;
    }
  }

  private static void markBoundaryRegion(int x, int y,
                                         List<List<Character>> board,
                                         List<List<Boolean>> visited) {
    Queue<Coordinate> q = new LinkedList<>();
    int[][] dirs = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
    visited.get(x).set(y, true);
    q.offer(new Coordinate(x, y));
    int xdim = board.size();
    int ydim = board.get(0).size();

    while (!q.isEmpty()) {
      Coordinate c = q.poll();
      for (int[] dir : dirs) {
        int newx = c.x + dir[0];
        int newy = c.y + dir[1];
        if (0<= newx && newx < xdim && 0 <= newy && newy < ydim &&
                Character.compare(board.get(newx).get(newy), 'W')==0 &&
                !visited.get(newx).get(newy)) {
          visited.get(newx).set(newy, true);
          q.offer(new Coordinate(newx, newy));
        }
      }
    }
    return;
  }

  @EpiTest(testfile = "matrix_enclosed_regions.tsv")
  public static List<List<Character>>
  fillSurroundedRegionsWrapper(List<List<Character>> board) {
    fillSurroundedRegions(board);
    return board;
  }

  public static void main(String[] args) {
    GenericTestHandler.executeTestsByAnnotation(
        new Object() {}.getClass().getEnclosingClass(), args);
  }
}
