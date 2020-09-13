import java.io.*;
import java.util.StringTokenizer;

public class Main {
    public static int[] arr;
    public static int[] temp;
    public static void main(String[] args) throws IOException {

        FileReader fr = new FileReader("data02.txt");
        BufferedReader br = new BufferedReader(fr);
        String line = br.readLine();
        StringTokenizer st = new StringTokenizer(line,",");

        arr = new int[st.countTokens()];
        temp = new int[arr.length];
        int count = 0;

        while(st.hasMoreTokens()){
            arr[count++] = Integer.parseInt(st.nextToken());
        }

        mergeSort(0, arr.length-1);

        for (int val : arr){
            System.out.print(val + " ");
        }

    }
    public static void mergeSort(int left, int right){
        if (left < right){
            int mid = (left+right)/2;
            // Divide
            mergeSort(left, mid);
            mergeSort(mid+1, right);

            // Conquer and Combine
            merge(left, mid, right);
        }
    }

    public static void merge(int left, int mid, int right){
        int p = left;
        int q = mid+1;
        int tempIndex = p;
        while(p <= mid || q <= right){
            if (q > right || (p <= mid && arr[p] < arr[q])){
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
