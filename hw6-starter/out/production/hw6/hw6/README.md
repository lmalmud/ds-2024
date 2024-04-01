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
JmhRuntimeTest.arrayMap    avgt    2  0.163          ms/op
JmhRuntimeTest.avlTreeMap  avgt    2  0.165          ms/op
JmhRuntimeTest.bstMap      avgt    2  0.117          ms/op
JmhRuntimeTest.treapMap    avgt    2  0.147          ms/op

federalist01.txt
Benchmark                  Mode  Cnt  Score   Error  Units
JmhRuntimeTest.arrayMap    avgt    2  1.581          ms/op
JmhRuntimeTest.avlTreeMap  avgt    2  1.367          ms/op
JmhRuntimeTest.bstMap      avgt    2  0.565          ms/op
JmhRuntimeTest.treapMap    avgt    2  0.762          ms/op

moby_dick.txt
Benchmark                  Mode  Cnt     Score   Error  Units
JmhRuntimeTest.arrayMap    avgt    2  2231.525          ms/op
JmhRuntimeTest.avlTreeMap  avgt    2   717.296          ms/op
JmhRuntimeTest.bstMap      avgt    2    95.278          ms/op
JmhRuntimeTest.treapMap    avgt    2   111.734          ms/op

pride_and_prejudice.txt
Benchmark                  Mode  Cnt    Score   Error  Units
JmhRuntimeTest.arrayMap    avgt    2  461.729          ms/op
JmhRuntimeTest.avlTreeMap  avgt    2  132.814          ms/op
JmhRuntimeTest.bstMap      avgt    2   52.357          ms/op
JmhRuntimeTest.treapMap    avgt    2   61.358          ms/op
```

We see that performance, from best to worst, is as follows:
1. bstMap
2. treapMap
3. avlTreeMap
4. arrayMap

Clearly, the array map performs quite poorly due to the linear search.
We find that the treap performs better than the avl tree because...