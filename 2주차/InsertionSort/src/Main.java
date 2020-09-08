import java.util.Scanner;

public class Main {
    public static int[] arr;
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("몇 개의 정수를 입력받을 지 입력하세요 : ");
        int n = sc.nextInt();
        arr = new int[n];
        System.out.println("정수열을 입력해주세요 : ");
        for (int i = 0; i < n; i++){
            arr[i] = sc.nextInt();
        }
        insertionSort(arr);

        for (int val : arr){
            System.out.print(val + " ");
        }
    }
    public static void insertionSort(int[] arr){
        for (int j = 1; j < arr.length; j++){
            int key = arr[j];
            int i = j - 1;
            while(i >= 0 && arr[i] > key){
                arr[i+1] = arr[i];
                i--;
            }
            arr[i+1] = key;
        }
    }
}
