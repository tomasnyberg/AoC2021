import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.*;
/*
----0----
|       |
1       2
|       |
|---3---|
|       |
4       5
|       |
----6----

*/
public class Solution {
    public static void main(String[] args){
        System.out.println(problemOne());
        System.out.println(problemTwo());
    }
    
    public static int problemOne(){
        ArrayList<String> list = parseInputToArray();
        int sum = 0;
        Set<Integer> set = new HashSet<>(Arrays.asList(2,3,4,7));
        for(String s: list){
            String[] parts = s.split("\s\\|\s")[1].split(" ");
            for(String part: parts){
                if(set.contains(part.length())){
                    sum++;
                }
            }
        }
        return sum;
    }

    public static int problemTwo(){
        ArrayList<String> list = parseInputToArray();
        int sum = 0;
        for(String s: list){
            String[] secondPart = s.split("\s\\|\s")[1].split(" ");  
            int[] counter = new int[7];
            // count the amount of chars in total across the whole input string (before |)
            for(String digit: s.split("\s\\|\s")[0].split(" ")){
                digit.chars().forEach(x -> counter[x - 97]++);
            }
            String allOutputs = generateOutput(counter, secondPart);
            sum += Integer.parseInt(allOutputs);
        }
        return sum;
    }
    
    public static String generateOutput(int[] counter, String[] secondPart){
        HashMap<String, Integer> m = generateMap();
        String output = "";
        for(int i = 0; i < secondPart.length; i++){
            StringBuilder sb = new StringBuilder();
            for(Character x: secondPart[i].toCharArray()){
                sb.append(counter[x-97]);
            }
            char[] ca = sb.toString().toCharArray();
            Arrays.sort(ca);
            output += m.get(String.valueOf(ca));
        }
        return output;
    }

    // {'467889': 0, '89': 1, '47788': 2, '77889': 3, '6789': 4, '67789': 5, '467789': 6, '889': 7, '4677889': 8, '677889': 9}
    public static HashMap<String, Integer> generateMap(){
        HashMap<String, Integer> result = new HashMap<>();
        result.put("467889", 0);
        result.put("89", 1);
        result.put("47788", 2);
        result.put("77889", 3);
        result.put("6789", 4);
        result.put("67789", 5);
        result.put("467789", 6);
        result.put("889", 7);
        result.put("4677889", 8);
        result.put("677889", 9);
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
            return list;
        } catch (Exception e){
            System.out.println("error");
            return null;
        }
    }

}
