import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

public class Solution {
    public static void main(String[] args){
        // System.out.println(problemOne());
        System.out.println(problemTwo());
    }
    
    public static String[] problemOne(){
        List<String> list = parseInputToArray();
        int[] counter = new int[list.get(0).length()];
        for(String s: list){
            for(int i = 0; i < list.get(0).length(); i++){
                counter[i] += Integer.parseInt(s.charAt(i) + "");
            }
        }
        String gamma = "";
        String epsilon = "";
        for(int i = 0; i < list.get(0).length(); i++){
            if(counter[i] >= list.size()/2){
                gamma += "1";
                epsilon += "0";
            } else {
                gamma += "0";
                epsilon += "1";
            }
        }
        System.out.println("Gamma: " + gamma + " Epsilon: " + epsilon);
        String[] result = {gamma, epsilon};
        return result;
    }
    
    
    // Måste räkna om counter för varje gång man tar bort ett element
    public static int problemTwo(){
        List<String> oxygenList = parseInputToArray();
        List<String> co2List = parseInputToArray();
        int wordLen = oxygenList.get(0).length();
        int oxygen = 0;
        int co2 = 0;
        int col = 0;
        while(oxygenList.size() > 1){
            // Count the amount of ones in the current position (i)
            int ones = 0;
            int zeroes = 0;
            for(int j = 0; j < oxygenList.size(); j++){
                if(oxygenList.get(j).charAt(col) == '0'){
                    zeroes++;
                } else {
                    ones++;
                }
            }
            char curr = ones >= zeroes ? '1':'0';
            for(int j = oxygenList.size() - 1; j >= 0; j--){
                if(oxygenList.get(j).charAt(col) != curr){
                    oxygenList.remove(j);
                }
            }
            col++;
        }
        col = 0;
        while(co2List.size() > 1){
            // Count the amount of ones in the current position (i)
            int ones = 0;
            int zeroes = 0;
            for(int j = 0; j < co2List.size(); j++){
                if(co2List.get(j).charAt(col) == '0'){
                    zeroes++;
                } else {
                    ones++;
                }
            }
            char curr = zeroes <= ones ? '0' : '1';
            for(int j = co2List.size() - 1; j >= 0; j--){
                if(co2List.get(j).charAt(col) != curr){
                    co2List.remove(j);
                }
            }
            col++;
        }
        System.out.println(oxygenList.get(0));
        System.out.println(co2List.get(0));
        oxygen = Integer.parseInt(oxygenList.get(0), 2);
        co2 = Integer.parseInt(co2List.get(0), 2);
        return oxygen * co2;
    }






    public static List<String> parseInputToArray(){
        try {
            BufferedReader reader = new BufferedReader(new FileReader("input.txt"));
            List<String> list = new ArrayList<>();
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
