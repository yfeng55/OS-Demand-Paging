# Demand Paging Simulation 

Simulates demand paging on a system for a set of processes and resources; uses FIFO, Random, and LRU page replacement methods.

##### Command Line Arguments 

- M, the machine size in words
- P, the page size in words
- S, the size of a process, i.e., the references are to virtual addresses 0..S-1
- J, the ‘‘job mix’’, which determines A, B, and C
- N, the number of references for each process
- R, the replacement algorithm, FIFO, RANDOM, or LRU
- D, the debug level (0 for none, 1 for debug, 11 for debug + random)


```
javac *.java
java Main [M] [P] [S] [J] [N] [R] [D]
```













