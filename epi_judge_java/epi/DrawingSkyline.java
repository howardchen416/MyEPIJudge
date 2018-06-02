package epi;

import epi.test_framework.EpiTest;
import epi.test_framework.EpiUserType;
import epi.test_framework.GenericTestHandler;
//import epi.test_framework.GenericTest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static epi.test_framework.GenericTestHandler.executeTestsByAnnotation;

public class DrawingSkyline {
  @EpiUserType(ctorParams = {int.class, int.class, int.class})

  public static class Rectangle {
    public int left, right, height;

    public Rectangle(int left, int right, int height) {
      this.left = left;
      this.right = right;
      this.height = height;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) {
        return true;
      }
      if (o == null || getClass() != o.getClass()) {
        return false;
      }

      Rectangle rectangle = (Rectangle)o;

      if (left != rectangle.left) {
        return false;
      }
      if (right != rectangle.right) {
        return false;
      }
      return height == rectangle.height;
    }

    @Override
    public String toString() {
      return "[" + left + ", " + right + ", " + height + ']';
    }
  }

  @EpiTest(testfile = "drawing_skyline.tsv")

  public static List<Rectangle> drawingSkylines(List<Rectangle> buildings) {
    int minLeft = Integer.MAX_VALUE;
    int maxRight = Integer.MIN_VALUE;
    for (Rectangle rec : buildings) {
      minLeft = Math.min(minLeft, rec.left);
      maxRight = Math.max(maxRight, rec.right);
    }
    List<Integer> heights = new ArrayList<>(Collections.nCopies(maxRight-minLeft+1, 0));
    for (Rectangle rec : buildings) {
      for (int i = rec.left; i <= rec.right; i++) {
        heights.set(i - minLeft, Math.max(heights.get(i - minLeft), rec.height));
      }
    }
    List<Rectangle> result = new ArrayList<>();
    int prevX = 0;
    for (int i = 1; i < heights.size(); i++) {
      if (heights.get(i) != heights.get(i-1)) {
        result.add(new Rectangle(prevX + minLeft, i-1+minLeft, heights.get(i-1)));
        prevX = i;
      }
    }
    result.add(new Rectangle(prevX+minLeft, maxRight, heights.get(prevX)));

    return result;
  }

  public static void main(String[] args) {
            GenericTestHandler.
            executeTestsByAnnotation(
                    new Object() {}.getClass().getEnclosingClass(), args);
  }
}