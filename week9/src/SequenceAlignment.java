public class SequenceAlignment {
    public static void main(String[] args) {
        String str1 = "tcatagttaaca";
        String str2 = "tcagaagtacc";
        int result = sequenceAlignment(str1, str2);
        System.out.print("점수 : " + result);
    }
    public static int sequenceAlignment(String str1, String str2){
        int lx = str1.length();
        int ly = str2.length();
        int[][] a = new int[ly+1][lx+1];

        for (int i = 0; i <= ly; i++){
            a[i][0] = i * -2;
        }
        for (int j = 0; j <=lx; j++){
            a[0][j] = j * -2;
        }
        for (int i = 1; i <= ly; i++){
            for (int j = 1; j <= lx; j++){
                int p;
                if (str1.charAt(j-1) == str2.charAt(i-1)){
                    p = 1;
                } else {
                    p = -1;
                }
                a[i][j] = Math.max(Math.max(a[i][j-1]-2, a[i-1][j-1]+p), a[i-1][j]-2);
            }
        }

        for (int i = 0; i <= ly; i++){
            for (int j = 0; j <= lx; j++){
                System.out.printf("%-6d", a[i][j]);
            }
            System.out.println();
        }
        return a[ly][lx];
    }

}
