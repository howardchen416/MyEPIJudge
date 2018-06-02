package epi;

import epi.test_framework.EpiTest;
import epi.test_framework.EpiUserType;
import epi.test_framework.GenericTestHandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

public class CalendarRendering {
  @EpiUserType(ctorParams = {int.class, int.class})

  public static class Event {
    public int start, finish;

    public Event(int start, int finish) {
      this.start = start;
      this.finish = finish;
    }
  }

  private static class Endpoint implements Comparable<Endpoint> {
    public int time;
    public boolean isStart;

    Endpoint(int time, boolean isStart) {
      this.time = time;
      this.isStart = isStart;
    }

    @Override
    public int compareTo(Endpoint o) { // this is the key here, start endpoints need to be processed ahead of end endpoints
      return (this.time==o.time) ? (this.isStart ? -1 : 1): Integer.compare(this.time, o.time);
    }
  }

  @EpiTest(testfile = "calendar_rendering.tsv")

  public static int findMaxSimultaneousEvents(List<Event> A) {
    // the durations are endpoint inclusive as such two events with one ending at the start of another has to count as a height of two.
    // special condition(s)
    if (A==null || A.size()==0) return 0;
    if (A.size()==1) return 1;

    //PriorityQueue<Endpoint> minQ = new PriorityQueue<>();
    List<Endpoint> minQ = new ArrayList<>();
    for (Event e : A) {
      if (e!=null) {
        minQ.add(new Endpoint(e.start, true));
        minQ.add(new Endpoint(e.finish, false));
      }
    }
    Collections.sort(minQ);
    int maxHeight = Integer.MIN_VALUE;
    int cnt = 0;
    //while (!minQ.isEmpty()) {
    for (int i = 0; i < minQ.size(); i++) {
      //Endpoint ep = minQ.poll();
      Endpoint ep = minQ.get(i);
      cnt += (ep.isStart) ? 1 : -1;
      maxHeight = Math.max(cnt, maxHeight);
      //System.out.println("Time:" + ep.time + " IsStart:" + ep.isStart + " Height:" + cnt + " maxHeight: " + maxHeight);
    }
    return maxHeight;
  }

  public static void main(String[] args) {
    GenericTestHandler.executeTestsByAnnotation(
        new Object() {}.getClass().getEnclosingClass(), args);
  }
}
