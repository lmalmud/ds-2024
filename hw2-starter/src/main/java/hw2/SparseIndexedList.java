package hw2;

import exceptions.IndexException;
import exceptions.LengthException;
import java.util.Iterator;
import java.util.NoSuchElementException;


/**
 * An implementation of an IndexedList designed for cases where
 * only a few positions have distinct values from the initial value.
 *
 * @param <T> Element type.
 */
public class SparseIndexedList<T> implements IndexedList<T> {

  private Node<T> head;
  private final T defaultValue;
  private final int length;

  /**
   * Default constructor that creates a new empty SparseIndexedList
   * with length 0 and default value null.
   */
  public SparseIndexedList() {
    this.head = null;
    this.defaultValue = null;
    this.length = 0;
  }

  /**
   * Constructs a new SparseIndexedList of length size
   * with default value of defaultValue.
   *
   * @param size Length of list, expected: size > 0.
   * @param defaultValue Default value to store in each slot.
   * @throws LengthException if size <= 0.
   */
  public SparseIndexedList(int size, T defaultValue) throws LengthException {
    // TODO - DONE
    if (size <= 0) {
      throw new LengthException();
    } else {
      this.head = null;
      this.defaultValue = defaultValue;
      this.length = size;
    }
  }

  /**
   * Returns the node of the indexed value if it is not default value,
   * and null otherwise.
   * @param index the index to be checked: 0 <= index < length
   * @return Node with the specified index, null if not found
   */
  private Node<T> indexIsNotDefault(int index) {
    Node<T> pos = head;
    // keep looking until either the node is found or
    // we have looked through all the nodes
    while (pos != null && pos.index != index) {
      pos = pos.next;
    }
    return pos;
  }

  @Override
  public int length() {
    return this.length;
  }

  @Override
  public T get(int index) throws IndexException {
    if (index < 0 || index >= length) {
      throw new IndexException();
    }

    Node<T> res = indexIsNotDefault(index);
    if (res == null) {
      return this.defaultValue;
    } else {
      return res.data;
    }
  }

  /**
   * Given an index that is currently not the default value,
   * modifies the list appropriately to make list[index] = value.
   * @param index the index of the value we would like to modify
   * @param value the value we are overriding to
   */
  private void overrideNonDefaultValue(int index, T value) {
    Node<T> beforeIndexNode = this.head;
    if (beforeIndexNode.index != index) { // then we must traverse

      while (beforeIndexNode.next.index != index) { // want to find the node prior to where we insert
        beforeIndexNode = beforeIndexNode.next;
      }

      if (value.equals(this.defaultValue)) { // need to get rid of a node
        beforeIndexNode.next = beforeIndexNode.next.next;
      } else {
        beforeIndexNode.next.data = value; // simply change the value of the node
      }

    } else { // the node that we are trying to modify is the head
      if (value.equals(this.defaultValue)) {
        this.head = this.head.next; // if there is only one element, then now there are no nodes
      } else {
        this.head.data = value; // overwrite the data
      }
    }
  }

  @Override
  public void put(int index, T value) throws IndexException {
    if (index < 0 || index >= length) {
      throw new IndexException(); // throw an exception if the index is out of range
    }

    if (indexIsNotDefault(index) != null) { // checking the destination index
      overrideNonDefaultValue(index, value);

    // we are overriding something that was previously the default - it did not have a node
    } else if (!value.equals(defaultValue)) { // we have to add a node with the appropriate index
      Node<T> newNode = new Node<>(value, index);
      newNode.next = this.head;
      this.head = newNode; // the order does not matter, and it is more efficient to prepend
    }
    // control falls here if we are overwriting a default value, in which case nothing has to be done
  }

  @Override
  public Iterator<T> iterator() {
    return new SparseIndexedListIterator();
  }

  // this class is static because we do not want to keep an implicit reference
  private static class Node<T> {
    T data;
    Node<T> next;
    int index;

    Node(T data, int index) {
      this.data = data;
      this.index = index;
      this.next = null;
    }
  }

  private class SparseIndexedListIterator implements Iterator<T> {

    int index;

    SparseIndexedListIterator() {
      this.index = 0;
    }

    @Override
    public boolean hasNext() {
      return this.index < length;
    }

    @Override
    public T next() throws NoSuchElementException {
      if (!hasNext()) {
        throw new NoSuchElementException();
      }

      Node<T> pos = indexIsNotDefault(index);
      ++index;
      if (pos != null) {
        return pos.data;
      } else {
        return defaultValue;
      }

    }
  }
}
