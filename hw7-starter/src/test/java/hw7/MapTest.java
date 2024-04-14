package hw7;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

@SuppressWarnings("All")
public abstract class MapTest {

  protected Map<String, String> map;

  @BeforeEach
  public void setup() {
    map = createMap();
  }

  protected abstract Map<String, String> createMap();

  @Test
  public void newMapIsEmpty() {
    assertEquals(0, map.size());
  }

  @Test
  public void insertOneElement() {
    map.insert("key1", "value1");
    assertEquals(1, map.size());
    assertTrue(map.has("key1"));
    assertEquals("value1", map.get("key1"));
  }

  @Test
  public void insertMultipleElement() {
    map.insert("key1", "value1");
    map.insert("key2", "value2");
    map.insert("key3", "value3");
    assertEquals(3, map.size());
    assertTrue(map.has("key1"));
    assertTrue(map.has("key2"));
    assertTrue(map.has("key3"));
    assertEquals("value1", map.get("key1"));
    assertEquals("value2", map.get("key2"));
    assertEquals("value3", map.get("key3"));
  }

  @Test
  public void insertDuplicatedKey() {
    try {
      map.insert("key1", "value1");
      map.insert("key1", "value2");
      fail("Failed to throw IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      return;
    }
  }

  @Test
  public void insertNullKey() {
    try {
      map.insert(null, "value1");
      fail("Failed to throw IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      return;
    }
  }

  @Test
  public void insertDuplicatedValue() {
    map.insert("key1", "value1");
    map.insert("key2", "value1");
    assertEquals(2, map.size());
  }

  @Test
  public void insertNullValue() {
    map.insert("null", null);
    assertEquals(1, map.size());
  }

  @Test
  public void removeOneElement() {
    map.insert("key1", "value1");
    assertEquals("value1", map.remove("key1"));
    assertEquals(0, map.size());
  }

  @Test
  public void removeMultipleElements() {
    map.insert("key1", "value1");
    map.insert("key2", "value2");
    map.insert("key3", "value3");
    assertEquals("value1", map.remove("key1"));
    assertEquals("value3", map.remove("key3"));
    assertEquals(1, map.size());
    assertFalse(map.has("key1"));
    assertTrue(map.has("key2"));
    assertFalse(map.has("key3"));
    assertEquals("value2", map.get("key2"));
  }

  @Test
  public void removeNull() {
    try {
      map.remove(null);
      fail("Failed to throw IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      return;
    }
  }

  @Test
  public void removeNoSuchElement() {
    try {
      map.remove("key1");
      fail("Failed to throw IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      return;
    }
  }

  @Test
  public void updateValue() {
    map.insert("key1", "value1");
    map.put("key1", "value2");
    assertEquals(1, map.size());
    assertEquals("value2", map.get("key1"));
  }

  @Test
  public void updateMultipleValues() {
    map.insert("key1", "value1");
    map.insert("key2", "value2");
    map.insert("key3", "value3");
    map.put("key1", "updated1");
    map.put("key3", "updated3");
    assertEquals(3, map.size());
    assertEquals("updated1", map.get("key1"));
    assertEquals("value2", map.get("key2"));
    assertEquals("updated3", map.get("key3"));
  }

  @Test
  public void updateMultipleTimes() {
    map.insert("key1", "value1");
    map.put("key1", "value2");
    map.put("key1", "value3");
    map.put("key1", "value4");
    assertEquals(1, map.size());
    assertEquals("value4", map.get("key1"));
  }

  @Test
  public void updateNullValue() {
    map.insert("key1", "value1");
    map.put("key1", null);
    assertEquals(1, map.size());
    assertNull(map.get("key1"));
  }

  @Test
  public void updateNullKey() {
    try {
      map.put(null, "value");
      fail("Failed to throw IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      return;
    }
  }

  @Test
  public void updateKeyNotMapped() {
    try {
      map.put("key", "value");
      fail("Failed to throw IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      return;
    }
  }

  @Test
  public void getKeyNull() {
    try {
      map.get(null);
      fail("Failed to throw IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      return;
    }
  }

  @Test
  public void iteratorEmptyMap() {
    for (String key : map) {
      fail("Empty map!");
    }
  }

  @Test
  public void iteratorMultipleElements() {
    map.insert("key1", "value1");
    map.insert("key2", "value2");
    map.insert("key3", "value3");
    int counter = 0;
    for (String key : map) {
      counter++;
      assertTrue(map.has(key));
    }
    assertEquals(3, counter);
  }

  // Ideally we should also check for "Keys must be immutable"
  // This is not trivial; check out
  // https://github.com/MutabilityDetector/MutabilityDetector

  /* MY CASES */

    @Test
    public void testInsert() {
      map.insert("a", "1");
      map.insert("b", "2");
      map.insert("c", "3");
      assertEquals(3, map.size());
      assertTrue(map.has("a"));
      assertTrue(map.has("b"));
      assertTrue(map.has("c"));
    }

    @Test
    public void testRemove() {
      map.insert("a", "1");
      map.insert("b", "2");
      map.insert("c", "3");
      assertEquals(3, map.size());

      String removedValue = map.remove("b");
      assertEquals("2", removedValue);
      assertEquals(2, map.size());
      assertFalse(map.has("b"));
    }

    @Test
    public void testInsertDuplicateKey() {
      map.insert("a", "1");
      try {
        map.insert("a", "2");
        fail();
      }
      catch (IllegalArgumentException e) {

      }

    }

    @Test
    public void testRemoveNonexistentKey() {
      try {
        map.remove("a"); // Should throw IllegalArgumentException
        fail();
      }
      catch (IllegalArgumentException e) {

      }
    }

    @Test
    public void testInsertAndRemove() {
      map.insert("a", "1");
      map.insert("b", "2");
      assertEquals(2, map.size());

      map.remove("a");
      assertEquals(1, map.size());
      assertFalse(map.has("a"));
      assertTrue(map.has("b"));
    }

  @Test
  public void testInsertWithNullKey() {
    try {
      map.insert(null, "value");
      fail("Expected IllegalArgumentException for inserting null key");
    } catch (IllegalArgumentException e) {
      assertEquals(0, map.size());
    }
  }

  @Test
  public void testInsertWithNullValue() {
    map.insert("key", null);
    assertEquals(1, map.size());
    assertNull(map.get("key"));
  }

  @Test
  public void testRemoveWithNullKey() {
    try {
      map.remove(null);
      fail("Expected IllegalArgumentException for removing null key");
    } catch (IllegalArgumentException e) {
      assertEquals(0, map.size());
    }
  }

  @Test
  public void testPutWithNullKey() {
    try {
      map.put(null, "value");
      fail("Expected IllegalArgumentException for putting null key");
    } catch (IllegalArgumentException e) {
      assertEquals(0, map.size());
    }
  }

  @Test
  public void testGetWithNullKey() {
    try {
      map.get(null);
      fail("Expected IllegalArgumentException for getting null key");
    } catch (IllegalArgumentException e) {
      assertEquals(0, map.size());
    }
  }

  @Test
  public void testGetEdgeCase() {
      map.insert("c", "c"); // mapped to index 4
      map.insert("pp", "pp"); // mapped to index 4
      assertTrue(map.has("c"));
      assertTrue(map.has("pp"));
      map.remove("c");
      assertFalse(map.has("c"));
      assertTrue(map.has("pp"));

  }

  @Test
  public void testHasWithNullKey() {
    assertFalse(map.has(null));
  }

  @Test
  public void testRehashingMoreThanFiveElements() {
      map.insert("a", "a");
      map.insert("b", "b");
      map.insert("c", "c");
      map.insert("d", "d");
      map.insert("e", "e");
      map.insert("f", "f"); // should trigger

      assertEquals(6, map.size());
      assertEquals("a", map.get("a"));
      assertTrue(map.has("a"));
      map.remove("d");
      assertFalse(map.has("d"));

      // now there are five elements- should rehash after four more added
      map.insert("g",  "g");
      map.insert("h", "h");
      map.insert("i", "i");
      map.insert("j", "j");
      assertEquals(9, map.size());

  }

  @Test
  public void insertAndGetIncludingRehash() {
      map.insert("a", "A");
      assertTrue(map.has("a"));
      assertEquals("A", map.get("a"));

      map.insert("b", "B");
      assertTrue(map.has("b"));
      assertEquals("B", map.get("b"));

      map.insert("c", "C");
      assertTrue(map.has("c"));
      assertEquals("C", map.get("c"));

      map.insert("d", "D");
      assertTrue(map.has("d"));
      assertEquals("D", map.get("d"));

      map.insert("e", "E");
      assertTrue(map.has("e"));
      assertEquals("E", map.get("e"));

      map.insert("f", "F");
      assertTrue(map.has("f"));
      assertEquals("F", map.get("f"));

      map.remove("f");
      map.remove("e");
      map.remove("a");
      map.remove("c");
      map.insert("c", "C");
      assertTrue(map.has("c"));
      assertEquals("C", map.get("c"));
  }

  @Test
  public void insertBunchOfElements() {
    String[] toInsert = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m"};
    for (int i = 0; i < toInsert.length; i++) {
      map.insert(toInsert[i], toInsert[i]);
    }
    String[] toRemove = {"d", "e", "f"};
    for (int i = 0; i < toRemove.length; i++) {
      map.remove(toRemove[i]);
    }

    for (int i = 0; i < toRemove.length; i++) {
      assertFalse(map.has(toRemove[i]));
    }

    String[] remaining = {"a", "b", "c", "g", "h", "i", "j", "k", "l", "m"};
    for (int i = 0; i < remaining.length; i++) {
      assertEquals(remaining[i], map.get(remaining[i]));
      assertTrue(map.has(remaining[i]));
    }

  }



}