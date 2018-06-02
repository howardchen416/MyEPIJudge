package epi;

import epi.test_framework.EpiTest;
import epi.test_framework.GenericTestHandler;

import java.util.*;

public class IsStringDecomposableIntoWords {
    @EpiTest(testfile = "is_string_decomposable_into_words.tsv")

    public static List<String> decomposeIntoDictionaryWords(String domain, Set<String> dictionary) {
        int[] lastLength = new int[domain.length()];
        Arrays.fill(lastLength, -1);


        for (int i = 0; i < domain.length(); i++) {
            if (dictionary.contains(domain.substring(0, i+1))) {
                lastLength[i] = i+1;
            }

            if (lastLength[i]==-1) {
                for (int j = 0; j < i; j++) {
                    if (lastLength[j]!=-1 && dictionary.contains(domain.substring(j+1, i+1))) {
                        lastLength[i] = i - j;
                        break;
                    }
                }
            }
        }

        List<String> strs = new ArrayList<>();
        if (lastLength[domain.length()-1]!=-1) {
            int idx = domain.length()-1;
            while (idx>=0) {
                strs.add(domain.substring(idx-lastLength[idx]+1, idx+1));
                idx -= lastLength[idx];
            }
        }
        Collections.reverse(strs);
        return strs;
    }

    public static void main(String[] args) {
        GenericTestHandler.executeTestsByAnnotation(
                new Object() {
                }.getClass().getEnclosingClass(), args);
    }
}
