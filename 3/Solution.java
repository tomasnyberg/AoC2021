import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

public class Solution {
    public static void main(String[] args){
        System.out.println(problemOne());
        System.out.println(problemTwo());
    }
    
    public static int problemOne(){
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
            gamma += counter[i] >= list.size()/2 ? "1":"0";
            epsilon += counter[i] >= list.size()/2 ? "0":"1";
        }
        return Integer.parseInt(gamma, 2) * Integer.parseInt(epsilon, 2);
    }
    
    
    // Måste räkna om counter för varje gång man tar bort ett element
    public static int problemTwo(){
        List<String> list = parseInputToArray();
        List<String> co2List = parseInputToArray();
        filterList(co2List, false);
        filterList(list, true);
        return Integer.parseInt(list.get(0), 2) * Integer.parseInt(co2List.get(0), 2);
    }

    public static void filterList(List<String> list, boolean change){
        int col = 0;
        while(list.size() > 1){
            // Count the amount of ones in the current position (i)
            int ones = 0;
            for(int j = 0; j < list.size(); j++){
                ones += list.get(j).charAt(col) == '0' ? 0:1;
            }
            char curr = (list.size()+1)/2 <= ones ? '0' : '1';
            curr = change ? curr:curr == '1' ? '0':'1';
            for(int j = list.size() - 1; j >= 0; j--){
                if(list.get(j).charAt(col) != curr){
                    list.remove(j);
                }
                if(list.size() == 1){
                    return;
                }
            }
            col++;
        }
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
