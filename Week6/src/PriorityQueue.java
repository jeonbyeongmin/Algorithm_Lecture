
import java.io.*;
import java.util.Scanner;
import java.util.StringTokenizer;

public class PriorityQueue {
    static int size = 1;
    static heapTree heap[] = new heapTree[50];

    public static class heapTree {
        public int key;
        public String name;
        public heapTree left;
        public heapTree right;

        public heapTree(int key, String name){
            this.key = key;
            this.name = name;
        }
        public heapTree(int key, String name, heapTree left, heapTree right){
            this.key = key;
            this.name = name;
            this.left = left;
            this.right = right;
        }
        public heapTree(heapTree that){
            this.key = that.key;
        }
        public int getKey(){
            return key;
        }
        public String getName(){
            return name;
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
        for (int i = (size-1)/2; i >= 1; i--){
            maxHeapify(heap, i);
            print(heap);
        }
    }

    static void insert(heapTree[] heap, int key, String name) {

        heap[size] = new heapTree(key, name);

        int isRight = size % 2;
        if (size != 1){
            if (isRight == 0) {
                heap[size / 2].left = heap[size];
            } else if (isRight == 1){
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
        if (size <= 1){
            return max;
        }

        int isRight = size % 2;
        if (isRight == 0){
            heap[size/2].left = null;
        } else if (isRight == 1){
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

    static void delete(heapTree[] heap, int x){
        boolean isExist = false;
        for (int i = 1; i < size; i++){
            if (heap[i].key == x){
                isExist = true;
                size--;

                int isRight = size % 2;
                if (isRight == 0){
                    heap[size/2].left = null;
                } else if (isRight == 1){
                    heap[size/2].right = null;
                }

                heap[i].key = heap[size].key;
                heap[i].name = heap[size].name;
                maxHeapify(heap, i);
            }
        }
        if (isExist == false){
            System.out.println("해당 키값을 가진 노드가 없습니다..");
        }
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

    static boolean iskThNumberSmaller(int k, int x){
        // heap 안에 있는 K번째 수가 x보다 작은지 판단
        // 만약 x가 70이라고 가정하고 k가 5라고 하면
        // 힙에서 70보다 큰 수를 카운팅하면 count는 4이다.
        // 그러면 k가 5라는 것은 이 힙에서 5번째로 크다는 것이기 때문에
        // k는 70보다 큰 수일 수가 없다.
        // 따라서 x보다 큰 수를 따라서 루프를 돌면된다.
        // 여기서 힙은 서브트리에서 루트는 항상 자신의 자식들 보다 큰 값을 가진다는 특성을
        // 적용하여 루프를 만들수 있다.

        int biggerCount = 0;
        int indexCount = 1;

        if (heap[indexCount].key < x){
            biggerCount = 0;
        } else {
            while(biggerCount <= k){
                if (heap[indexCount].left.key > x && heap[indexCount].right.key <= x){

                }

            }
        }

        return false;
    }

    public static void main(String[] args) throws IOException{

        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("data06_heap.txt"), "euc-kr"));
        String line;

        while((line = br.readLine()) != null) {
            StringTokenizer st = new StringTokenizer(line, ",");
            int key = Integer.parseInt(st.nextToken());
            String name = st.nextToken();
            insert(heap, key, name);
        }
        print(heap);


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