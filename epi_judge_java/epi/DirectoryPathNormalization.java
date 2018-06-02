package epi;

import epi.test_framework.EpiTest;
import epi.test_framework.GenericTestHandler;

import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;

public class DirectoryPathNormalization {
  @EpiTest(testfile = "directory_path_normalization.tsv")

  public static String shortestEquivalentPath(String path) {
    Deque<String> d = new LinkedList<String>();

    // special case - path starts with '/' - meaning absolute path
    if (d.isEmpty() && path.startsWith("/"))
      d.offerFirst("/");

    for (String token : path.split("/")) {
      if (token.equals("..")) {
        if (d.isEmpty() || d.peekFirst().equals(".."))
          d.offerFirst("..");
        else if (d.peekFirst().equals("/"))
          throw new IllegalArgumentException("");
        else
          d.pollFirst();
      }
      else if (token.length()>0 && !token.equals(".")) {
        d.offerFirst(token);
      }
    }

    StringBuilder sb = new StringBuilder();
    if (!d.isEmpty()) {
      Iterator<String> iter = d.descendingIterator();
      String str = iter.next();
      if (!str.equals("/") || (str.equals("/") && d.size()==1)) sb.append(str);
      //else sb.append(str + "/");
      while (iter.hasNext()) {
        sb.append("/" + iter.next());
      }
    }
    return sb.toString();
  }

  public static void main(String[] args) {
    GenericTestHandler.executeTestsByAnnotation(
        new Object() {}.getClass().getEnclosingClass(), args);
  }
}
