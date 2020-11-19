import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Prim {

    public static String[] node; // 노드의 순서대로 담아놓는 배열.
    public static int[][] graph; // 그래프 인접행렬
    public static int size; // 노드의 총 개수

    public static class Node implements Comparable<Node>{
        String vertex;
        int distance;

        public Node(String vertex, int distance){
            this.vertex = vertex;
            this.distance = distance;
        }
        @Override
        public int compareTo(Node o) {
            return this.distance - o.distance;
        }
    }

    public static int prim(int start){
        int[] key = new int[size+1];
        int sum = 0;
        String[] temp2 = new String[size+1];

        for (int i = 1; i <= size; i++){
            if (i == start){ // start vertex 와 같다면
                key[i] = 0; // 해당 키값은 0이된다.
            } else {
                key[i] = Integer.MAX_VALUE;
            }
        }

        System.out.println("=================   edge 탐색  ===================");
        System.out.println("노드들의 key가 update되는 과정이 '모두' 표시됨");
        System.out.println("더 작은 가중치를 update하고 최종적으로 해당 키값을 가지게 되며 mst가 완성됨");
        System.out.println();
        System.out.println("w( ," + num2Alpha(start) + ") = " + key[start]);

        PriorityQueue<Node> pq = new PriorityQueue<>();
        for (int i = 1; i <= size; i++){ // 큐에 {0, inf, inf, inf, ... inf } 를 넣음.
            pq.add(new Node(num2Alpha(i), key[i]));
        }

        while(!pq.isEmpty()){
            Node u = pq.poll(); // Extract-Min
            String currentVertex = u.vertex;

            for (Node v : pq){
                String nextVertex = v.vertex;

                // u와 연결되어 있는 모든 노드들에 대해서 그 노드가 가진 키값보다
                // 현재 노드와 연결된 간선의 가중치가 더 작다면 키값을 바꿈.
                int currentValue = graph[alpha2Num(currentVertex)][alpha2Num(nextVertex)];
                if (currentValue != 0 && currentValue < key[alpha2Num(nextVertex)]){
                    key[alpha2Num(nextVertex)] = currentValue;
                    System.out.println("w(" + currentVertex + "," + nextVertex + ") = " + currentValue);
                    v.distance = currentValue; // 큐에도 반영.

                    temp2[alpha2Num(nextVertex)] = "w(" + currentVertex + "," + nextVertex + ") = " + currentValue;
                }
            }
            // heapify
            if (pq.size() != 0){
                Node temp = pq.poll();
                pq.add(temp);
            }
        }

        System.out.println();
        System.out.println("=================   MST를 구성하는 edge들   ===================");
        for (int i = 2; i <= size; i++){
            System.out.println(temp2[i]);
        }

        // 반복문을 돌며 구한 MST가 되는 간선의 가중치를 모두 더함
        for (int i = 1; i <= size; i++){
            sum = sum + key[i];
        }

        return sum;
    }

    public static void main(String[] args) throws IOException {
        FileReader fr = new FileReader("data11_mst.txt");
        BufferedReader br = new BufferedReader(fr);
        String line = br.readLine();
        StringTokenizer st = new StringTokenizer(line, ",");

        size = st.countTokens(); // 노드의 개수
        node = new String[size+1];
        for (int i = 1; i <= size; i++){
            node[i] = st.nextToken();
        }

        graph = new int[size+1][size+1];

        while((line = br.readLine()) != null){
            st = new StringTokenizer(line, ",");
            String firstIndex = st.nextToken();
            String secondIndex = st.nextToken();
            int value = Integer.parseInt(st.nextToken());

            // 무방향 그래프이므로 대칭적인 그래프를 그려줌.
            graph[alpha2Num(firstIndex)][alpha2Num(secondIndex)] = value;
            graph[alpha2Num(secondIndex)][alpha2Num(firstIndex)] = value;
        }

        int sum = prim(alpha2Num("a"));
        System.out.println();
        System.out.println("w(MST) = " + sum);

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
