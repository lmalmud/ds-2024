package hw6.bst;

import hw6.OrderedMap;
import java.util.Iterator;

import static java.lang.Math.max;

/**
 * Map implemented as an AVL Tree.
 *
 * @param <K> Type for keys.
 * @param <V> Type for values.
 */
public class AvlTreeMap<K extends Comparable<K>, V> implements OrderedMap<K, V> {

  /*** Do not change variable name of 'root'. ***/
  private Node<K, V> root;
  private int numElements;

  public AvlTreeMap() {
    numElements = 0;
    root = null;
  }

  /**
   * Attempts to find if a node with a given key is present,
   * starting from search at the given root
   * @param r starting point for the search
   * @param k the target key value
   * @return Node if it is present, null otherwise
   */
  private Node<K, V> find(Node<K, V> r, K k) {
    if (r == null) {
      return null;
    } else {
      int comparisonResult = r.key.compareTo(k);
      if (comparisonResult == 0) { // root == target
        return r;
      } else if (comparisonResult < 0) { // root < target
        return find(r.right, k);
      } else { // root > target
        return find(r.left, k);
      }
    }
  }

  /**
   * If the balance factor of the given node is off,
   * performs appropriate recalibrations
   * @param node node to be checked
   */
  private Node<K, V> balance(Node<K, V> node) {
    Node<K, V> potentialNewRoot = node;
    // single right rotation: left heavy node, left heavy child
    if (balanceFactor(node) > 1 && balanceFactor(node.left) >= 1) {
      potentialNewRoot = node.left;
      rightRotate(node);

    // single left rotation: right heavy node, right heavy child
    } else if (balanceFactor(node) < -1 && balanceFactor(node.right) <= -1) {
      System.out.println("LEFT");
      potentialNewRoot = node.right;
      leftRotate(node);

    // right-left rotation: right heavy node, left heavy child
    } else if (balanceFactor(node) < -1 && balanceFactor(node.right) >= 1) {
      potentialNewRoot = node.right.left; // FIXME: is it possible this could be null?
      rightLeftRotate(node);

    // left-right rotation: left heavy node, right heavy child
    } else if (balanceFactor(node) > 1 && balanceFactor(node.left) <= -1) {
      potentialNewRoot = node.left.right;
      leftRightRotate(node);
    }
    if (node == root) { // if the node that we performed a rotation on was the root, we must update accordingly
      root = potentialNewRoot;
      return potentialNewRoot; // must return since this function is called form insert
    }
    return node;

  }

  /**
   * Performs a single left rotation.
   * @param node that is right-heavy (bf < -1)
   */
  private void leftRotate(Node<K, V> node) {
    Node<K, V> child = node.right;
    node.right = child.left;
    child.left = node;
    height(node);
    height(child);
  }

  /**
   * Performs a single right rotation.
   * @param node that is left-heavy (bf > 1)
   */
  private void rightRotate(Node<K, V> node) {
    Node<K, V> child = node.left;
    node.left = child.right;
    child.right = node;
    System.out.println("RIGHT");
  }

  /**
   * Performs a left rotation and right rotation.
   * @param node target
   */
  private void leftRightRotate(Node<K, V> node) {
    Node<K, V> r = node;
    Node<K, V> child = node.left;
    Node<K, V> grandchild = node.left.right;
    child.right = grandchild.left;
    grandchild.left = child;
    r.left = grandchild;
    r.right = grandchild.right;
    grandchild.right = r; // grandchild is now the root
  }

  /**
   * Performs a right and left rotation.
   * @param node target
   */
  private void rightLeftRotate(Node<K, V> node) {

  }

  /**
   * Inserts node to the tree defined at root.
   * Always call with root = insert(root, node).
   * @param r base of the tree
   * @param k key to be added
   * @param v value to be added
   * @return the (updated) root
   */
  private Node<K, V> insert(Node<K, V> r, K k, V v) {
    if (r == null) {
      return new Node<K, V>(k, v);
    } else {
      int comparisonResult = r.key.compareTo(k);
      if (comparisonResult < 0) { // root < node
        r.right = insert(r.right, k, v);
      } else {
        r.left = insert(r.left, k, v);
      }
      height(r); // update height
      return balance(r); // re-balance if needed
    }
  }

