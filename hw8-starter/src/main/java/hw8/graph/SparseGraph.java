package hw8.graph;

import exceptions.InsertionException;
import exceptions.PositionException;
import exceptions.RemovalException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * An implementation of Graph ADT using incidence lists
 * for sparse graphs where most nodes aren't connected.
 *
 * @param <V> Vertex element type.
 * @param <E> Edge element type.
 */
public class SparseGraph<V, E> implements Graph<V, E> {

  // used for telling if a vertex is present
  Set<V> vertexData; // the data of all vertices (must be unique for each vertex)
  // all vertices present in the graph
  Set<VertexNode<V>> vertices;
  // maps nodes to their outgoing edges
  Map<VertexNode<V>, ArrayList<EdgeNode<E>>> outgoing;
  // maps nodes to their incoming edges
  Map<VertexNode<V>, ArrayList<EdgeNode<E>>> incoming;

  /**
   * Default constructor for a SpareGraph.
   */
  public SparseGraph() {
    this.vertexData = new HashSet<V>();
    this.vertices = new HashSet<VertexNode<V>>();
    this.outgoing = new HashMap<VertexNode<V>, ArrayList<EdgeNode<E>>>();
    this.incoming = new HashMap<VertexNode<V>, ArrayList<EdgeNode<E>>>();
  }

  // Converts the vertex back to a VertexNode to use internally
  private VertexNode<V> convert(Vertex<V> v) throws PositionException {
    try {
      VertexNode<V> gv = (VertexNode<V>) v;
      if (gv.owner != this) {
        throw new PositionException();
      }
      return gv;
    } catch (NullPointerException | ClassCastException ex) {
      throw new PositionException();
    }
  }

  // Converts an edge back to a EdgeNode to use internally
  private EdgeNode<E> convert(Edge<E> e) throws PositionException {
    try {
      EdgeNode<E> ge = (EdgeNode<E>) e;
      if (ge.owner != this) {
        throw new PositionException();
      }
      return ge;
    } catch (NullPointerException | ClassCastException ex) {
      throw new PositionException();
    }
  }

  /**
   * Ensures that the Vertex is a VertexNode and present.
   * @param v target Vertex
   * @return converted VertexNode
   */
  private VertexNode<V> checkNode(Vertex<V> v) throws PositionException {
    VertexNode<V> node;
    try {
      node = convert(v);
    } catch (PositionException ex) { // vertex position is invalid
      throw new PositionException();
    }

    if (!this.vertices.contains(node)) { // if node is not in graph
      throw new PositionException(); // NOTE: is this the correct exception that should be thrown?
    }
    return node;
  }

  /**
   * Ensures that Edge is an EdgeNode and in this graph.
   * @param e target Edge
   * @return converted EdgeNode
   */
  private EdgeNode<E> checkEdge(Edge<E> e) throws PositionException {
    EdgeNode<E> edge;
    try {
      edge = convert(e);
    } catch (PositionException ex) {
      throw new PositionException();
    }

    // throw exception if the edge is not presently in the graph
    if (!this.incoming.get(edge.to).contains(edge) || !this.outgoing.get(edge.from).contains(edge)) {
      throw new PositionException();
    }
    return edge;
  }

  @Override
  public Vertex<V> insert(V v) throws InsertionException {
    if (this.vertexData.contains(v) || v == null) {
      throw new InsertionException();
    } else {
      VertexNode<V> node = new VertexNode<>(this, v);
      this.vertexData.add(v);
      this.vertices.add(node);
      return (Vertex<V>) node;
    }
  }

