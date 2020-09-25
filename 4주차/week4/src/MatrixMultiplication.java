import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.StringTokenizer;

public class MatrixMultiplication {
    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);

        // A행렬과 B행렬을 만들어주기 위해서 이차원 배열을 두개 생성한다.
        int[][] a = new int[64][64];
        int[][] b = new int[64][64];

        // 텍스트 파일에서 행렬의 값을 받아오기 위해서 다음과 같이 작성하였다.
        FileReader fr = new FileReader("data04.txt");
        BufferedReader br = new BufferedReader(fr);

        // 첫번 째 줄에 방문한다.
        String line = br.readLine();

        //  "A" 라는 줄이 나오면 a[][]에 첫번 째 행렬을 저장한다. 단 B가 있는 줄이 나올 때까지 반복한다.
        if (line.contains("A")) {
            int countI = 0;
            while(!(line = br.readLine()).contains("B")) {
                int countJ = 0;
                StringTokenizer st = new StringTokenizer(line," ");
                while(st.hasMoreTokens()){
                    a[countI][countJ++] = Integer.parseInt(st.nextToken());
                }
                countI++;
            }
        }

        // "B" 라는 줄이 나오면 b[][]에 두번 째 행렬을 저장한다. 다음 줄이 없을 때까지 반복한다.
        if (line.contains("B")) {
            int countI = 0;
            while((line = br.readLine()) != null) {
                int countJ = 0;
                StringTokenizer st = new StringTokenizer(line," ");
                while(st.hasMoreTokens()){
                    b[countI][countJ++] = Integer.parseInt(st.nextToken());
                }
                countI++;
            }
        }


        System.out.print("분할정복은 숫자 '1'을 strassen의 방법은 숫자'2'를 입력해주세요 : ");
        int inputNumber = sc.nextInt();

        if (inputNumber == 1){

            System.out.println();
            System.out.println("[ Divide and Conquer 방법을 이용합니다. ]");
            // Divide and conquer를 이용하여 C행렬을 만들어준다.
            int[][] c = dnc(a, b);

            // C 행렬 출력
            for (int i = 0; i < 64; i ++){
                for (int j = 0; j < 64; j++){
                    System.out.print(c[i][j] + " ");
                }
                System.out.println();
            }
        } else if (inputNumber == 2){

            System.out.println();
            System.out.println("[ Strassen 방법을 이용합니다. ]");
            // strassen을 이용하여 C행렬을 만들어준다.
            int[][] c = strassen(a, b);

            // C 행렬 출력
            for (int i = 0; i < 64; i ++){
                for (int j = 0; j < 64; j++){
                    System.out.print(c[i][j] + " ");
                }
                System.out.println();
            }
        } else{
            System.out.println("숫자 '1' 또는 '2'만 입력해주세요.");
        }


    }

    // 첫번 째 방법 : Divide and conquer
    static int[][] dnc(int[][] a, int[][] b){
        int n = a.length;
        int[][] c = new int[n][n];
        if (n == 1) {
            c[0][0] = a[0][0] * b[0][0];
        } else {

            // A행렬(a[][])을 4부분으로 나누기 위해서 다음과 같이 작성하였다.
            int[][] a11 = new int[n/2][n/2];
            int[][] a12 = new int[n/2][n/2];
            int[][] a21 = new int[n/2][n/2];
            int[][] a22 = new int[n/2][n/2];

            // B행렬(b[][])을 4부분으로 나누기 위해서 다음과 같이 작성하였다.
            int[][] b11 = new int[n/2][n/2];
            int[][] b12 = new int[n/2][n/2];
            int[][] b21 = new int[n/2][n/2];
            int[][] b22 = new int[n/2][n/2];

            // divide(currentMatrix, dividedMatrix, row, column)
            divide(a, a11,0,0);
            divide(a, a12,0,n/2);
            divide(a, a21,n/2,0);
            divide(a, a22,n/2,n/2);
            divide(b, b11,0,0);
            divide(b, b12,0,n/2);
            divide(b, b21,n/2,0);
            divide(b, b22,n/2,n/2);

            // C11 = A11*B11 + A12*B21
            // C12 = A11*B12 + A12*B22
            // C21 = A21*B11 + A22*B21
            // C22 = A21*B12 + A22*B22
            int[][] c11 = addMatrix(dnc(a11, b11), dnc(a12, b21));
            int[][] c12 = addMatrix(dnc(a11, b12), dnc(a12, b22));
            int[][] c21 = addMatrix(dnc(a21, b11), dnc(a22, b21));
            int[][] c22 = addMatrix(dnc(a21, b12), dnc(a22, b22));

            // 4개로 나누었던 부분문제를 하나의 행렬로 만들어주기 위해서 다음과 같이 작성하였다.
            combine(c11, c, 0, 0);
            combine(c12, c, 0, n / 2);
            combine(c21, c, n / 2, 0);
            combine(c22, c, n / 2, n / 2);
        }
        return c;
    }

    // 두번 째 방법 : Strassen's algorithm
    static int[][] strassen(int[][] a, int[][] b){
        int n = a.length;
        int[][] c = new int[n][n];
        if (n == 1) {
            c[0][0] = a[0][0] * b[0][0];
        } else {

            // A행렬(a[][])을 4부분으로 나누기 위해서 다음과 같이 작성하였다.
            int[][] a11 = new int[n/2][n/2];
            int[][] a12 = new int[n/2][n/2];
            int[][] a21 = new int[n/2][n/2];
            int[][] a22 = new int[n/2][n/2];

            // B행렬(b[][])을 4부분으로 나누기 위해서 다음과 같이 작성하였다.
            int[][] b11 = new int[n/2][n/2];
            int[][] b12 = new int[n/2][n/2];
            int[][] b21 = new int[n/2][n/2];
            int[][] b22 = new int[n/2][n/2];

            // divide(currentMatrix, dividedMatrix, row, column)
            divide(a, a11,0,0);
            divide(a, a12,0,n/2);
            divide(a, a21,n/2,0);
            divide(a, a22,n/2,n/2);
            divide(b, b11,0,0);
            divide(b, b12,0,n/2);
            divide(b, b21,n/2,0);
            divide(b, b22,n/2,n/2);

            int[][] p1 = strassen(a11, subMatrix(b12, b22));
            int[][] p2 = strassen(addMatrix(a11, a12), b22);
            int[][] p3 = strassen(addMatrix(a21, a22), b11);
            int[][] p4 = strassen(a22, subMatrix(b21, b11));
            int[][] p5 = strassen(addMatrix(a11, a22), addMatrix(b11, b22));
            int[][] p6 = strassen(subMatrix(a12, a22), addMatrix(b21, b22));
            int[][] p7 = strassen(subMatrix(a11, a21), addMatrix(b11, b12));

            int[][] r = addMatrix(addMatrix(p4, p5), subMatrix(p6, p2));
            int[][] s = addMatrix(p1, p2);
            int[][] t = addMatrix(p3, p4);
            int[][] u = addMatrix(subMatrix(p1, p3), subMatrix(p5, p7));

            // 4개로 나누었던 부분문제를 하나의 행렬로 만들어주기 위해서 다음과 같이 작성하였다.
            combine(r, c, 0, 0);
            combine(s, c, 0, n / 2);
            combine(t, c, n / 2, 0);
            combine(u, c, n / 2, n / 2);
        }
        return c;
    }

    // 4개의 행렬로 나누는 메소드.
    static void divide(int[][] currentMatrix, int[][] dividedMatrix, int row, int column){
        for(int divideRow = 0, currentRow = row; divideRow < dividedMatrix.length; divideRow++, currentRow++){
            for(int divideColumn = 0, currentColumn = column; divideColumn < dividedMatrix.length; divideColumn++, currentColumn++){
                dividedMatrix[divideRow][divideColumn] = currentMatrix[currentRow][currentColumn];
            }
        }
    }
    // 나누어진 4개의 행렬을 자리에 맞게 합치기 위한 메소드
    public static void combine(int[][] dividedMatrix, int[][] combinedMatrix, int row, int column) {
        for(int divideRow = 0, combineRow = row; divideRow < dividedMatrix.length; divideRow++, combineRow++){
            for(int divideColumn = 0, combineColumn = column; divideColumn < dividedMatrix.length; divideColumn++, combineColumn++){
                combinedMatrix[combineRow][combineColumn] = dividedMatrix[divideRow][divideColumn];
            }
        }
    }

    // 두 개의 행렬을 더하는 메소드.
    static int[][] addMatrix(int[][] a, int[][] b) {
        int n = a.length;
        int[][] newMatrix = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                newMatrix[i][j] = a[i][j] + b[i][j];
            }
        }
        return newMatrix;
    }

    // 두 개의 행렬을 빼는 메소드.
    static int[][] subMatrix(int[][] a, int[][] b) {
        int n = a.length;
        int[][] newMatrix = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                newMatrix[i][j] = a[i][j] - b[i][j];
            }
        }
        return newMatrix;
    }
}