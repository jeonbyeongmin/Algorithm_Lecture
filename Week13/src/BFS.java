
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class BFS {

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
        public void insert(int vertex1, int vertex2){
            adjList.get(vertex1).add(vertex2);
        }
    }

    public static int numberOfVertex; // vertex의 수

    public static void main(String[] args) throws IOException {
        FileReader fr = new FileReader("data13.txt");
        BufferedReader br = new BufferedReader(fr);
        String line = br.readLine();
        numberOfVertex = Integer.parseInt(line);

        int[][] adj = new int[numberOfVertex][numberOfVertex]; // 파일에서 받아온 데이터로 인접행렬 구성

        int count = 0;
        while((line = br.readLine()) != null){
            StringTokenizer st = new StringTokenizer(line, " ");
            for (int j = 0; j < numberOfVertex; j++){
                adj[count][j] = Integer.parseInt(st.nextToken());
            }
            count++;
        }

        AdjList adjList = new AdjList(numberOfVertex); // 인접리스트 생성
        boolean[] visit = new boolean[numberOfVertex+1];

        for (int i = 1; i <= numberOfVertex; i++) { // 인접리스트에 받아온 인접행렬 값 부여
            for (int j = 1; j <= numberOfVertex; j++) {
                if (adj[i - 1][j - 1] == 1) {
                    adjList.insert(i, j);
                }
            }
        }
        bfs(adjList, visit, "r");
    }


    public static class Vertex {
        int d;
        String name;
        Vertex parent;

        Vertex(String name, int d, Vertex parent) {
            this.name = name;
            this.d = d;
            this.parent = parent;
        }
        public String getName(){
            return name;
        }
        public int getD() {
            return d;
        }
        public Vertex getParent() {
            return parent;
        }
    }
    public static void bfs(AdjList adjList, boolean[] visit, String startVertex){
        Queue<Vertex> q = new LinkedList<>();
        q.add(new Vertex(startVertex, 0, null));
        visit[alpha2Num(startVertex)] = true;

        while(!q.isEmpty()){
            Vertex u = q.poll();
            System.out.print("현재 정점 : " + u.getName() +  ", 비용 : " + u.getD());
            if (u.getParent() != null){
                System.out.println(", 부모 정점 : " + u.getParent().getName());
            } else {
                System.out.println(", 부모 정점 : 없음");
            }
            for (int t : adjList.get(alpha2Num(u.getName()))){
                if (visit[t]) continue;
                visit[t] = true;
                q.add(new Vertex(num2Alpha(t), u.getD()+1, u));
            }
        }
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
