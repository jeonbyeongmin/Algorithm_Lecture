
import java.io.*;
import java.util.*;

public class Huffman {
    // 노드 클래스
    public static class Node {
        public int freq;
        public char alpha;
        public Node left;
        public Node right;

        public Node(){
            this.freq = 0;
            this.alpha = 0;
            this.left = null;
            this.right = null;
        }
        public Node(char alpha){
            this.freq = 0;
            this.alpha = alpha;
            this.left = null;
            this.right = null;
        }
        public Node(char alpha, int freq){
            this.freq = freq;
            this.alpha = alpha;
            this.left = null;
            this.right = null;
        }
    }
    // 힙 클래스
    public static class MinHeap {
        private List<Node> tree = new ArrayList<>(100);
        public MinHeap(){
            tree.add(null);
        }
        // 힙에 노드를 추가하면 다시 min 힙구조를 맞춰야함.
        public void insert(Node node){
            tree.add(node);

            int child = tree.size()-1;
            int parent = child/2;

            // 부모는 자식보다 항상 작아야 한다.
            while(parent >= 1 && tree.get(child).freq < tree.get(parent).freq){
                Collections.swap(tree, child, parent);

                child = parent;
                parent = child/2;
            }
        }
        public Node extractMin(){
            if (isEmpty()) {
                return null; // 힙이 비어있다면 null 반환
            }
            Node min = min();

            int lastNode = tree.size()-1;
            tree.set(1, tree.get(lastNode));
            tree.remove(lastNode);

            int parent = 1; // 부모 인덱스
            int left = parent * 2; // 왼쪽 자식 인덱스
            int right = parent * 2 + 1; // 오른쪽 자식 인덱스

            // min heapify
            while(left <= tree.size()-1){ // 왼쪽 자식이 없으면 오른쪽 자식도 없음.
                int target;
                if (right > tree.size()-1){
                    if (tree.get(left).freq >= tree.get(parent).freq){
                        break;
                    }
                    target = left;
                } else {
                    if (tree.get(left).freq >= tree.get(parent).freq &&
                            tree.get(right).freq >= tree.get(parent).freq){
                        break;
                    }
                    target = (tree.get(left).freq < tree.get(right).freq) ? left : right ;
                }
                Collections.swap(tree, target, parent); // 더 작은 자식과 스왑

                parent = target;
                left = parent * 2;
                right = parent * 2 + 1;
            }

            return min;
        }
        public boolean isEmpty(){
            return (tree.size() <= 1);
        }
        public Node min(){
            return tree.get(1);
        }
    }


    // key : alpha -> value : freq
    public static HashMap<Character, Integer> alphaFreq = new HashMap<>();
    // key : alpha -> value : encoded code
    public static HashMap<Character, String> alphaCode = new HashMap<>();

    public static Node huffman(){ // Huffman code를 얻기 위해서 tree의 root를 리턴
        int n = alphaFreq.size();
        MinHeap heap = new MinHeap();

        if (alphaFreq.isEmpty()){
            return null;
        }
        // heap 모든 노드 저장
        for (char key : alphaFreq.keySet()){
            heap.insert(new Node(key, alphaFreq.get(key)));
        }
        for (int i = 1; i <= n-1; i++){
            Node x = heap.extractMin();
            Node y = heap.extractMin();
            Node z = new Node('!', x.freq + y.freq);

            z.left = x;
            z.right = y;

            heap.insert(z);
        }
        return heap.extractMin();
    }

    // 얻어진 tree로부터 Huffman code를 얻어 테이블 작성
   public static  void writeHuffmanTable(Node root, int[] trace, int size){
        if (root.left != null){
            trace[size] = 0;
            writeHuffmanTable(root.left, trace, size+1);
        }
        if (root.right != null){
            trace[size] = 1;
            writeHuffmanTable(root.right, trace, size+1);
        }
        if (root.left == null && root.right == null){
            String code = "";
            for (int i = 0; i < size; i++){
                code = code + trace[i];
            }
            alphaCode.put(root.alpha, code);
        }
    }

    public static void main(String[] args) throws IOException {
        FileReader fr = new FileReader("data12_huffman.txt");
        BufferedReader br = new BufferedReader(fr);

        String line;

        while((line = br.readLine()) != null){
            for (int i = 0; i < line.length(); i++){
                char alpha = line.charAt(i);
                if (alphaFreq.containsKey(alpha)){
                    alphaFreq.put(alpha, alphaFreq.get(alpha)+1);
                } else {
                    alphaFreq.put(alpha, 1);
                }
            }
        }
        int[] tempArr = new int[alphaFreq.size()-1];

        Node root = huffman();
        writeHuffmanTable(root, tempArr, 0);


        // 테이블 데이터 저장
        FileWriter tableData = new FileWriter("201702068_table.txt");
        for (char key : alphaCode.keySet()){
            String data = key + " " + alphaCode.get(key) +"\n";
            tableData.write(data);
        }
        tableData.close();

        // 인코딩 데이터 저장
        FileWriter encodingData = new FileWriter("201702068_encoded.txt");
        FileReader fr2 = new FileReader("data12_huffman.txt");
        BufferedReader br2 = new BufferedReader(fr2);

        String line2;

        while((line2 = br2.readLine()) != null){
            for (int i = 0; i < line2.length(); i++){
                char alpha = line2.charAt(i);
                if (alphaCode.containsKey(alpha)){
                    encodingData.write(alphaCode.get(alpha));
                } else {
                    System.out.println("encoding error");
                }
            }
        }
        encodingData.close();
        fr2.close();
        br2.close();

        /************************  encoded and table data 를 이용해서 decode ***********************************/
        FileReader fr3 = new FileReader("201702068_encoded.txt");
        FileReader fr4 = new FileReader("201702068_table.txt");
        FileWriter decoded = new FileWriter("201702068_decoded.txt");

        BufferedReader br4 = new BufferedReader(fr4);
        String line4;

        // 인코딩된 데이터를 이용해서 허프만 트리 생성
        Node huffmanTree = new Node('!');
        while((line4 = br4.readLine()) != null){
            StringTokenizer st = new StringTokenizer(line4, " ");
            char alpha = st.nextToken().charAt(0);
            String code = st.nextToken();
            makeHuffmanTree(huffmanTree, alpha, code, 0, code.length()); // 해당 코드를 읽고 트리 업데이트
        }

        BufferedReader br3 = new BufferedReader(fr3);
        String line3;
        while((line3 = br3.readLine()) != null){
            Node tempNode = huffmanTree;
            for (int i = 0; i < line3.length(); i++){
                if (line3.charAt(i) == '0'){
                    tempNode = tempNode.left;
                } else {
                    tempNode = tempNode.right;
                }
                if (tempNode.alpha != '!'){
                    decoded.write(tempNode.alpha);
                    tempNode = huffmanTree;
                }
            }
        }
        decoded.close();
    }

    public static void makeHuffmanTree(Node parent, char alpha, String code, int at, int length){
        if (at == length-1){
            if (code.charAt(at) == '0'){
                parent.left = new Node(alpha);
            } else {
                parent.right = new Node(alpha);
            }
            return;
        }
        if (code.charAt(at) == '0'){
            if (parent.left == null){
                parent.left = new Node('!');
            }
            makeHuffmanTree(parent.left, alpha, code, at+1, length);
        } else {
            if (parent.right == null){
                parent.right = new Node('!');
            }
            makeHuffmanTree(parent.right, alpha, code, at+1, length);
        }
    }
}
