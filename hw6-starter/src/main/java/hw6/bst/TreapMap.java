package hw6.bst;

import hw6.OrderedMap;
import java.util.Iterator;
import java.util.Random;
import java.util.Stack;

import static java.lang.Integer.MAX_VALUE;

/**
 * Map implemented as a Treap.
 *
 * @param <K> Type for keys.
 * @param <V> Type for values.
 */
public class TreapMap<K extends Comparable<K>, V> implements OrderedMap<K, V> {

  /*** Do not change variable name of 'rand'. ***/
  private static Random rand;
  /*** Do not change variable name of 'root'. ***/
  private Node<K, V> root;

  private int size; // number of elements

  /**
   * Make a TreapMap.
   */
  public TreapMap() {
    rand = new Random();
  }

  /**
   * Non-default constructor for testing purposes.
   * @param seed seed value for Random
   */
  public TreapMap(int seed) {
    rand = new Random(seed);
    // Uncomment for writing tests:
    /*for (int i = 0; i < 10; i++) {
      System.out.println(rand.nextInt());
    }*/
  }

  private Node<K, V> rightRotate(Node<K, V> n) {
    Node<K, V> child = n.left;
    n.left = child.right;
    child.right = n;
    return child;
  }

  private Node<K, V> leftRotate(Node<K, V> n) {
    Node<K, V> child = n.right;
    n.right = child.left;
    child.left = n;
    return child;
  }

  private int getPriority(Node<K, V> n) {
    if (n == null) {
      return MAX_VALUE;
    } else {
      return n.priority;
    }
  }

  boolean nodeIsLeaf(Node<K, V> n) {
    return n.left == null && n.right == null;
  }

  private Node<K, V> removeSubtree(Node<K, V> n, K toRemove) {
    if (nodeIsLeaf(n) && n.key.compareTo(toRemove) == 0) {
      return null;
    } else {
      return remove(n, toRemove);
    }
  }

  // Return node for given key,
  // throw an exception if the key is not in the tree.
  private Node<K, V> findForSure(K k) {
    Node<K, V> n = find(k);
    if (n == null) {
      throw new IllegalArgumentException("cannot find key " + k);
    }
    return n;
  }

  // Return node for given key.
  private Node<K, V> find(K k) {
    if (k == null) {
      throw new IllegalArgumentException("cannot handle null key");
    }
    Node<K, V> n = root;
    while (n != null) {
      int cmp = k.compareTo(n.key);
      if (cmp < 0) {
        n = n.left;
      } else if (cmp > 0) {
        n = n.right;
      } else {
        return n;
      }
    }
    return null;
  }

  // Insert given key and value into subtree rooted at given node;
  // return changed subtree with a new node added.
  private Node<K, V> insert(Node<K, V> n, K k, V v) {
    if (n == null) {
      return new Node<>(k, v);
    }

    int cmp = k.compareTo(n.key);
    if (cmp < 0) {
      n.left = insert(n.left, k, v);
      if (n.left.priority < n.priority) {
        n = rightRotate(n);
      }
    } else if (cmp > 0) {
      n.right = insert(n.right, k, v);
      if (n.right.priority < n.priority) {
        n = leftRotate(n);
      }
    } else {
      throw new IllegalArgumentException("duplicate key " + k);
    }

    return n;
  }

  @Override
  public void insert(K k, V v) throws IllegalArgumentException {
    if (k == null) {
      throw new IllegalArgumentException("cannot handle null key");
    }
    root = insert(root, k, v);
    size++;
  }

