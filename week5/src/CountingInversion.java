import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;

public class CountingInversion {

    public static void main(String[] args) throws IOException {
        FileReader fr = new FileReader("data05_inversion.txt");
        BufferedReader br = new BufferedReader(fr);
        String line = br.readLine();
        StringTokenizer st = new StringTokenizer(line,",");

        int[] arr = new int[st.countTokens()];
        int count = 0;

        while(st.hasMoreTokens()){
            arr[count++] = Integer.parseInt(st.nextToken());
        }

        // 결과값
        System.out.println("Output Data : " + sortAndCount(arr, 0, arr.length-1));

    }

    static int sortAndCount(int[] arr, int start, int end){
        if (end - start < 1){
            return 0;
        }
        int mid = (start + end)/2;
        int rA = sortAndCount(arr, start, mid);
        int rB = sortAndCount(arr, mid+1, end);
        int r = mergeAndCount(arr, start, mid, end);

        return rA + rB + r;

    }
    static int mergeAndCount(int[] arr, int start, int mid, int end){
        int inversionCount = 0;

        int indexA = start;
        int indexB = mid + 1;

        int[] temp = new int[arr.length];
        int tempIndex = start;

        while(indexA <= mid && indexB <= end){ // 한쪽이 0이 될 때까지.
            if (arr[indexA] > arr[indexB]){
                inversionCount += (mid-indexA+1);
                temp[tempIndex++] = arr[indexB++];
            } else{
                temp[tempIndex++] = arr[indexA++];
            }
        }

        // 남은 리스트 추가.
        while (indexA <= mid){
            temp[tempIndex++] = arr[indexA++];
        }
        while (indexB <= end){
            temp[tempIndex++] = arr[indexB++];
        }

        for (int i = start; i <= end; i++){
            arr[i] = temp[i];
        }

        return inversionCount;
    }
}
