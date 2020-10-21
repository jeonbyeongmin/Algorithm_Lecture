import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.StringTokenizer;

public class MatrixChainMultiplication {
    public static int[][] m;
    public static int[][] s;
    public static void main(String[] args) throws IOException {
        FileReader fr = new FileReader("data08_matrix_chain.txt");
        BufferedReader br = new BufferedReader(fr);
        String line;
        int[] temp = new int[1000];
        int count = 0;

        while((line = br.readLine()) != null){
            StringTokenizer st = new StringTokenizer(line, ",");
            if (count == 0){
                temp[count++] = Integer.parseInt(st.nextToken());
                temp[count++] = Integer.parseInt(st.nextToken());
            } else {
                st.nextToken();
                temp[count++] = Integer.parseInt(st.nextToken());
            }
        }

        int[] p = new int[count];
        for (int i = 0; i < count ; i++) {
            p[i] = temp[i];
        }

        Scanner sc = new Scanner(System.in);
        System.out.print("N을 입력해주세요 : ");
        int inputN = sc.nextInt();

        m = new int[inputN+1][inputN+1];
        s = new int[inputN+1][inputN+1];

        System.out.println();
        for (int i = 0; i < count-1 ; i++) {
            System.out.println("A("+ (i+1) +") = " + p[i] + " X " + p[i+1]);
        }
        System.out.println("=============================================================");
        matrixChainOrder(p);
        for (int i = 1; i <= inputN; i++){
            for (int j = 1; j <= inputN; j++){
                System.out.printf("%-8d", m[i][j]);
            }
            System.out.println();
        }
        System.out.println("=============================================================");
        for (int i = 1; i <= inputN; i++){
            for (int j = 1; j <= inputN; j++){
                System.out.printf("%-8d", s[i][j]);
            }
            System.out.println();
        }
        System.out.println(m[1][6]);
        printOptimalParens(s, 1, 6);

    }


    public static void matrixChainOrder(int[] p){
        int n = p.length-1;
        for (int i = 1; i <= n; i++){
            m[i][i] = 0;
        }
        for (int l = 2; l <= n; l++){
            for (int i = 1; i <= n-l+1; i++){
                int j = i+l-1;
                m[i][j] = Integer.MAX_VALUE;
                for (int k = i; k <= j-1; k++){
                    int q = m[i][k] + m[k+1][j] + p[i-1]*p[k]*p[j];
                    if (q < m[i][j]){
                        m[i][j] = q;
                        s[i][j] = k;
                    }
                }
            }
        }
    }
    public static void printOptimalParens(int[][] s, int i, int j){
        if (i == j){
            System.out.print("A"+i);
        } else {
            System.out.print("(");
            printOptimalParens(s, i, s[i][j]);
            printOptimalParens(s, s[i][j]+1, j);
            System.out.print(")");
        }
    }

}
