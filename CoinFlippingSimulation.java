public class CoinFlippingSimulation {
    private static boolean heads(){
        return (Math.random() < 0.5);
    }

    public static void main (String args[]){
        int cnt = 0,  j;
        int n = Integer.parseInt(args[0]);
        int m = Integer.parseInt(args[1]);
        int [] result = new int[n+1];

        for (int i = 0; i < m; i++){
            cnt = 0;
            for (j = 0; j < n; j++){
                if (heads()) cnt++;
            }
            result[cnt]++;

        }

        for (j = 0; j <= n; j++){
            if (result[j] == 0){
                System.out.print(".");
            }
            for (int i = 0; i < result[j]; i+=10){
                System.out.print("*");
            }
            System.out.println();
                
        }
    }
}
