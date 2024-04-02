package hw6;

import hw6.bst.TreapMap;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * In addition to the tests in BinarySearchTreeMapTest (and in OrderedMapTest & MapTest),
 * we add tests specific to Treap.
 */
@SuppressWarnings("All")
public class TreapMapTest extends BinarySearchTreeMapTest {

  @Override
  protected Map<String, String> createMap() {
    return new TreapMap<>();
  }
  
  private Map<String, String> createMapWithSeed(int seed) {
    return new TreapMap<>(seed);
  }

  @Test
  @DisplayName("Adds elements where the priorities require a left rotation.")
  public void insertLeftRotation() {
    Map<String, String> map = createMapWithSeed(2);
    map.insert("a", "a"); // -1154715079 (1)
    map.insert("b", "b"); // 1260042744 (3)
    map.insert("c", "c"); // -423279216 (2)

    assertEquals(3, map.size());
    assertEquals("a:a:-1154715079\nnull c:c:-423279216\nnull null b:b:1260042744 null\n", map.toString());

  }

  @Test
  @DisplayName("Adds elements where the priorities require a right rotaiton.")
  public void insertRightRotation() {
    Map<String, String> map = createMapWithSeed(3);
    map.insert("b", "b");  // -1155099828 (2)
    map.insert("a", "a"); // -1879439976 (1) - triggers right rotation
    map.insert("c", "c"); // 304908421 (3)


    assertEquals(3, map.size());
    assertEquals("a:a:-1879439976\nnull b:b:-1155099828\nnull null null c:c:304908421\n", map.toString());
  }

  @Test
  @DisplayName("Triggers a right rotation by removal.")
  public void removeRightRotationTwoChildren() {
    Map<String, String> map = createMapWithSeed(4);
    map.insert("b", "b"); // -1157023572 (1) - root
    map.insert("a", "a"); // -396984392 (2) - left child
    map.insert("d", "d"); // -349120689 (3) - right child
    map.remove("b"); // triggeres a right rotation at b and then a left rotation at b

    // NOTE: inadverdently also tests a left rotation with one child

    assertEquals(2, map.size());
    assertEquals("a:a:-396984392\nnull d:d:-349120689\n", map.toString());
  }

  @Test
  @DisplayName("Triggers a left rotation by removal.")
  public void removeLeftRotationTwoChildren() {
    Map<String, String> map = createMapWithSeed(4);
    map.insert("b", "b"); // -1157023572 (1) - root
    map.insert("d", "d"); // -396984392 (2) - right child
    map.insert("a", "a"); // -349120689 (3) - left child
    map.remove("b"); // triggers a left rotation at b and then a right rotation at a

    // NOTE: also inadvertently tests a right rotation with one node

    assertEquals(2, map.size());
    assertEquals("d:d:-396984392\na:a:-349120689 null\n", map.toString());

  }

  @Test
  @DisplayName("Ensures that structure is maintained when a node with one child is removed.")
  public void removeOneChildNoRotation() {
    Map<String, String> map = createMapWithSeed(4);
    map.insert("c", "c"); // -1157023572 (1) - root
    map.insert("b", "b"); // -396984392 (2) - right child
    map.insert("a", "a"); // -349120689 (3) - left child
    map.remove("b"); // removes b which has a as a left child

    assertEquals(2, map.size());
    assertEquals("c:c:-1157023572\na:a:-349120689 null\n", map.toString());
  }

  @Test
  @DisplayName("Ensures removing a leaf functions correctly.")
  public void removeLeaf() {
    Map<String, String> map = createMapWithSeed(4);
    map.insert("c", "c"); // -1157023572 (1) - root
    map.insert("b", "b"); // -396984392 (2) - right child
    map.remove("b");

    assertEquals(1, map.size());
    assertEquals("c:c:-1157023572\n", map.toString());
  }

  @Test
  @DisplayName("Ensures inserting when no swaps should occur happens properly.")
  public void insertNoSwapsOccur() {
    Map<String, String> map = createMapWithSeed(4);
    map.insert("b", "b"); // -1157023572 (1) - root
    map.insert("a", "a"); // -396984392 (2)
    map.insert("c", "c"); // -349120689 (3)

    assertEquals(3, map.size());
    assertEquals("b:b:-1157023572\na:a:-396984392 c:c:-349120689\n", map.toString());
  }

}