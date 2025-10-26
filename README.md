# **Optimization of a City Transportation Network**
### *Minimum Spanning Tree(MST) - Kruskal's vs Prim's Algorithm*

## **Introduction**
### **Objective**
The purpose of this assignment is to apply Prim’s and Kruskal’s algorithms to optimize a city’s transportation network by determining the minimum set of roads that connect all city districts with the lowest possible total construction cost. 
Also analyzing the efficiency of both algorithms and compare their performance.

### **Algorithm's Description**
**Kruskal's Algorithm** - is a greedy algorithm but takes a different approach. It begins with all the vertices and no edges, and it adds edges one by one in increasing order of weight, ensuring no cycles are formed until the MST is complete.

#### **Steps of Kruskal's Algorithm:**
1. **Initialization:** Sort edges by weight in non-decreasing order.

2. **Edge Selection:** Add the smallest edge to the MST if it does not create a cycle.

3. **Cycle Detection:** Use a union-find data structure to detect and prevent cycles.

4. **Repeat:** Continue adding edges until the MST contains exactly(v-1) edges.
-------------------------------------------------------------------------------------------
**Prim's Algorithm** -is also a greedy algorithm that builds the MST incrementally. It starts with a single vertex and grows the MST one edge at a time, always choosing the smallest edge that connects a vertex in the MST to a vertex outside the MST .

#### **Steps of Prim's Algorithm:**
1. **Initialization:** Start with an arbitrary vertex as part of the MST.

2. **Edge Selection:** Choose the minimum weight edge connecting MST vertices to non-MST vertices.

3. **Update:** Add the selected edge and new vertex to the MST.

4. **Repeat:** Continue until all vertices are included in the MST.

---------------------------------------------------------------------

### **Time Complexity Comparison**
#### **Kruskal's Algorithm**
Theorem:Algorithm kruskal finds a minimum cost spanning tree in a weighted undirected graph with m edges in **O(m log n)** time.

Proof:Since m can be as large as n(n− 1)/2 = Θ(n2), the time complexity expressed in terms of n is O(n2 log n). If the graph is planar, then m =
O(n) and hence the running time of the algorithm becomes **O(n log n)**.

#### **Prim's Algorithm**
Theorem:Algorithm prim finds a minimum cost spanning tree in a  weighted undirected graph with n vertices in **Θ(n2)** time.

Proof: The time complexity of the algorithm is Θ(m + n²), since selecting the minimum-weight edge in each iteration takes Θ(n²) time and scanning all edges takes Θ(m); therefore, in the worst case where m = O(n²), the total running time is **Θ(n²)**.

-----------------------------------------------------------------
### **Practical Performance**
#### **Kruskal's Algorithm**
- Performs well on sparse graphs because sorting fewer edges is faster.
- Union-Find operations (with path compression) are nearly constant time.
- Easier to implement when graph is given as an edge list.

#### **Prim's Algorithm**
- Performs well on dense graphs because adjacency list lookups are efficient when there are many edges per vertex.
- More efficient when the graph is represented as a priority queue + adjacency list.
- Implementation is slightly simpler when graph is already in adjacency form.
----------------------------------------------------------------------------
Here is a table summarizing the key differences between Prim's and Kruskal's Algorithms for finding the MST.

|Feature|Prim |Kruskal|
|-------|-----|-------|
|Approach|Vertex-based, grows the MST one vertex at a time|Edge-based, adds edges in increasing order of weight|
|Data Structure|Priority queue (min-heap)|Union-Find data structure|
|Suitable for|Dense graphs|Sparse graphs|
|Starting Point|Requires a starting vertex|No specific starting point, operates on global edges|
|Memory Usage|More memory for priority queue|Less memory if edges can be sorted externally|
|Example Use Cases|Network design, clustering with dense connections|Road networks, telecommunications with sparse connections|

-------------------------------------------------

## **Work Completed**
In this project, I performed the following steps:
1. **Prepared input data**
- Created and formated graph data in JSON files.
- Designed several graphs with different sized and densities.
2. **Developed core components**
- Implemented the **Graph**,**Edge**,and **GraphReader** classes.
- Implemented **Prim's** and **Kruskal's** algorithm classes.
3. **Testing and Validation**
- Wrote **AlgorithmsTest** class using JUnit.
- Verified that both algorithms produce the same total minimum cost.
4. **Performance Analysis**
- Collected runtime data for multiple graph sizes.
- Exported result into a CSV file for comparison.
- Optionally produced output.json file summarizing results.

## **Input data**
For testing, several graphs with different sizes and densities were used.

|Graph Type|Count| Vertices    | Edges  |
|-----------|--------|-------------|--------|
|Small|5| 5 < 30      | 1 < 50 |
|Medium|10| 30 < 300    | 1 < 50 |
|Large|10| 300 < 1000  | 1 < 50 |
|Extra Large|3| 1000 < 2000 | 1 < 50 |

## **Algorithm Results**
After running the automated JUnit tests and CsvExporter class, two files generated:
- src/test/resources/output.json - detailed graph results.
- src/test/resources/results.csv - summarized comparison table.
  
Each graph was processed by both Prim’s and Kruskal’s algorithms. The program measured:
- Total cost of the MST 
- Execution time (ms)
- Number of algorithmic operations (comparisons, unions, etc.)

## 📊 **Performance Results (Prim vs Kruskal)**