  /**
   * Returns the node of the given height, appropriately
   * updating heights accordingly.
   * @param node target node
   * @return height of node
   */
  private int height(Node<K, V> node) {
    if (node == null) {
      return -1;
    } else if (node.right == null && node.left == null) {
      node.height = 0;
    } else {
      node.height = 1 + max(height(node.right), height(node.left));
    }
    return node.height;
  }

  /**
   * Given a node, returns its balance factor
   * @param node to find the balance factor of
   * @return integer representing the balance factor
   */
  private int balanceFactor(Node<K, V> node) {
    int leftSubtreeHeight = node.left == null ? -1 : node.left.height;
    int rightSubtreeHeight = node.right == null ? -1 : node.right.height;
    return leftSubtreeHeight - rightSubtreeHeight;
  }

  @Override
  public void insert(K k, V v) throws IllegalArgumentException {
    if (k == null || has(k)) {
      throw new IllegalArgumentException();
    }
    // now, we know the key is not present and not null
    root = insert(root, k, v);
    numElements++;
  }

  private Node<K, V> findLargestNode(Node<K, V> node) {
    if (node.right == null) {
      return node;
    } else {
      return findLargestNode(node.right);
    }
  }

  private Node<K, V> removeSubtree(Node<K, V> node, K k) {
    int compareResult = k.compareTo(node.key);
    if (compareResult == 0) { // found the node
      if (node.left == null) {
        return node.right;
      } else if (node.right == null) {
        return node.left;
      } else {
        // recurse
        Node<K, V> smallest = findLargestNode(node.left);
        node.value = smallest.value;
        smallest = removeSubtree(smallest, smallest.key);
      }
    } else if (compareResult < 0) { // k < node.key
      node.left = removeSubtree(node.left, k);
    } else { // k > node.key
      node.right = removeSubtree(node.right, k);
    }
    height(node);
    balance(node); // check if needs to be rebalanced on recursion up
    return node;
  }

  @Override
  public V remove(K k) throws IllegalArgumentException {
    Node<K, V> found =  find(root, k);
    if (k == null || found == null) {
      throw new IllegalArgumentException();
    }
    // now, we know the key is present and not null
    V result = found.value;
    root = removeSubtree(root, k);
    numElements--;
    return result;
  }

  @Override
  public void put(K k, V v) throws IllegalArgumentException {
    // TODO Implement Me!
  }

  @Override
  public V get(K k) throws IllegalArgumentException {
    // TODO Implement Me!
    return null;
  }

  @Override
  public boolean has(K k) {
    return find(root, k) != null;
  }

  @Override
  public int size() {
    return numElements;
  }

  @Override
  public Iterator<K> iterator() {
    // TODO Implement Me!
    return null;
  }

  /*** Do not change this function's name or modify its code. ***/
  @Override
  public String toString() {
    return BinaryTreePrinter.printBinaryTree(root);
  }

  /**
   * Feel free to add whatever you want to the Node class (e.g. new fields).
   * Just avoid changing any existing names, deleting any existing variables,
   * or modifying the overriding methods.
   *
   * <p>Inner node class, each holds a key (which is what we sort the
   * BST by) as well as a value. We don't need a parent pointer as
   * long as we use recursive insert/remove helpers.</p>
   **/
  private static class Node<K, V> implements BinaryTreeNode {
    Node<K, V> left;
    Node<K, V> right;
    K key;
    V value;

    int height; // added

    // Constructor to make node creation easier to read.
    Node(K k, V v) {
      // left and right default to null
      key = k;
      value = v;
      height = 0;
    }

    @Override
    public String toString() {
      return key + ":" + value;
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
