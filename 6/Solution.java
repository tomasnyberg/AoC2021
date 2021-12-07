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
        String input = parseInputToArray().get(0);
        int[] counter = new int[9];
        Arrays.stream(input.split(",")).mapToInt(Integer::parseInt).forEach(x -> counter[x]++);
        for(int i = 0; i < 80; i++){
            iterate(counter);
        }
        System.out.println(Arrays.toString(counter));
        int sum = 0;
        for(Integer x: counter){
            sum += x;
        }
        return sum;
    }
    
    public static void iterate(int[] counter){
        int temp = counter[0];
        for(int i = 0; i < counter.length-1; i++){
            counter[i] = counter[i+1];
        }
        counter[8] = temp;
        counter[6] += temp;
    }
    public static void iterate(Long[] counter){
        Long temp = counter[0];
        for(int i = 0; i < counter.length-1; i++){
            counter[i] = counter[i+1];
        }
        counter[8] = temp;
        counter[6] += temp;
    }
    
    public static Long problemTwo(){
        String input = parseInputToArray().get(0);
        Long[] counter = new Long[9];
        Arrays.fill(counter, 0L);
        Arrays.stream(input.split(",")).mapToInt(Integer::parseInt).forEach(x -> counter[x]++);
        for(int i = 0; i < 256; i++){
            iterate(counter);
        }
        System.out.println(Arrays.toString(counter));
        Long sum = 0L;
        for(Long x: counter){
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
