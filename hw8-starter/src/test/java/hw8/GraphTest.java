package hw8;

import exceptions.InsertionException;
import exceptions.PositionException;
import exceptions.RemovalException;
import hw8.graph.Edge;
import hw8.graph.Graph;
import hw8.graph.Vertex;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public abstract class GraphTest {

  protected Graph<String, String> graph;

  @BeforeEach
  public void setupGraph() {
    this.graph = createGraph();
  }

  protected abstract Graph<String, String> createGraph();

  @Test
  @DisplayName("insert(v) returns a vertex with given data")
  public void canGetVertexAfterInsert() {
    Vertex<String> v1 = graph.insert("v1");
    assertEquals(v1.get(), "v1");
  }

  @Test
  @DisplayName("insert(U, V, e) returns an edge with given data")
  public void canGetEdgeAfterInsert() {
    Vertex<String> v1 = graph.insert("v1");
    Vertex<String> v2 = graph.insert("v2");
    Edge<String> e1 = graph.insert(v1, v2, "v1-v2");
    assertEquals(e1.get(), "v1-v2");
  }

  @Test
  @DisplayName("insert(null, V, e) throws exception")
  public void insertEdgeThrowsPositionExceptionWhenfirstVertexIsNull() {
    try {
      Vertex<String> v = graph.insert("v");
      graph.insert(null, v, "e");
      fail("The expected exception was not thrown");
    } catch (PositionException ex) {
      return;
    }
  }

  @Test
  @DisplayName("insert(v) throws InsertionException if v is already in the graph.")
  public void insertVertexThrowsExceptionWhenVertexAlreadyPresent() {
    Vertex<String> v1 = graph.insert("v1");
    try {
      graph.insert("v1");
      fail("The expected exception was not thrown");
    } catch (InsertionException ex) {
      return;
    }
  }

  @Test
  @DisplayName("insert(v, v, e) throws an exception when edge would create self-loop.")
  public void insertEdgeThrowsExceptionWhenSelfLoop() {
    Vertex<String> v1 = graph.insert("v1");
    try {
      graph.insert(v1, v1, "v1-v1");
      fail("The expected exception was not thrown");
    } catch (InsertionException ex) {
      return;
    }
  }

  @Test
  @DisplayName("insert(v, v, e) throws an exeption when insertion would create duplicate edge.")
  public void insertEdgeThrowsExceptionWhenDuplicateEdge() {
    Vertex<String> v1 = graph.insert("v1");
    Vertex<String> v2 = graph.insert("v2");
    Edge<String> v1v2 = graph.insert(v1, v2, "v1-v2");
    try {
      graph.insert(v1, v2, "v1-v2");
      fail("The expected exception was not thrown");
    } catch (InsertionException ex) {
      return;
    }
  }

  @Test
  @DisplayName("insert(null, v, e) throws exception since source is null.")
  public void insertVertexThrowsExceptionWhenSourceIsNull() {
    Vertex<String> v1 = graph.insert("v1");
    try {
      graph.insert(null, v1, "null-v1");
      fail("The expected exception was not thrown");
    } catch (PositionException ex) {
      return;
    }
  }

  @Test
  @DisplayName("remove(v) throws exception on invalid position.")
  public void removeVertexThrowsExceptionWhenInvalidPosition() {
    try {
      graph.remove((Vertex<String>) null);
      fail("The expected exception was not thrown");
    } catch (PositionException ex) {
      return;
    }
  }

  @Test
  @DisplayName("remove(v) throws except when vertex still has incident edges.")
  public void removeThrowsExceptionWhenVertexHasIncidentEdges() {
    Vertex<String> v1 = graph.insert("v1");
    Vertex<String> v2 = graph.insert("v2");
    graph.insert(v1, v2, "v1-v2");
    try {
      graph.remove(v1);
      fail("The expected exception was not thrown");
    } catch (RemovalException ex) {
      return;
    }
  }

  @Test
  @DisplayName("remove(v) sucessfully removes one vertex when it is the only vertex present.")
  public void removesOneVertexWithNoOtherVertices() {
    Vertex<String> v1 = graph.insert("v1");
    graph.remove(v1);
    // FIXME
  }

  @Test
  @DisplayName("remove(v) does not modify the surrounding graph when a vertex is removed.")
  public void removesOneVertexAndPreservesStructure() {
    // FIXME
  }

  @Test
  @DisplayName("vertices() iterates over three vertices.")
  public void verticesIteratesOverThreeVertices() {
    Vertex<String> v1 = graph.insert("v1");
    Vertex<String> v2 = graph.insert("v2");
    Vertex<String> v3 = graph.insert("v3");
    int vertexCount = 0;
    ArrayList<Vertex<String>> all = new ArrayList<>(); // track all vertices
    all.add(v1);
    all.add(v2);
    all.add(v3);
    for (Vertex<String> v : graph.vertices()) {
      vertexCount++;
      all.remove(v); // remove vertex when it is iterated over
    }
    assertEquals(vertexCount, 3);
    assertEquals(0, all.size());
  }

  @Test
  @DisplayName("vertices() returns empty iterator upon empty graph.")
  public void verticesIteratesEmptyGraph() {
    int vertexCount = 0;
    for (Vertex<String> v : graph.vertices()) {
      vertexCount++;
    }
    assertEquals(0, vertexCount);
  }

  @Test
  @DisplayName("vertices() iterates over different connected components.")
  public void verticesIteratesOverDifferentConnectedComponents() {
    Vertex<String> v1 = graph.insert("v1");
    Vertex<String> v2 = graph.insert("v2");
    Vertex<String> v3 = graph.insert("v3");
    Vertex<String> v4 = graph.insert("v4");
    Edge<String> v1v2 = graph.insert(v1, v2, "v1v2");
    Edge<String> v3v4 = graph.insert(v3, v4, "v3v4");
    ArrayList<Vertex<String>> all = new ArrayList<>();
    all.add(v1);
    all.add(v2);
    all.add(v3);
    all.add(v4);
    int vertexCount = 0;
    for (Vertex<String> v : graph.vertices()) {
      all.remove(v);
      vertexCount++;
    }
    assertEquals(4, vertexCount);
    assertEquals(0, all.size());
  }

  @Test
  @DisplayName("vertices() iterates over nothing after vertex has been removed.")
  public void verticesIteratesOverNothingAfterVertexHasBeenRemoved() {
    Vertex<String> v1 = graph.insert("v1");
    graph.remove(v1);
    int vertexCount = 0;
    for (Vertex<String> v : graph.vertices()) {
      vertexCount++;
    }
    assertEquals(0, vertexCount);
  }

  @Test
  @DisplayName("edges() returns empty iterator upon empty graph")
  public void edgesIteratesEmptyGraph() {
    int edgeCount = 0;
    for (Edge<String> e : graph.edges()) {
      edgeCount++;
    }
    assertEquals(0, edgeCount);
  }

  @Test
  @DisplayName("edges() returns empty iterator upon entirely disconnected graph.")
  public void edgesIteratesEntirelyDisconnectedGraph() {
    Vertex<String> v1 = graph.insert("v1");
    Vertex<String> v2 = graph.insert("v2");
    int edgeCount = 0;
    for (Edge<String> e : graph.edges()) {
      edgeCount++;
    }
    assertEquals(0, edgeCount);
  }

  @Test
  @DisplayName("edges() iterates over edges in different connected components.")
  public void edgesIteratesOverEdgesInDifferentConnectedComponents() {
    Vertex<String> v1 = graph.insert("v1");
    Vertex<String> v2 = graph.insert("v2");
    Vertex<String> v3 = graph.insert("v3");
    Vertex<String> v4 = graph.insert("v4");
    Edge<String> v1v2 = graph.insert(v1, v2, "v1v2");
    Edge<String> v3v4 = graph.insert(v3, v4, "v3v4");
    ArrayList<Edge<String>> all = new ArrayList<>();
    all.add(v1v2);
    all.add(v3v4);
    int edgeCount = 0;
    for (Edge<String> e : graph.edges()) {
      all.remove(e);
      edgeCount++;
    }
    assertEquals(2, edgeCount);
    assertEquals(0, all.size());
  }

  @Test
  @DisplayName("edges() iterates over two edges.")
  public void edgesIteratesOverTwoEdges() {
    Vertex<String> v1 = graph.insert("v1");
    Vertex<String> v2 = graph.insert("v2");
    Vertex<String> v3 = graph.insert("v3");
    Edge<String> v1v2 = graph.insert(v1, v2, "v1-v2");
    Edge<String> v2v3 = graph.insert(v2, v3, "v2-v3");
    ArrayList<Edge<String>> all = new ArrayList<>();
    all.add(v1v2);
    all.add(v2v3);
    int edgeCount = 0;
    for (Edge<String> e : graph.edges()) {
      edgeCount++;
      all.remove(e);
    }
    assertEquals(2, edgeCount);
    assertEquals(0, all.size());
  }

  @Test
  @DisplayName("remove(v) throws exception when vertex not present.")
  public void removeVertexThrowsExceptionWhenVertexNotPresent() {
    Vertex<String> v1 = graph.insert("v1");
    graph.remove(v1);
    try {
      graph.remove(v1);
      fail("The expected exception was not thrown");
    } catch (PositionException ex) {
      return;
    }
  }

  @Test
  @DisplayName("remove(e) throws exception for invalid edge ")
  public void removeEdgeThrowsExceptionWhenInvalidEdge() {
    try {
      graph.remove((Edge<String>)null);
      fail("The expected exception was not thrown");
    } catch (PositionException ex) {
      return;
    }
  }

  @Test
  @DisplayName("remove(e) throws exception when edge not present.")
  public void removeThrowExceptionWhenEdgeNotPresent() {
    Vertex<String> v1 = graph.insert("v1");
    Vertex<String> v2 = graph.insert("v2");
    Edge<String> v1v2 = graph.insert(v1, v2, "v1-v2");
    graph.remove(v1v2);
    try {
      graph.remove(v1v2);
      fail("The expected exception was not thrown");
    } catch (PositionException ex) {
      return;
    }
  }

  @Test
  @DisplayName("outgoing() iterates over no edges when a vertex has degree zero.")
  public void outgoingIteratesOverNoEdges() {
    Vertex<String> v1 = graph.insert("v1");
    int edgeCount = 0;
    for (Edge<String> e : graph.outgoing(v1)) {
      edgeCount++;
    }
    assertEquals(0, edgeCount);
  }

  @Test
  @DisplayName("outgoing() iterates over outgoing edges only.")
  public void outgoingIteratesOverOutgoingEdges() {
    Vertex<String> v1 = graph.insert("v1");
    Vertex<String> v2 = graph.insert("v2");
    Vertex<String> v3 = graph.insert("v3");
    Edge<String> v1v2 = graph.insert(v1, v2, "v1->v2");
    Edge<String> v1v3 = graph.insert(v1, v3, "v1->v3");
    Edge<String> v2v1 = graph.insert(v2, v1, "v2->v1");
    int edgeCount = 0;
    ArrayList<Edge<String>> expected = new ArrayList<>();
    expected.add(v1v2);
    expected.add(v1v3);
    for (Edge<String> e : graph.outgoing(v1)) {
      edgeCount++;
      expected.remove(e);
    }
    assertEquals(2, edgeCount);
    assertEquals(0, expected.size());
  }

  @Test
  @DisplayName("incoming() iterates over no edges when a vertex has degree zero.")
  public void incomingIteratesOverNoEdges() {
    Vertex<String> v1 = graph.insert("v1");
    int edgeCount = 0;
    for (Edge<String> e : graph.incoming(v1)) {
      edgeCount++;
    }
    assertEquals(0, edgeCount);
  }

  @Test
  @DisplayName("incoming() iterates over incoming edges only.")
  public void incomingIteratesOverIncomingEdges() {
    Vertex<String> v1 = graph.insert("v1");
    Vertex<String> v2 = graph.insert("v2");
    Vertex<String> v3 = graph.insert("v3");
    Edge<String> v1v2 = graph.insert(v1, v2, "v1->v2");
    Edge<String> v1v3 = graph.insert(v1, v3, "v1->v3");
    Edge<String> v2v1 = graph.insert(v2, v1, "v2->v1");
    int edgeCount = 0;
    ArrayList<Edge<String>> expected = new ArrayList<>();
    expected.add(v2v1);
    for (Edge<String> e : graph.incoming(v1)) {
      edgeCount++;
      expected.remove(e);
    }
    assertEquals(1, edgeCount);
    assertEquals(0, expected.size());
  }

  @Test
  @DisplayName("from(e) returns appropriate source edge.")
  public void fromReturnsAppropriateSourceEdge() {
    Vertex<String> v1 = graph.insert("v1");
    Vertex<String> v2 = graph.insert("v2");
    Edge<String> v1v2 = graph.insert(v1, v2, "v1->v2");
    assertEquals(v1, graph.from(v1v2));
  }

  @Test
  @DisplayName("to(e) returns appropriate destination edge.")
  public void toReturnsAppropriateDestinationEdge() {
    Vertex<String> v1 = graph.insert("v1");
    Vertex<String> v2 = graph.insert("v2");
    Edge<String> v1v2 = graph.insert(v1, v2, "v1->v2");
    assertEquals(v2, graph.to(v1v2));
  }

  @Test
  @DisplayName("from(null) throws exception")
  public void fromNullThrowsException() {
    Vertex<String> v1 = graph.insert("v1");
    Vertex<String> v2 = graph.insert("v2");
    Edge<String> v1v2 = graph.insert(v1, v2, "v1->v2");
    try {
      graph.from(null);
      fail("The expected exception was not thrown");
    } catch (PositionException ex) {
      return;
    }

  }

  @Test
  @DisplayName("to() throws exception when edge has already been removed from graph.")
  public void toThrowsExceptionWhenEdgeHasAlreadyBeenRemovedFromGraph() {
    Vertex<String> v1 = graph.insert("v1");
    Vertex<String> v2 = graph.insert("v2");
    Edge<String> v1v2 = graph.insert(v1, v2, "v1->v2");
    graph.remove(v1v2); // removes edge before inquiring
    try {
      graph.to(v1v2);
      fail("The expected exception was not thrown");
    } catch (PositionException ex) {
      return;
    }
  }

  @Test
  @DisplayName("label(v) appropriately relabels an existing vertex.")
  public void labelRenamesVertex() {
    Vertex<String> v1 = graph.insert("v1");
    graph.label(v1, "label!");
    assertEquals("label!", graph.label(v1));
  }

  @Test
  @DisplayName("label(v) throws exception when an nonexistent vertex is attempted to be relabeled.")
  public void labelDoesNotRenamesNonexistentVertex() {
    Vertex<String> v1 = graph.insert("v1");
    graph.remove(v1);
    try {
      graph.label(v1, "label!");
      fail("The expected exception was not thrown.");
    } catch (PositionException ex) {
      return;
    }
  }

  @Test
  @DisplayName("label(e) appropriately relabels an existing edge.")
  public void labelRenamesEdge() {
    Vertex<String> v1 = graph.insert("v1");
    Vertex<String> v2 = graph.insert("v2");
    Edge<String> v1v2 = graph.insert(v1, v2, "v1->v2");
    graph.label(v1v2, "label!");
    assertEquals("label!", graph.label(v1v2));
  }

  @Test
  @DisplayName("label((Edge<E>) null) throws exception.")
  public void labelNullThrowsException() {
    try {
      graph.label((Edge<String>) null);
      fail("The expected exception was not thrown.");
    } catch (PositionException ex) {
      return;
    }
  }

  @Test
  @DisplayName("clearLabels() clears all edge labels.")
  public void clearLabelsClearsAllEdgeLabels() {
    Vertex<String> v1 = graph.insert("v1");
    Vertex<String> v2 = graph.insert("v2");
    Vertex<String> v3 = graph.insert("v3");
    Edge<String> v1v2 = graph.insert(v1, v2, "v1->v2");
    Edge<String> v1v3 = graph.insert(v1, v3, "v1->v3");
    graph.label(v1v2, "label!!!");
    graph.label(v1v3, "label!!!");
    graph.clearLabels();
    assertEquals(null, graph.label(v1v2));
    assertEquals(null, graph.label(v1v3));
  }

  @Test
  @DisplayName("clearLabels() clears all vertex labels.")
  public void clearLabelsClearsAllVertexLabels() {
    Vertex<String> v1 = graph.insert("v1");
    Vertex<String> v2 = graph.insert("v2");
    Vertex<String> v3 = graph.insert("v3");
    Edge<String> v1v2 = graph.insert(v1, v2, "v1->v2");
    Edge<String> v1v3 = graph.insert(v1, v3, "v1->v3");
    graph.label(v1, "label!!!");
    graph.label(v2, "label!!!");
    graph.label(v3, "label!!!");
    graph.clearLabels();
    assertEquals(null, graph.label(v1));
    assertEquals(null, graph.label(v2));
    assertEquals(null, graph.label(v3));
  }
}
