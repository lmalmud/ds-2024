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
   * Constructs a new SparseIndexedList of length size
   * with default value of defaultValue.
   *
   * @param size         Length of list, expected: size > 0.
   * @param defaultValue Default value to store in each slot.
   * @throws LengthException if size <= 0.
   */
  public SparseIndexedList(int size, T defaultValue) throws LengthException {
    if (size <= 0) {
      throw new LengthException();
    } else {
      this.head = null;
      this.defaultValue = defaultValue;
      this.length = size;
    }
  }

  /**
   * Returns a Node containing a non-default value.
   * @param index the index we would like to access
   * @return the node at the corresponding index
   */
  private Node<T> getNodeAtIndex(int index) {
    Node<T> pos = head;
    // keep looking until either the node is found or
    // we have looked through all the nodes
    while (pos != null && pos.index != index) {
      pos = pos.next;
    }
    return pos;
  }

  /**
   * Given a non-default value, appends a node to the linked
   * list, maintaining the ascending order of indices.
   * @param index the index of the node
   * @param value the non-default value that the node holds
   */
  private void insertNode(int index, T value) {
    Node<T> newNode = new Node<>(value, index);
    if (this.head == null) { // if there are no non-default elements
      this.head = newNode;
    } else {
      Node<T> pos = this.head;
      while (pos.next != null && pos.next.index < index) {
        pos = pos.next;
      }

      if (pos.index > index) { // need to change the head
        newNode.next = pos;
        this.head = newNode; // attach at beginning
      } else {
        newNode.next = pos.next; // inserts after pos
        pos.next = newNode;
      }
    }

  }

  /**
   * Given then index of a node holding a non-default value,
   * removes the node from the list.
   * @param index the index of the node that is desired to be deleted
   */
  private void deleteNode(int index) {
    // case where head must be modified
    if (index == this.head.index || index == 0) {
      this.head = this.head.next;
    } else {
      Node<T> before = head;
      while (before.next != null && before.next.index != index) {
        before = before.next;
      }
      before.next = before.next.next;
    }
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

    Node<T> res = getNodeAtIndex(index);
    if (res != null) { // if value has been changed
      return res.data;
    } else {
      return this.defaultValue;
    }
  }

  @Override
  public void put(int index, T value) throws IndexException {
    if (index < 0 || index >= length) {
      throw new IndexException(); // throw an exception if the index is out of range
    }

    Node<T> res = getNodeAtIndex(index);
    if (res != null) {
      if (!res.data.equals(this.defaultValue)) {
        res.data = value;
      } else { // must remove a node
        deleteNode(index);
      }
    } else if (!value.equals(this.defaultValue)) { // add new node in PROPER SPOT
      insertNode(index, value);
    }
    // control reaches here if we are replacing a default with a default
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

    private int index;
    private Node<T> cursor;

    SparseIndexedListIterator() {
      this.index = 0;
      this.cursor = head;
    }

    @Override
    public boolean hasNext() {
      return this.index < length && length != 0;
    }

    @Override
    public T next() throws NoSuchElementException {
      if (!hasNext()) {
        throw new NoSuchElementException();
      }

      // if there are no more non-default elements, or we aren't currently at one
      T value;
      if (cursor != null && cursor.index == index) {
        value = cursor.data;
        cursor = cursor.next;
      } else {
        value = defaultValue;
      }

      ++index;
      return value;
    }
  }
}
