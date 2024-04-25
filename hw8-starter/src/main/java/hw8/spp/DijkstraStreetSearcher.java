package hw8.spp;

import hw8.graph.Edge;
import hw8.graph.Graph;
import hw8.graph.Vertex;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class DijkstraStreetSearcher extends StreetSearcher {

  /**
   * Creates a StreetSearcher object.
   *
   * @param graph an implementation of Graph ADT.
   */
  public DijkstraStreetSearcher(Graph<String, String> graph) {
    super(graph);
  }

  class DijkstraInfo {
    double distance;
    Edge<String> previous;
    boolean explored;

    DijkstraInfo() {
      distance = Double.MAX_VALUE;
      previous = null;
      explored = false;
    }

    DijkstraInfo(double d, Edge<String> p, boolean e) {
      distance = d;
      previous = p;
      explored = e;
    }

    @Override
    public String toString() {
      return "distance: " + distance + ", explored: " + explored + ", previous: " + previous;
    }
  }

  /**
   * Class to implement compare method so that a Priority Queue
   * maybe sorted by distance.
   */
  class SortByDistance implements Comparator<Vertex<String>> {

    // returns 0 if equal, negative if o1 < o2, positive if o1 > o2
    @Override
    public int compare(Vertex<String> o1, Vertex<String> o2) {
      double d1 = ((DijkstraInfo) graph.label(o1)).distance;
      double d2 = ((DijkstraInfo) graph.label(o1)).distance;
      if (d1 < d2) {
        return -1;
      } else if (d1 > d2) {
        return 1;
      } else {
        return 0;
      }
    }
  }

  @Override
  public void findShortestPath(String startName, String endName) {
    Vertex<String> start = vertices.get(startName);
    Vertex<String> end = vertices.get(endName);

    // TODO - Implement Dijkstra Algorithm!
    // use label for data
    for (Vertex<String> v : graph.vertices()) {
      graph.label(v, new DijkstraInfo());
    }

    graph.label(start, new DijkstraInfo(0, null, false)); // distance[start] = 0
    PriorityQueue<Vertex<String>> pq = new PriorityQueue<>(this.vertices.size(), new SortByDistance());
    pq.add(start);

    while (!pq.isEmpty()) {
      Vertex<String> v = pq.poll();
      ((DijkstraInfo) graph.label(v)).explored = true; // mark as explored

      for (Edge<String> u : graph.outgoing(v)) { // for every neighbor
        Vertex<String> nodeU = graph.to(u);

        if (!((DijkstraInfo) graph.label(nodeU)).explored) { // if neighbor is unexplored
          double d = ((DijkstraInfo) graph.label(v)).distance + (double) graph.label(u);
          if (d < ((DijkstraInfo) graph.label(nodeU)).distance) {
            ((DijkstraInfo) graph.label(nodeU)).distance = d;
            ((DijkstraInfo) graph.label(nodeU)).previous = u;
          }
          pq.add(nodeU);
        }

      }

    }

    double totalDist = ((DijkstraInfo) graph.label(end)).distance;

    // update so that the vertex label is the Edge into it
    for (Vertex<String> v : graph.vertices()) {
      graph.label(v, ((DijkstraInfo) graph.label(v)).previous);
    }

    // These method calls will create and print the path for you
    List<Edge<String>> path = getPath(end, start);
    if (VERBOSE) {
      printPath(path, totalDist);
    }
  }
}
