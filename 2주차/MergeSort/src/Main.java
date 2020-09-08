import java.util.Scanner;

public class Main {
    public static int[] arr;
    public static int[] temp;
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("몇개의 정수를 입력할 것인지 입력해주세요 : ");
        int n = sc.nextInt();
        arr = new int[n];
        temp = new int[n];

        System.out.println("정수열을 입력해주세요 : ");

        for (int i = 0; i < n; i++){
            arr[i] = sc.nextInt();
        }
        mergeSort(0, arr.length-1);

        for (int val : arr){
            System.out.print(val + " ");
        }
    }
    public static void mergeSort(int left, int right){
        if (left < right){
            int mid = (left+right)/2;
            mergeSort(left, mid);
            mergeSort(mid+1, right);

            int p = left;
            int q = mid+1;
            int tempIndex = p;

            while(p <= mid || q <= right){
                if (q > right || arr[p] < arr[q]){
                    temp[tempIndex++] = arr[p++];
                } else{
                    temp[tempIndex++] = arr[q++];
                }
            }

            for (int i = left; i <= right; i++){
                arr[i] = temp[i];
            }
        }
    }
}
