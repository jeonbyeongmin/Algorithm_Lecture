import java.util.Scanner;

public class FarthestInFuture {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int k = sc.nextInt();
        String cache = sc.next();
        if (cache.length() != k){
            System.out.println("cache의 크기는 " + k + "가 되어야 합니다.");
        } else {
            String requests = sc.next();
            System.out.println();
            for (int i = 0; i < requests.length(); i++){
                char currentRequest = requests.charAt(i);
                System.out.println("Schedule : " + (i+1));
                System.out.println("Request : " + currentRequest);
                String tempStr = Character.toString(currentRequest);

                if (cache.contains(tempStr)){ // cache hit
                    System.out.println("Cache : " + cache);
                } else { // cache miss
                    char farthestRequest = 0;
                    int max = -1;
                    for (int j = 0; j < k; j++) {
                        int count = 0;
                        for (int q = i+1; q < requests.length(); q++){
                            if (cache.charAt(j) == requests.charAt(q)){
                                break;
                            } else {
                                count++;
                            }
                        }
                        if (max < count){
                            max = count;
                            farthestRequest = cache.charAt(j);
                        }
                    }
                    System.out.println("Evict : " + farthestRequest);
                    cache = cache.replace(farthestRequest, currentRequest);
                    System.out.println("Cache : " + cache);
                }
                System.out.println();
            }
        }
    }
}
