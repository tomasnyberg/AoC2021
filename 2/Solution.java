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
        return solve(false);
    }
    
    public static int problemTwo(){
        return solve(true);
    }

    public static int solve(boolean second){
        ArrayList<String[]> list = parseInputToArray();
        int depth = 0;
        int horizontal = 0;
        int aim = 0;
        for(String[] xs: list){
            String direction = xs[0];
            int amount = Integer.parseInt(xs[1]);
            switch(direction){
                case "forward":
                    horizontal += amount;
                    if(second){
                        depth += aim*amount;
                    }
                    break;
                case "down":
                    if(second){
                        aim += amount;
                    } else {
                        depth += amount;
                    }
                    break;
                default:
                    if(second){
                        aim -= amount;
                    } else {
                        depth -= amount;
                    }
                    break;
            }
        }
        return depth * horizontal;
    }

    public static ArrayList<String[]> parseInputToArray(){
        try {
            BufferedReader reader = new BufferedReader(new FileReader("input.txt"));
            ArrayList<String[]> list = new ArrayList<>();
            String line = reader.readLine();
            while(line != null){
                String[] xs = line.split(" ");
                list.add(xs);
                line = reader.readLine();
            }
            return list;
        } catch (Exception e){
            System.out.println("error");
            return null;
        }
    }

}
