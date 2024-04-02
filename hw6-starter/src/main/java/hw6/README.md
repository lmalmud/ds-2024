# Discussion

## Unit testing TreapMap
The most obvious difficulty was in generating test cases that were in accordance
with the randomly generated values. I created a separate constructor that takes a seed
value as a parameter and made that constructor output the first few randomly generated
integers based on that seed value. I then kept those values in mind as I constructed
the test cases.

For example, I wanted to test a left rotation so I generated three values
for the seed of 2: -1154715079, 1260042744, -423279216. I then realized
that if I wanted to trigger a left rotation, I would have to insert the nodes
in an order such that the value of each key was greater than the value
of the previous key.
```
a
 \
  b
   \
    c
```
Since the priority of b is greater than the priority of c, there will be
a left rotation triggered at b resulting in the tree:
```
a
 \
  b
 /
c
```
Similarly, I wanted to test a rotation triggered by a removal. When generating
values with a seed of 4, I received: -1157023572, -396984392, -349120689. These
priorities were already in ascending orders. I knew that given these priorities,
the nodes would have to be inserted in the order b, d, a if I wanted to trigger a left
rotation at the node containing b.
```
  b
 / \
a   d
```
Since, upon replacement, we use the maximum element in the left subtree,
the root will now have a higher priority than its right child, thus
causing a left rotation.
```
  d
 /
a
```
Something that was just slightly annoying is that if I forgot to comment out the
loop that printed out the first few randomly generated issues then the test cases would fail,
because the sequence of integers would be offset.

## Benchmarking
```
hotel_california.txt
Benchmark                  Mode  Cnt  Score   Error  Units
JmhRuntimeTest.arrayMap    avgt    2  0.168          ms/op
JmhRuntimeTest.avlTreeMap  avgt    2  0.123          ms/op
JmhRuntimeTest.bstMap      avgt    2  0.119          ms/op
JmhRuntimeTest.treapMap    avgt    2  0.142          ms/op

federalist01.txt
Benchmark                  Mode  Cnt  Score   Error  Units
JmhRuntimeTest.arrayMap    avgt    2  1.629          ms/op
JmhRuntimeTest.avlTreeMap  avgt    2  0.669          ms/op
JmhRuntimeTest.bstMap      avgt    2  0.566          ms/op
JmhRuntimeTest.treapMap    avgt    2  0.773          ms/op

moby_dick.txt
Benchmark                  Mode  Cnt     Score   Error  Units
JmhRuntimeTest.arrayMap    avgt    2  2213.605          ms/op
JmhRuntimeTest.avlTreeMap  avgt    2    97.027          ms/op
JmhRuntimeTest.bstMap      avgt    2   100.441          ms/op
JmhRuntimeTest.treapMap    avgt    2   119.348          ms/op

pride_and_prejudice.txt
Benchmark                  Mode  Cnt    Score   Error  Units
JmhRuntimeTest.arrayMap    avgt    2  478.697          ms/op
JmhRuntimeTest.avlTreeMap  avgt    2   52.419          ms/op
JmhRuntimeTest.bstMap      avgt    2   52.616          ms/op
JmhRuntimeTest.treapMap    avgt    2   63.294          ms/op
```

We see that performance, from best to worst, is as follows:
1. avlTreeMap / bstTreeMap
3. treapMap
4. arrayMap

Clearly, the array map performs quite poorly due to the linear search required to
find elements. The AVL
tree and BST have comparable performances because they are both sorted. The AVL
has the best performance on datasets that are overall larger because the added time
of balancing the tree ends up outweighing the time to maintain that balance, where
this effect is not as prominent in the smaller datasets. For AVLs,
is guaranteed that there will never need to be a traversal of length more than `O(lg(n))`,
since there is a balanced structure of the tree that is maintained. A BST performs better
than a treap in this instance because, the extra work done by assigning random priorities
does not compensate for the extra swapping that is required to maintain the priority. Since the
text is extracted from literature, it is not often in entirely ascending or descending order.

In conclusion, it is important to note that, based on the nature of the dataset, the extra
time and space required to maintain the balance of the AVL tree/generate random priorities
did not necessarily compensate for the gained efficiency.
