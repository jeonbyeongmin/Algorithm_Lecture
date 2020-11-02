
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Knapsack {

    public static void main(String[] args) throws IOException {

        FileReader fr = new FileReader("data10_knapsack.txt");
        BufferedReader br = new BufferedReader(fr);
        String line;

        int[] value = new int[50];
        int[] weight = new int[50];
        int itemNum = 0;

        while((line = br.readLine()) != null){
            StringTokenizer st = new StringTokenizer(line, ",");
            itemNum = Integer.parseInt(st.nextToken());
            value[itemNum] = Integer.parseInt(st.nextToken());
            weight[itemNum] = Integer.parseInt(st.nextToken());
        }

        Scanner sc = new Scanner(System.in);
        System.out.print("배낭의 사이즈를 입력하세요(0~50) : ");
        int sizeOfBag = sc.nextInt();

        int[][] opt = knapsack(itemNum, value, weight, sizeOfBag);

        // OPT 값 출력.
        for (int i = 0; i <= itemNum; i++){
            for (int j = 0; j <= sizeOfBag; j++){
                System.out.printf("%-5d", opt[i][j]);
            }
            System.out.println();
        }
        // max 출력
        System.out.print("max : ");
        printTotalValue(opt, itemNum, sizeOfBag);
        //item 구성 출력
        System.out.print("item : ");
        printItem(weight, opt, itemNum, sizeOfBag);



    }
    //배열에 OPT값을 채우는 함수.
    public static int[][] knapsack(int numberOfSize, int[] value, int[]itemWeight, int weight){
        int[][] dp = new int[numberOfSize+1][weight+1];
        for (int w = 0; w <= weight; w++){
            dp[0][w] = 0;
        }
        for (int i = 1; i <= numberOfSize; i++){
            for (int w = 1; w <= weight; w++){
                if (itemWeight[i] > w){
                    dp[i][w] = dp[i-1][w];
                } else {
                    dp[i][w] = Math.max(dp[i-1][w], value[i] + dp[i-1][w-itemWeight[i]]);
                }
            }
        }
        return dp;
    }
    //완성된 배열을 분석하여 가치 총합이 가장 높은 item 구성을 출력하는 함수.
    public static void printItem(int[] weight, int[][] OPT,  int num, int sizeOfBag){
        if (num > 0 && sizeOfBag >= weight[num]){
            if (OPT[num][sizeOfBag] == OPT[num-1][sizeOfBag]){
                printItem(weight, OPT, num-1, sizeOfBag);
            } else {
                printItem(weight, OPT, num-1, sizeOfBag-weight[num]);
                System.out.print(num + " ");
            }
        }
    }
    // value의 합을 출력하는 함수
    public static void printTotalValue(int[][] OPT, int num, int sizeOfBag){
        System.out.println(OPT[num][sizeOfBag]);
    }
}
