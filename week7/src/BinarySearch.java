import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.StringTokenizer;

public class BinarySearch {
    public static void main(String[] args) throws IOException {
        FileReader fr = new FileReader("data07_1.txt");
        BufferedReader br = new BufferedReader(fr);
        String line = br.readLine();
        StringTokenizer st = new StringTokenizer(line, ", ");

        int[] arr = new int[st.countTokens()];
        int count = 0;

        while(st.hasMoreTokens()){
            arr[count++] = Integer.parseInt(st.nextToken());
        }

        System.out.print("찾을 숫자를 입력해주세요 : ");
        Scanner sc = new Scanner(System.in);
        int inputNumber = sc.nextInt();

        int location = binarySearch(arr, inputNumber);
        if (location == -1){
            System.out.println("찾으려는 숫자가 없습니다.");
        } else {
            System.out.println(inputNumber+" 는 현재 [" + location +"번 index]에 있습니다.");
        }


    }
    public static int binarySearch(int[] arr, int x){
        int left = 0;
        int right = arr.length-1;

        while(left <= right){
            int mid = (left+right)/2;
            if (arr[mid] == x){
                return mid;
            } else if (arr[mid] > x){
                right = mid -1;
            } else{
                left = mid + 1;
            }
        }
        // x가 없으면 -1 리턴
        return -1;
    }
}
