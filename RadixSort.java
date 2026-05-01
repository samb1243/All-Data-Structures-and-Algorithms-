public class RadixSort {
    public static void main(String[] args) {
        int[] arr = {5, 3, 8, 1, 2};
        radixSort(arr);
        for (int num : arr) {
            System.out.print(num + " ");
        }
    }

    public static void radixSort(int[] arr) {
        int max = getMax(arr);

        // Do counting sort for every digit. Note that instead
        // of passing digit number, exp is passed. exp is 10^i
        // where i is current digit number
        for (int exp = 1; max / exp > 0; exp *= 10) {
            countingSort(arr, exp);
        }
    }

    public static int getMax(int[] arr) {
        int max = arr[0];
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] > max) {
                max = arr[i];
            }
        }
        return max;
    }

    public static void countingSort(int[] arr, int exp) {
        int n = arr.length;
        int[] output = new int[n]; // Output array that will hold the sorted numbers
        int[] count = new int[10]; // Count array to store count of occurrences of digits

        // Count occurrences of digits in the specified place value
        for (int i = 0; i < n; i++) {
            count[(arr[i] / exp) % 10]++;
        }

        // Update count[i] to contain the actual position of this digit in output[]
        for (int i = 1; i < 10; i++) {
            count[i] += count[i - 1];
        }

        // Build the output array
        for (int i = n - 1; i >= 0; i--) {
            output[count[(arr[i] / exp) % 10] - 1] = arr[i];
            count[(arr[i] / exp) % 10]--;
        }

        // Copy the output array back to arr
        for (int i = 0; i < n; i++) {
            arr[i] = output[i];
        }
    }
}
