import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.Buffer;
import java.util.ArrayList;

public class Solution {
    public static void main(String[] args){
        System.out.println(problemOne());
        System.out.println(problemTwo());
    }
    
    public static int problemOne(){
        int[] data = parseInputToArray();
        int count = 0;
        for(int i = 1; i < data.length; i++){
            count += data[i] > data[i-1] ? 1:0;
        }
        return count;
    }

    public static int problemTwo(){
        int[] data = parseInputToArray();
        int count = 0;
        for(int i = 3; i < data.length; i++){
            count += data[i] > data[i-3] ? 1:0; 
        }
        return count;
    }
    public static int[] parseInputToArray(){
        try {
            BufferedReader reader = new BufferedReader(new FileReader("input.txt"));
            ArrayList<String> list = new ArrayList<>();
            String line = reader.readLine();
            while(line != null){
                list.add(line);
                line = reader.readLine();
            }
            return list.stream().mapToInt(x -> Integer.parseInt(x)).toArray();
        } catch (Exception e){
            System.out.println("error");
            return null;
        }
    }

}
