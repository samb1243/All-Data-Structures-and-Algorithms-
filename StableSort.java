public class StableSort {
    public static void main(String[] args) {
        int[] arr = {5, 3, 8, 1, 2};
        stableSort(arr);
        for (int num : arr) {
            System.out.print(num + " ");
        }
    }

    public static void stableSort(int[] arr) {
        int n = arr.length;
        int[] output = new int[n];
        int[] count = new int[10]; // Assuming the input numbers are in the range 0-9

        // Count occurrences of each digit
        for (int i = 0; i < n; i++) {
            count[arr[i]]++;
        }

        // Update count[i] to contain the actual position of this digit in output[]
        for (int i = 1; i < 10; i++) {
            count[i] += count[i - 1];
        }

        // Build the output array
        for (int i = n - 1; i >= 0; i--) {
            output[count[arr[i]] - 1] = arr[i];
            count[arr[i]]--;
        }

        // Copy the output array back to arr
        for (int i = 0; i < n; i++) {
            arr[i] = output[i];
        }
    }
}
