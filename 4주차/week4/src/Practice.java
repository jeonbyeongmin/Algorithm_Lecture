public class Practice {
    public static void main(String[] args) {
        int[] asdf = new int[10];

        for (int i = 0; i < 10; i++){
            asdf[i] = i;
        }
        for (int val : asdf){
            System.out.println(asdf[val]);
        }
        System.out.println();
        prac(asdf);

        for (int i = 0; i < asdf.length; i++){
            System.out.println(asdf[i]);
        }
    }
    static void prac(int[] a){
        for (int i = 10; i > 0; i--){
            a[10-i] = i;
        }
    }
}
