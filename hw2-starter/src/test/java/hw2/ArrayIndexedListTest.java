package hw2;

public class ArrayIndexedListTest extends IndexedListTest {

  @Override
  public IndexedList<Integer> createArray() {
    return new ArrayIndexedList<>(LENGTH, INITIAL);
  }

}
