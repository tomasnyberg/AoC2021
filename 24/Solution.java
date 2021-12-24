import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

public class Solution {
    public static void main(String[] args){
        long start = System.currentTimeMillis();
        System.out.println(problemOne());
        System.out.println(problemTwo());
        System.out.println("This run took: "  + (System.currentTimeMillis() - start) +"ms");
    }

    //Both results are derived from the following (specific to my input):
    // i[5] = i[4] + 4
    // i[7] = i[6] + 2
    // i[8] = i[3] + 8
    // i[10] = i[9]
    // i[11] = i[2] - 5
    // i[12] = i[1] + 7
    // i[13] = i[0] - 1
    // where i[0] is the first index of the input string, i[1] the second etc

    public static long problemOne(){
        return runOperations("92915979999498");
    }
        
    public static long problemTwo(){
        return runOperations("21611513911181");
    }

    public static long runOperations(String s){
        long w = 0;
        long x = 0;
        long y = 0;
        long z = 0;
        String testInput = s;
        int inpidx = 0;
        ArrayList<String> instructions = parseInputToArray();
        for(String instruction: instructions){
            String[] split = instruction.split(" ");
            String op = split[0];
            String dest = split[1];
            String valueStr = "0";
            if(!op.equals("inp")){
                valueStr = split[2];
            }
            long value = 0;
            switch(valueStr){
                case "w":
                    value = w;
                    break;
                case "x":
                    value = x;
                    break;
                case "y":
                    value = y;
                    break;
                case "z":
                    value = z;
                    break;
                default:
                    value = Long.parseLong(valueStr);
            }
            switch(dest){
                case "w":
                    w = Long.parseLong(testInput.charAt(inpidx++) + "");
                    break;
                case "x":
                    x = executeOp(op, x, value);
                    break;
                case "y":
                    y = executeOp(op, y, value);
                    break;
                case "z":
                    z = executeOp(op, z, value);
                    break;
                default:
                    throw new IllegalArgumentException("couldn't find a dest, breaking");
                }
            }
        return z;
    }
        
    public static long executeOp(String operation, long variable, long value){
        switch(operation){
            case "add":
                return variable + value;
            case "mul":
                return variable*value;
            case "div":
                return variable / value;
            case "mod":
                return variable % value;
            case "eql":
                return variable == value ? 1:0;
            default:
                throw new IllegalArgumentException("unknwon operation : " + operation +  ", breaking");
        }
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
