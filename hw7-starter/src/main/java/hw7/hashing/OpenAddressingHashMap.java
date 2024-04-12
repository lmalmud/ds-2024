package hw7.hashing;

import hw7.Map;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class OpenAddressingHashMap<K, V> implements Map<K, V> {

  private Node<K, V>[] data;
  int primes[] = {5, 11, 23, 47, 97, 197, 397, 797, 1597, 3203, 6421, 12853, 25717, 51437,102877, 205759, 411527, 823117, 1646237,3292489, 6584983, 13169977};
  private int primeCapacity;
  private int capacity;
  private int numElements;
  private final double alpha = .75;
  private final Node<K, V> tombstone = new Node<>(null, null);

  /**
   * Default constructor for OpenAddressingHashMap.
   */
  public OpenAddressingHashMap() {
    this.capacity = 5;
    this.data = new Node[this.capacity];
    this.numElements = 0;
    this.primeCapacity = 0;
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
    if (k == null) { // can never find null keys
      return null;
    }
    int index = getIndex(k);
    // note we first have to check if the value is null because we cannot dereference null values
    if (this.data[index] != null && this.data[index].key == k) { // see if the key is at the mapped address
      return this.data[index];
    } else {
      for (int i = 0; i < this.capacity; i++) {
        index = (getIndex(k) + i) % this.capacity;
        if (this.data[index] != null && this.data[index].key == k) { // see if the key is at the mapped address
          return this.data[index];
        }
      }
      return null; // we traversed the entire list and could not find the value
    }
  }

  /**
   * Rehashes the underlying array, changing size to the next prime number.
   */
  private void rehash() {
    Node<K, V>[] replicaData = this.data; // create a copy of all data
    this.primeCapacity++;
    this.capacity = this.primes[primeCapacity]; // use prime numbers for table size
    this.data = new Node[this.capacity]; // now we have an empty array of twice the size

    for (int i = 0; i < this.capacity / 2; i++) { // loop through all elements in original array
      if (replicaData[i] != tombstone) { // only want to insert non-tombstone values
        this.insert(replicaData[i].key, replicaData[i].value); // this function will has and probe appropriately
      }
    }

  }

  @Override
  public void insert(K k, V v) throws IllegalArgumentException {
    if (k == null || find(k) != null) { // k null or already mapped
      throw new IllegalArgumentException();
    }
    if (this.numElements / this.capacity >= alpha) { // grow proactively
      this.rehash();
    }
    // we are guaranteed that there is space in the array
    int index = getIndex(k);

    // if table[index] is occupied, then
    for (int i = 0; i < this.capacity; i++) {
      index = (getIndex(k) + i) % this.capacity;
      if (this.data[index] == null) { // we found an empty spot
        this.data[index] = new Node<>(k, v); // insert the new node
        break;
      }
    }
    this.numElements++;
  }

  @Override
  public V remove(K k) throws IllegalArgumentException {
    if (k == null || find(k) == null) {
      throw new IllegalArgumentException();
    }

    int index = getIndex(k);
    V result = null;

    // if table[index] is occupied, then
    for (int i = 0; i < this.capacity; i++) {
      index = (getIndex(k) + i) % this.capacity;
      if (this.data[index].key == k) { // we found the node
        result = this.data[index].value; // save the value
        this.data[index] = this.tombstone; // put a tombstone in
        break;
      }
    }
    this.numElements--;
    return result;
  }

  @Override
  public void put(K k, V v) throws IllegalArgumentException {
    Node<K, V> findResult = find(k);
    if (k == null || findResult == null) {
      throw new IllegalArgumentException();
    }
    findResult.value = v;
  }

  @Override
  public V get(K k) throws IllegalArgumentException {
    Node<K, V> findResult = find(k);
    if (k == null || findResult == null) { // throw exception if k is null or not mapped
      throw new IllegalArgumentException();
    }
    return findResult.value;
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
    return new OpenAddressingHashMapIterator();
  }

  static class Node<K, V> {

    K key;
    V value;

    Node(K k, V v) {
      this.key = k;
      this.value = v;
    }

  }

  class OpenAddressingHashMapIterator implements Iterator<K> {
    int currentSize;
    int currentIndex;

    OpenAddressingHashMapIterator() {
      currentSize = 0; // how many elements we have visited
      currentIndex = 0; // where we currently are in the underlying array
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
      while (data[currentIndex] == null || data[currentIndex] == tombstone) {
        currentIndex++;
      }
      K result = data[currentIndex].key;
      currentSize++;
      currentIndex++; // traverse over the current element
      return result;
    }
  }

}
