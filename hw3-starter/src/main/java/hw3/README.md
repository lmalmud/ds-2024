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
for randomly generated data. Additionally, upon inspection, the descending
data is not actually entirely in descending order.

Additionally, the list items are processed as strings (for example, "41" not 41) , so algorithms that have more compairsons
will be more expensive.




## PART III: ANALYSIS OF SELECTION SORT

**Determine exactly how many comparisons C(n) and assignments A(n) are performed by this 
implementation of selection sort in the worst case. Both of those should be polynomials of degree 2 
since you know that the asymptotic complexity of selection sort is O(n^2).**

### C(n) = n(n-1)/2
- The outer loop will execute n - 1 times, with one comparison each time (line 3)
- In the worst case, the inner loop also executes from 1, ..., n - 1 times, where it checks the condition each time
- We can use Gauss' formula: n(n-1)/2

### A(n) = 4(n-1) + n(n-1)/2
- The outer loop will run n-1 times, executing 4 assignments each time, contributing 4(n-1) assignment operations
- In each iteration of the inner loop, since we are assuming that the condition is true, 
we will have one assignment operation. We use Gauss' formula once more: n(n-1)/2

