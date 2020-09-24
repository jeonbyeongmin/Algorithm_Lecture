import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;

public class MatrixMultiplication {
    public static void main(String[] args) throws IOException {
        int[][] a = new int[64][64];
        int[][] b = new int[64][64];

        FileReader fr = new FileReader("data04.txt");
        BufferedReader br = new BufferedReader(fr);
        String line = br.readLine();
        StringTokenizer st = new StringTokenizer(line," ");

        int countI = 0;
        int countJ = 0;


    }
    static int[][] dnc(int[][] a, int[][] b){
        int n = a.length;
        int[][] c = new int[n][n];
        if (n == 1) {
            c[0][0] = a[0][0] * b[0][0];
        } else{
            int[][] a11 = new int[n/2][n/2];
            int[][] a12 = new int[n/2][n/2];
            int[][] a21 = new int[n/2][n/2];
            int[][] a22 = new int[n/2][n/2];

            int[][] b11 = new int[n/2][n/2];
            int[][] b12 = new int[n/2][n/2];
            int[][] b21 = new int[n/2][n/2];
            int[][] b22 = new int[n/2][n/2];

            divide(a, a11,0,0);
            divide(a, a12,0,n/2);
            divide(a, a21,n/2,0);
            divide(a, a22,n/2,n/2);

            divide(a, b11,0,0);
            divide(a, b12,0,n/2);
            divide(a, b21,n/2,0);
            divide(a, b22,n/2,n/2);

            int[][] c11 = addMatrices(dnc(a11, b11), dnc(a12, b21));
            int[][] c12 = addMatrices(dnc(a11, b12), dnc(a12, b22));
            int[][] c21 = addMatrices(dnc(a21, b11), dnc(a22, b21));
            int[][] c22 = addMatrices(dnc(a21, b12), dnc(a22, b22));

            copySubArray(c11, c, 0, 0);
            copySubArray(c12, c, 0, n / 2);
            copySubArray(c21, c, n / 2, 0);
            copySubArray(c22, c, n / 2, n / 2);
        }
        return c;
    }
    static void divide(int[][] a, int[][] b, int p, int q){
        for(int i1 = 0, i2 = p; i1 < b.length; i1++, i2++)
            for(int j1 = 0, j2 = q; j1 < b.length; j1++, j2++)
                b[i1][j1] = a[i2][j2];
    }
    static int[][] addMatrices(int[][] a, int[][] b) {
        int n = a.length;
        int[][] res = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                res[i][j] = a[i][j] + b[i][j];
            }
        }
        return res;
    }
    public static void copySubArray(int[][] C, int[][] P, int iB, int jB)
    {
        for(int i1 = 0, i2 = iB; i1 < C.length; i1++, i2++)
            for(int j1 = 0, j2 = jB; j1 < C.length; j1++, j2++)
                P[i2][j2] = C[i1][j1];
    }
}
