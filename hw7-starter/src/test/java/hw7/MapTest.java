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
  public void testHasWithNullKey() {
    assertFalse(map.has(null));
  }

  @Test
  public void testRehashing() {
    map.insert("a", "1");
    map.insert("b", "2");
    map.insert("c", "3");
    assertEquals(3, map.size());

    // Inserting one more element should trigger rehashing
    map.insert("d", "4");
    assertEquals(4, map.size());
    assertTrue(map.has("d"));
  }

  @Test
  public void testLinearProbing() {
    map.insert("1", "a");
    map.insert("6", "b"); // Collides with index 1, should be probed to index 2
    map.insert("11", "c"); // Collides with index 1 and 2, should be probed to index 3
    map.insert("16", "d"); // Collides with index 1, 2, and 3, should be probed to index 4
    map.insert("21", "e"); // Collides with index 1, 2, 3, and 4, should be probed to index 0
    assertEquals(5, map.size());

    // All elements should be retrievable
    assertEquals("a", map.get("1"));
    assertEquals("b", map.get("6"));
    assertEquals("c", map.get("11"));
    assertEquals("d", map.get("16"));
    assertEquals("e", map.get("21"));
  }


}