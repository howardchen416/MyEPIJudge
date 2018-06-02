package epi;

import epi.test_framework.EpiTest;
import epi.test_framework.GenericTestHandler;
import epi.test_framework.TestTimer;

public class ReverseWords {

  public static void reverseWords(char[] input) {
    reverseChars(input, 0, input.length);
    int start = 0;
    int ende;
    while ((ende = find(input, start, ' ')) != -1) {
      reverseChars(input, start, ende);
      start = ende+1;
    }
    // reverse last word
    reverseChars(input, start, input.length);

    return;
  }

  public static void reverseChars(char[] input, int start, int ende) {
    for (int i = 0; i < (ende-start)/2; i++) {
      char t = input[start+i];
      input[start+i] = input[ende-i-1];
      input[ende-i-1] = t;
    }
    return;
  }

  public static int find(char[] input, int start, char c) {
    for (int i = start; i < input.length; i++) {
      if (input[i]==c)
        return i;
    }
    return -1;
  }

  @EpiTest(testfile = "reverse_words.tsv")
  public static String reverseWordsWrapper(TestTimer timer, String s) {
    char[] sCopy = s.toCharArray();

    timer.start();
    reverseWords(sCopy);
    timer.stop();

    return String.valueOf(sCopy);
  }

  public static void main(String[] args) {
    GenericTestHandler.executeTestsByAnnotation(
        new Object() {}.getClass().getEnclosingClass(), args);
  }
}
