package epi;

import epi.test_framework.EpiTest;
import epi.test_framework.EpiUserType;
import epi.test_framework.GenericTestHandler;
import epi.test_framework.TestTimer;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class IsCircuitWirable {

  public static class GraphVertex {
    public int d = -1;
    public List<GraphVertex> edges = new ArrayList<>();
  }

  public static boolean isAnyPlacementFeasible(List<GraphVertex> graph) {
    for (GraphVertex v : graph) {
      if (v.d == -1) {// hasn't been visited
        v.d = 0;
        if (!isAnyPlacementFeasibleHelper(v)) return false;
      }
    }
    return true;
  }

  public static boolean isAnyPlacementFeasibleHelper(GraphVertex v) {
    Queue<GraphVertex> q = new LinkedList<>();
    q.offer(v);
    while(!q.isEmpty()) {
      GraphVertex c = q.poll();
      for(GraphVertex n : c.edges) {
        if (n.d==-1) { // hasn't been visited before
          n.d = c.d + 1;
          q.offer(n);
        } else { // has been visited before
          if (n.d==c.d) return false;
        }
      }
    }
    return true;
  }


  @EpiUserType(ctorParams = {int.class, int.class})
  public static class Edge {
    public int from;
    public int to;

    public Edge(int from, int to) {
      this.from = from;
      this.to = to;
    }
  }

  @EpiTest(testfile = "is_circuit_wirable.tsv")
  public static boolean isAnyPlacementFeasibleWrapper(TestTimer timer, int k,
                                                      List<Edge> edges) {
    if (k <= 0)
      throw new RuntimeException("Invalid k value");
    List<GraphVertex> graph = new ArrayList<>();
    for (int i = 0; i < k; i++)
      graph.add(new GraphVertex());
    for (Edge e : edges) {
      if (e.from < 0 || e.from >= k || e.to < 0 || e.to >= k)
        throw new RuntimeException("Invalid vertex index");
      graph.get(e.from).edges.add(graph.get(e.to));
    }

    timer.start();
    boolean result = isAnyPlacementFeasible(graph);
    timer.stop();
    return result;
  }

  public static void main(String[] args) {
    GenericTestHandler.executeTestsByAnnotation(
        new Object() {}.getClass().getEnclosingClass(), args);
  }
}
