package hw2;

import exceptions.IndexException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * Unit Tests for any class implementing the IndexedList interface.
 */
public abstract class IndexedListTest {
  protected static final int LENGTH = 10;
  protected static final int INITIAL = 7;
  private IndexedList<Integer> indexedList;

  public abstract IndexedList<Integer> createArray();

  @BeforeEach
  public void setup() {
    indexedList = createArray();
  }

  @Test
  @DisplayName("get() returns the default value after IndexedList is instantiated.")
  void testGetAfterConstruction() {
    for (int index = 0; index < indexedList.length(); index++) {
      assertEquals(INITIAL, indexedList.get(index));
    }
  }

  @Test
  @DisplayName("get() throws exception if index is below the valid range.")
  void testGetWithIndexBelowRangeThrowsException() {
    try {
      indexedList.get(-1);
      fail("IndexException was not thrown for index < 0");
    } catch (IndexException ex) {
      //return;
    }
  }

  // TODO Add more tests!
  @Test
  @DisplayName("get() throws exception if index is above the valid range.")
  void testGetWithIndexAboveRangeThrowsException() {
    try {
      indexedList.get(LENGTH + 1);
      fail("IndexException was not thrown for index > length");
    } catch (IndexException ex) {
      //return;
    }
  }

  @Test
  @DisplayName("put() throws exception if index is below the valid range.")
  void testPutWithIndexBelowRangeThrowsException() {
    try {
      indexedList.put(-1, 10);
      fail("IndexException was not thrown for index < 0");
    } catch (IndexException ex) {
      //return;
    }
  }

  @Test
  @DisplayName("put() throws exception if index is above the valid range.")
  void testPutWithIndexAboveRangeThrowsException() {
    try {
      indexedList.put(LENGTH + 1, 10);
      fail("IndexException was not thrown for index > length");
    } catch (IndexException ex) {
      //return;
    }
  }

  @Test
  @DisplayName("get() is able to retrieve multiple non default values.")
  void getRetrievesMultipleNonDefaultValues() {
    indexedList.put(0, 1); // indexedList[0] = 1
    indexedList.put(2, -2); // indexedList[2] = -2
    indexedList.put(5, 0); // indexedList[5] = 0
    int count = 0;
    while (count < LENGTH) {
      if (count == 0) {
        assertEquals(indexedList.get(count), 1);
      } else if  (count == 2) {
        assertEquals(indexedList.get(count), -2);
      } else if (count == 5) {
        assertEquals(indexedList.get(count), 0);
      } else {
        assertEquals(indexedList.get(count), INITIAL);
      }
      ++count;
    }
    assertEquals(count, LENGTH);
  }

  @Test
  @DisplayName("put() changes the default value after IndexedList is instantiated.")
  void testPutChangesValueAfterConstruction() {
    indexedList.put(2, 7);
    assertEquals(7, indexedList.get(2));
  }

  @Test
  @DisplayName("put() overwrites the existing value at given index to provided value.")
  void testPutUpdatesValueAtGivenIndex() {
    indexedList.put(1, 8);
    assertEquals(8, indexedList.get(1));
    indexedList.put(1, -5);
    assertEquals(-5, indexedList.get(1));
  }

  @Test
  @DisplayName("put() overwrites an overwritten value back to default value.")
  void testPutOverwritesOverwrittenWithDefault() {
    indexedList.put(2, 8);
    assertEquals(8, indexedList.get(2));
    indexedList.put(2, INITIAL);
    assertEquals(INITIAL,indexedList.get(2));
  }

  @Test
  @DisplayName("put() overwrites default value at first index.")
  void putOverwritesDefaultValueAtFirstIndex() {
      indexedList.put(0, -1);
      int count = 0;
      while (count < LENGTH) {
        if (count == 0) {
          assertEquals(indexedList.get(count), -1);
        } else {
          assertEquals(indexedList.get(count), INITIAL);
        }
        ++count;
      }
  }

  @Test
  @DisplayName("put() overwrites value at last index.")
  void putOverwritesValueAtLastIndex() {
    indexedList.put(LENGTH - 1, -1);
    int count = 0;
    while (count < LENGTH) {
      if (count == LENGTH - 1) {
        assertEquals(indexedList.get(count), -1);
      } else {
        assertEquals(indexedList.get(count), INITIAL);
      }
      ++count;
    }
    indexedList.put(LENGTH - 1, INITIAL);
    count = 0;
    while (count < LENGTH) {
      assertEquals(indexedList.get(count), INITIAL);
      ++count;
    }
  }

  @Test
  @DisplayName("length() returns the correct size after IndexedList is instantiated.")
  void testLengthAfterConstruction() {
    assertEquals(LENGTH, indexedList.length());
  }

