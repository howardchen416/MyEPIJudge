package epi;

import epi.test_framework.EpiTest;
import epi.test_framework.GenericTestHandler;

import java.util.List;

public class PrettyPrinting {
    @EpiTest(testfile = "pretty_printing.tsv")

    public static int minimumMessiness(List<String> words, int lineLength) {

        int[] minMessiness = new int[words.size()];
        int remainingSpaces = lineLength - words.get(0).length();
        minMessiness[0] = remainingSpaces*remainingSpaces;
        for (int i = 1; i < words.size(); i++) {
            remainingSpaces = lineLength - words.get(i).length();
            minMessiness[i] = remainingSpaces * remainingSpaces + minMessiness[i - 1];
            for (int j = i - 1; j >= 0; j--) { //
                remainingSpaces -= words.get(j).length() + 1;
                if (remainingSpaces<0) break;
                int firstJMessiness = (j==0) ? 0 : minMessiness[j-1];
                minMessiness[i] = Math.min(minMessiness[i], firstJMessiness + remainingSpaces*remainingSpaces);
            }
        }

        return minMessiness[words.size()-1];
    }

    public static void main(String[] args) {
        GenericTestHandler.executeTestsByAnnotation(
                new Object() {
                }.getClass().getEnclosingClass(), args);
    }
}
