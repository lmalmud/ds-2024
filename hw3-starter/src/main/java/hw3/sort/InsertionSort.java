package hw3.sort;


import hw3.list.IndexedList;

/**
 * The Insertion Sort algorithm, with minimizing swaps optimization.
 *
 * @param <T> Element type.
 */
public final class InsertionSort<T extends Comparable<T>>
    implements SortingAlgorithm<T> {

  @Override
  public void sort(IndexedList<T> indexedList) {
    int i = 1;
    while (i < indexedList.length()) {
      T x = indexedList.get(i);
      int j = i;
      while (j > 0 && indexedList.get(j - 1).compareTo(x) > 0) {
        indexedList.put(j, indexedList.get(j - 1));
        j--;
      }
      indexedList.put(j, x);
      i++;
    }
  }

  @Override
  public String name() {
    return "Insertion Sort";
  }
}
