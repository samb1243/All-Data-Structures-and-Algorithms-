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

            if (!scanner.hasNextLine()) {
                System.out.println("No input available. Exiting.");
                scanner.close();
                return;
            }
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
                    runBubbleSort(scanner);
                    break;
                case 2:
                    runInsertionSort(scanner);
                    break;
                case 3:
                    runSelectionSort(scanner);
                    break;
                case 4:
                    runQuickSort(scanner);
                    break;
                case 5:
                    runMergeSort(scanner);
                    break;
                case 6:
                    runRadixSort(scanner);
                    break;
                case 7:
                    runStableSort(scanner);
                    break;
                case 8:
                    runCoinFlip(scanner);
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
                    runQueueDemo(scanner);
                    break;
                case 13:
                    runStackDemo(scanner);
                    break;
                case 14:
                    runTreeDemo(scanner);
                    break;
                case 15:
                    runAVLDemo(scanner);
                    break;
                case 16:
                    runRecurrence(scanner);
                    break;
                default:
                    System.out.println("Choice not recognised.");
            }
            System.out.println("--- Press Enter to continue ---");
            scanner.nextLine();
        }
    }

    private static void runBubbleSort(java.util.Scanner scanner) {
        int[] def = {5, 3, 8, 1, 2};
        int[] arr = readIntArray(scanner, def, "Enter integers for Bubble Sort (space or comma separated), or press Enter for default:");
        BubbleSort.bubbleSort(arr);
        printArray(arr);
    }

    private static void runInsertionSort(java.util.Scanner scanner) {
        int[] def = {5, 3, 8, 1, 2};
        int[] arr = readIntArray(scanner, def, "Enter integers for Insertion Sort (space or comma separated), or press Enter for default:");
        InsertionSort.insertionSort(arr);
        printArray(arr);
    }

    private static void runSelectionSort(java.util.Scanner scanner) {
        int[] def = {5, 3, 8, 1, 2};
        int[] arr = readIntArray(scanner, def, "Enter integers for Selection Sort (space or comma separated), or press Enter for default:");
        SelectionSort.selectionSort(arr);
        printArray(arr);
    }

    private static void runQuickSort(java.util.Scanner scanner) {
        int[] def = {5, 3, 8, 1, 2};
        int[] arr = readIntArray(scanner, def, "Enter integers for Quick Sort (space or comma separated), or press Enter for default:");
        QuickSort.quickSort(arr, 0, arr.length - 1);
        printArray(arr);
    }

    private static void runMergeSort(java.util.Scanner scanner) {
        int[] def = {5, 3, 8, 1, 2};
        int[] arr = readIntArray(scanner, def, "Enter integers for Merge Sort (space or comma separated), or press Enter for default:");
        MergeSort.mergeSort(arr, 0, arr.length - 1);
        printArray(arr);
    }

    private static void runRadixSort(java.util.Scanner scanner) {
        int[] def = {5, 3, 8, 1, 2};
        int[] arr = readIntArray(scanner, def, "Enter non-negative integers for Radix Sort (space or comma separated), or press Enter for default:");
        RadixSort.radixSort(arr);
        printArray(arr);
    }

    private static void runStableSort(java.util.Scanner scanner) {
        int[] def = {5, 3, 8, 1, 2};
        int[] arr = readIntArray(scanner, def, "Enter integers (0-9) for Stable Sort (space or comma separated), or press Enter for default:");
        StableSort.stableSort(arr);
        printArray(arr);
    }

    private static void runCoinFlip(java.util.Scanner scanner) {
        try {
            System.out.println("Enter n m for Coin Flipping Simulation (n flips per trial, m trials), or press Enter for defaults 10 100:");
            String line = scanner.nextLine().trim();
            String[] a;
            if (line.isEmpty()) {
                a = new String[]{"10", "100"};
            } else {
                String[] parts = line.split("\\s+");
                if (parts.length >= 2) a = new String[]{parts[0], parts[1]};
                else {
                    System.out.println("Need two integers; using defaults.");
                    a = new String[]{"10", "100"};
                }
            }
            CoinFlippingSimulation.main(a);
        } catch (Exception e) {
            System.out.println("CoinFlippingSimulation failed: " + e.getMessage());
        }
    }

    private static void runQueueDemo(java.util.Scanner scanner) {
        System.out.println("Enter integers to enqueue into Queue (space or comma separated), or press Enter for default 1 2 3:");
        int[] arr = readIntArray(scanner, new int[]{1,2,3}, "");
        Queue q = new Queue(Math.max(5, arr.length + 2));
        for (int v : arr) q.enqueue(v);
        System.out.print("Dequeued: ");
        System.out.println(q.dequeue());
    }

    private static void runStackDemo(java.util.Scanner scanner) {
        System.out.println("Enter integers to push onto Stack (space or comma separated), or press Enter for default 10 20:");
        int[] arr = readIntArray(scanner, new int[]{10,20}, "");
        Stack s = new Stack(Math.max(5, arr.length + 2));
        for (int v : arr) s.push(v);
        System.out.print("Popped: ");
        System.out.println(s.pop());
    }

    private static void runTreeDemo(java.util.Scanner scanner) {
        System.out.println("Enter integers to insert into Tree (space or comma separated), or press Enter for default 5 3 7 1 4:");
        int[] arr = readIntArray(scanner, new int[]{5,3,7,1,4}, "");
        Tree t = new Tree();
        for (int v : arr) t.insert(v);
        System.out.print("Inorder traversal: ");
        t.inorder();
        System.out.println();
    }

    private static void runAVLDemo(java.util.Scanner scanner) {
        System.out.println("Enter integers to insert into AVLTree (space or comma separated), or press Enter for default 5 3 7 2 4:");
        int[] arr = readIntArray(scanner, new int[]{5,3,7,2,4}, "");
        AVLTree avl = new AVLTree();
        for (int v : arr) {
            avl.insertAndVisualize(v);
        }
        System.out.println("Completed AVL inserts and visualizations.");
    }

    private static void runRecurrence(java.util.Scanner scanner) {
        try {
            System.out.println("Enter n for Recurrence (or press Enter for default n=5):");
            String line = scanner.nextLine().trim();
            String[] a;
            if (line.isEmpty()) a = new String[]{"5"};
            else a = new String[]{line.split("\\s+")[0]};
            Recurrence.main(a);
        } catch (Exception e) {
            System.out.println("Recurrence failed: " + e.getMessage());
        }
    }

    /**
     * Read an int array from the scanner. If the user presses Enter, return the default array.
     */
    private static int[] readIntArray(java.util.Scanner scanner, int[] def, String prompt) {
        if (prompt != null && !prompt.isEmpty()) System.out.println(prompt);
        String line = scanner.nextLine().trim();
        if (line.isEmpty()) return def.clone();
        String[] parts = line.split("[,\\s]+");
        java.util.List<Integer> vals = new java.util.ArrayList<>();
        for (String p : parts) {
            if (p.length() == 0) continue;
            try {
                vals.add(Integer.parseInt(p));
            } catch (NumberFormatException e) {
                System.out.println("Skipping invalid number: " + p);
            }
        }
        if (vals.isEmpty()) return def.clone();
        int[] out = new int[vals.size()];
        for (int i = 0; i < vals.size(); i++) out[i] = vals.get(i);
        return out;
    }

    private static void printArray(int[] arr) {
        System.out.print("Result: ");
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + (i < arr.length - 1 ? " " : ""));
        }
        System.out.println();
    }
}