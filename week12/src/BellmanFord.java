import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;

public class BellmanFord {
    public static class Edge {
        public int start;
        public int end;
        public int value;
        public Edge(int start, int end, int value){
            this.start = start;
            this.end = end;
            this.value = value;
        }
    }

    public static int[] d;

    public static void main(String[] args) throws IOException {
        FileReader fr = new FileReader("data12_bellman.txt");
        BufferedReader br = new BufferedReader(fr);

        String line = br.readLine();
        int numberOfNode = Integer.parseInt(line); // 노드 개수.
        line = br.readLine();

        StringTokenizer st = new StringTokenizer(line, " ");

        // s ~ t 까지의 최단거리를 구하여라.
        int s = Integer.parseInt(st.nextToken());
        int t = Integer.parseInt(st.nextToken());

        line = br.readLine();

        int numberOfEdge = Integer.parseInt(line); // 간선 개수.
        Edge[] edges = new Edge[numberOfEdge];

        int count = 0;
        while((line = br.readLine()) != null){
            st = new StringTokenizer(line, " ");
            int start = Integer.parseInt(st.nextToken());
            int end = Integer.parseInt(st.nextToken());
            int value = Integer.parseInt(st.nextToken());

            Edge edge = new Edge(start, end, value);
            edges[count++] = edge;
        }

        if (bellmanFord(edges, s, numberOfNode, numberOfEdge)){
            System.out.println("음수 사이클이 존재합니다.");
        } else {
            System.out.println(d[t]);
            // start node가 s일때 각각의 노드까지의 최단거리가 d에 담겨있고 d[t]는 목적지까지의 최단거리
        }
    }


    public static boolean bellmanFord(Edge[] edges, int start, int numberOfNodes, int numberOfEdges){

        d = new int[numberOfNodes];
        boolean result = false;

        for (int i = 0; i < numberOfNodes; i++){
            if (i == start){
                d[i] = 0; // 시작 지점까지의 거리는 0임
            } else {
                d[i] = Integer.MAX_VALUE; // 나머지 정점에 대한 거리를 모두 무한대로 설정
            }
        }
        for (int i = 1; i < numberOfNodes; i++){
            for (int j = 0; j < numberOfEdges; j++){
                if (d[edges[j].start] != Integer.MAX_VALUE && d[edges[j].end] > d[edges[j].start] + edges[j].value){
                    d[edges[j].end] = d[edges[j].start] + edges[j].value;
                }
            }
        }

        for (int j = 0; j < numberOfEdges; j++){
            if (d[edges[j].start] != Integer.MAX_VALUE && d[edges[j].end] > d[edges[j].start] + edges[j].value){
                result = true;
                break;
            }
        }

        return result;
    }
}
