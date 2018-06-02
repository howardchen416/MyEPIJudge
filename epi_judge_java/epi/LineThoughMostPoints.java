package epi;

import epi.test_framework.EpiTest;
import epi.test_framework.EpiUserType;
import epi.test_framework.GenericTestHandler;

import java.util.*;

public class LineThoughMostPoints {
  @EpiUserType(ctorParams = {int.class, int.class})

  public static class Point {
    public int x, y;

    public Point(int x, int y) {
      this.x = x;
      this.y = y;
    }
  };

  public static class Line {
    private static class Rational {
      int numerator;
      int denominator;

      public Rational(int numerator, int denominator) {
        this.numerator = numerator;
        this.denominator = denominator;
      }

      public static Rational findCanonicalRational(int numerator, int denominator) {
        if (denominator==0) return new Rational(numerator, denominator);

        int gcd = (int) Gcd.GCD(numerator, denominator);
        numerator /= gcd;
        denominator /= gcd;

        return (denominator<0) ? new Rational(-numerator, -denominator) : new Rational(numerator, denominator);
      }

      @Override
      public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rational rational = (Rational) o;
        return numerator == rational.numerator &&
                denominator == rational.denominator;
      }

      @Override
      public int hashCode() {

        return Objects.hash(numerator, denominator);
      }
    }

    Rational slope;
    Rational intercept;

    Line(Point p1, Point p2) {
      if (p1.x==p2.x) {
        this.intercept = new Rational(p1.x, 1);
        this.slope = new Rational(1, 0);
      }
      else {
        this.intercept = Rational.findCanonicalRational(p2.x*p1.y - p1.x*p2.y, p2.x - p1.x);
        this.slope = Rational.findCanonicalRational(p2.y - p1.y, p2.x - p1.x);
      }
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      Line line = (Line) o;
      return slope.equals(line.slope) &&
              intercept.equals(line.intercept);
    }

    @Override
    public int hashCode() {
      return Objects.hash(slope.numerator, slope.denominator, intercept.numerator, intercept.denominator);
    }
  }

  @EpiTest(testfile = "line_though_most_points.tsv")

  public static int findLineWithMostPoints(List<Point> points) {
    Map<Line, Set<Point>> map = new HashMap<>();

    for (int i = 0; i < points.size()-1; i++) {
      for (int j = i+1; j < points.size(); j++) {
        Line line = new Line(points.get(i), points.get(j));
        if (!map.containsKey(line)) {
          Set<Point> set = new HashSet<>();
          map.put(line, set);
        }
        map.get(line).add(points.get(i));
        map.get(line).add(points.get(j));
      }
    }

    int maxPoints = 0;
    for (Map.Entry<Line, Set<Point>> me : map.entrySet()) {
      maxPoints = Math.max(maxPoints, me.getValue().size());
    }
    return maxPoints;
  }

  public static void main(String[] args) {
    GenericTestHandler.executeTestsByAnnotation(
        new Object() {}.getClass().getEnclosingClass(), args);
  }
}
