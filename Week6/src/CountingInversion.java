public class CountingInversion {
    public static int[] temp;
    public static int[] a;
    public static void main(String[] args) {
        a = new int[5];
        temp = new int[5];
        int count = 0;
        for (int i = 4; i > 0; i--){
            a[count++] = i;
        }
        for (int val : a){
            System.out.print(val + " ");
        }
        System.out.println();
        System.out.println(countingInversion(a);
        for (int val : a){
            System.out.print(val + " ");
        }


    }
    static int countingInversion(int[] L) {
        int countInversion = 0;

        int left = 0;
        int right = L.length-1;

        if (left < right){
            int mid = (left + right)/2;
            int[] A =
            int r1 = countingInversion(left, mid);
            int r2 = countingInversion(mid+1, right);

            int p = left;
            int q = mid+1;
            int tempIndex = p;

            while(p <= mid || q <= right){
                if (p > mid || (q <= right && a[p] > a[q])){
                    temp[tempIndex++] = a[q++];
                    countInversion += q-p;
                } else if (q > right || (p <= mid && a[p] < a[q])) {
                    temp[tempIndex++] = a[p++];
                }
            }
            for (int i = left; i <= right; i++){
                a[i] = temp[i];
            }
        }
        return countInversion;
    }
    static void mergeAndCount(int ) {

    }
}