  @Override
  public Edge<E> insert(Vertex<V> from, Vertex<V> to, E e)
      throws PositionException, InsertionException {
    VertexNode<V> fromNode = checkNode(from);
    VertexNode<V> toNode = checkNode(to);
    if (fromNode == toNode) { // would create self-loop
      throw new InsertionException();
    }

    if (this.outgoing.containsKey(fromNode)) {
      List<EdgeNode<E>> allOutgoing = this.outgoing.get(fromNode);
      for (int i = 0; i < allOutgoing.size(); ++i) { // loops for the degree of the node
        if (allOutgoing.get(i).to == toNode) {
          throw new InsertionException(); // would create duplicate edge
        }
      }
    }

    EdgeNode<E> newEdge = new EdgeNode<>(this, fromNode, toNode, e); // create the new edge
    addToMap(this.outgoing, fromNode, newEdge); // add new outgoing edge
    addToMap(this.incoming, toNode, newEdge); // add new incoming edge

    return newEdge;
  }

  private void addToMap(Map<VertexNode<V>, ArrayList<EdgeNode<E>>> m, VertexNode<V> v, EdgeNode<E> e) {
    if (m.containsKey(v)) { // update fromNode
      m.get(v).add(e);
    } else {
      m.put(v, new ArrayList<>());
      m.get(v).add(e);
    }
  }

  @Override
  public V remove(Vertex<V> v) throws PositionException, RemovalException {
    VertexNode<V> node = checkNode(v); //  just added

    if (this.outgoing.containsKey(node)) { // there is at least one outgoing edge
      if (!this.outgoing.get(node).isEmpty()) {
        throw new RemovalException();
      }
    }
    if (this.incoming.containsKey(node)) { // there is at least one incoming edge
      if (!this.incoming.get(node).isEmpty()) {
        throw new RemovalException();
      }
    }

    // remove the node from all relevant data structures
    this.vertices.remove(node);
    this.outgoing.remove(node);
    this.incoming.remove(node);

    return node.data;
  }

  @Override
  public E remove(Edge<E> e) throws PositionException {
    EdgeNode<E> edge = checkEdge(e);

    // remove from both relevant lists
    this.incoming.get(edge.to).remove(edge); // uses 'to' as a key
    this.outgoing.get(edge.from).remove(edge);
    return edge.data;
  }

  @Override
  public Iterable<Vertex<V>> vertices() {
    // FIXME: is it okay for the construction of the iterator to be O(N)?
    ArrayList<Vertex<V>> v = new ArrayList<>();
    for (VertexNode<V> node : this.vertices) {
      v.add((Vertex<V>) node);
    }
    return Collections.unmodifiableCollection(v);
  }

  @Override
  public Iterable<Edge<E>> edges() {
    // FIXME: is it okay for the construction of the iterator to be linear?
    ArrayList<Edge<E>> allEdges = new ArrayList<>();
    for (VertexNode<V> v : this.incoming.keySet()) {
      for (EdgeNode<E> e : this.incoming.get(v)) {
        allEdges.add((Edge<E>) e);
      }
    }
    return Collections.unmodifiableCollection(allEdges);
  }

  @Override
  public Iterable<Edge<E>> outgoing(Vertex<V> v) throws PositionException {
    VertexNode<V> node;
    try {
      node = convert(v);
    } catch (PositionException ex) { // vertex position is invalid
      throw new PositionException();
    }

    if (!this.vertices.contains(node)) { // if node is not in graph
      throw new PositionException(); // NOTE: is this the correct exception that should be thrown?
    }

    ArrayList<Edge<E>> result = new ArrayList<>();
    if (this.outgoing.containsKey(node)) {
      for (EdgeNode<E> e : this.outgoing.get(node)) {
        result.add((Edge<E>) e);
      }
    }
    return result;
  }

  @Override
  public Iterable<Edge<E>> incoming(Vertex<V> v) throws PositionException {
    VertexNode<V> node;
    try {
      node = convert(v);
    } catch (PositionException ex) { // vertex position is invalid
      throw new PositionException();
    }

    if (!this.vertices.contains(node)) { // if node is not in graph
      throw new PositionException(); // NOTE: is this the correct exception that should be thrown?
    }

    ArrayList<Edge<E>> result = new ArrayList<>();
    if (this.incoming.containsKey(node)) {
      for (EdgeNode<E> e : this.incoming.get(node)) {
        result.add((Edge<E>) e);
      }
    }
    return result;
  }

