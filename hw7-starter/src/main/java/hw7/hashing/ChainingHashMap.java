package hw7.hashing;

import hw7.Map;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class ChainingHashMap<K, V> implements Map<K, V> {

  private Node<K, V>[] data;
  private int capacity;
  private int numElements;
  int primes[] = {5, 11, 23, 47, 97, 197, 397, 797, 1597, 3203, 6421, 12853, 25717, 51437,102877, 205759, 411527, 823117, 1646237,3292489, 6584983, 13169977};
  private int primeCapacity;
  private final double maxBucketSize = 4;

  /**
   * Default constructor for OpenAddressingHashMap.
   */
  public ChainingHashMap() {
    this.capacity = 5;
    // there is a dummy node at the beginning of each index
    this.data = createNewNodeArray(this.capacity);
    this.primeCapacity = 0; // the current size of the data as stored in the primes array
    this.numElements = 0;
  }

  /**
   * Creates an array of size n with each space having a reference
   * to emptyNode.
   * @param n size of array
   * @return arary
   */
  private Node[] createNewNodeArray(int n) {
    Node[] newNodeArray = new Node[n];
    for (int i = 0; i < n; i++) {
      newNodeArray[i] = new Node<K, V>(null, null);
    }
    return newNodeArray;
  }

  /**
   * Given a key, performs a calculation to return
   * the index where the item would be found.
   * @param key target key
   * @return index that will contain the element
   */
  int getIndex(K key) {
    return key.hashCode() % this.capacity;
  }

  /**
   * Returns the Node with key k if present and null otherwise.
   * @param k the target key
   * @return the node containing the target value or null
   */
  private Node<K, V> find(K k) {
    if (k == null) {
      return null;
    }
    int index = getIndex(k);
    Node<K, V> current = this.data[index];
    while (current != null && current.key != k) {
      current = current.next;
    }
    return current;
  }

  /**
   * Doubles the capacity of the underlying array, and rehashes.
   */
  private void rehash() {

  }

  @Override
  public void insert(K k, V v) throws IllegalArgumentException {
    if (k == null || find(k) != null) {
      throw new IllegalArgumentException();
    }
    int index = getIndex(k);
    Node<K, V> currentNode = this.data[index];
    int bucketSize = 0;
    while (currentNode.next != null) { // keep traversing till at the end of the linked list
      currentNode = currentNode.next;
      bucketSize++;
    }
    // currentNode is now the node before where we would like to insert
    currentNode.next = new Node<K, V>(k, v);
    this.numElements++;
    if (bucketSize > maxBucketSize) {
      rehash();
    }

  }

  @Override
  public V remove(K k) throws IllegalArgumentException {
    if (k == null || find(k) == null) {
      throw new IllegalArgumentException();
    }
    int index = getIndex(k);
    Node<K, V> currentNode = this.data[index];
    while (currentNode.next.key != k) {
      currentNode = currentNode.next;
    }
    // currentNode is now the node before the target
    V value = currentNode.next.value; // save the value of the target node
    currentNode.next = currentNode.next.next; // remove all references to the node
    this.numElements--;
    return value;
  }

  @Override
  public void put(K k, V v) throws IllegalArgumentException {
    Node<K, V> target = this.find(k); // returns null if k is null
    if (k == null || target == null) {
      throw new IllegalArgumentException();
    }
    target.value = v;
  }

  @Override
  public V get(K k) throws IllegalArgumentException {
    Node<K, V> target = this.find(k); // returns null if k is null
    if (k == null || target == null) {
      throw new IllegalArgumentException();
    }
    return target.value;
  }

  @Override
  public boolean has(K k) {
    return find(k) != null;
  }

  @Override
  public int size() {
    return this.numElements;
  }

  @Override
  public Iterator<K> iterator() {
    return new ChainingHashMapIterator();
  }

  class ChainingHashMapIterator implements Iterator<K> {

    int currentIndex;
    Node<K, V> currentNode;
    int currentSize;

    ChainingHashMapIterator() {
      currentIndex = 0;
      currentNode = data[currentIndex];
      currentSize = 0;
    }

    @Override
    public boolean hasNext() {
      return currentSize < numElements;
    }

    @Override
    public K next() {
      if (!hasNext()) {
        throw new NoSuchElementException();
      }
      // keep searching until we find a slot that has non-dummy nodes
      while (currentNode.next == null) {
        currentIndex++;
        // goes to next index if first was empty
        // or at the end of the current bucket
        currentNode = data[currentIndex];
      }
      currentNode = currentNode.next;
      K result = currentNode.key;
      currentSize++;
      return result;
    }
  }

  static class Node<K, V> {

    K key;
    V value;
    Node<K, V> next;

    Node(K k, V v) {
      this.key = k;
      this.value = v;
      next = null;
    }

  }
}
