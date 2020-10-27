
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
        Scanner sc = new Scanner(System.in);
        System.out.print("N을 입력해주세요 : ");
        int inputN = sc.nextInt();

        while(inputN != 0){
            int[] p = new int[inputN+1];
            for (int i = 0; i < inputN+1 ; i++) {
                p[i] = temp[i];
            }

            m = new int[inputN+1][inputN+1];
            s = new int[inputN+1][inputN+1];

            System.out.println();
            for (int i = 0; i < inputN ; i++) {
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
            System.out.println("=============================================================");
            System.out.println(m[1][inputN]);
            printOptimalParens(s, 1, inputN);
            System.out.println();
            System.out.println();
            System.out.print("N을 입력해주세요 : ");
            inputN = sc.nextInt();
        }
    }
    public static void matrixChainOrder(int[] p){
        int n = p.length-1;

        // i = j 라면 행렬 하나를 의미하기 때문에 행렬 하나로는 곱셈을 할 수 없다. 따라서 0으로 채운다.
        for (int i = 1; i <= n; i++){
            m[i][i] = 0;
        }

        // i = j를 기준으로 오른쪽 테이블을 채우는 loop
        for (int l = 2; l <= n; l++){ // row loop
            for (int i = 1; i <= n-l+1; i++){ // column loop
                int j = i+l-1;
                m[i][j] = Integer.MAX_VALUE; // 최대값으로 초기화
                for (int k = i; k <= j-1; k++){ // i <= k < j 이기 때문
                    // q가 최소가 되는 k를 찾는 것이 목표.
                    int q = m[i][k] + m[k+1][j] + p[i-1]*p[k]*p[j];
                    if (q < m[i][j]){ // q의 최소값이 m[i][j]에 들어감.
                        m[i][j] = q;
                        s[i][j] = k; // q가 최소일 때의 k를 s배열에 저장.
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
