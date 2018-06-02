package epi;

import epi.test_framework.EpiTest;
import epi.test_framework.GenericTestHandler;

public class ConvertBase {
  @EpiTest(testfile = "convert_base.tsv")

  public static String convertBase(String numAsString, int b1, int b2) {
    boolean isNeg = (numAsString.charAt(0)=='-') ? true : false;
    int x = 0;
    //if (numAsString.equalsIgnoreCase("0") || numAsString.equalsIgnoreCase("-0")) return(numAsString);

    for (int i = (isNeg)?1:0; i < numAsString.length(); i++) {
      x*=b1;
      x+= (Character.isDigit(numAsString.charAt(i)) ? numAsString.charAt(i)-'0' : numAsString.charAt(i)-'A'+10);
    }

    String str = (x==0) ? "0" : constructFromBase(x, b2);

    return (isNeg)?'-'+str: str;
  }

  public static String constructFromBase(int x, int b2) {
    return (x==0) ? "" : constructFromBase(x / b2, b2) + ((x%b2>=10) ? (char) (x%b2 - 10 + 'A') : (char) (x%b2 + '0'));
  }

  public static void main(String[] args) {
    GenericTestHandler.executeTestsByAnnotation(
        new Object() {}.getClass().getEnclosingClass(), args);
  }
}
