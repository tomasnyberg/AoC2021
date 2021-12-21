import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

public class Solution {
    public static Integer diceCount = 0;
    public static void main(String[] args){
        long start = System.currentTimeMillis();
        System.out.println(problemOne());
        System.out.println(problemTwo());
        System.out.println("This run took: "  + (System.currentTimeMillis() - start) +"ms");
    }
    
    public static int problemOne(){
        int[] pos = startPos();
        int dice = 1;
        Integer[] scores = {0,0};
        while(scores[0] < 1000 & scores[1] < 1000){
            pos = oneRound(pos, dice, scores);
            dice += 6;
        }
        return Math.min(scores[0], scores[1]) * diceCount;
    }

    public static int[] oneRound(int[] pos, int dice, Integer[] scores){
        // Return new positions (index 0-1), 
        for(int i = 0; i < 3; i++){
            pos[0] += dice++;
            if(dice == 101){
                dice = 1;
            }
            diceCount++;
        } 
        pos[0] = pos[0] % 10 == 0 ? 10:pos[0] % 10;
        scores[0] += pos[0];
        if(scores[0] >= 1000){
            return pos;
        }
        for(int i = 0; i < 3; i++){
            pos[1] += dice++;
            if(dice == 101){
                dice = 1;
            }
            diceCount++;
        }
        pos[1] = pos[1] % 10 == 0 ? 10:pos[1] % 10;
        scores[1] += pos[1];
        if(scores[1] >= 1000){
            return pos;
        }
        return pos; 
    }

    public static long problemTwo(){
        int[] result = {0, 0};
        Long[] ans = recur(startPos(), result, new HashMap<>());
        return Math.max(ans[0], ans[1]); 
    }


    public static Long[] recur(int[] pos, int[] scores, HashMap<String, Long[]> m){
        String mString = ""+pos[0] + pos[1] + scores[0] + scores[1];
        if(scores[0] > 20){
            Long[] result = {1L, 0L};
            return result;
        }
        if(scores[1] > 20){
            Long[] result = {0L, 1L};
            return result;
        }
        if(m.containsKey(mString)){
            return m.get(mString);
        }
        Long[] ans = {0L,0L};
        for(int i = 1; i <= 3; i++){
            for(int j = 1; j <= 3; j++){
                for(int k = 1; k <= 3; k++){
                    int p1next = (pos[0] + i+j+k)%10;
                    int s1 = scores[0] + p1next + 1;
                    int[] newPos = {pos[1], p1next};
                    int[] newScores = {scores[1], s1};
                    Long[] temp = recur(newPos, newScores, m);
                    ans[0] += temp[1];
                    ans[1] += temp[0];
                }
            }
        }
        m.put(mString, ans);
        return ans;
    }



    

    public static int[] startPos(){
        ArrayList<String> list = parseInputToArray();
        int[] result = new int[2];
        String a = list.get(0);
        String b = list.get(1);
        result[0] = Integer.parseInt(a.charAt(a.length() - 1) + "") - 1;
        result[1] = Integer.parseInt(b.charAt(b.length() - 1) + "") - 1;
        return result;
    }

    public static ArrayList<String> parseInputToArray(){
        try {
            BufferedReader reader = new BufferedReader(new FileReader("input.txt"));
            ArrayList<String> list = new ArrayList<>();
            String line = reader.readLine();
            while(line != null){
                list.add(line);
                line = reader.readLine();
            }
            reader.close();
            return list;
        } catch (Exception e){
            System.out.println("error");
            return null;
        }
    }

}
