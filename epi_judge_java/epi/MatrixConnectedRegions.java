package epi;

import epi.test_framework.EpiTest;
import epi.test_framework.GenericTestHandler;
import epi.test_framework.TestTimer;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class MatrixConnectedRegions {

    private static class Coordinate {
        public Integer x;
        public Integer y;

        public Coordinate(Integer x, Integer y) {
            this.x = x;
            this.y = y;
        }
    }

/*    public static void flipColor(int x, int y, List<List<Boolean>> image) {
        final int[][] dirs = {{1, 0}, {0, 1}, {-1, 0}, {0, -1}};
        boolean color = image.get(x).get(y);
        final int xdim = image.size();
        final int ydim = image.get(0).size();

        // breadth first search
        Queue<Coordinate> q = new LinkedList<>();
        q.offer(new Coordinate(x, y));
        image.get(x).set(y, !color);

        while (!q.isEmpty()) {
            Coordinate c = q.poll();
            for (int[] dir : dirs) {
                int newx = c.x + dir[0];
                int newy = c.y + dir[1];
                if (newx >=0 && newx < xdim && newy >=0 && newy < ydim &&
                        image.get(newx).get(newy)==color) {
                    Coordinate n = new Coordinate(newx, newy);
                    q.offer(n);
                    image.get(newx).set(newy, !color);
                }
            }
        }
        return;
    }*/

    public static void flipColor(int x, int y, List<List<Boolean>> image) {  //DFS
        final int[][] dirs = {{1, 0}, {0, 1}, {-1, 0}, {0, -1}};
        boolean color = image.get(x).get(y);
        final int xdim = image.size();
        final int ydim = image.get(0).size();

        // flip color first
        image.get(x).set(y, !color);
        for (int[] dir : dirs) {
            int newx = x + dir[0];
            int newy = y + dir[1];
            if (newx >= 0 && newx < xdim && newy >= 0 && newy < ydim &&
                    image.get(newx).get(newy) == color) {
                //image.get(newx).set(newy, !color);
                flipColor(newx, newy, image);
            }
        }
        return;
    }

    @EpiTest(testfile = "painting.tsv")
    public static List<List<Integer>>
    flipColorWrapper(TestTimer timer, int x, int y, List<List<Integer>> image) {
        List<List<Boolean>> B = new ArrayList<>();
        for (int i = 0; i < image.size(); i++) {
            B.add(new ArrayList<>());
            for (int j = 0; j < image.get(i).size(); j++) {
                B.get(i).add(image.get(i).get(j) == 1);
            }
        }

        timer.start();
        flipColor(x, y, B);
        timer.stop();

        image = new ArrayList<>();
        for (int i = 0; i < B.size(); i++) {
            image.add(new ArrayList<>());
            for (int j = 0; j < B.get(i).size(); j++) {
                image.get(i).add(B.get(i).get(j) ? 1 : 0);
            }
        }

        return image;
    }

    public static void main(String[] args) {
        GenericTestHandler.executeTestsByAnnotation(
                new Object() {
                }.getClass().getEnclosingClass(), args);
    }
}
