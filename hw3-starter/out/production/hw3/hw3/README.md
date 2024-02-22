# Discussion

## PART I: MEASURED IndexedList

**Discuss from a design perspective whether iterating over a `MeasuredIndexedList`should 
affect the accesses and mutation counts. Note that for the purposes of this assignment we are NOT 
asking you to rewrite the `ArrayIndexedListIterator` to do so. However, if you wanted to include 
the `next()` and/or `hasNext()` methods in the statistics measured, can you inherit 
`ArrayIndexedListIterator` from `ArrayIndexedList` and override the relevant methods, or not? 
Explain.**

Iterating over a `MeasuredIndexedList` should increment the access and mutation counts.
As discussed in HW2, an efficient implementation of an iterator should have a constant
runtime for accessing each element. Using an iterator on a list of elements will then be
O(n), since there are n access operations.
We are not able to override these implementations, though, as the `ArrayIndexedListIterator`
is a private class within `ArrayIndexedList,` so when `ArrayIndexedList` is inherited from,
we cannot overwrite its inner classes.



## PART II: EXPERIMENTS

**Explain the mistake in the setup/implementation of the experiment which resulted in a discrepancy 
between the results and what is expected from each sorting algorithm.**
Insertion sort is able to tell if all of the data is sorted within one pass.
Bubble sort can also abort early if a swap is never triggered.
So, on partially sorted data, we would expect Insertion and Bubble sort to
be faster than Selection sort. We are sorting in ascending order. So, the worst case
for selection sort would be when the array is in descending order, but we note that this
is not the case.





## PART III: ANALYSIS OF SELECTION SORT

**Determine exactly how many comparisons C(n) and assignments A(n) are performed by this 
implementation of selection sort in the worst case. Both of those should be polynomials of degree 2 
since you know that the asymptotic complexity of selection sort is O(n^2).**
n = 100
descending: Selection Sort   true     10,000       100          0.000460
random.data:       Selection Sort   true     10,086       186          0.000443
