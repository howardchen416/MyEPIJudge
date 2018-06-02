package epi;

import epi.test_framework.EpiTest;
import epi.test_framework.EpiUserType;
import epi.test_framework.GenericTestHandler;
import epi.test_framework.TestFailureException;
import epi.test_framework.TestTimer;

import java.util.*;

public class GroupEqualEntries {
  @EpiUserType(ctorParams = {Integer.class, String.class})

  public static class Person {
    public Integer age;
    public String name;

    public Person(Integer k, String n) {
      age = k;
      name = n;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o)
        return true;
      if (o == null || getClass() != o.getClass())
        return false;

      Person person = (Person)o;

      if (!age.equals(person.age))
        return false;
      return name.equals(person.name);
    }

    @Override
    public int hashCode() {
      int result = age.hashCode();
      result = 31 * result + name.hashCode();
      return result;
    }
  }

  public static void
  groupByAge(List<Person> people) {
    Map<Integer, Integer> ageCount = new HashMap<>();
    for (Person p : people) {
      if (!ageCount.containsKey(p.age)) ageCount.put(p.age, 1);
      else ageCount.put(p.age, ageCount.get(p.age)+1);
    }
    Map<Integer, Integer> ageOffset = new HashMap<>();
    int offset = 0;
    for (Map.Entry<Integer, Integer> e : ageCount.entrySet()) {
      ageOffset.put(e.getKey(), offset);
      offset += e.getValue();
    }

    while (!ageOffset.isEmpty()) {
      Map.Entry<Integer, Integer> e = ageOffset.entrySet().iterator().next();
      int posnFrom = e.getValue();
      int age = people.get(posnFrom).age;
      int posnTo = ageOffset.get(age);
      Collections.swap(people, posnFrom, posnTo);
      int count = ageCount.get(age);
      if (count==1) {
        ageOffset.remove(age);
        ageCount.put(age, 0);
      } else {
        ageCount.put(age, ageCount.get(age) - 1);
        ageOffset.put(age, ageOffset.get(age) + 1);
      }
    }
    return;
  }

  private static Map<Person, Integer> buildMultiset(List<Person> people) {
    Map<Person, Integer> m = new HashMap<>();
    for (Person p : people) {
      m.put(p, m.getOrDefault(p, 0) + 1);
    }
    return m;
  }

  @EpiTest(testfile = "group_equal_entries.tsv")
  public static void groupByAgeWrapper(TestTimer timer, List<Person> people)
      throws TestFailureException {
    if (people.isEmpty()) {
      return;
    }
    Map<Person, Integer> values = buildMultiset(people);

    timer.start();
    groupByAge(people);
    timer.stop();

    Map<Person, Integer> newValues = buildMultiset(people);
    if (!values.equals(newValues)) {
      throw new TestFailureException("Entry set changed");
    }
    int lastAge = people.get(0).age;
    Set<Integer> ages = new HashSet<>();

    for (Person p : people) {
      if (ages.contains(p.age)) {
        throw new TestFailureException("Entries are not grouped by age");
      }
      if (p.age != lastAge) {
        ages.add(lastAge);
        lastAge = p.age;
      }
    }
  }

  public static void main(String[] args) {
    GenericTestHandler.executeTestsByAnnotation(
        new Object() {}.getClass().getEnclosingClass(), args);
  }
}
