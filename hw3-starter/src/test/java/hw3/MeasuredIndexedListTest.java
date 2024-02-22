package hw3;

import hw3.list.MeasuredIndexedList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MeasuredIndexedListTest {

  private static final int LENGTH = 15;
  private static final int DEFAULT_VALUE = 3;

  private MeasuredIndexedList<Integer> measuredIndexedList;

  @BeforeEach
  void setup() {
    measuredIndexedList = new MeasuredIndexedList<>(LENGTH, DEFAULT_VALUE);
  }

  @Test
  @DisplayName("MeasuredIndexedList starts with zero reads")
  void zeroReadsStart() {
    assertEquals(0, measuredIndexedList.accesses());
  }

  @Test
  @DisplayName("MeasuredIndexedList starts with zero writes")
  void zeroWritesStart() {
    assertEquals(0, measuredIndexedList.mutations());
  }

  // TODO Add more tests
  @Test
  @DisplayName("MeasuredIndexedList counts all instances of default values correctly.")
  void countsDefaultValues() {
    assertEquals(measuredIndexedList.count(DEFAULT_VALUE), LENGTH);
  }

  @Test
  @DisplayName("MeasuredIndexedList counts non-default values after modification.")
  void countsNonDefaultValuesAfterModification() {
    measuredIndexedList.put(0, DEFAULT_VALUE - 2);
    measuredIndexedList.put(2, DEFAULT_VALUE - 2);
    assertEquals(DEFAULT_VALUE - 2, measuredIndexedList.get(0));
    assertEquals(DEFAULT_VALUE - 2, measuredIndexedList.get(2)); // ensure values were correctly placed
    assertEquals(2, measuredIndexedList.count(DEFAULT_VALUE - 2));
  }

  @Test
  @DisplayName("MeasuredIndexedList counts default values after modification.")
  void countsDefaultValuesAfterModification() {
    measuredIndexedList.put(0, DEFAULT_VALUE - 2);
    measuredIndexedList.put(2, DEFAULT_VALUE - 2);
    measuredIndexedList.put(4, DEFAULT_VALUE - 2);
    assertEquals(LENGTH - 3, measuredIndexedList.count(DEFAULT_VALUE));
  }
}
