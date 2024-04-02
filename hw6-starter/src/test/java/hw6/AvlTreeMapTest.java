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
  @DisplayName("Ensures that a right left rotation is performed after a removal.")
  public void removeRightLeftRotation() {
    map.insert("c", "c");
    map.insert("a", "a");
    map.insert("e", "e");
    map.insert("d", "d");
    map.remove("a"); // triggers a right left rotation at c

    assertEquals(3, map.size());
    assertEquals("d:d\nc:c e:e\n", map.toString());
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
  @DisplayName("Ensures that a left right rotation is performed after a removal")
  public void removeLeftRightRotation() {
    map.insert("c", "c");
    map.insert("a", "a");
    map.insert("d", "d");
    map.insert("b", "b");
    map.remove("d"); // triggers a left right rotation at c

    assertEquals(3, map.size());
    assertEquals("b:b\na:a c:c\n", map.toString());
  }

  @Test
  @DisplayName("Properly inserts a node when no rotation should occur.")
  public void insertNoRotation() {
    map.insert("b", "b");
    map.insert("a", "a");
    map.insert("c", "c");

    assertEquals(3, map.size());
    assertEquals("b:b\na:a c:c\n", map.toString());
  }

  @Test
  @DisplayName("Removes a node with no children, which causes no rotation.")
  public void removeNoChildrenNoRotation() {
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

  @Test
  @DisplayName("Insert many nodes without rotations.")
  public void insertManyNodesNoRotations() {
    map.insert("g", "g");
    map.insert("d", "d");
    map.insert("l", "l");
    map.insert("a", "a");
    map.insert("e", "e");
    map.insert("k", "k");
    map.insert("m", "m");

    assertEquals(7, map.size());
    assertEquals("g:g\nd:d l:l\na:a e:e k:k m:m\n", map.toString());
  }

  @Test
  @DisplayName("Insert many nodes with rotations.")
  public void insertManyNodesWithRotations() {
    map.insert("c", "c");
    map.insert("b", "b");
    map.insert("a", "a"); // triggers a right rotation at c
    assertEquals("b:b\na:a c:c\n", map.toString());
    map.insert("d", "d");
    map.insert("e", "e"); // triggers a left rotation at d
    assertEquals("b:b\na:a d:d\nnull null c:c e:e\n", map.toString());
    map.insert("Z", "Z");
    map.insert("Y", "Y"); // triggeres a right rotation at a
    assertEquals("b:b\nZ:Z d:d\nY:Y a:a c:c e:e\n", map.toString());
  }

  @Test
  @DisplayName("Removes a leaf from a tree with many nodes.")
  public void removeLeafFromTreeWithManyNodes() {
    map.insert("c", "c");
    map.insert("b", "b");
    map.insert("a", "a");
    map.insert("d", "d");
    map.insert("e", "e");
    map.insert("Z", "Z");
    map.insert("Y", "Y");
    map.remove("a");
    assertEquals("b:b\nZ:Z d:d\nY:Y null c:c e:e\n", map.toString());
  }

  @Test
  @DisplayName("Removes a node with one child from a tree with many nodes.")
  public void removeNodeWithOneChildFromTreeWithManyNodes() {
    map.insert("c", "c");
    map.insert("b", "b");
    map.insert("a", "a"); // triggers a right rotation at c
    map.insert("d", "d");
    map.insert("e", "e"); // triggers a left rotation at d
    map.insert("Z", "Z");
    map.remove("Z");
    assertEquals("b:b\na:a d:d\nnull null c:c e:e\n", map.toString());
  }

  @Test
  @DisplayName("Removes a node with two children from a tree with many nodes.")
  public void removeNodeWithTwoChildrenFromTreeWithManyNodes() {
    map.insert("c", "c");
    map.insert("b", "b");
    map.insert("a", "a");
    map.insert("d", "d");
    map.insert("e", "e");
    map.insert("Z", "Z");
    map.insert("Y", "Y");
    map.remove("b");
    assertEquals("a:a\nZ:Z d:d\nY:Y null c:c e:e\n", map.toString());
  }

  @Test
  @DisplayName("Performs a right left rotation on a larger tree triggered by insertion.")
  public void rightLeftRotationLargeTreeInsert() {
    // Exercise VI
    map.insert("d", "10"); // d
    map.insert("b", "5"); // b
    map.insert("f", "20"); // f
    map.insert("a", "3"); // a
    map.insert("c", "7"); // c
    map.insert("e", "15"); // e
    map.insert("i", "25"); // i
    map.insert("g", "22"); // g
    map.insert("j", "30"); // j
    System.out.println(map.toString());
    map.insert("h", "23"); // h

    assertEquals("d:10\nb:5 g:22\na:3 c:7 f:20 i:25\nnull null null null e:15 null h:23 j:30\n", map.toString());
  }

  @Test
  @DisplayName("Ensures a left right rotation is triggered after a removal.")
  public void leftRightLargeTreeRemoval() {
    // Exercise VII
    map.insert("g", "7"); // g
    map.insert("d", "4"); // d
    map.insert("l", "12"); // l
    map.insert("b", "2"); // b
    map.insert("e", "5"); // e
    map.insert("i", "9"); // i
    map.insert("m", "13"); // m
    map.insert("a", "1"); // a
    map.insert("c", "3"); // c
    map.insert("f", "6"); // f
    map.insert("h", "8"); // h
    map.insert("j", "10"); // j
    map.insert("n", "14"); // n
    map.insert("k", "11"); // k
    map.remove("m"); // triggers left right removal at l

    assertEquals("g:7\nd:4 j:10\nb:2 e:5 i:9 l:12\na:1 c:3 null f:6 h:8 null k:11 n:14\n", map.toString());
  }

  @Test
  @DisplayName("Inserts eight elements, removes two of them, and reinserts them.")
  public void removeInsertRemoveAgainSameNodes() {
    /// b c f g d a e h
    map.insert("b", "b");
    map.insert("c", "c");
    map.insert("f", "f"); // triggers left rotation at b
    map.insert("g", "g");
    map.insert("d", "d");
    map.insert("a", "a");
    map.insert("e", "e");
    map.insert("h", "h");

    // remove f and e
    map.remove("f"); // triggers no rotation
    assertEquals("c:c\nb:b e:e\na:a null d:d g:g\nnull null null null null null null h:h\n", map.toString());
    map.remove("e"); // triggers left rotation at d
    assertEquals("c:c\nb:b g:g\na:a null d:d h:h\n", map.toString());

    // insert f and e back
    map.insert("f", "f");
    map.insert("e", "e"); // triggers a right left rotation at d

    assertEquals(8, map.size());
    assertEquals("c:c\nb:b g:g\na:a null e:e h:h\nnull null null null d:d f:f null null\n", map.toString());
  }

  @Test
  @DisplayName("Inserts eight elements, removes two of them, inserts two new nodes, and removes one of the original nodes.")
  public void removeInsertRemoveDifferentNodes() {
    // e f g h a d b c
    map.insert("e", "e");
    map.insert("f", "f");
    map.insert("g", "g"); // triggers left rotation at e
    assertEquals("f:f\ne:e g:g\n", map.toString());
    map.insert("h", "h");
    map.insert("a", "a");
    map.insert("d", "d"); // triggers a left right rotation at e
    assertEquals("f:f\nd:d g:g\na:a e:e null h:h\n", map.toString());
    map.insert("b", "b");
    map.insert("c", "c"); // trigger a left rotation at a, and a right rotation at f
    assertEquals("f:f\nd:d g:g\nb:b e:e null h:h\na:a c:c null null null null null null\n", map.toString());

    // removes two nodes
    map.remove("h"); // triggers imbalance at f requiring right rotation
    assertEquals("d:d\nb:b f:f\na:a c:c e:e g:g\n", map.toString());

    map.remove("d");
    assertEquals("c:c\nb:b f:f\na:a null e:e g:g\n", map.toString());

    // inserts two new nodes
    map.insert("A", "A");
    assertEquals("c:c\na:a f:f\nA:A b:b e:e g:g\n", map.toString());

    map.insert("i", "i");
    assertEquals("c:c\na:a f:f\nA:A b:b e:e g:g\nnull null null null null null null i:i\n", map.toString());

    // removes one of the original nodes
    map.remove("e");
    assertEquals("c:c\na:a g:g\nA:A b:b f:f i:i\n", map.toString());

  }

  @Test
  @DisplayName("Inserts multiple nodes in consecutive order.")
  public void multipleNodesConsequitive() {
    map.insert("a", "a");
    map.insert("b", "b");
    map.insert("c", "c");
    map.insert("d", "d");
    map.insert("e", "e");
    map.insert("f", "f");
    map.insert("g", "g");

    assertEquals("d:d\nb:b f:f\na:a c:c e:e g:g\n", map.toString());
  }

  // based off example from stack overflow https://stackoverflow.com/questions/3955680/how-to-check-if-my-avl-tree-implementation-is-correct
  @Test
  @DisplayName("Removing a node causes a double rotation.")
  public void removeCausesDoubleRotation() {
    map.insert("b", "b");
    map.insert("a", "a");
    map.insert("d", "d");
    map.insert("c", "c");
    map.insert("e", "e");
    map.remove("a");

    assertEquals("d:d\nb:b e:e\nnull c:c null null\n", map.toString());
  }

  // based off example from stack overflow https://stackoverflow.com/questions/3955680/how-to-check-if-my-avl-tree-implementation-is-correct
  @Test
  @DisplayName("Insert causes double rotation.")
  public void insertCausesDoubleRotation() {
    map.insert("e", "e");
    map.insert("b", "b");
    map.insert("f", "f");
    map.insert("a", "a");
    map.insert("c", "c");
    map.insert("d", "d"); // causes two rotations

    assertEquals("c:c\nb:b e:e\na:a null d:d f:f\n", map.toString());
  }

  @Test
  @DisplayName("Inserting and removing randomly.")
  public void multipleInsertRemovalRandom() {
    // h f d c e b a g
    map.insert("h", "h");
    map.insert("f", "f");
    map.insert("d", "d"); // triggers rotation
    assertEquals("f:f\nd:d h:h\n", map.toString());
    map.insert("c", "c");
    map.insert("e", "e");
    map.insert("b", "b"); // triggers rotation
    assertEquals("d:d\nc:c f:f\nb:b null e:e h:h\n", map.toString());
    map.insert("a", "a"); // triggers rotation
    assertEquals("d:d\nb:b f:f\na:a c:c e:e h:h\n", map.toString());
    map.insert("g", "g");
    map.remove("e"); // triggers rotation
    assertEquals("d:d\nb:b g:g\na:a c:c f:f h:h\n", map.toString());
    map.remove("f");
    map.remove("a");
    map.insert("i", "i"); // triggers rotation
    assertEquals("d:d\nb:b h:h\nnull c:c g:g i:i\n", map.toString());
    map.insert("j", "j");
    map.insert("k", "k"); // triggers rotation
    assertEquals("d:d\nb:b h:h\nnull c:c g:g j:j\nnull null null null null null i:i k:k\n", map.toString());
  }

  @Test
  @DisplayName("More repeated inserts and removals to trigger complex rotations.")
  public void complexInsertRemovalsTriggerRotations() {
    //h b k f d e i m c g a j l
    map.insert("h", "h");
    map.insert("b", "b");
    map.insert("k", "k");
    map.insert("f", "f");
    map.insert("d", "d"); // triggers rotation
    assertEquals("h:h\nd:d k:k\nb:b f:f null null\n", map.toString());
    map.insert("e", "e"); // triggers rotation
    assertEquals("f:f\nd:d h:h\nb:b e:e null k:k\n", map.toString());
    map.insert("i", "i"); // triggers rotation
    assertEquals("f:f\nd:d i:i\nb:b e:e h:h k:k\n", map.toString());
    map.insert("m", "m");
    map.insert("c", "c");
    map.insert("g", "g");
    map.insert("a", "a");
    map.insert("j", "j");
    map.insert("l", "l");

    map.remove("g"); // triggers rotation
    assertEquals("f:f\nd:d k:k\nb:b e:e i:i m:m\na:a c:c null null h:h j:j l:l null\n", map.toString());
    map.remove("h");
    map.remove("k");

    map.insert("n", "n");

    map.remove("a");
    map.remove("i"); // triggers rotatin
    assertEquals("f:f\nd:d m:m\nb:b e:e j:j n:n\nnull c:c null null null l:l null null\n", map.toString());
  }

}
