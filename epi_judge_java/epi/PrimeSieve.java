package epi;

import epi.test_framework.EpiTest;
import epi.test_framework.GenericTestHandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PrimeSieve {
  @EpiTest(testfile = "prime_sieve.tsv")
  // Given n, return all primes up to and including n.
  public static List<Integer> generatePrimes(int n) {
    List<Integer> primes = new ArrayList<>();
    List<Boolean> isPrime = new ArrayList<>(Collections.nCopies(n+1, true));
    isPrime.set(0, false);
    isPrime.set(1, false);
    for (int i = 2; i <= n; i++) {
      if (isPrime.get(i)) {
        primes.add(i);
        for (int j = i; j <=n; j+=i) {
          isPrime.set(j, false);
        }
      }
    }
    return primes;
  }

  public static void main(String[] args) {
    GenericTestHandler.executeTestsByAnnotation(
        new Object() {}.getClass().getEnclosingClass(), args);
  }
}
