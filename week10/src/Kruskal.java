import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.StringTokenizer;


public class Kruskal {

    public static int size;
    public static LinkedList<Edge>[] edges;
    public static String[] node;
    public static int sum;

    public static class fEdge {
        String from;
        String to;
        int value;

        public fEdge(String from, String to, int value){
            this.from = from;
            this.to = to;
            this.value = value;
        }

    }
    public static class Edge {
        String to;
        int value;
        public Edge(String to, int value){
            this.to = to;
            this.value = value;
        }
    }

    public static class UnionFind {
        private final int[] root;
        private final int[] size;

        public UnionFind(int n){
            this.root = new int[n+1];
            this.size = new int[n+1];
            for (int i = 1; i <= n; i++){
                this.root[i] = i;
                this.size[i] = i;
            }

        }
        public int find(int i){
            if (i == root[i]){
                return i;
            } else {
                return find(root[i]);
            }
        }
        public void union(int i, int j){
            int root1 = find(i);
            int root2 = find(j);
            int size1 = size[root1];
            int size2 = size[root2];

            if (size1 > size2) {
                size[root1] += size[size2];
                root[root2] = root1;
            } else {
                size[root2] += size[root1];
                root[root1] = root2;
            }
        }
        public boolean isSame(int i, int j){
            return find(i) == find(j);
        }
    }


    public static void kruskal(){
        LinkedList<fEdge> fedges = new LinkedList<>();
        for (int i = 1; i <= size; i++){
            for (Edge edge : edges[i]){
                if (i <= alpha2Num(edge.to)){
                    fEdge fedge = new fEdge(num2Alpha(i), edge.to, edge.value);
                    fedges.add(fedge);
                }
            }
        }

        // 리스트를 가중치를 기준으로 오름차순 정렬.
        fedges.sort(new Comparator<fEdge>() {
            @Override
            public int compare(fEdge o1, fEdge o2) {
                return Integer.compare(o1.value, o2.value);
            }
        });
        UnionFind unionFind = new UnionFind(size);
        while(!fedges.isEmpty()){
            fEdge fedge = fedges.poll();
            if (!unionFind.isSame(alpha2Num(fedge.from), alpha2Num(fedge.to))){
                System.out.println("w(" + fedge.from + "," + fedge.to +  ") = " +fedge.value);
                sum = sum + fedge.value;
                unionFind.union(alpha2Num(fedge.from), alpha2Num(fedge.to));
            }
        }
    }

    public static void addEdge(int i, int j, int w) {
        Edge edge = new Edge(num2Alpha(j), w);
        edges[i].add(edge);

        edge = new Edge(num2Alpha(i), w);
        edges[j].add(edge);
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

    public static void main(String[] args) throws IOException {
        FileReader fr = new FileReader("data11_mst.txt");
        BufferedReader br = new BufferedReader(fr);
        String line = br.readLine();
        StringTokenizer st = new StringTokenizer(line, ",");

        size = st.countTokens();
        node = new String[size+1];
        for (int i = 1; i <= size; i++){
            node[i] = st.nextToken();
        }

        edges = new LinkedList[size+1];
        for (int i = 1; i <= size; i++){
            edges[i] = new LinkedList<>();
        }
        while((line = br.readLine()) != null){
            st = new StringTokenizer(line, ",");
            String from = st.nextToken();
            String to = st.nextToken();
            int value = Integer.parseInt(st.nextToken());

            addEdge(alpha2Num(from), alpha2Num(to), value);
        }
        kruskal();
        System.out.println();
        System.out.println("w(MST) = " + sum);
    }
}
