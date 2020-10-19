import java.io.*;

public class LCS {
    public static String answer = "";
    public static void main(String[] args) throws IOException {
        FileReader fr = new FileReader("data07_3.txt");
        BufferedReader br = new BufferedReader(fr);
        String line;
        int count = 0;
        String str1 = "";
        String str2 = "";

        while((line = br.readLine()) != null){
            if (count == 1){
                str1 = line;
            } else if (count == 3){
                str2 = line;
            }
            count++;
        }

        lcs_Length(str1, str2);
        System.out.println(answer);
        FileWriter fw = new FileWriter("output.txt");
        BufferedWriter bw = new BufferedWriter(fw);
        bw.append(answer);
        bw.flush();
        bw.close();
    }
    public static void lcs_Length(String x, String y){
        int m = x.length();
        int n = y.length();
        int[][] b = new int[m+1][n+1];
        int[][] c = new int[m+1][n+1];
        for (int i = 1; i <= m; i++){
            c[i][0] = 0;
        }
        for (int j = 0; j <= n; j++){
            c[0][j] = 0;
        }
        for (int i = 1; i <= m; i++){
            for (int j = 1; j <= n; j++){
                if (x.charAt(i-1) == y.charAt(j-1)){
                    c[i][j] = c[i-1][j-1] + 1;
                    b[i][j] = 3;
                } else if (c[i-1][j] >= c[i][j-1]){
                    c[i][j] = c[i-1][j];
                    b[i][j] = 2;
                } else {
                    c[i][j] = c[i][j-1];
                    b[i][j] = 1;
                }
            }
        }
        print_lcs(b, x, m, n);
    }
    public static void print_lcs(int[][] b, String x, int i, int j){
        if (i == 0 || j == 0){
            return;
        }
        if (b[i][j] == 3){
            print_lcs(b, x, i-1, j-1);
            answer += x.charAt(i-1);
        } else if (b[i][j] == 2){
            print_lcs(b, x, i-1, j);
        } else if (b[i][j] == 1){
            print_lcs(b, x, i, j-1);
        }
    }
}
