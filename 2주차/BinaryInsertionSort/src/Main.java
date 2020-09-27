import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {

        FileReader fr = new FileReader("data02.txt");
        BufferedReader br = new BufferedReader(fr);
        String line = br.readLine();
        StringTokenizer st = new StringTokenizer(line, ",");

        int[] arr = new int[st.countTokens()];
        int count = 0;

        while (st.hasMoreTokens()) {
            arr[count++] = Integer.parseInt(st.nextToken());
        }

        insertionSort(arr);

        for (int val : arr){
            System.out.print(val + " ");
        }
    }
    public static void insertionSort(int[] arr){
        for (int j = 1; j < arr.length; j++){
            int key = arr[j];
            int i = binarySearch(arr, key, j);
            for (int k = j; k > i; k--){
                arr[k] = arr[k-1];
            }
            arr[i] = key;
        }
    }
    public static int binarySearch(int[] arr, int key, int j){
        /*
         이진탐색은 이미 배열이 정렬되었다고 가정하고 사용한다.
         삽입정렬은 배열에서 이미 정렬된 부분이 존재하고
         그 부분은 첫번째 인덱스부터 j-1까지이다.
         */
        int low = 0;
        int high = j-1;

        while(low <= high){
            int mid = (low+high)/2;
            if (arr[mid] > key){
                high = mid-1;
            } else{
                low = mid+1;
            }
        }
        return low;
    }
}
