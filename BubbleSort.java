public class BubbleSort {
    public static void main(String[] args) {
        int[] arr = {5, 3, 8, 1, 2};
        System.out.println("Initial array: ");
        printArray(arr);
        System.out.println("\n--- Starting Bubble Sort ---\n");
        bubbleSort(arr);
        System.out.println("\n--- Final sorted array ---");
        printArray(arr);
    }

    public static void printArray(int[] arr) {
        for (int num : arr) {
            System.out.print(num + " ");
        }
        System.out.println();
    }

    public static void bubbleSort(int[] arr) {
        int n = arr.length;
        boolean swapped;

        // Traverse through all array elements
        for (int i = 0; i < n - 1; i++) {
            System.out.println("Pass " + (i + 1) + ":");
            swapped = false;

            // Last i elements are already in place
            for (int j = 0; j < n - i - 1; j++) {
                System.out.print("  Comparing arr[" + j + "]=" + arr[j] + " and arr[" + (j+1) + "]=" + arr[j + 1] + " -> ");
                // Swap if the element found is greater than the next element
                if (arr[j] > arr[j + 1]) {
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                    System.out.println("SWAP");
                    swapped = true;
                } else {
                    System.out.println("no swap");
                }
            }
            
            System.out.print("  Array after pass " + (i + 1) + ": ");
            printArray(arr);

            // If no two elements were swapped in the inner loop, then the array is already sorted
            if (!swapped) {
                System.out.println("Array is sorted! Exiting.\n");
                break;
            }
            System.out.println();
        }
    }
}
