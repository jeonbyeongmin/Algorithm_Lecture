import java.util.Scanner;

public class Main {
    public static int max = Integer.MIN_VALUE;
    public static int min = Integer.MAX_VALUE;
    public static int arr[];

    public static void findMaxMin(int left, int right) {
        if(left == right) {
            max = arr[left];
            min = arr[left];
        } else {
            int mid = (left+right)/2;
            findMaxMin(left, mid);

            int max1 = max;
            int min1 = min;

            findMaxMin(mid+1, right);

            if(max < max1){
                max = max1;
            }
            if(min > min1){
                min = min1;
            }
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        arr = new int[n];

        for (int i = 0; i < n; i++){
            arr[i] = sc.nextInt();
        }

        findMaxMin(0, arr.length-1);

        System.out.println("Min : " + min);
        System.out.println("Max : " + max);
    }
}