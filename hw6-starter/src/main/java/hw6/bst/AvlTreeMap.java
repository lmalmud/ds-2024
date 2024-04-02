package hw6.bst;

import hw6.OrderedMap;

import static java.lang.Math.max;
import java.util.Iterator;
import java.util.Stack;

/**
 * Map implemented as an AVL Tree.
 *
 * @param <K> Type for keys.
 * @param <V> Type for values.
 */
public class AvlTreeMap<K extends Comparable<K>, V> implements OrderedMap<K, V> {

  /*** Do not change variable name of 'root'. ***/
  private Node<K, V> root;
  private int size;

  /**
   * Default constructor for AVL tree.
   */
  public AvlTreeMap() {
    size = 0;
    root = null;
  }

  /**
   * If the balance factor of the given node is off,
   * performs appropriate recalibrations.
   * @param node node to be checked
   */
  private Node<K, V> balance(Node<K, V> node) {
    // right-left rotation: right heavy node, left heavy child
    if (balanceFactor(node) < -1 && balanceFactor(node.right) >= 1) {
      return rightLeftRotate(node);

    // left-right rotation: left heavy node, right heavy child
    } else if (balanceFactor(node) > 1 && balanceFactor(node.left) <= -1) {
      return leftRightRotate(node);

    // single right rotation: left heavy node, left heavy child
    } else if (balanceFactor(node) > 1) {
      return rightRotate(node);

      // single left rotation: right heavy node, right heavy child
    } else if (balanceFactor(node) < -1) {
      return leftRotate(node);
    }
    return node;

  }

  /**
   * Performs a single left rotation.
   * @param node that is right-heavy (bf < -1)
   * @return the new root
   */
  private Node<K, V> leftRotate(Node<K, V> node) {
    Node<K, V> child = node.right;
    node.right = child.left;
    child.left = node;
    node.height = height(node); // changed since not necessarily - 1
    child.height = height(child);
    return child;
  }

  /**
   * Performs a single right rotation.
   * @param node that is left-heavy (bf > 1)
   * @return the new root
   */
  private Node<K, V> rightRotate(Node<K, V> node) {
    Node<K, V> child = node.left;
    node.left = child.right;
    child.right = node;
    node.height = height(node); // changed since not necessarily - 1
    child.height = height(child);
    return child;
  }

  /**
   * Performs a left rotation and right rotation.
   * @param node target
   * @return the new root
   */
  private Node<K, V> leftRightRotate(Node<K, V> node) {
    node.left = leftRotate(node.left);
    return rightRotate(node);
  }

  /**
   * Performs a right and left rotation.
   * @param node target
   * @return the new root
   */
  private Node<K, V> rightLeftRotate(Node<K, V> node) {
    node.right = rightRotate(node.right);
    return leftRotate(node);
  }

  /**
   * Returns the node of the given height, appropriately
   * updating heights accordingly.
   * @param node target node
   * @return height of node
   */
  private int height(Node<K, V> node) {
    int leftHeight = (node.left == null) ? -1 : node.left.height;
    int rightHeight = (node.right == null) ? -1 : node.right.height;
    return 1 + Math.max(leftHeight, rightHeight);
  }

  /**
   * Given a node, returns its balance factor.
   * @param node to find the balance factor of
   * @return integer representing the balance factor
   */
  private int balanceFactor(Node<K, V> node) {
    int leftSubtreeHeight = node.left == null ? -1 : node.left.height;
    int rightSubtreeHeight = node.right == null ? -1 : node.right.height;
    return leftSubtreeHeight - rightSubtreeHeight;
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
    } else if (cmp > 0) {
      n.right = insert(n.right, k, v);
    } else {
      throw new IllegalArgumentException("duplicate key " + k);
    }

    // to make the tree an avl...
    n.height = height(n);
    return balance(n);
  }

  @Override
  public void insert(K k, V v) {
    if (k == null) {
      throw new IllegalArgumentException("cannot handle null key");
    }
    root = insert(root, k, v);
    size++;
  }

  @Override
  public V remove(K k) throws IllegalArgumentException {
    Node<K, V> node = findForSure(k);
    V value = node.value;
    root = remove(root, node);
    size--;
    return value;
  }

  // Remove node with given key from subtree rooted at given node;
  // Return changed subtree with given key missing.
  private Node<K, V> remove(Node<K, V> subtreeRoot, Node<K, V> toRemove) {

    int cmp = subtreeRoot.key.compareTo(toRemove.key);
    if (cmp == 0) {
      return remove(subtreeRoot);
    } else if (cmp > 0) {
      subtreeRoot.left = remove(subtreeRoot.left, toRemove);
    } else {
      subtreeRoot.right = remove(subtreeRoot.right, toRemove);
    }

    // to make the tree an avl...
    subtreeRoot.height = height(subtreeRoot);
    return balance(subtreeRoot);
  }

  // Remove given node and return the remaining tree (structural change).
  private Node<K, V> remove(Node<K, V> node) {
    // Easy if the node has 0 or 1 child.
    if (node.right == null) {
      return node.left;
    } else if (node.left == null) {
      return node.right;
    }

    // If it has two children, find the predecessor (max in left subtree),
    Node<K, V> toReplaceWith = max(node);
    // then copy its data to the given node (value change),
    node.key = toReplaceWith.key;
    node.value = toReplaceWith.value;
    // then remove the predecessor node (structural change).
    node.left = remove(node.left, toReplaceWith);

    // to make the tree an avl...
    node.height = height(node);
    return balance(node);
  }

  // Return a node with maximum key in subtree rooted at given node.
  private Node<K, V> max(Node<K, V> node) {
    Node<K, V> curr = node.left;
    while (curr.right != null) {
      curr = curr.right;
    }
    return curr;
  }

  @Override
  public void put(K k, V v) throws IllegalArgumentException {
    Node<K, V> n = findForSure(k);
    n.value = v;
  }

  // Return node for given key,
  // throw an exception if the key is not in the tree.
  private Node<K, V> findForSure(K k) throws IllegalArgumentException {
    Node<K, V> n = find(k);
    if (n == null) {
      throw new IllegalArgumentException("cannot find key " + k);
    }
    return n;
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

  @Override
  public int size() {
    return size;
  }

  @Override
  public Iterator<K> iterator() {
    return new AvlTreeMapIterator();
  }

  public class AvlTreeMapIterator implements Iterator<K> {

    private final Stack<Node<K, V>> stack;

    AvlTreeMapIterator() {
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
