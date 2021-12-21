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
    
    // 5685 too high
    // 5568 too low
    // 5326 answer for input2
    public static int problemOne(){
        String template = parseInputToArray().get(0);
        char[][] smallMatrix = getInitialMatrix();
        char[][] bigMatrix = new char[1000][1000];
        //copy the elements from initial array
        for(int i = 500; i < 500 + smallMatrix.length; i++){
            for(int j = 500; j < 500 + smallMatrix[0].length; j++){
                bigMatrix[i][j] = smallMatrix[i-500][j-500];
            }
        }
        bigMatrix = stepMatrix(bigMatrix, template);
    }

    public static char[][] stepMatrix(char[][] matrix, String template){
        
    }

    public static 


    public static int problemTwo(){
        return 0;
    }


    public static int countHashTags(char[][] matrix){
        int count = 0;
        for(var xs: matrix){
            for(char c: xs){
                count += c == '#' ? 1:0;
            }
        }
        return count;
    }

    public static char[][] getInitialMatrix(){
        ArrayList<String> list = parseInputToArray();
        String l = list.get(2);
        char[][] result = new char[list.size()-2][l.length()];
        for(int i = 2; i < list.size(); i++){
            String curr = list.get(i);
            for(int j = 0; j < l.length(); j++){
                result[i-2][j] = curr.charAt(j);
            }
        }
        return result;
    }


    public static ArrayList<String> parseInputToArray(){
        try {
            // input 1 changed first character in template
            BufferedReader reader = new BufferedReader(new FileReader("input1.txt"));
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
