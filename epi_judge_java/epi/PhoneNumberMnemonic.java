package epi;

import epi.test_framework.EpiTest;
import epi.test_framework.EpiTestComparator;
import epi.test_framework.GenericTestHandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.BiPredicate;

public class PhoneNumberMnemonic {
  @EpiTest(testfile = "phone_number_mnemonic.tsv")

  public static List<String> phoneMnemonic(String phoneNumber) {
    List<String> mnemonics = new ArrayList<>();
    mnemonicHelper(phoneNumber, 0, new char[phoneNumber.length()], mnemonics);
    return mnemonics;
  }

  public static void mnemonicHelper(String phoneNumber, int digit, char[] partialMnemonic, List<String> mnemonics) {
    if (digit == phoneNumber.length()) {
      mnemonics.add(new String(partialMnemonic));
      return;
    }
    for (int i = 0; i < MAPPING[(int) phoneNumber.charAt(digit) - '0'].length(); i++) {
      partialMnemonic[digit] = MAPPING[(int) phoneNumber.charAt(digit) - '0'].charAt(i);
      mnemonicHelper(phoneNumber, digit+1, partialMnemonic, mnemonics);
    }
  }

  private static final String[] MAPPING = {"0", "1", "ABC", "DEF", "GHI", "JKL", "MNO", "PQRS", "TUV", "WXYZ"};



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
