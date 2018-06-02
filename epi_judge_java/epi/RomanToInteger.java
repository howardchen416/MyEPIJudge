package epi;

import epi.test_framework.EpiTest;
import epi.test_framework.GenericTestHandler;

import java.util.HashMap;
import java.util.Map;

public class RomanToInteger {
  @EpiTest(testfile = "roman_to_integer.tsv")

  public static int romanToInteger(String s) {

    Map<Character, Integer> m = new HashMap<Character, Integer>() {
      {
        put('I', 1);
        put('V', 5);
        put('X', 10);
        put('L', 50);
        put('C', 100);
        put('D', 500);
        put('M', 1000);
      }
    };

    int sum = m.get(s.charAt(s.length()-1));
    for (int i = s.length()- 2; i >= 0 ; --i) {
      if (m.get(s.charAt(i))<m.get(s.charAt(i+1)))
        sum-=m.get(s.charAt(i));
      else
        sum+=m.get(s.charAt(i));
    }

    return sum;
  }

  public static void main(String[] args) {
    GenericTestHandler.executeTestsByAnnotation(
        new Object() {}.getClass().getEnclosingClass(), args);
  }
}
