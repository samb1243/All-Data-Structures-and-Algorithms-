/**
 * MaxHeap.java
 * 
 * A max-heap backed by an array.
 * 
 * Key properties (from slides):
 *   - Complete binary tree
 *   - Every parent >= its children (heap-order property)
 *   - Root always holds the maximum value
 * 
 * Array index formulas:
 *   - Root        -> index 0
 *   - Left child  -> 2i + 1
 *   - Right child -> 2i + 2
 *   - Parent      -> (i - 1) / 2
 */
public class MaxHeap {

    private int[] data;   // backing array
    private int size;     // number of elements currently in the heap

    // -------------------------------------------------------------------------
    // Constructor
    // -------------------------------------------------------------------------

    public MaxHeap(int capacity) {
        data = new int[capacity];
        size = 0;
    }

    // -------------------------------------------------------------------------
    // Helper: index arithmetic
    // -------------------------------------------------------------------------

    private int parent(int i)    { return (i - 1) / 2; }
    private int leftChild(int i) { return 2 * i + 1;   }
    private int rightChild(int i){ return 2 * i + 2;   }

    private boolean hasParent(int i)     { return i > 0;               }
    private boolean hasLeftChild(int i)  { return leftChild(i) < size;  }
    private boolean hasRightChild(int i) { return rightChild(i) < size; }

    private void swap(int i, int j) {
        int temp = data[i];
        data[i]  = data[j];
        data[j]  = temp;
    }

    // -------------------------------------------------------------------------
    // findMax  –  O(1)
    // Returns the maximum element (always at the root).
    // -------------------------------------------------------------------------

    public int findMax() {
        if (size == 0) throw new IllegalStateException("Heap is empty");
        return data[0];
    }

    // -------------------------------------------------------------------------
    // insert  –  O(log n)
    // 
    // 1. Place the new value at the next available position (end of array).
    // 2. SWIM: repeatedly swap it upward with its parent while it is larger.
    // -------------------------------------------------------------------------

    public void insert(int value) {
        if (size == data.length) throw new IllegalStateException("Heap is full");

        data[size] = value;  // place at bottom-right
        size++;
        swim(size - 1);      // restore heap-order property
    }

    /**
     * SWIM: move the node at index i upward until the heap-order property holds.
     * Called after insert.
     */
    private void swim(int i) {
        while (hasParent(i) && data[i] > data[parent(i)]) {
            swap(i, parent(i));
            i = parent(i);
        }
    }

    // -------------------------------------------------------------------------
    // deleteMax  –  O(log n)
    // 
    // 1. Save and return the root (maximum).
    // 2. Move the rightmost leaf to the root (preserves completeness).
    // 3. SINK: repeatedly swap the new root downward with its larger child
    //    while it is smaller than that child.
    // -------------------------------------------------------------------------

    public int deleteMax() {
        if (size == 0) throw new IllegalStateException("Heap is empty");

        int max = data[0];          // save the maximum
        data[0] = data[size - 1];   // move last element to root
        size--;
        sink(0);                    // restore heap-order property
        return max;
    }

    /**
     * SINK: move the node at index i downward until the heap-order property holds.
     * Called after deleteMax and during heapify.
     */
    private void sink(int i) {
        while (hasLeftChild(i)) {
            // Find the larger child
            int larger = leftChild(i);
            if (hasRightChild(i) && data[rightChild(i)] > data[leftChild(i)]) {
                larger = rightChild(i);
            }

            // Stop if parent is already >= the larger child
            if (data[i] >= data[larger]) break;

            swap(i, larger);
            i = larger;
        }
    }

    // -------------------------------------------------------------------------
    // heapify  –  O(n)
    // 
    // Build a heap from an arbitrary array using the bottom-up approach.
    // Process every internal node in reverse level-order (right-to-left,
    // bottom-to-top) and sink each one.
    // External (leaf) nodes are valid single-element heaps already.
    // -------------------------------------------------------------------------

    public static MaxHeap heapify(int[] array) {
        MaxHeap heap = new MaxHeap(array.length);
        System.arraycopy(array, 0, heap.data, 0, array.length);
        heap.size = array.length;

        // Last internal node is at index (size/2 - 1)
        for (int i = heap.size / 2 - 1; i >= 0; i--) {
            heap.sink(i);
        }
        return heap;
    }

    // -------------------------------------------------------------------------
    // heapSort  –  O(n log n), in-place
    // 
    // 1. Build a max-heap from the input array (bottom-up heapify).
    // 2. Repeatedly swap the root (max) with the last unsorted element,
    //    shrink the heap by 1, then sink the new root.
    // Result: array sorted in ascending order.
    // -------------------------------------------------------------------------

