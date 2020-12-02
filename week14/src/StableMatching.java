import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class StableMatching {

    public static List<String> men = new ArrayList<>();
    public static List<String> women = new ArrayList<>();
    public static HashMap<String, List<String>> menPreference = new HashMap<>();
    public static HashMap<String, List<String>> womenPreference = new HashMap<>();

    public static void main(String[] args) throws IOException {
        FileReader fr = new FileReader("data14.txt");
        BufferedReader br = new BufferedReader(fr);
        String line;

        int count = 0;
        while(count < 3){
            line = br.readLine();
            StringTokenizer st = new StringTokenizer(line, " ");

            String currentMan = st.nextToken();
            men.add(currentMan);

            int count2 = 0;
            ArrayList<String> women = new ArrayList<>();
            while(count2 < 3){
                women.add(st.nextToken());
                count2++;
            }
            menPreference.put(currentMan, women);
            count++;
        }

        count = 0;
        while(count < 3){
            line = br.readLine();
            StringTokenizer st = new StringTokenizer(line, " ");

            String currentWoman = st.nextToken();
            women.add(currentWoman);

            int count2 = 0;
            ArrayList<String> men = new ArrayList<>();
            while(count2 < 3){
                men.add(st.nextToken());
                count2++;
            }
            womenPreference.put(currentWoman, men);
            count++;
        }

        Map<String, String> womenXmen = stableMatching();

        int matchCount = 1;
        for(String woman : womenXmen.keySet()){
            System.out.println(matchCount+ "번째 커플 : " + woman + " X " + womenXmen.get(woman));
            matchCount++;
        }
    }
    public static Map<String, String> stableMatching() {

        List<String> freeMen = new ArrayList<>(men);
        Map<String, String> womenXmen = new TreeMap<String, String>();

        while(!freeMen.isEmpty()) {
            String currentMan = freeMen.remove(0);
            List<String> currentManPreference = menPreference.get(currentMan);

            for(String woman : currentManPreference) {
                if(womenXmen.get(woman) == null) {
                    womenXmen.put(woman, currentMan);
                    break;
                } else {
                    String oldMan = womenXmen.get(woman);
                    List<String> currentWomanPreference = womenPreference.get(woman);                             // 이번 여자의 우선순위를 가져와서
                    if(currentWomanPreference.indexOf(currentMan) < currentWomanPreference.indexOf(oldMan)){      // 여자 입장에서 전에 매칭된 남자와 이번 남자의 우선 순위를 따져본다.
                        womenXmen.put(woman, currentMan);                                                         // 이번 남자의 승리로 이어지면, 이번 여자와 남자가 재매칭되고
                        freeMen.add(oldMan);                                                                      // 이전에 매칭되었던 비운의 남자는 다시 Free Man이 된다.
                        break;
                    }
                }
            }
        }
        return womenXmen;
    }
}