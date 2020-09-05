
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    static int[][] map;
    static boolean[] visit;
    static int last;
    static List<Integer> list = new ArrayList<>();
    static List<Integer> list2 = new ArrayList<>();

    public static void dfs(int i){
        visit[i] = true;
        for (int j = 1; j < 100; j++){
            if (map[i][j] == 1 && !visit[j]){
                list.add(i);
                list2.add(j);
                dfs(j);
            } else if (map[i][j] == 1 && j == last){
                list.add(i);
                list2.add(j);
            }
        }
    }
    public static void func(){
        for (int i = 0; i < list.size()-1; i++){
            while(true){
                if (list.size() == i || list2.get(i) == list.get(i+1)){
                    break;
                }
                if (list2.get(i) != list.get(i+1)){
                    list.remove(i);
                    list2.remove(i);
                }
            }

        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        map = new int[101][101];
        visit = new boolean[101];
        int num1, num2;
        for (int i = 1; i <=100; i++){
            num1 = sc.nextInt();
            num2 = sc.nextInt();
            map[num1][num2] = 1;
        }

        for (int i = 1; i <= 100; i++){
            last = i;
            dfs(i);
            func();
            for (int k = 0; k < list.size(); k++){
                System.out.print("(" + list.get(k) + " " + list2.get(k) + ") ");
            }
            if (list.size() != 0){
                System.out.println();
            }
            list.clear();
            list2.clear();
            for (int j = 0; j <= 100; j++){
                visit[i] = false;
            }
        }

    }
}
