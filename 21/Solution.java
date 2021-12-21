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

    // 911090395997650 too low
    // Idea: keep a memo map with strings as keys that contains every single combination of positions and steps
    // The value is 1 for player 1 win, 0 for player 2 win, so we simply sum all the entries and count the 1s
    public static long problemTwo(){
        int[] pos = startPos();
        Long[] ans = recur(pos[0]-1, pos[1]-1, 0, 0, new HashMap<>());
        System.out.println(Arrays.toString(ans));
        return Math.max(ans[0], ans[1]); 
    }

    
    //No return value since the result is simply the sum of all memo

    public static Long[] recur(int p1, int p2, int s1, int s2, HashMap<String, Long[]> m){
        String mString = p1 + " " + p2 + " " + s1 + " " + s2;
        if(s1 > 20){
            Long[] result = {1L, 0L};
            return result;
        }
        if(s2 > 20){
            Long[] result = {0L, 1L};
            return result;
        }
        if(m.containsKey(mString)){
            return m.get(mString);
        }
        Long[] ans = {0L,0L};
        for(int d1 = 1; d1 <= 3; d1++){
            for(int d2 = 1; d2 <= 3; d2++){
                for(int d3 = 1; d3 <= 3; d3++){
                    int newP1 = (p1 + d1 + d2 + d3)%10;
                    int newS1 = s1 + newP1 + 1;

                    Long[] temp = recur(p2, newP1, s2, newS1, m);
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
        result[0] = Integer.parseInt(a.charAt(a.length() - 1) + "");
        result[1] = Integer.parseInt(b.charAt(b.length() - 1) + "");
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
