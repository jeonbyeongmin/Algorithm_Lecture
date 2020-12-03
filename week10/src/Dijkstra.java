
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Dijkstra {

    public static int[][] w;
    public static int size;
    public static String[] node;

    public static class Node implements Comparable<Node>{
        String vertex;
        int distance;

        Node(String vertex, int distance){
            this.vertex = vertex;
            this.distance = distance;
        }
        @Override
        public int compareTo(Node o) {
            return this.distance - o.distance;
        }
    }

    public static void dijkstra(int start){

        int d[] = new int[size+1];

        for (int i = 1; i <= size; i++){
            if (i == start){
                d[i] = 0;
            } else {
                d[i] = Integer.MAX_VALUE;
            }
        }

        List<Node> S = new ArrayList<>(); // d[v] 의 계산이 완료된 노드들의 집합. 초기 공집합.
        PriorityQueue<Node> Q = new PriorityQueue<>(); // 나머지 노드들의 집합. 초기 전체 노드.

        for (int i = 1; i <= size; i++){
            Q.add(new Node(num2Alpha(i), d[i]));
        }

        System.out.println("Dijkstra's Algorithm 으로 계산한 결과는 다음과 같습니다.");

        while(!Q.isEmpty()){
            System.out.println("==============================================================");
            Node u = Q.poll();
            S.add(u);
            int currentSIndex = S.size()-1;
            String currentSVertex = u.vertex;
            int currentSDistance = u.distance;
            System.out.println("S[" + currentSIndex + "] : d[" + currentSVertex + "] = " + currentSDistance);
            System.out.println("--------------------------------------------------------------");

            int tempCount = 1;
            for (Node v : Q){ // 추출한 노드 u를 Q에 남아있는 모든 노드 v와 비교.
                String nextVertex = v.vertex;
                int nextDistance = v.distance;
                System.out.print("Q["  + (tempCount-1) + "] : d[" + nextVertex + "] = " + nextDistance);

                if ((w[alpha2Num(currentSVertex)][alpha2Num(nextVertex)] != Integer.MAX_VALUE)
                        && (d[alpha2Num(nextVertex)] > d[alpha2Num(currentSVertex)]
                        + w[alpha2Num(currentSVertex)][alpha2Num(nextVertex)])) {

                    d[alpha2Num(nextVertex)] = d[alpha2Num(currentSVertex)]
                            + w[alpha2Num(currentSVertex)][alpha2Num(nextVertex)];

                    v.distance = d[alpha2Num(nextVertex)]; // 임의적으로 노드에 접근하여 값 변경.
                    System.out.print("   -->   d[" + v.vertex + "] = " + v.distance);
                }

                System.out.println();
                tempCount++;
            }
            System.out.println();

            // 노드에 값이 변경은 되었지만 함수를 통해서 바꾸지 않아 heap의 형태를 갖추지 않는다.
            // 따라서 Q 집합 heapify
            if (Q.size() != 0){
                Node temp = Q.poll();
                Q.add(temp);
            }
        }
    }
    public static void main(String[] args) throws IOException {
        FileReader fr = new FileReader("data11_dijkstra.txt");
        BufferedReader br = new BufferedReader(fr);

        String line = br.readLine(); // 첫줄에서 배열의 크기의 단서를 찾아내야 함.
        StringTokenizer st = new StringTokenizer(line, ",");
        size = st.countTokens();
        node = new String[size+1];
        for (int i = 1; i <= size; i++){
            node[i] = st.nextToken();
        }

        w = new int[size+1][size+1]; // w 배열의 크기를 노드의 개수만큼 설정.

        while((line = br.readLine()) != null){
            st = new StringTokenizer(line, ",");
            int firstIndex = Integer.parseInt(st.nextToken()); // from
            int secondIndex = Integer.parseInt(st.nextToken()); // to
            w[firstIndex][secondIndex] = Integer.parseInt(st.nextToken()); // 가중치 저장
        }

        for (int i = 1; i <= size; i++){ // path가 없는 경우 무한대로 채움.
            for (int j = 1; j <= size; j++){
                if ((i != j) && w[i][j] == 0 ){
                    w[i][j] = Integer.MAX_VALUE;
                }
            }
        }

        dijkstra(1);

    }

    // 순서대로 입력받은 알파벳대로 오름차순으로 숫자를 부여함.
    public static int alpha2Num(String alpha){
        for (int i = 1; i <= size; i++){
            if (alpha.equals(node[i])){
                return i;
            }
        }
        return -1; // 이상한 데이터가 입력되었으면 에러가 났다는 것을 쉽게 알 수 있게 하기 위해서 음수 반환
    }

    // 반대로 입력받은 숫자에 해당하는 알파벳을 반환.
    public static String num2Alpha(int num){
        return node[num];
    }

}
