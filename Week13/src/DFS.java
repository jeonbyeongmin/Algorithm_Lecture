import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class DFS {

    public static String[] alpha = {"r", "s", "t", "u", "v", "w", "x", "y"};

    public static class AdjList {
        private ArrayList<ArrayList<Integer>> adjList;

        public AdjList(int size) {
            adjList = new ArrayList<ArrayList<Integer>>();

            adjList.add(new<Integer> ArrayList()); // dummy

            for (int i = 0; i < size; i++){
                adjList.add(new<Integer> ArrayList());
            }
        }
        public ArrayList<Integer> get(int index){
            return adjList.get(index);
        }
        public void insert(int edge1, int edge2){
            adjList.get(edge1).add(edge2);
        }
    }

    public static int numberOfVertex; // vertex의 수
    public static boolean[] visit;
    public static int time;
    public static Vertex[] temp;

    public static void main(String[] args) throws IOException {
        FileReader fr = new FileReader("data13.txt");
        BufferedReader br = new BufferedReader(fr);
        String line = br.readLine();
        numberOfVertex = Integer.parseInt(line);

        int[][] adj = new int[numberOfVertex][numberOfVertex]; // 파일에서 받아온 데이터로 인접행렬 구성
        visit = new boolean[numberOfVertex+1];

        int count = 0;
        while((line = br.readLine()) != null){
            StringTokenizer st = new StringTokenizer(line, " ");
            for (int j = 0; j < numberOfVertex; j++){
                adj[count][j] = Integer.parseInt(st.nextToken());
            }
            count++;
        }

        AdjList adjList = new AdjList(numberOfVertex); // 인접리스트 생성

        for (int i = 1; i <= numberOfVertex; i++) { // 인접리스트에 받아온 인접행렬 값 부여
            for (int j = 1; j <= numberOfVertex; j++) {
                if (adj[i - 1][j - 1] == 1) {
                    adjList.insert(i, j);
                }
            }
        }

        time = 0;
        temp = new Vertex[numberOfVertex+1];

        dfs(adjList, "r", null, temp);

        for (int i = 1; i <= numberOfVertex; i++){
            System.out.println("정점 : [" + num2Alpha(i) + "]");
            if (temp[i].parent != null){
                System.out.println("부모 정점 : " + temp[i].parent.name);
            } else {
                System.out.println("부모 정점 : 없음");
            }
            System.out.println("발견 시간(u.d) : " + temp[i].d + ", 종료시간(u.f) : " + temp[i].f);
            System.out.println();
        }
    }

    public static class Vertex {
        int d;
        int f;
        String name;
        Vertex parent;

        Vertex(String name, int d, Vertex parent) {
            this.name = name;
            this.d = d;
            this.f = 0;
            this.parent = parent;
        }
    }
    public static void dfs(AdjList adjList, String startVertex, Vertex oldVertex, Vertex[] temp){
        time += 1;
        visit[alpha2Num(startVertex)] = true;
        Vertex newVertex = new Vertex(startVertex, time, oldVertex);
        temp[alpha2Num(newVertex.name)] = newVertex;

        for (int i : adjList.get(alpha2Num(startVertex))){
            if (!visit[i]){
                dfs(adjList, num2Alpha(i), newVertex, temp);
            }
        }
        time += 1;
        temp[alpha2Num(newVertex.name)].f = time;
    }
    public static int alpha2Num(String al){
        for (int i = 0; i < numberOfVertex; i++){
            if (alpha[i].equals(al)){
                return i+1;
            }
        }
        return -1;
    }
    public static String num2Alpha(int num){
        return alpha[num-1];
    }
}