  /**
   * Removes a node from a subtree.
   * @param subtreeRoot the root of the given subtree
   * @param toRemove the item to be removed (already has maximum priority)
   * @return the new root of the subtree
   */
  private Node<K, V> remove(Node<K, V> subtreeRoot, K toRemove) {

    if (subtreeRoot == null || nodeIsLeaf(subtreeRoot)) {
      return null;
    }

    //int cmp = subtreeRoot.key.compareTo(toRemove);
    if (subtreeRoot.key.compareTo(toRemove) < 0) { // subtreeRoot < toRemove
      // if the target is a leaf and the right child
      subtreeRoot.right = removeSubtree(subtreeRoot.right, toRemove);

    } else if (subtreeRoot.key.compareTo(toRemove) > 0) {
      // if the target is a leaf and the left child
      subtreeRoot.left = removeSubtree(subtreeRoot.left, toRemove);

    } else { // target found
      if (!nodeIsLeaf(subtreeRoot)) {
        // always swap with the node that has higher priority
        if (getPriority(subtreeRoot.left) < getPriority(subtreeRoot.right)) { // swap with left
          subtreeRoot = rightRotate(subtreeRoot);
        } else { // swap with right
          // the issue is that we are performing a left rotation
          // once the node is already a leaf and we found it
          subtreeRoot = leftRotate(subtreeRoot);
        }
      }
      subtreeRoot = remove(subtreeRoot, toRemove);

    }
    return subtreeRoot;
  }

  @Override
  public V remove(K k) throws IllegalArgumentException {
    Node<K, V> node = findForSure(k);
    node.priority = MAX_VALUE; // ensure that bubbling properly occurs
    V value = node.value;
    root = remove(root, k); // switched to also find the node
    size--;
    return value;
  }

  @Override
  public void put(K k, V v) throws IllegalArgumentException {
    Node<K, V> n = findForSure(k);
    n.value = v;
  }

  @Override
  public V get(K k) throws IllegalArgumentException {
    Node<K, V> n = findForSure(k);
    return n.value;
  }

  @Override
  public boolean has(K k) {
    if (k == null) {
      return false;
    }
    return find(k) != null;
  }

  @Override
  public int size() {
    return size;
  }

  @Override
  public Iterator<K> iterator() {
    return new InorderIterator();
  }

  /*** Do not change this function's name or modify its code. ***/
  @Override
  public String toString() {
    return BinaryTreePrinter.printBinaryTree(root);
  }

  // Iterative in-order traversal over the keys
  private class InorderIterator implements Iterator<K> {
    private final Stack<Node<K, V>> stack;

    InorderIterator() {
      stack = new Stack<>();
      pushLeft(root);
    }

    private void pushLeft(Node<K, V> curr) {
      while (curr != null) {
        stack.push(curr);
        curr = curr.left;
      }
    }

    @Override
    public boolean hasNext() {
      return !stack.isEmpty();
    }

    @Override
    public K next() {
      Node<K, V> top = stack.pop();
      pushLeft(top.right);
      return top.key;
    }
  }


  /**
   * Feel free to add whatever you want to the Node class (e.g. new fields).
   * Just avoid changing any existing names, deleting any existing variables,
   * or modifying the overriding methods.
   * Inner node class, each holds a key (which is what we sort the
   * BST by) as well as a value. We don't need a parent pointer as
   * long as we use recursive insert/remove helpers. Since this is
   * a node class for a Treap we also include a priority field.
   **/
  private static class Node<K, V> implements BinaryTreeNode {
    Node<K, V> left;
    Node<K, V> right;
    K key;
    V value;
    int priority;

    // Constructor to make node creation easier to read.
    Node(K k, V v) {
      // left and right default to null
      key = k;
      value = v;
      priority = generateRandomInteger();
    }

    // Use this function to generate random values
    // to use as node priorities as you insert new
    // nodes into your TreapMap.
    private int generateRandomInteger() {
      // Note: do not change this function!
      return rand.nextInt();
    }

    @Override
    public String toString() {
      return key + ":" + value + ":" + priority;
    }

    @Override
    public BinaryTreeNode getLeftChild() {
      return left;
    }

    @Override
    public BinaryTreeNode getRightChild() {
      return right;
    }


  }
}
