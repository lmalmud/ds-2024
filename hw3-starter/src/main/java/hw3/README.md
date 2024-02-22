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
Insertion sort is able to tell if all data is sorted within one pass.
Bubble sort can also abort early if a swap is never triggered.
So, on partially sorted data, we would expect Insertion and Bubble sort to
be faster than Selection sort. This does appear to be consistent with our data.

But, we are sorting in ascending order.
So, the worst case for selection sort would be when the array is in descending order, this is not the case.
When n = 100, for descending data there are 10,000 accesses and 100 mutations as opposed to
10,086 accesses and 186 mutations for random data. Similarly, when n = 4000, there are 16,000,000 accesses
and 4,000 mutations for descending data which is less than than the 16,003,992 accesses and 7,9992 mutations
for randomly generated data.




## PART III: ANALYSIS OF SELECTION SORT

**Determine exactly how many comparisons C(n) and assignments A(n) are performed by this 
implementation of selection sort in the worst case. Both of those should be polynomials of degree 2 
since you know that the asymptotic complexity of selection sort is O(n^2).**
The worst case would be descending order.
The outer loop would run length(A). The inner loop would run from i to 0. Each iteration
of the inner loop has two comparisons, and each iteration of the outer loop has one comparison.
Each iteration of the outer loop has 4 assignments, and each iteration of the inner loop has 2 assignments.
number iterations = summation(i = 1, ... length(A)) ( summation(j = i, ... 0 ) ) = summation(i = 2, ... n) (i)
C(n) = 3(n(n+1)/2)
A(n) = 8(n(n+1)/2)