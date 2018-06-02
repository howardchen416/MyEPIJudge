package epi;

import epi.test_framework.EpiTest;
import epi.test_framework.GenericTestHandler;

import java.util.ArrayList;
import java.util.List;

public class LeftRightJustifyText {
  @EpiTest(testfile = "left_right_justify_text.tsv")

  public static List<String> justifyText(List<String> words, int L) {
    int numWordsCurrLine = 0;
    int currLineWordLength = 0;
    int startIdx = 0;
    List<String> result = new ArrayList<>();

    for (int i = 0; i < words.size(); i++) {
      numWordsCurrLine++;
      int lookAheadLineLength = currLineWordLength + words.get(i).length() + (numWordsCurrLine-1);

      if (lookAheadLineLength==L) {
        result.add(joinALineWithSpace(words, startIdx, i, numWordsCurrLine-1));
        startIdx = i+1;
        currLineWordLength = 0;
        numWordsCurrLine = 0;
      }
      else if (lookAheadLineLength>L) {
        result.add(joinALineWithSpace(words, startIdx, i-1, L - currLineWordLength));
        startIdx = i;
        currLineWordLength = words.get(i).length();
        numWordsCurrLine = 1;
      }
      else { // lookAheadLineLength < L
        currLineWordLength += words.get(i).length();
      }
    }

    // take care of last line
    if (numWordsCurrLine>0) {
      StringBuilder sb = new StringBuilder();
      sb.append(words.get(startIdx));
      for (int i = startIdx+1; i < words.size(); i++) {
        sb.append(" " + words.get(i));
      }
      for (int i = 0; i< L - currLineWordLength - (numWordsCurrLine-1); i++) {
        sb.append(" ");
      }
      result.add(sb.toString());
    }

    return result;
  }

  // Joins strings in words[start : end] with numSpaces spaces spread evenly.
  private static String joinALineWithSpace(List<String> words, int start, int end, int numSpaces) {
    int numWordsCurrLine = end - start + 1;
    StringBuilder line = new StringBuilder();
    for (int i = start; i < end; ++i) {
      line.append(words.get(i));
      --numWordsCurrLine;
      int numCurrSpace = (int) Math.ceil((double) numSpaces / numWordsCurrLine);
      for (int j = 0; j<numCurrSpace; j++){
        line.append(" ");
      }
      numSpaces -= numCurrSpace;
    }
    line.append(words.get(end));
    for (int i = 0; i<numSpaces; i++){
      line.append(" ");
    }
    return line.toString();
  }

  public static void main(String[] args) {
    GenericTestHandler.executeTestsByAnnotation(
        new Object() {}.getClass().getEnclosingClass(), args);
  }
}
