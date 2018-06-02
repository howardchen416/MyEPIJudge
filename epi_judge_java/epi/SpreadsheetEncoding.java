package epi;

import epi.test_framework.EpiTest;
import epi.test_framework.GenericTestHandler;

public class SpreadsheetEncoding {
  @EpiTest(testfile = "spreadsheet_encoding.tsv")

  public static int ssDecodeColID(final String col) {
    int n  = 0;
    for (int i = 0; i < col.length(); i++) {
      n*=26;
      n+= (int) (col.charAt(i)-'A'+1);
    }
    return n;
  }

  public static void main(String[] args) {
    GenericTestHandler.executeTestsByAnnotation(
        new Object() {}.getClass().getEnclosingClass(), args);
  }
}