  @Override
  public Vertex<V> from(Edge<E> e) throws PositionException {
    EdgeNode<E> edge;
    try {
      edge = convert(e);
    } catch (PositionException ex) {
      throw new PositionException();
    }

    // throw exception if the edge is not presently in the graph
    if (!this.incoming.get(edge.to).contains(edge) || !this.outgoing.get(edge.from).contains(edge)) {
      throw new PositionException();
    }

    return (Vertex<V>) edge.from;
  }

  @Override
  public Vertex<V> to(Edge<E> e) throws PositionException {
    EdgeNode<E> edge;
    try {
      edge = convert(e);
    } catch (PositionException ex) {
      throw new PositionException();
    }

    // throw exception if the edge is not presently in the graph
    if (!this.incoming.get(edge.to).contains(edge) || !this.outgoing.get(edge.from).contains(edge)) {
      throw new PositionException();
    }

    return (Vertex<V>) edge.to;
  }

  @Override
  public void label(Vertex<V> v, Object l) throws PositionException {
    VertexNode<V> node;
    try {
      node = convert(v);
    } catch (PositionException ex) { // vertex position is invalid
      throw new PositionException();
    }

    if (!this.vertices.contains(node)) { // if node is not in graph
      throw new PositionException(); // NOTE: is this the correct exception that should be thrown?
    }

    node.label = l; // update the label to the given label
  }

  @Override
  public void label(Edge<E> e, Object l) throws PositionException {
    EdgeNode<E> edge;
    try {
      edge = convert(e);
    } catch (PositionException ex) {
      throw new PositionException();
    }

    // throw exception if the edge is not presently in the graph
    if (!this.incoming.get(edge.to).contains(edge) || !this.outgoing.get(edge.from).contains(edge)) {
      throw new PositionException();
    }

    edge.label = l; // update the label
  }

  @Override
  public Object label(Vertex<V> v) throws PositionException {
    VertexNode<V> node;
    try {
      node = convert(v);
    } catch (PositionException ex) { // vertex position is invalid
      throw new PositionException();
    }

    if (!this.vertices.contains(node)) { // if node is not in graph
      throw new PositionException(); // NOTE: is this the correct exception that should be thrown?
    }

    return node.label; // return the current label
  }

  @Override
  public Object label(Edge<E> e) throws PositionException {
    EdgeNode<E> edge;
    try {
      edge = convert(e);
    } catch (PositionException ex) {
      throw new PositionException();
    }

    // throw exception if the edge is not presently in the graph
    if (!this.incoming.get(edge.to).contains(edge) || !this.outgoing.get(edge.from).contains(edge)) {
      throw new PositionException();
    }

    return edge.label; // return the label
  }

  @Override
  public void clearLabels() {
    for (VertexNode<V> v : this.vertices) {
      v.label = null;
    }

    // for each of the vertices that has incoming edges
    for (VertexNode<V> v : this.incoming.keySet()) {
      for (EdgeNode<E> e : this.incoming.get(v)) {
        e.label = null; // set all of its edges to null
      }
    }
  }

  @Override
  public String toString() {
    GraphPrinter<V, E> gp = new GraphPrinter<>(this);
    return gp.toString();
  }

  // Class for a vertex of type V
  private final class VertexNode<V> implements Vertex<V> {
    V data;
    Graph<V, E> owner;
    Object label;

    VertexNode(Graph<V, E> o, V v) {
      this.owner = o;
      this.data = v;
      this.label = null;
    }

    @Override
    public V get() {
      return this.data;
    }

    @Override
    public String toString() {
      return (String) data;
    }
  }

  //Class for an edge of type E
  private final class EdgeNode<E> implements Edge<E> {
    E data;
    Graph<V, E> owner;
    VertexNode<V> from;
    VertexNode<V> to;
    Object label;

    // Constructor for a new edge
    EdgeNode(Graph<V, E> o, VertexNode<V> f, VertexNode<V> t, E e) {
      this.owner = o;
      this.from = f;
      this.to = t;
      this.data = e;
      this.label = null;
    }

    @Override
    public E get() {
      return this.data;
    }
  }
}
