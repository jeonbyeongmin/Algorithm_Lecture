import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;

public class OptimalBinarySearchTree {

    public static double[][] e;
    public static double[][] w;
    public static int[][] root;

    public static void main(String[] args) throws IOException {
        FileReader fr = new FileReader("data08_optimal_bst.txt");
        BufferedReader br = new BufferedReader(fr);
        String line = br.readLine();

        // 첫번째 줄 : 노드 개수 (키 개수)
        int numberOfNode = Integer.parseInt(line);
        int[] keys = new int[numberOfNode];
        double[] probability = new double[numberOfNode];

        // 두번째 줄 : key -- 오름차순으로 정렬이 되어 있다고 가정.
        line = br.readLine();
        StringTokenizer st = new StringTokenizer(line, " ");
        for (int i = 0; i < numberOfNode; i++){
            keys[i] = Integer.parseInt(st.nextToken());
        }

        // 세번째 줄 : probability -- 각 key에 대한 확률
        line = br.readLine();
        st = new StringTokenizer(line, " ");
        for (int i = 0; i < numberOfNode; i++){
            probability[i] = Double.parseDouble(st.nextToken());
        }

        // 어떤 bst가 가장 적은 비용을 가지는 지 알아내기 위한 함수.
        optimalBST(keys, probability, numberOfNode);
        constructOptimalBST(root, numberOfNode);

    }
    public static void optimalBST(int[] key, double[] p, int n){
        e = new double[n+2][n+1]; // 기대값.
        w = new double[n+2][n+1];
        root = new int[n+1][n+1];

        // j = i-1 이면 서브트리가 없다는 뜻이므로 0을 채움. (더미노드 없다고 가정)
        for (int i = 1; i <= n+1; i++){
            e[i][i-1] = 0;
            w[i][i-1] = 0;
        }

        // j >= i 일때 각각의 서브트리들이 모두 optimal BST여야 함.
        for (int l = 1; l <= n; l++){ // row 를 위한 loop
            for (int i = 1; i <= n-l+1; i++){ // col 를 위한 loop
                int j = i+l-1;
                e[i][j] = Double.MAX_VALUE;
                w[i][j] = w[i][j-1] + p[j-1];
                /*
                    루트를 r로 정했음.
                    가령 i = 1, j = 3 이라면 루트는 1, 2 ,3이 될 수 있음.
                    그리고 루트가 r이면
                    e[i][r-1]이 왼쪽서브트리의 기대치,
                    e[r+1][j]가 오른쪽 서브트리의 기대치이다.
                    loop를 돌며 루트가 r일때 각각의 기대치를 알아내어
                    최소값을 e[i][j]에 넣어주면 된다.
                    넣기 전에 전체 노드의 기대치들을 한번씩 더해주어야한다.
                    (서브트리들은 한단계 밑에 있기 때문에 한번씩 더 더해지는 것이 옳다.)
                */
                for (int r = i; r <= j; r++){
                    double t = e[i][r-1] + e[r+1][j] + w[i][j];
                    if (t < e[i][j]){ // 루프를 돌며 최소값이 넣어지게 됨.
                        e[i][j] = t;
                        root[i][j] = key[r-1]; // 루트에는 최소가 되는 인덱스 r에 해당하는 키의 값을 넣어주었다.
                    }
                }
            }
        }
    }

    public static void constructOptimalBST(int[][] root, int n){
        int r = root[1][n];
        System.out.println(r + " is the root");
        constructSub(root, 1, r-1, r, "left");
        constructSub(root, r+1, n, r, "right");
    }
    public static void constructSub(int[][] root, int i, int j, int r, String str){
        if (i <= j){
            int t = root[i][j];
            System.out.println(t + " is " + str + " child of " + r);
            constructSub(root, i, t-1, t, "left");
            constructSub(root, t+1, j, t, "right");
        }
    }
}
