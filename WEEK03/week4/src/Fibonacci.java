
import java.util.Scanner;

public class Fibonacci {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("n을 입력해주세요 : ");
        int inputN = sc.nextInt();
        System.out.println("1. Recursion");
        System.out.println("2. Bottom-up");
        System.out.println("3. Recursive squaring");
        int inputNumber = sc.nextInt();

        switch (inputNumber){
            case 1 :
                System.out.println("[ Recursion을 이용하여 문제를 풉니다. ]");
                for (int i = 0; i <= inputN; i ++){
                    if (i % 10 == 0) {
                        System.out.println("************************************************************************");
                    }
                    long start = System.currentTimeMillis();
                    System.out.print("f("+i+") = "+ recursionFibo(i) +"\t\t\t");
                    long end = System.currentTimeMillis();
                    double time = (end - start);
                    System.out.println( time +" ms" );
                }
                break;
            case 2 :
                System.out.println("[ Bottom-up을 이용하여 문제를 풉니다. ]");
                for (int i = 0; i <= inputN; i ++){
                    if (i % 10 == 0) {
                        System.out.println("************************************************************************");
                    }
                    long start = System.nanoTime();
                    System.out.print("f("+i+") = "+ bottomUpFibo(i) + "\t\t\t");
                    long end = System.nanoTime();
                    double time = (end - start)/1000000.0;
                    System.out.println( time +" ms" );
                }
                break;
            case 3 :
                System.out.println("[ Recursive squaring을 이용하여 문제를 풉니다. ]");
                for (int i = 0; i <= inputN; i ++){
                    if (i % 10 == 0) {
                        System.out.println("************************************************************************");
                    }
                    long start = System.nanoTime();
                    System.out.print("f("+i+") = "+ recursiveSquaringFibo(i) + "\t\t\t");
                    long end = System.nanoTime();
                    double time = (end - start)/1000000.0;
                    System.out.println( time +" ms" );
                }
                break;
            default:
                System.out.println("1~3 이외에 다른 숫자를 입력했습니다.");
                break;
        }
    }
    static long recursionFibo(int n){
        if (n < 2){
            return n;
        }
        return recursionFibo(n-1) + recursionFibo(n-2);

    }
    static long bottomUpFibo(int n){
        long[] arr = new long[Math.max(n+1, 2)];
        arr[0] = 0;
        arr[1] = 1;
        if (n > 1){
            for (int i = 2; i <= n; i++){
                arr[i] = arr[i-1]+arr[i-2];
            }
        }
        return arr[n];
    }
    static long recursiveSquaringFibo(int n){
        if (n < 2){
            return n;
        } else{
            long[][] matrix = new long[2][2];
            matrix[0][0] = 1;
            matrix[0][1] = 1;
            matrix[1][0] = 1;
            matrix[1][1] = 0;
            return pow(matrix, n)[0][1];
        }
    }
    static long[][] pow(long[][] a, int n){
        if (n == 1){
            return a;
        } else if (n % 2 == 0){
            return mul(pow(a, n/2), pow(a, n/2));
        } else{
            return mul(a, mul(pow(a, n/2), pow(a, n/2)));
        }
    }
    static long[][] mul(long[][] a, long[][] b){
        long[][] c = new long[2][2];
        c[0][0] = a[0][0]*b[0][0] + a[0][1]*b[1][0];
        c[0][1] = a[0][0]*b[0][1] + a[0][1]*b[1][1];
        c[1][0] = a[1][0]*b[0][0] + a[1][1]*b[1][0];
        c[1][1] = a[1][0]*b[0][1] + a[1][1]*b[1][1];
        return c;
    }
}
