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
    
    public static long problemOne(){
        return solve(80);
    }
    
    public static long problemTwo(){
        return solve(256);
    }
    
    public static void iterate(long[] counter){
        long temp = counter[0];
        for(int i = 0; i < counter.length-1; i++){
            counter[i] = counter[i+1];
        }
        counter[8] = temp;
        counter[6] += temp;
    }

    public static long solve(int iterations){
        String input = parseInputToArray().get(0);
        long[] counter = new long[9];
        Arrays.stream(input.split(",")).mapToInt(Integer::parseInt).forEach(x -> counter[x]++);
        for(int i = 0; i < iterations; i++){
            iterate(counter);
        }
        long sum = 0L;
        for(long x: counter){
            sum += x;
        }
        return sum;
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
