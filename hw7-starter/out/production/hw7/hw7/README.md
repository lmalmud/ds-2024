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
up deciding on a bucket size of four.

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

```
Space it takes to build `ChainingHashMap`...
```

```

Time it takes to build `OpenAddressingHashMap`...
```

```
Space it takes to build `OpenAddressingHashMap...`
```

```