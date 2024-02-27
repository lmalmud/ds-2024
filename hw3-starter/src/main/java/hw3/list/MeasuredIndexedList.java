package hw3.list;

import exceptions.IndexException;

/**
 * An ArrayIndexedList that is able to report the number of
 * accesses and mutations, as well as reset those statistics.
 *
 * @param <T> The type of the array.
 */
public class MeasuredIndexedList<T> extends ArrayIndexedList<T>
    implements Measured<T> {

  /**
   * Constructor for a MeasuredIndexedList.
   *
   * @param size         The size of the array.
   * @param defaultValue The initial value to set every object to in the array..
   */

  private int numAccesses;
  private int numMutations;

  /**
   * Initializes a MeasuredIndexedList.
   * @param size the number of elements in the list
   * @param defaultValue the value that will be automatically assigned to all indices
   */
  public MeasuredIndexedList(int size, T defaultValue) {
    super(size, defaultValue);
    reset();
  }

  @Override
  public int length() {
    return super.length();
  }

  @Override
  public T get(int index) throws IndexException {
    // TODO: Implement me
    T val;
    try {
      val = super.get(index);
      this.numAccesses++;
      return val;
    } catch (IndexException e) {
      throw new IndexException();
    }
  }

  @Override
  public void put(int index, T value) throws IndexException {
    try {
      super.put(index, value);
      this.numMutations++;
    } catch (IndexException e) {
      throw new IndexException();
    }
  }

  @Override
  public void reset() {
    this.numAccesses = 0;
    this.numMutations = 0;
  }

  @Override
  public int accesses() {
    return this.numAccesses;
  }

  @Override
  public int mutations() {
    return this.numMutations;
  }

  @Override
  public int count(T value) {
    int count = 0;
    for (int i = 0; i < this.length(); ++i) {
      if (get(i).equals(value)) {
        ++count;
      }
    }
    return count;
  }

}
