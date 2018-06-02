package epi;

import epi.test_framework.EpiTest;
import epi.test_framework.EpiUserType;
import epi.test_framework.GenericTestHandler;

import java.util.*;

public class HuffmanCoding {
  @EpiUserType(ctorParams = {String.class, double.class})

  public static class CharWithFrequency {
    public char c;
    public double freq;
    public String code;

    public CharWithFrequency(String s, double freq) {
      if (s.length() != 1) {
        throw new RuntimeException(
            "CharWithFrequency parser: string length is not 1");
      }
      this.c = s.charAt(0);
      this.freq = freq;
    }
  }

  private static class BinaryTreeNode implements Comparable<BinaryTreeNode> {
    double frequency;
    CharWithFrequency charWithFrequency; // only not null for leaf nodes
    BinaryTreeNode left;
    BinaryTreeNode right;

    public BinaryTreeNode(double frequency, CharWithFrequency charWithFrequency, BinaryTreeNode left, BinaryTreeNode right) {
      this.frequency = frequency;
      this.charWithFrequency = charWithFrequency;
      this.left = left;
      this.right = right;
    }

    @Override
    public int compareTo(BinaryTreeNode o) {
      return Double.compare(this.frequency, o.frequency);
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      BinaryTreeNode that = (BinaryTreeNode) o;
      return Double.compare(that.frequency, frequency) == 0;
    }

    @Override
    public int hashCode() {

      return Objects.hash(frequency);
    }
  }

  @EpiTest(testfile = "huffman_coding.tsv")

  public static Double huffmanEncoding(List<CharWithFrequency> symbols) {
    PriorityQueue<BinaryTreeNode> pq = new PriorityQueue<>();
    // add all leaf nodes
    for (int i = 0; i < symbols.size(); i++) {
      pq.offer(new BinaryTreeNode(symbols.get(i).freq, symbols.get(i), null, null));
    }
    // process two nodes at a time
    while (pq.size()>1) {
      BinaryTreeNode left = pq.poll();
      BinaryTreeNode right = pq.poll();
      pq.offer(new BinaryTreeNode(left.frequency+right.frequency, null, left, right));
    }

    Map<Character, String> encodingMap = new HashMap<>();
    assignHuffmanEncoding(encodingMap, pq.peek(), new StringBuilder());

    double avg = 0.0d;
    for (CharWithFrequency symbol : symbols) {
      avg += (double
              ) encodingMap.get(symbol.c).length() * symbol.freq;
    }
    return avg/100.0d;
  }

  private static void assignHuffmanEncoding(Map<Character, String> map, BinaryTreeNode tree, StringBuilder sb) {

    if (tree==null) return;
    if (tree.charWithFrequency!=null) { // leaf
      map.put(tree.charWithFrequency.c, sb.toString());
      return;
    }
    sb.append('0');
    assignHuffmanEncoding(map, tree.left, sb);
    sb.setLength(sb.length()-1);
    sb.append('1');
    assignHuffmanEncoding(map, tree.right, sb);
    sb.setLength(sb.length()-1);
    return;
  }

  public static void main(String[] args) {
    GenericTestHandler.executeTestsByAnnotation(
        new Object() {}.getClass().getEnclosingClass(), args);
  }
}
