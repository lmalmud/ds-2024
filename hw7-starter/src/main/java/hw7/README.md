# Discussion
For my implementation of `OpenAddressingHashMap`, I used a linear probing strategy. In testing, I noticed
that clumping was occurring but did not want to have to deal with the implementation difficulties of implementing
something like quadratic sorting, where it is possible to wrap many times around the underlying array in order to
find the target element.

For rehashing in `OpenAddresisngHashMap`, I used a load factor of `.75`. I found that the most
readable way to implement `rehash()` would be to use the `insert()` function. One debugging challenge
was noticing that I had to manually reset the number of elements, as it is incremented within the `insert()` function.
For `ChainingHashMap`, I needed a different metric to decide when to rehash, as there are no tombstones. I decided
to create a maximum bucket size, and rehash the entire table whenever any bucket reached the maximum size allowed. I ended
up deciding on a bucket size of six.

For time complexity, `ChainingHashMap` was faster in all tests but one. This is because we can guarantee
that you will never have to check more than the `maxBucketSize` number of nodes to find the value. For
`OpenAddressingHashMap`, it is possible (albeit unlikely) that the entire underlying array will have to
be checked in order to find an element.

For space complexity, it is important to note that I chose to use a dummy node at the beginning
of each bucket in the `ChainingHashMap`. This was for ease of implementation, as to not have to check if any entries were
null. Additionally, each node has to store its own key and value and also a reference to the following node.
This resulted in fairly consistently higher space complexity for the `ChainingHashMap` than the `OpenAddressingHashMap`.

In conclusion, I choose `ChainingHashMap` for `JHUgle`, since the main objective is to perform queries.
Therefore, it is more important to have efficient searching that may be easily regulated with the
`maxBucketSize` parameter.

Time it takes to build `JDKHashMap`...
```
Benchmark(fileName)  Mode  Cnt           Score   Error   Units
JmhRuntimeTest.buildSearchEngine                                                         apache.txt  avgt    2         124.569           ms/op
JmhRuntimeTest.buildSearchEngine                                                            jhu.txt  avgt    2           0.135           ms/op
JmhRuntimeTest.buildSearchEngine                                                         joanne.txt  avgt    2           0.055           ms/op
JmhRuntimeTest.buildSearchEngine                                                         newegg.txt  avgt    2          60.916           ms/op
JmhRuntimeTest.buildSearchEngine                                                      random164.txt  avgt    2         466.851           ms/op
JmhRuntimeTest.buildSearchEngine                                                           urls.txt  avgt    2           0.017           ms/op
```
Space it takes to build `JDKHashMap`...
```
JmhRuntimeTest.buildSearchEngine:+c2k.gc.maximumUsedAfterGc                              apache.txt  avgt    2   186415428.000           bytes
JmhRuntimeTest.buildSearchEngine:+c2k.gc.maximumUsedAfterGc                                 jhu.txt  avgt    2    28102932.000           bytes
JmhRuntimeTest.buildSearchEngine:+c2k.gc.maximumUsedAfterGc                              joanne.txt  avgt    2    28130624.000           bytes
JmhRuntimeTest.buildSearchEngine:+c2k.gc.maximumUsedAfterGc                              newegg.txt  avgt    2    69476876.000           bytes
JmhRuntimeTest.buildSearchEngine:+c2k.gc.maximumUsedAfterGc                           random164.txt  avgt    2   621634280.000           bytes
JmhRuntimeTest.buildSearchEngine:+c2k.gc.maximumUsedAfterGc                                urls.txt  avgt    2    26939288.000           bytes
```
Time it takes to build `ChainingHashMap`...
```
JmhRuntimeTest.buildSearchEngine                                                         apache.txt  avgt    2         122.773           ms/op
JmhRuntimeTest.buildSearchEngine                                                            jhu.txt  avgt    2           0.132           ms/op
JmhRuntimeTest.buildSearchEngine                                                         joanne.txt  avgt    2           0.058           ms/op
JmhRuntimeTest.buildSearchEngine                                                         newegg.txt  avgt    2          65.230           ms/op
JmhRuntimeTest.buildSearchEngine                                                      random164.txt  avgt    2         472.384           ms/op
JmhRuntimeTest.buildSearchEngine                                                           urls.txt  avgt    2           0.017           ms/op

```
Space it takes to build `ChainingHashMap`...
```
JmhRuntimeTest.buildSearchEngine:+c2k.gc.maximumUsedAfterGc                              apache.txt  avgt    2   191260192.000           bytes
JmhRuntimeTest.buildSearchEngine:+c2k.gc.maximumUsedAfterGc                                 jhu.txt  avgt    2    28372984.000           bytes
JmhRuntimeTest.buildSearchEngine:+c2k.gc.maximumUsedAfterGc                              joanne.txt  avgt    2    28095660.000           bytes
JmhRuntimeTest.buildSearchEngine:+c2k.gc.maximumUsedAfterGc                              newegg.txt  avgt    2    76000652.000           bytes
JmhRuntimeTest.buildSearchEngine:+c2k.gc.maximumUsedAfterGc                           random164.txt  avgt    2   670987256.000           bytes
JmhRuntimeTest.buildSearchEngine:+c2k.gc.maximumUsedAfterGc                                urls.txt  avgt    2    26893948.000           bytes

```

Time it takes to build `OpenAddressingHashMap`...
```
JmhRuntimeTest.buildSearchEngine                                                         apache.txt  avgt    2         126.636           ms/op
JmhRuntimeTest.buildSearchEngine                                                            jhu.txt  avgt    2           0.140           ms/op
JmhRuntimeTest.buildSearchEngine                                                         joanne.txt  avgt    2           0.055           ms/op
JmhRuntimeTest.buildSearchEngine                                                         newegg.txt  avgt    2          65.329           ms/op
JmhRuntimeTest.buildSearchEngine                                                      random164.txt  avgt    2         497.877           ms/op
JmhRuntimeTest.buildSearchEngine                                                           urls.txt  avgt    2           0.017           ms/op

```
Space it takes to build `OpenAddressingHashMap...`
```
JmhRuntimeTest.buildSearchEngine:+c2k.gc.maximumUsedAfterGc                              apache.txt  avgt    2   126322964.000           bytes
JmhRuntimeTest.buildSearchEngine:+c2k.gc.maximumUsedAfterGc                                 jhu.txt  avgt    2    28139096.000           bytes
JmhRuntimeTest.buildSearchEngine:+c2k.gc.maximumUsedAfterGc                              joanne.txt  avgt    2    28322968.000           bytes
JmhRuntimeTest.buildSearchEngine:+c2k.gc.maximumUsedAfterGc                              newegg.txt  avgt    2    69472544.000           bytes
JmhRuntimeTest.buildSearchEngine:+c2k.gc.maximumUsedAfterGc                           random164.txt  avgt    2   630245280.000           bytes
JmhRuntimeTest.buildSearchEngine:+c2k.gc.maximumUsedAfterGc                                urls.txt  avgt    2    27022080.000           bytes
```