| graph_id | vertices | edges | prim_cost | kruskal_cost | prim_time_ms | kruskal_time_ms | prim_ops | kruskal_ops |
|-----------|-----------|--------|------------|---------------|----------------|------------------|-------------|--------------|
| 1 | 25 | 26 | 658 | 658 | 0.5862 ms | 0.6785 ms | 26 | 26 |
| 2 | 17 | 17 | 402 | 402 | 0.0370 ms | 0.0192 ms | 17 | 17 |
| 3 | 26 | 27 | 610 | 610 | 0.0759 ms | 0.0223 ms | 27 | 27 |
| 4 | 23 | 24 | 469 | 469 | 0.0563 ms | 0.0198 ms | 24 | 24 |
| 5 | 27 | 28 | 605 | 605 | 0.0804 ms | 0.0252 ms | 28 | 28 |
| 6 | 30 | 31 | 693 | 693 | 0.0978 ms | 0.0344 ms | 31 | 31 |
| 7 | 130 | 142 | 3351 | 3351 | 1.3732 ms | 0.1065 ms | 142 | 142 |
| 8 | 48 | 51 | 1237 | 1237 | 0.1488 ms | 0.0716 ms | 51 | 51 |
| 9 | 108 | 117 | 2607 | 2607 | 1.1900 ms | 0.0775 ms | 116 | 117 |
| 10 | 121 | 131 | 3072 | 3072 | 0.7679 ms | 0.0933 ms | 131 | 131 |
| 11 | 195 | 213 | 4569 | 4569 | 1.5260 ms | 0.1423 ms | 213 | 212 |
| 12 | 184 | 201 | 4355 | 4355 | 0.5238 ms | 0.1285 ms | 201 | 200 |
| 13 | 164 | 179 | 3779 | 3779 | 0.3373 ms | 0.1990 ms | 178 | 176 |
| 14 | 169 | 184 | 4050 | 4050 | 0.3048 ms | 0.1109 ms | 183 | 183 |
| 15 | 279 | 304 | 6781 | 6781 | 0.8057 ms | 0.1902 ms | 303 | 304 |
| 16 | 841 | 924 | 20219 | 20219 | 4.8251 ms | 0.6003 ms | 922 | 921 |
| 17 | 303 | 332 | 7107 | 7107 | 0.6416 ms | 0.3238 ms | 330 | 330 |
| 18 | 429 | 470 | 10056 | 10056 | 1.3199 ms | 0.3945 ms | 469 | 470 |
| 19 | 583 | 640 | 13745 | 13745 | 1.9713 ms | 0.3360 ms | 639 | 638 |
| 20 | 781 | 858 | 17524 | 17524 | 3.7538 ms | 0.4221 ms | 858 | 857 |
| 21 | 531 | 583 | 11884 | 11884 | 2.0943 ms | 0.7672 ms | 581 | 583 |
| 22 | 957 | 1051 | 22593 | 22593 | 4.0290 ms | 0.7865 ms | 1050 | 1048 |
| 23 | 913 | 1003 | 21741 | 21741 | 48.1901 ms | 0.7198 ms | 1002 | 1001 |
| 24 | 475 | 521 | 11054 | 11054 | 10.9100 ms | 0.1891 ms | 521 | 519 |
| 25 | 392 | 430 | 9265 | 9265 | 0.6914 ms | 0.1463 ms | 428 | 429 |
| 26 | 1438 | 1580 | 33789 | 33789 | 8.0427 ms | 0.4816 ms | 1578 | 1577 |
| 27 | 1288 | 1414 | 30271 | 30271 | 6.3017 ms | 0.4266 ms | 1414 | 1412 |
| 28 | 1417 | 1557 | 33817 | 33817 | 7.9040 ms | 0.8394 ms | 1557 | 1553 |

#### For all datasets correctness confirmed:
-  MST total cost identical for both algorithms
-  MST edges = *V − 1*
-  MST is acyclic (no cycles)
- MST connects all vertices (single connected component)
-  Execution time ≥ 0, operations ≥ 0  

---------------------------------------------------------------------------

##  **Conclusion**
Both Prim's and Kruskal's algorithms effectively enhanced the city's transportation network by generating the same Minimum Spanning Tree (MST) costs across all datasets, validating the accuracy of both implementations. Nevertheless, their efficiency and real-world performance differed based on the characteristics of the graph.

Prim's and Kruskal's algorithms are both powerful tools for finding the MST of a graph, each with its unique advantages. Prim's algorithm is typically preferred for dense graphs, leveraging its efficient priority queue-based approach, while Kruskal's algorithm excels in handling sparse graphs with its edge-sorting and union-find techniques. Understanding the structural differences and appropriate use cases for each algorithm ensures optimal performance in various graph-related problems.

## References
- Alsuwaiyel, M. H. (2016). *Algorithms: Design Techniques and Analysis (Lecture Notes Series on Computing, Vol. 14), 209-216.* algorithms-design-techniques-and-analysis.pdf

- GeeksforGeeks. (2025, July 12). *Difference between Prim’s and Kruskal’s algorithm for MST.* https://www.geeksforgeeks.org/dsa/difference-between-prims-and-kruskals-algorithm-for-mst/
- Sedgewick, R., & Wayne, K. (2011). *Algorithms (4th edition),616-627*. Robert_Sedgewick_and_Kevin_Wayne_Algorithms,_4th_edition.pdf

