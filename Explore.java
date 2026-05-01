public class Explore {
    public static void main(String[] args) {
        java.util.Scanner scanner = new java.util.Scanner(System.in);
        while (true) {
            System.out.println();
            System.out.println("=== All Data Structures & Algorithms — Explorer ===");
            System.out.println("Choose an algorithm or data structure to run (enter number):");
            System.out.println(" 1) Bubble Sort");
            System.out.println(" 2) Insertion Sort");
            System.out.println(" 3) Selection Sort");
            System.out.println(" 4) Quick Sort");
            System.out.println(" 5) Merge Sort");
            System.out.println(" 6) Radix Sort");
            System.out.println(" 7) Stable Sort");
            System.out.println(" 8) Coin Flipping Simulation (default 10,100)");
            System.out.println(" 9) Max Heap demo");
            System.out.println("10) Graphs demo");
            System.out.println("11) List demo");
            System.out.println("12) Queue demo");
            System.out.println("13) Stack demo");
            System.out.println("14) Tree demo");
            System.out.println("15) AVLTree demo (basic inserts)");
            System.out.println("16) Recurrence (default n=5)");
            System.out.println(" 0) Exit");
            System.out.print("Selection: ");

            String line = scanner.nextLine().trim();
            if (line.isEmpty()) continue;
            int choice;
            try {
                choice = Integer.parseInt(line);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Enter a number.");
                continue;
            }

            switch (choice) {
                case 0:
                    System.out.println("Goodbye.");
                    scanner.close();
                    return;
                case 1:
                    runBubbleSort();
                    break;
                case 2:
                    runInsertionSort();
                    break;
                case 3:
                    runSelectionSort();
                    break;
                case 4:
                    runQuickSort();
                    break;
                case 5:
                    runMergeSort();
                    break;
                case 6:
                    runRadixSort();
                    break;
                case 7:
                    runStableSort();
                    break;
                case 8:
                    runCoinFlipDefault();
                    break;
                case 9:
                    MaxHeap.main(new String[0]);
                    break;
                case 10:
                    Graphs.main(new String[0]);
                    break;
                case 11:
                    List.main(new String[0]);
                    break;
                case 12:
                    runQueueDemo();
                    break;
                case 13:
                    runStackDemo();
                    break;
                case 14:
                    runTreeDemo();
                    break;
                case 15:
                    runAVLDemo();
                    break;
                case 16:
                    runRecurrenceDefault();
                    break;
                default:
                    System.out.println("Choice not recognised.");
            }
            System.out.println("--- Press Enter to continue ---");
            scanner.nextLine();
        }
    }

    private static void runBubbleSort() {
        int[] arr = {5, 3, 8, 1, 2};
        BubbleSort.bubbleSort(arr);
        printArray(arr);
    }

    private static void runInsertionSort() {
        int[] arr = {5, 3, 8, 1, 2};
        InsertionSort.insertionSort(arr);
        printArray(arr);
    }

    private static void runSelectionSort() {
        int[] arr = {5, 3, 8, 1, 2};
        SelectionSort.selectionSort(arr);
        printArray(arr);
    }

    private static void runQuickSort() {
        int[] arr = {5, 3, 8, 1, 2};
        QuickSort.quickSort(arr, 0, arr.length - 1);
        printArray(arr);
    }

    private static void runMergeSort() {
        int[] arr = {5, 3, 8, 1, 2};
        MergeSort.mergeSort(arr, 0, arr.length - 1);
        printArray(arr);
    }

    private static void runRadixSort() {
        int[] arr = {5, 3, 8, 1, 2};
        RadixSort.radixSort(arr);
        printArray(arr);
    }

    private static void runStableSort() {
        int[] arr = {5, 3, 8, 1, 2};
        StableSort.stableSort(arr);
        printArray(arr);
    }

    private static void runCoinFlipDefault() {
        try {
            String[] a = {"10", "100"};
            CoinFlippingSimulation.main(a);
        } catch (Exception e) {
            System.out.println("CoinFlippingSimulation failed: " + e.getMessage());
        }
    }

    private static void runQueueDemo() {
        Queue q = new Queue(5);
        q.enqueue(1);
        q.enqueue(2);
        q.enqueue(3);
        System.out.print("Dequeued: ");
        System.out.println(q.dequeue());
    }

    private static void runStackDemo() {
        Stack s = new Stack(5);
        s.push(10);
        s.push(20);
        System.out.print("Popped: ");
        System.out.println(s.pop());
    }

    private static void runTreeDemo() {
        Tree t = new Tree();
        t.insert(5);
        t.insert(3);
        t.insert(7);
        t.insert(1);
        t.insert(4);
        System.out.print("Inorder traversal: ");
        t.inorder();
        System.out.println();
    }

    private static void runAVLDemo() {
        AVLTree avl = new AVLTree();
        avl.insert(5);
        avl.insert(3);
        avl.insert(7);
        avl.insert(2);
        avl.insert(4);
        System.out.println("Inserted sample values into AVLTree (no public traversal available).");
    }

    private static void runRecurrenceDefault() {
        try {
            String[] a = {"5"};
            Recurrence.main(a);
        } catch (Exception e) {
            System.out.println("Recurrence failed: " + e.getMessage());
        }
    }

    private static void printArray(int[] arr) {
        System.out.print("Result: ");
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + (i < arr.length - 1 ? " " : ""));
        }
        System.out.println();
    }
}