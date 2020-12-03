import java.util.Scanner;

public class PoweringNumber {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("a와 n을 입력해주세요: ");
        int inputA = sc.nextInt();
        int inputN = sc.nextInt();
        System.out.println(pow(inputA, inputN));
    }
    static int pow(int a, int n){
        if (n == 0) {
            return 1;
        } else if (n == 1) {
            return a;
        } else if (n % 2 != 0) { // n = odd
            return a * pow(a, n / 2) * pow(a, n / 2);
        } else { // n = even
            return pow(a, n / 2) * pow(a, n / 2);
        }
    }
}