    public static void heapSort(int[] array) {
        // Phase 1: build max-heap in-place
        // (same as heapify but operating directly on the array)
        int n = array.length;
        for (int i = n / 2 - 1; i >= 0; i--) {
            sinkInArray(array, i, n);
        }

        // Phase 2: sort by repeatedly extracting the max
        for (int end = n - 1; end > 0; end--) {
            // Swap root (current max) into its final sorted position
            int temp   = array[0];
            array[0]   = array[end];
            array[end] = temp;

            // Sink the new root within the reduced heap
            sinkInArray(array, 0, end);
        }
    }

    /** Sink helper for heapSort that operates directly on a plain array. */
    private static void sinkInArray(int[] array, int i, int heapSize) {
        while (true) {
            int left   = 2 * i + 1;
            int right  = 2 * i + 2;
            int larger = i;

            if (left  < heapSize && array[left]  > array[larger]) larger = left;
            if (right < heapSize && array[right] > array[larger]) larger = right;

            if (larger == i) break;   // heap-order satisfied

            int temp        = array[i];
            array[i]        = array[larger];
            array[larger]   = temp;
            i               = larger;
        }
    }

    // -------------------------------------------------------------------------
    // Utility: print the heap as an array and as a tree
    // -------------------------------------------------------------------------

    public void printArray() {
        System.out.print("Heap (array): [ ");
        for (int i = 0; i < size; i++) {
            System.out.print(data[i] + (i < size - 1 ? ", " : ""));
        }
        System.out.println(" ]");
    }

    /**
     * Prints a simple level-order tree view of the heap.
     * Each level is printed on a new line.
     */
    public void printTree() {
        System.out.println("Heap (level-order tree):");
        int level = 0;
        int levelSize = 1;
        int i = 0;
        while (i < size) {
            System.out.print("  Level " + level + ": ");
            for (int j = 0; j < levelSize && i < size; j++, i++) {
                System.out.print(data[i] + " ");
            }
            System.out.println();
            level++;
            levelSize *= 2;
        }
    }

    // -------------------------------------------------------------------------
    // main – worked example matching the slides
    // -------------------------------------------------------------------------

    public static void main(String[] args) {

        System.out.println("=== Example 1: Insert (swim) ===");
        // Recreates the heap from the slides: 23, 12, 16, 7, 9, 13, 5, 1, 3, 2
        MaxHeap h = new MaxHeap(20);
        int[] initial = {23, 12, 16, 7, 9, 13, 5, 1, 3, 2};
        for (int v : initial) h.insert(v);
        h.printArray();
        h.printTree();

        System.out.println("\nInserting 20 (should swim up past 12)...");
        h.insert(20);
        h.printArray();
        h.printTree();

        System.out.println("\n=== Example 2: Delete max (sink) ===");
        int removed = h.deleteMax();
        System.out.println("Removed max: " + removed);
        h.printArray();
        h.printTree();

        System.out.println("\n=== Example 3: Bottom-up heapify ===");
        // Matches the slides example: 6, 4, 17, 12, 1, 13, 6, 11, 22, 3
        int[] unordered = {6, 4, 17, 12, 1, 13, 6, 11, 22, 3};
        System.out.print("Input: ");
        printArray(unordered);
        MaxHeap built = MaxHeap.heapify(unordered);
        built.printArray();
        built.printTree();
        System.out.println("Max element: " + built.findMax());

        System.out.println("\n=== Example 4: Heapsort (in-place) ===");
        int[] toSort = {4, 11, 3, 6, 12, 1};
        System.out.print("Before sort: ");
        printArray(toSort);
        MaxHeap.heapSort(toSort);
        System.out.print("After sort:  ");
        printArray(toSort);

        System.out.println("\n=== Example 5: Priority queue (dequeue in order) ===");
        MaxHeap pq = new MaxHeap(10);
        int[] tasks = {7, 3, 15, 1, 9, 4};
        System.out.print("Task priorities added: ");
        printArray(tasks);
        for (int t : tasks) pq.insert(t);
        System.out.print("Dequeue order (highest first): ");
        while (pq.size > 0) {
            System.out.print(pq.deleteMax() + " ");
        }
        System.out.println();
    }

    /** Small static helper to print a plain int array. */
    private static void printArray(int[] arr) {
        System.out.print("[ ");
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + (i < arr.length - 1 ? ", " : ""));
        }
        System.out.println(" ]");
    }
}