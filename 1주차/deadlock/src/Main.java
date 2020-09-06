import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static boolean[][] edge;
    public static boolean[] visit;
    public static boolean isDeadlock;
    public static int firstId;
    public static int numberOfProcesses;

    public static List<Integer> firstProcessId = new ArrayList<>();
    public static List<Integer> secondProcessId = new ArrayList<>();
    public static List<Integer> deadlockProcess = new ArrayList<>();

    public static void findDeadlock(int i){

        visit[i] = true;

        for (int j = 1; j < 100; j++){
            if (edge[i][j] && !visit[j]) {
                firstProcessId.add(i);
                secondProcessId.add(j);
                findDeadlock(j);
            } else if (edge[i][j] && visit[j] && firstId == j){
                firstProcessId.add(i);
                secondProcessId.add(j);
                isDeadlock = true;
            }
        }
    }

    public static void removeExtra(){
        for (int i = 1; i < firstProcessId.size(); i++){
            if (firstProcessId.get(i) != secondProcessId.get(i-1)){
                if (secondProcessId.get(i-1) == firstId){
                    break;
                }
                firstProcessId.remove(i-1);
                secondProcessId.remove(i-1);
                i = 1;
            }
        }
        int last = firstProcessId.size()-1;
        while(secondProcessId.get(last) != firstId){
            firstProcessId.remove(last);
            secondProcessId.remove(last);
            last = firstProcessId.size()-1;
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("프로세스 정수 쌍을 총 몇개 입력할 것인지 입력해주세요 : ");
        numberOfProcesses = sc.nextInt();
        System.out.println("공백을 이용하여 프로세스 정수 쌍을 입력해주세요.");
        visit = new boolean[101];
        edge = new boolean[101][101];
        int num1, num2;
        for (int i = 1; i <= numberOfProcesses; i++){
            num1 = sc.nextInt();
            num2 = sc.nextInt();
            edge[num1][num2] = true;
        }

        for (int j = 1; j <= 100; j++){
            firstId = j;
            findDeadlock(j);
            if (isDeadlock){
                removeExtra();
                System.out.print(j+"와 관련된 프로세스 체인 : ");
                for (int i = 0; i < firstProcessId.size(); i++){
                    System.out.print("(" + firstProcessId.get(i) + " " + secondProcessId.get(i) + ")");
                    if (secondProcessId.get(i) == firstId && i < firstProcessId.size()-1){
                        System.out.print(" | ");
                    }
                }
                deadlockProcess.add(firstProcessId.get(0));
                System.out.println();
            }
            secondProcessId.clear();
            firstProcessId.clear();
            for (int i = 1; i <= 100; i++){
                visit[i] = false;
            }
            isDeadlock = false;
        }
        System.out.println();
        System.out.println("[교착상태인 프로세스]");
        System.out.println(deadlockProcess);
    }
}
