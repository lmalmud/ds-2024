package hw6;

import hw6.bst.AvlTreeMap;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * In addition to the tests in BinarySearchTreeMapTest (and in OrderedMapTest & MapTest),
 * we add tests specific to AVL Tree.
 */
@SuppressWarnings("All")
public class AvlTreeMapTest extends BinarySearchTreeMapTest {

  @Override
  protected Map<String, String> createMap() {
    return new AvlTreeMap<>();
  }

  @Test
  @DisplayName("Ensures a left rotation is performed when three nodes are inserted in ascending order.")
  public void insertLeftRotation() {
    map.insert("1", "a");
    // System.out.println(avl.toString());
    // must print
    /*
        1:a
     */

    map.insert("2", "b");
    // System.out.println(avl.toString());
    // must print
    /*
        1:a,
        null 2:b
     */

    map.insert("3", "c"); // it must do a left rotation here!
    // System.out.println(avl.toString());
    // must print
    /*
        2:b,
        1:a 3:c
     */

    String[] expected = new String[]{
        "2:b",
        "1:a 3:c"
    };
    assertEquals(3, map.size());
    assertEquals((String.join("\n", expected) + "\n"), map.toString());
  }

  @Test
  @DisplayName("Ensures a left rotation preserves the overall structure of the tree when other nodes are involved.")
  public void insertLeftRotationWithExtra() {
    map.insert("d", "d");
    map.insert("a", "a");
    map.insert("e", "e");
    map.insert("f", "f");
    map.insert("g", "g"); // will trigger a left rotation at node with e

    assertEquals("d:d\na:a f:f\nnull null e:e g:g\n", map.toString());
    assertEquals(5, map.size());
  }

  @Test
  @DisplayName("Ensures a left rotation triggered by a removal preserves overall structure.")
  public void removeLeftRotaiton() {
    map.insert("b", "b");
    map.insert("a", "a");
    map.insert("c", "c");
    map.insert("d", "d");
    map.remove("a"); // causes an imbalance at b resulting in a left rotaiton

    assertEquals(3, map.size());
    assertEquals("c:c\nb:b d:d\n", map.toString());
  }

  @Test
  @DisplayName("Ensures a right rotation is performed with three nodes inserted in descending order.")
  public void insertRightRotation() {
    map.insert("c","c"); // 1:a
    map.insert("b", "b"); // 1:a,\n2:b
    map.insert("a","a"); // should perform a right rotation

    assertEquals("b:b\na:a c:c\n", map.toString());
    assertEquals(3, map.size());
  }

  @Test
  @DisplayName("Ensures a right rotation preserves the overall structure of tree when other nodes are present.")
  public void insertRightRotationWithExtra() {
    map.insert("d", "d");
    map.insert("e", "e");
    map.insert("c", "c");
    map.insert("b", "b");
    map.insert("a", "a"); // triggers a right rotation at node with c

    assertEquals(5, map.size());
    assertEquals("d:d\nb:b e:e\na:a c:c null null\n", map.toString());
  }

  @Test
  @DisplayName("Ensures that a right rotation triggered by a node removal is sucessful.")
  public void removeRightRotation() {
    map.insert("d", "d");
    map.insert("b", "b");
    map.insert("e", "e");
    map.insert("a", "a"); // tree is currently balanced
    map.remove("e"); // will cause an unbalance at d resulting in right rotaiton

    assertEquals(3, map.size());
    System.out.println(map.toString());
    assertEquals("b:b\na:a d:d\n", map.toString());
  }

  @Test
  @DisplayName("Ensures that a right left rotation is performed in a simple case of three nodes.")
  public void insertRightLeftRotation() {
    map.insert("a", "a");
    map.insert("c", "c");
    map.insert("b", "b");

    assertEquals(3, map.size());
    assertEquals("b:b\na:a c:c\n", map.toString());
  }

  @Test
  @DisplayName("Ensures that a left right rotation is performed in a simple case of three nodes.")
  public void insertLeftRightRotation() {
    map.insert("c", "c");
    map.insert("a", "a");
    map.insert("b", "b");

    assertEquals(3, map.size());
    assertEquals("b:b\na:a c:c\n", map.toString());
  }

  @Test
  @DisplayName("Removes a node with no children.")
  public void removeNoChildren() {
    map.insert("b", "b");
    map.insert("a", "a");
    map.insert("c", "c");
    map.remove("c");

    assertEquals(2, map.size());
    assertEquals("b:b\na:a null\n", map.toString());
  }

  @Test
  @DisplayName("Removes a node with one child.")
  public void removeOneChild() {
    map.insert("b", "b");
    map.insert("a", "a");
    map.insert("c", "c");
    map.insert("d", "d");
    map.remove("c");

    assertEquals(3, map.size());
    assertEquals("b:b\na:a d:d\n", map.toString());
  }

  @Test
  @DisplayName("Removes a node with two children")
  public void removeTwoChildren() {
    map.insert("b", "b");
    map.insert("a", "a");
    map.insert("d", "d");
    map.insert("c", "c");
    map.insert("f", "f");
    map.remove("d");

    System.out.println(map.toString());
    assertEquals(4, map.size());
    assertEquals("b:b\na:a c:c\nnull null null f:f\n", map.toString());
  }

  @Test
  @DisplayName("Put throws exception for nonexistent key.")
  public void putThrowsExceptionForBadKey() {
    try {
      map.put("a", "b");
      fail();
    } catch (IllegalArgumentException e) {
      return;
    }
  }

  @Test
  @DisplayName("Put throws exception for null key.")
  public void putThrowsExceptionForNullKey() {
    try {
      map.put(null, "b");
      fail();
    } catch (IllegalArgumentException e) {
      return;
    }
  }

  @Test
  @DisplayName("Put throws exception for nonexistent key - nonempty tree.")
  public void putThrowsExceptionNonexistentKeyNonemptyTree() {
    map.insert("d", "d");
    map.insert("a", "a");
    map.insert("e", "e");
    try {
      map.put("b", "b");
      fail();
    } catch (IllegalArgumentException e) {
      return;
    }
  }

}
