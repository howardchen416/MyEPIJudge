package epi;

import epi.test_framework.EpiTest;
import epi.test_framework.EpiTestComparator;
import epi.test_framework.EpiTestExpectedType;
import epi.test_framework.EpiUserType;
import epi.test_framework.GenericTestHandler;

import java.util.*;
import java.util.function.BiPredicate;

public class RemoveDuplicates {
  @EpiUserType(ctorParams = {String.class, String.class})
  //@include
  public static class Name implements Comparable<Name> {
    String firstName;
    String lastName;

    public Name(String first, String last) {
      firstName = first;
      lastName = last;
    }

    @Override
    public boolean equals(Object obj) {
      if (obj == null || !(obj instanceof Name)) {
        return false;
      }
      if (this == obj) {
        return true;
      }
      Name name = (Name)obj;
      return firstName.equals(name.firstName);
    }

    @Override
    public String toString() {
      return firstName;
    }

    @Override
    public int compareTo(Name name) {
      return firstName.compareTo(name.firstName);
    }

    @Override
    public int hashCode() {

      return Objects.hash(firstName);
    }
  }

  public static List<Name> eliminateDuplicate(List<Name> names) {

    Collections.sort(names);

    int writeIdx = 1;
    for (int i = 1; i < names.size(); i++) {
      if (!names.get(i).equals(names.get(writeIdx-1)))
        names.set(writeIdx++, names.get(i));
    }
    names.subList(writeIdx, names.size()).clear();
    return names;
  }

  @EpiTest(testfile = "remove_duplicates.tsv")
  public static List<Name> eliminateDuplicateWrapper(List<Name> names) {
    names = eliminateDuplicate(names);
    return names;
  }

  @EpiTestComparator
  public static BiPredicate<List<String>, List<Name>> comp =
      (expected, result) -> {
    if (result == null) {
      return false;
    }
    Collections.sort(expected);
    Collections.sort(result);
    if (expected.size() != result.size()) {
      return false;
    }
    for (int i = 0; i < result.size(); i++) {
      if (!expected.get(i).equals(result.get(i).firstName)) {
        return false;
      }
    }
    return true;
  };

  @EpiTestExpectedType
  public static List<Class<?>> expectedType =
      Arrays.asList(List.class, String.class);

  public static void main(String[] args) {
    GenericTestHandler.executeTestsByAnnotation(
        new Object() {}.getClass().getEnclosingClass(), args);
  }
}
