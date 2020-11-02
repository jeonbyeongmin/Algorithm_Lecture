
public class LIS {
    public static int count;
    public static int[] trace;
    public static void main(String[] args) {
        int[] sequence = {9, 1, 3, 7, 5, 6, 10};
        int answer[] = lis(sequence);

        System.out.println("증가하는 가장 긴 부분 수열의 길이 : " + count);
        System.out.print("증가하는 가장 긴 부분 수열 : ");
        for (int i = 0; i < answer.length; i++){
            if (count == answer[i]){
                print(sequence, trace[i]);
                System.out.print(sequence[i]+ " ");
                break;
            }
        }
        System.out.println();

    }
    public static int[]  lis(int[] a) {
        int[] lis = new int[a.length];
        trace = new int[a.length];
        count = 0;

        for (int i = 0; i < a.length; i++) {
            lis[i] = 1;
            trace[i] = -1;
            for (int j = 0; j < i; j++) {
                if (a[j] < a[i] && lis[i] < lis[j] + 1) {
                    lis[i] = lis[j] + 1;
                    trace[i] = j;
                }
            }
            count = Math.max(lis[i], count);
        }
        return lis;
    }
    public static void print(int[] arr, int a){
        if (a == -1){
            return;
        }
        print(arr, trace[a]);
        System.out.print(arr[a] + " ");
    }
}
