
import java.io.*;
import java.util.Scanner;
import java.util.StringTokenizer;

public class PriorityQueue {
    static int size = 1;
    static heapTree[] heap = new heapTree[1000];
    public static class heapTree {
        public int key;
        public String name;
        public heapTree left;
        public heapTree right;

        public heapTree(int key, String name){
            this.key = key;
            this.name = name;
        }
    }

    static void maxHeapify(heapTree[] heap, int target) {

        int lChild = target * 2;
        int rChild = target * 2 + 1;

        int largest;

        if (heap[target].left != null && heap[lChild].key > heap[target].key) {
            largest = lChild;
        } else {
            largest = target;
        }
        if (heap[target].right != null && heap[rChild].key > heap[largest].key){
            largest = rChild;
        }
        if (largest != target){
            int tempKey = heap[target].key;
            String tempName = heap[target].name;

            heap[target].key = heap[largest].key;
            heap[target].name = heap[largest].name;

            heap[largest].key = tempKey;
            heap[largest].name = tempName;

            maxHeapify(heap, largest);
        }
    }

    static void buildMaxHeap(heapTree[] heap){
        for (int i = size/2; i >= 1; i--){
            maxHeapify(heap, i);
        }
    }

    static void insert(heapTree[] heap, int key, String name) {

        heap[size] = new heapTree(key, name);

        int isRight = size % 2;
        if (size != 1){
            if (isRight == 0) {
                heap[size / 2].left = heap[size];
            } else {
                heap[size / 2].right = heap[size];
            }
        }

        int targetKey = heap[size].key;
        String targetName = heap[size].name;
        int current = size;

        while(current != 1){
            if(heap[current/2].key > targetKey) {
                break;
            }

            else {
                heap[current].key = heap[current/2].key;
                heap[current].name = heap[current/2].name;
                current = current/2;
            }
        }

        heap[current].key = targetKey;
        heap[current].name = targetName;

        size++;
    }

    static heapTree max(heapTree[] heap){
        return heap[1];
    }

    static heapTree extractMax(heapTree[] heap){
        heapTree max = max(heap);
        size--;

        int isRight = size % 2;
        if (isRight == 0){
            heap[size/2].left = null;
        } else {
            heap[size/2].right = null;
        }

        heap[1].key = heap[size].key;
        heap[1].name = heap[size].name;
        maxHeapify(heap, 1);

        return max;
    }

    static void increase_key(heapTree[] heap, int index, int keyUp){
        if (heap[index].key < keyUp){
            heap[index].key = keyUp;
            buildMaxHeap(heap);
        } else {
            System.out.println("해당 노드의 키값이 이미 더 크거나 같습니다.");
        }
    }

    static void delete(heapTree[] heap, int index){
        size--;

        int isRight = size % 2;
        if (isRight == 0){
            heap[size/2].left = null;
        } else if (isRight == 1){
            heap[size/2].right = null;
        }

        heap[index].key = heap[size].key;
        heap[index].name = heap[size].name;
        buildMaxHeap(heap);
    }

    static void print(heapTree[] heap){
        System.out.println("**** 현재 우선순위 큐에 저장되어 있는 작업 대기 목록은 다음과 같습니다. ****");
        System.out.println();
        for (int i = 1; i < size; i++){
            System.out.println(heap[i].key + ", " + heap[i].name);
        }
        System.out.println();
        System.out.println("--------------------------------------------------");
        System.out.println("1. 작업추가        2.최대값        3. 최대 우선순위 작업처리 ");
        System.out.println("4. 원소 키값 증가         5. 작업 제거         6. 종료 ");
        System.out.println("--------------------------------------------------");
        System.out.println();
    }


    static int count = 0;
    static boolean iskThNumberSmaller(heapTree heap, int k, int x){
        if (heap.key > x){
            count++;
            if (count >= k){
                return false; // count가 이미 k개 이상이이라는 것은 k번째 수도 x보다 크다는 것.
            }
            iskThNumberSmaller(heap.left, k, x);
            iskThNumberSmaller(heap.right, k, x);
        }
        if (count < k){ // x보다 큰 값들의 개수가 k보다 작다는 것은 k번째부터 x보다 작다는 것을 의미.
            return true;
        } else {
            return false;
        }
    }

    public static void main(String[] args) throws IOException{

        BufferedReader br = new BufferedReader(new InputStreamReader
                (new FileInputStream("data06_heap.txt"), "euc-kr"));
        String line;

        while((line = br.readLine()) != null) {
            StringTokenizer st = new StringTokenizer(line, ",");
            int key = Integer.parseInt(st.nextToken());
            String name = st.nextToken();
            insert(heap, key, name);
        }
        print(heap);


//        System.out.println("4번째로 큰 수가 81보다 작은가? : " + iskThNumberSmaller(max(heap), 4, 81));
//        System.out.println("X보다 큰 수의 개수 : " + count);

        Scanner sc = new Scanner(System.in);
        int check = 0;

        while(check != 1){
            int inputNumber = sc.nextInt();
            switch (inputNumber) {
                case 1:
                    System.out.println("키값과 이름을 입력해주세요.");
                    int inputKey = sc.nextInt();
                    String inputName = sc.next();
                    insert(heap, inputKey, inputName);
                    print(heap);
                    break;
                case 2:
                    if (size == 1){
                        System.out.println("heap이 비었습니다.");
                    } else {
                        System.out.println("최대값은 " + max(heap).key +"," + max(heap).name + "입니다.");
                    }

                    break;
                case 3:
                    if (size > 1){
                        extractMax(heap);
                    } else {
                        System.out.println("heap이 비었습니다.");
                    }
                    print(heap);
                    break;
                case 4:
                    System.out.println("현재 키와 이보다 더 큰 수를 입력해주세요.");
                    int currentKey = sc.nextInt();
                    int newKey = sc.nextInt();
                    increase_key(heap, currentKey, newKey);
                    print(heap);
                    break;
                case 5:
                    if (size > 1){
                        System.out.println("제거할 키를 입력해주세요.");
                        int deleteNodeKey = sc.nextInt();
                        delete(heap, deleteNodeKey);
                    } else {
                        System.out.println("heap이 비었습니다.");
                    }
                    print(heap);
                    break;
                case 6:
                    System.out.println("프로그램을 종료합니다.");
                    check = 1;
                    break;
                default:
                    System.out.println("1~6까지 숫자를 입력해주세요.");
                    print(heap);
                    break;
            }
        }
    }
}