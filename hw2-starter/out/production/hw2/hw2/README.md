# Discussion

The `Roster` class uses `IndexedList` to store a list of students. The
`Roster.find` implements the binary search algorithm. Which
implementation of the `IndexedList` should be used to implement the
`Roster` class? (It could be one or more of `ArrayIndexedList`,
`LinkedIndexList`, `SparseIndexedList`). And why?
   
--------------- Write your answers below this line ----------------

We can only know for certain that it is not optimal to use 
a `SparseIndexedList`, as most of the values
added to the roster will not be default. Since we do
not know the relative proportions of using get versus
search, it is impossible to say which of `LinkedIndexedList`
ahd `ArrayIndexedList` will be optimal.