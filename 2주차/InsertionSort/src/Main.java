import java.io.*;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {

        // 파일로 입력받고 StringTokenizer를 이용하여 ','를 기준으로 값을 배열에 가져온다.
        FileReader fr = new FileReader("data02.txt");
        BufferedReader br = new BufferedReader(fr);
        String line = br.readLine();
        StringTokenizer st = new StringTokenizer(line, ",");

        int[] arr = new int[st.countTokens()];
        int count = 0;

        while (st.hasMoreTokens()) {
            arr[count++] = Integer.parseInt(st.nextToken());
        }

        // insertionSort 함수 호출
        insertionSort(arr);

        // 출력
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