  @Test
  @DisplayName("put() and IndexedListIterator() do not interfere with respective functionality.")
  void iteratorAndPutWorkTogether() {
    indexedList.put(0, -1);
    indexedList.put(LENGTH - 1, -1);
    indexedList.put(2, -1);
    int count = 0;
    for (int element : indexedList) {
      if (count == 0 || count == LENGTH - 1 || count == 2) {
        assertEquals(element, -1);
      } else {
        assertEquals(element, INITIAL);
      }
      ++count;
    }

    indexedList.put(LENGTH - 1, INITIAL);
    count = 0;
    for (int element : indexedList) {
      if (count == 0 || count == 2) {
        assertEquals(element, -1);
      } else {
        assertEquals(element, INITIAL);
      }
      ++count;
    }
  }

  @Test
  @DisplayName("IndexedListIterator() throws exception when applying get to an element that does not exist.")
  void iteratorThrowsExceptionUponGettingNonexistentElement() {

    int count = 0;
    Iterator<Integer> it = indexedList.iterator();
    while (it.hasNext()) {
      ++count;
      it.next();
    }
    assertEquals(count, LENGTH);
    try {
      it.next();
      fail("NoSuchElementException was not thrown for index > length");
    } catch (NoSuchElementException ex) {
      return;
    }
  }

  @Test
  @DisplayName("put() overwrites all values and IndexedListIterator() returns all.")
  void putOverwritesAllValuesAndIteratorsReturns() {
    for (int i = 0; i < LENGTH; i++) {
      indexedList.put(i, -2);
    }
    int count = 0;
    for (int element: indexedList) {
      assertEquals(element, -2);
      ++count;
    }
    assertEquals(count, LENGTH);

    // modify one element to be default again
    indexedList.put(2, INITIAL);
    count = 0;
    for (int element: indexedList) {
      if (count == 2) {
        assertEquals(element, INITIAL);
      } else {
        assertEquals(element, -2);
      }
      ++count;
    }
    assertEquals(count, LENGTH);
  }

  @Test
  @DisplayName("IndexedListIterator() properly iterates all default values.")
  void iteratorReturnsAllDefaultValues() {
    int count = 0;
    for (int element : indexedList) {
      assertEquals(element, INITIAL);
      ++count;
    }
    assertEquals(count, LENGTH);
  }

  @Test
  @DisplayName("IndexedListIterator() properly iterates default and non-default values")
  void iteratorReturnsDefaultAndNonDefaultValues() {
    int count = 0;
    indexedList.put(0, 1); // indexedList[0] = 1
    indexedList.put(3, 2); // indexedList[3] = 2
    for (int element : indexedList) {
      if (count == 0) {
        assertEquals(element, 1);
      } else if (count == 3) {
        assertEquals(element, 2);
      } else {
        assertEquals(element, INITIAL);
      }
      ++count;
    }
    assertEquals(count, LENGTH);
  }

  @Test
  @DisplayName("IndexedListIterator() iterates over modified list.")
  void iteratorReturnsModifiedList() {
    int index = 0;
    indexedList.put(1, -1);
    indexedList.put(LENGTH - 1, -2);
    for (int element : indexedList) {
      if (index == 1) {
        assertEquals(element, -1);
      } else if (index == LENGTH - 1) {
        assertEquals(element, -2);
      } else {
        assertEquals(element, INITIAL);
      }
      ++index;
    }
    assertEquals(index, LENGTH);

    indexedList.put(LENGTH - 1, INITIAL);
    index = 0;
    for (int element : indexedList) {
      if (index == 1) {
        assertEquals(element, -1);
      } else {
        assertEquals(element, INITIAL);
      }
      ++index;
    }
    assertEquals(index, LENGTH);

    indexedList.put(1, INITIAL);
    index = 0;
    for (int element : indexedList) {
      assertEquals(element, INITIAL);
      ++index;
    }
    assertEquals(index, LENGTH);
  }

  @Test
  @DisplayName("IndexedListIterator() iterates over entirely non-default list, with distinct values.")
  void iteratorReturnsAllDistinctNonDefaultValues() {
    for (int i = 0; i < LENGTH; ++i) {
      indexedList.put(i, i);
    }

    int count = 0;
    for (Integer element : indexedList) {
      assertEquals(element, count);
      count++;
    }
  }

  @Test
  @DisplayName("put() works as expected for multiple successive calls.")
  void putSuccessiveCalls() {
    indexedList.put(0, -1);
    assertEquals(indexedList.get(0), -1);
    indexedList.put(0, INITIAL);
    indexedList.put(1, -2);
    assertEquals(indexedList.get(0), INITIAL);
    assertEquals(indexedList.get(1), -2);
  }





}
