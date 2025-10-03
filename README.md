## Assignment 2: Heap Sort

* **Author:** Tulegenova Karina  
* **Group:** SE-2419

## Overview

**Algorithm Used:**  
Heap Sort with bottom-up heapify (in-place version).  
Implements binary max-heap construction for efficient sorting.

**Technologies:**  
Java 17, Maven, JUnit5.

**Tracked Metrics:**  
Comparisons, swaps, array accesses, memory allocations, execution time.

## Project Structure
<img width="352" height="504" alt="image" src="https://github.com/user-attachments/assets/e8eae573-1d7a-49a8-a478-d9d36b5bfafd" />

## How to Build & Run
1) mvn clean test
2) Run benchmark

## CSV
<img width="1171" height="670" alt="image" src="https://github.com/user-attachments/assets/f8d3b671-e864-4d1f-9650-ceb694497633" />
<img width="1177" height="588" alt="image" src="https://github.com/user-attachments/assets/54131029-a367-489c-a86a-5e03b8b987f0" />

### Explanation for the allocations

The allocations column is included as part of the required performance metrics for **Assignment 2**, which asks to track memory usage alongside other key operations (comparisons, swaps, accesses, etc.).
In the case of **Heap Sort**, all sorting operations are performed **in-place**, directly within the input array, without creating any additional arrays or objects during execution. As a result, **no dynamic memory allocation** occurs during the sorting process, which is why all values in the allocations column are **zero**.

This reflects one of the main advantages of Heap Sort:
> it is memory-efficient and does not require extra space for buffers or recursion stacks, unlike algorithms such as Merge Sort.

## Recurrence Analysis
HeapSort does not use recursion for sorting itself, but bottom-up heap construction runs in O(n) time, and the sorting phase runs in O(n log n) time due to successive heapify calls.
Heapify is called log n times per removal.
Total comparisons and swaps scale accordingly.
<img width="1319" height="539" alt="image" src="https://github.com/user-attachments/assets/783f51c5-1b2a-4e49-8c8d-4db3e8971b63" />
The graph shows how the runtime increases as the input size n grows from 100 to 100,000 elements. Each curve corresponds to a different input distribution:
> random,
> sorted,
> reversed,
> nearly sorted

The curves all show a consistent logarithmic growth pattern, which aligns well with Heap Sort’s expected complexity.
Minor variations between input types are due to constant factors such as the number of swaps or comparisons, but the overall trend remains Θ(n log n).

## Architecture Notes

* In-place array sorting

* Bottom-up heap construction improves initial build phase to O(n)

* Avoids recursion in sorting phase — uses iterative heapifyDown()

* Tracks key operations via PerformanceTracker

* CLI runner allows testing various input types and sizes

## JVM & Runtime Notes

* For small input sizes, JVM warm-up and GC may skew timings

* Tests include warm-up runs before measurements

* No extra memory is allocated — fully in-place

* Consistent behavior across sorted, reversed, and random arrays

## Summary

* Heap Sort shows reliable Θ(n log n) performance in all cases

* In-place and memory-efficient

* Bottom-up heapify improves build phase to linear time

* Metrics confirm theoretical complexity
