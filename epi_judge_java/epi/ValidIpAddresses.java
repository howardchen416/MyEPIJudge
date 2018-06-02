package epi;

import epi.test_framework.EpiTest;
import epi.test_framework.GenericTestHandler;
import epi.test_framework.EpiTestComparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.BiPredicate;

public class ValidIpAddresses {
  @EpiTest(testfile = "valid_ip_addresses.tsv")

  public static List<String> getValidIpAddress(String s) {
    if (s.length()<4) return new ArrayList<String>(0);
    List<String> a = new ArrayList<>();

    getValidIpAddressHelper(s, 0, 0, new int[4], a);

    return a;
  }

  public static void getValidIpAddressHelper(String s, int positionIdx, int startIdx, int[] partialResults, List<String> results) {
    // base condition
    if (positionIdx==4){
      results.add(Integer.toString(partialResults[0])+'.'+Integer.toString(partialResults[1])+'.'+Integer.toString(partialResults[2])+'.'+Integer.toString(partialResults[3]));
      return;
    }

    for (int i = Math.max(startIdx+1, s.length()-(3-positionIdx)*3); i <= Math.min(startIdx+3, s.length()-(3-positionIdx)); i++) {
      if (isValidIpPart(s.substring(startIdx, i))) {
        partialResults[positionIdx] = Integer.parseInt(s.substring(startIdx, i));
        getValidIpAddressHelper(s, positionIdx+1, i, partialResults, results);
      }
    }

    return;
  }

  public static boolean isValidIpPart(String s) {
    //
    if (s.length()>3) return false;
    if (s.length()>1 && s.charAt(0)=='0') return false;
    return (Integer.parseInt(s)<=255 && Integer.parseInt(s)>=0);
  }

  @EpiTestComparator
  public static BiPredicate<List<String>, List<String>> comp =
      (expected, result) -> {
    if (result == null) {
      return false;
    }
    Collections.sort(expected);
    Collections.sort(result);
    return expected.equals(result);
  };

  public static void main(String[] args) {
    GenericTestHandler.executeTestsByAnnotation(
        new Object() {}.getClass().getEnclosingClass(), args);
  }
}
