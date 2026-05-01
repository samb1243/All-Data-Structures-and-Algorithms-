public class Recurrence {
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int[] a = new int[n+1];
        a[0] = 1;
        for (int i = 1; i <= n; i++) {
            a[i] = 2 * a[i-1] + 1;
        }
        System.out.println(a[n]);
    }
}
