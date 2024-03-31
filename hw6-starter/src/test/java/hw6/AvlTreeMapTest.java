package hw6;

import hw6.bst.AvlTreeMap;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
    assertEquals((String.join("\n", expected) + "\n"), map.toString());
  }

  @Test
  @DisplayName("Ensures a right rotation preserves the overall structure of tree when other nodes are present.")
  public void insertRightRotationWithExtra() {

  }

  @Test
  @DisplayName("Ensures a right rotation is performed with three nodes inserted in descending order.")
  public void insertRightRotation() {
    map.insert("c","c"); // 1:a
    map.insert("b", "b"); // 1:a,\n2:b
    map.insert("a","a"); // should perform a right rotation

    assertEquals("b:b\na:a c:c\n", map.toString());
  }

  @Test
  @DisplayName("Ensures a left rotation preserves the overall structure of the tree when other nodes are involved.")
  public void insertLeftRotationWithExtra() {
    map.insert("d", "d");
    map.insert("a:", "a");
    map.insert("e", "e");
    map.insert("f", "f");
    map.insert("g", "g"); // will trigger a left rotation at node with e
    System.out.println(map.toString());
  }

  @Test
  @DisplayName("Ensures that a right left rotation is performed in a simple case of three nodes.")
  public void insertRightLeftRotation() {
    map.insert("a", "a");
    map.insert("b", "c");
    map.insert("c", "b");

    assertEquals("c:b\na:a c:c\n", map.toString());
  }

  @Test
  @DisplayName("Ensures that a left right rotation is performed in a simple case of three nodes.")
  public void insertLeftRightRotation() {
    map.insert("c", "c");
    map.insert("a", "a");
    map.insert("b", "b");
    System.out.println(map);
    assertEquals("b:b\na:a c:c\n", map.toString());
  }

}
