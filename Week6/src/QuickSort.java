import java.io.*;
import java.util.Random;
import java.util.StringTokenizer;

public class QuickSort {
    public static void main(String[] args) throws IOException {
        FileReader fr = new FileReader("data06.txt");
        BufferedReader br = new BufferedReader(fr);
        String line = br.readLine();
        StringTokenizer st = new StringTokenizer(line,",");

        int[] arr = new int[st.countTokens()];
        int count = 0;

        while(st.hasMoreTokens()){
            arr[count++] = Integer.parseInt(st.nextToken());
        }

        System.out.println("*************** 중간값을 찾은 결과 *****************");
        int mid = (arr.length-1)/2;
        System.out.println(findK(arr, 0, arr.length-1, mid));

        System.out.println("*************** 퀵소트 결과 *****************");
        quickSort(arr, 0, arr.length-1);
        for (int val : arr){
            System.out.print(val + " ");
        }
        FileWriter fw = new FileWriter("data06_quick.txt");
        BufferedWriter bw = new BufferedWriter(fw);

        int num = 0;

        while (num != count-1) {
            bw.append(arr[num] + " ");
            num++;
        }
        bw.append(arr[num]+"");

        bw.flush();
        bw.close();
    }
    static void quickSort(int[] a, int p, int r) {
        if (p < r){
            int q = partition(a, p, r);
            quickSort(a, p, q-1);
            quickSort(a, q+1, r);
        }
    }
    static int partition(int[] a, int p, int r){
        int x = a[r];
        int i = p -1;
        for (int j = p; j <= r-1; j++){
            if (a[j] <= x){
                i++;
                swap(a, i, j);
            }
        }
        i++;
        swap(a, i, r);
        return i;
    }
    static int randomizedPartition(int[] a, int p, int r){
        Random random = new Random();
        int i = random.nextInt(a.length-1);
        swap(a, r, i);
        return partition(a, p ,r);
    }
    static void swap(int[] arr, int a, int b){
        int temp = arr[a];
        arr[a] = arr[b];
        arr[b] = temp;
    }

    static int findK (int[] a, int p, int q, int k) {
        if (p <= q) {
            int pivot = partition(a, p, q);

            if (pivot == k) {
                return a[k];
            } else if (pivot > k) {
                return findK(a, p, pivot-1, k);
            } else {
                return findK(a, pivot+1, q, k);
            }
        }
        return -1;
    }
}
