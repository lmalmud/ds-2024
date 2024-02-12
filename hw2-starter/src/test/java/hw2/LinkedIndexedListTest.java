package hw2;

public class LinkedIndexedListTest extends IndexedListTest {

  @Override
  public IndexedList<Integer> createArray() {
    return new LinkedIndexedList<>(LENGTH, INITIAL);
  }

}
