import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.StringTokenizer;

public class ClosestPairOfPoint {

    public static ArrayList<location> locationList = new ArrayList<>();

    public static class location {
        public double x;
        public double y;

        public location(double x, double y){
            this.x = x;
            this.y = y;
        }
        public double getX() {
            return x;
        }
        public double getY() {
            return y;
        }
    }

    // x값을 기준으로 sorting 되어 있다고 가정.
    static double closestPair(int start, int end){

        // 좌표 수가 3이하면 브루트포스로 계산한다.
        if (end-start+1 <= 3){
            if (end-start == 2){
                return bruteForce_ThreeElements(locationList.get(start),
                        locationList.get(start+1), locationList.get(end));
            } else if (end-start == 1){
                return bruteForce_TwoElements(locationList.get(start), locationList.get(end));
            } else {
                return -1;
            }
        }

        // 브루트포스로 구한 값부터 차근차근 왼쪽 구역, 오른쪽 구역까지 계산하여 d를 만들게 됨.
        int mid = (start + end)/2;
        double d1 = closestPair(start, mid); // 왼쪽 구역에서의 최단 거리 계산
        double d2 = closestPair(mid+1, end); // 오른쪽 구역에서의 최단 거리 계산
        double d = Math.min(d1, d2);

        // N/2 지점에서 x좌표 값으로부터 d 이내에 있는 좌표만 분리하기 위한 temp Array
        ArrayList<location> dAwayFromL = new ArrayList<>();
        location midValue = locationList.get(mid);

        // 분리하는 과정.
        for (int i = start; i <= end; i++){
            double xLengthFromL = Math.abs(midValue.x - locationList.get(i).x);
            if (xLengthFromL < d){
                dAwayFromL.add(locationList.get(i));
            }
        }

        // 분리한 좌표를 y를 기준으로 Sort
        dAwayFromL.sort(new Comparator<location>() {
            @Override
            public int compare(location o1, location o2) {
                if (o1.y > o2.y) {
                    return 1;
                } else if (o1.y == o2.y){
                    return 0;
                } else {
                    return -1;
                }
            }
        });

        // Window 내부의 최단거리를 구한다.
        for (int i = 0; i < dAwayFromL.size(); i++){
            for (int j = i+1; j < dAwayFromL.size(); j++){
                location p1 = dAwayFromL.get(i);
                location p2 = dAwayFromL.get(j);
                if (p2.y - p1.y < d){ // y이 d보다 크면 계산의 의미가 없음.
                    double lengthOfTwoPoint = bruteForce_TwoElements(p1, p2);
                    d = Math.min(d, lengthOfTwoPoint);
                } else {
                    break;
                }
            }
        }
        return d;
    }
    static double bruteForce_TwoElements(location p1, location  p2){
        double x1 = p1.x;
        double y1 = p1.y;

        double x2 = p2.x;
        double y2 = p2.y;

        return Math.sqrt((x2-x1)*(x2-x1) + (y2-y1)*(y2-y1));
    }
    static double bruteForce_ThreeElements(location p1, location p2, location p3){
        double length1 = bruteForce_TwoElements(p1, p2);
        double length2 = bruteForce_TwoElements(p2, p3);
        double length3 = bruteForce_TwoElements(p1, p3);

        return Math.min(length1, Math.min(length2, length3));
    }


    public static void main(String[] args) throws IOException {
        FileReader fr = new FileReader("data05_closest.txt");
        BufferedReader br = new BufferedReader(fr);
        String line;
        while((line = br.readLine()) != null){
            StringTokenizer st = new StringTokenizer(line,",");
            double x = Double.parseDouble(st.nextToken());
            double y = Double.parseDouble(st.nextToken());
            location loc = new location(x, y);
            locationList.add(loc);
        }

        // x를 기준으로 Sort
        locationList.sort(new Comparator<location>() {
            @Override
            public int compare(location o1, location o2) {
                if (o1.x > o2.x){
                    return 1;
                } else if (o1.x == o2.x){
                    return 0;
                } else {
                    return -1;
                }
            }
        });
        // 결과값 출력.
        System.out.println("Output : " + closestPair(0, locationList.size()-1));
    }
}
