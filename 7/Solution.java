import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.*;

public class Solution {
    public static void main(String[] args){
        System.out.println(problemOne());
        System.out.println(problemTwo());
    }
    
    public static int problemOne(){
        return solve(false);
    }

    public static int problemTwo(){
        return solve(true);
    }

    public static int solve(boolean second){
        List<Integer> list = Arrays.stream(parseInputToArray().get(0).split(",")).mapToInt(Integer::parseInt).boxed().toList();
        int[] counter = new int[2000];
        list.stream().forEach(x -> counter[x]++);
        int shortest = Integer.MAX_VALUE;
        for(int i = 0; i < counter.length; i++){
            int sum = 0;
            for(int j = 0; j < counter.length; j++){
                if(second){
                    sum += (Math.abs(i - j) *(Math.abs(i-j) + 1))/2 * counter[j]; 
                } else {
                    sum += Math.abs(i - j) * counter[j];
                }
            }
            shortest = sum < shortest ? sum:shortest;
        }
        return shortest;
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
