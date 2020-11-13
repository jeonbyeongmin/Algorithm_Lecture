
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Dijkstra {

    public static class Node implements Comparable<Node>{
        int vertex;
        int distance;

        Node(int vertex, int distance){
            this.vertex = vertex;
            this.distance = distance;
        }
        @Override
        public int compareTo(Node o) {
            return (int) (this.distance - o.distance);
        }
    }

    public static int[][] w;
    public static int count;

    public static void dijkstra(int start){

        int d[] = new int[count+1];

        for (int i = 1; i <= count; i++){
            if (i == start){
                d[i] = 0;
            } else {
                d[i] = Integer.MAX_VALUE;
            }
        }

        List<Node> S = new ArrayList<>(); // d[v] 의 계산이 완료된 노드들의 집합. 초기 공집합.
        PriorityQueue<Node> Q = new PriorityQueue<>(); // 나머지 노드들의 집합. 초기 전체 노드.

        // Q에 모든 노드가 담겨져있다고 추상적으로 생각만 해두고
        // 반복문을 돌면서 변경되는 값들을 add 하는 방식으로 알고리즘을 구현할 수 있지만,
        // 이번 과제에서는 Q를 각 단계별로 모두 출력해야 하기 때문에 어쩔 수 없이 값을 넣었다.
        for (int i = 1; i <= count; i++){
            Q.add(new Node(i, d[i]));
        }

        System.out.println("Dijkstra's Algorithm 으로 계산한 결과는 다음과 같습니다.");

        while(!Q.isEmpty()){
            System.out.println("==============================================================");
            Node u = Q.poll();
            S.add(u);
            int currentSIndex = S.size()-1;
            int currentSVertex = u.vertex;
            int currentSDistance = u.distance;
            System.out.println("S[" + currentSIndex + "] : d[" + currentSVertex + "] = " + currentSDistance);
            System.out.println("--------------------------------------------------------------");

            int tempCount = 1;
            for (Node v : Q){ // 추출한 노드 u를 Q에 남아있는 모든 노드 v와 비교.
                System.out.print("Q["  + (tempCount-1) + "] : d[" + v.vertex + "] = " + v.distance);
                if ((w[currentSVertex][v.vertex] != Integer.MAX_VALUE) && (d[v.vertex] > d[currentSVertex] + w[currentSVertex][v.vertex])){
                    d[v.vertex] = d[currentSVertex] + w[currentSVertex][v.vertex];
                    v.distance = d[v.vertex]; // 임의적으로 노드에 접근하여 값 변경.
                    System.out.print("   -->   d[" + v.vertex + "] = " + v.distance);
                }
                System.out.println();
                tempCount++;
            }
            System.out.println();

            // 노드에 값이 변경은 되었지만 함수를 통해서 바꾸지 않아 heap의 형태를 갖추지 않는다.
            // 따라서 Q 집합 heapify (Dijkstra 알고리즘은 음수 가중치가 없다는 가정이 있기 때문에 다음과 같이 heapify가 가능.)
            Q.add(new Node(-1, -1));
            Q.poll();
        }
    }
    public static void main(String[] args) throws IOException {
        FileReader fr = new FileReader("data11_dijkstra.txt");
        BufferedReader br = new BufferedReader(fr);

        String line = br.readLine(); // 첫줄에서 배열의 크기의 단서를 찾아내야 함.
        StringTokenizer st = new StringTokenizer(line, ",");
        count = st.countTokens();
        w = new int[count+1][count+1]; // w 배열의 크기를 노드의 개수만큼 설정.

        while((line = br.readLine()) != null){
            st = new StringTokenizer(line, ",");
            int firstIndex = Integer.parseInt(st.nextToken()); // from
            int secondIndex = Integer.parseInt(st.nextToken()); // to
            w[firstIndex][secondIndex] = Integer.parseInt(st.nextToken()); // 가중치 저장
        }

        for (int i = 1; i <= count; i++){ // path가 없는 경우 무한대로 채움.
            for (int j = 1; j <= count; j++){
                if ((i != j) && w[i][j] == 0 ){
                    w[i][j] = Integer.MAX_VALUE;
                }
            }
        }

        dijkstra(1);

    }
}
