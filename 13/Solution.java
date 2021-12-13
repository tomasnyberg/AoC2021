import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

public class Solution {
    public static void main(String[] args){
        System.out.println(problemOne());
        System.out.println(problemTwo());
    }
    
    public static int problemOne(){
        int[][] matrix = generateArray();
        //filter out all folds to the following format: "x=7", "y=5"
        List<String> folds = generateFolds();
        if(folds.get(0).charAt(0) == 'x'){
            matrix = foldLeft(matrix);
        } else {
            matrix = foldUp(matrix);
        }
        return countdots(matrix);
    }

    // works now
    public static int[][] foldLeft(int[][] matrix){
        int[][] result = new int[matrix.length][matrix[0].length/2];
        for(int i = 0; i < matrix.length; i++){
            //copy the ones that are currently on the left side
            for(int j = 0; j < matrix[0].length/2; j++){
                result[i][j] = matrix[i][j];
            }
            //copy the ones that are on the right side to the left
        }
        for(int i = 0; i < matrix.length; i++){
            for(int j = matrix[0].length/2 + 1; j < matrix[0].length; j++){
                int newcol = j % (matrix[0].length/2 + 1);
                //Math max since we don't want to remove previous ones
                result[i][matrix[0].length/2 - newcol-1] = Math.max(matrix[i][j], result[i][matrix[0].length/2 - newcol-1]); 
            }
        }
        return result;
    }

    //works
    public static int[][] foldUp(int[][] matrix){
        int[][] result = new int[matrix.length/2][matrix[0].length];
        for(int i = 0; i < matrix.length/2; i++){
            for(int j = 0; j < matrix[0].length; j++){
                result[i][j] = matrix[i][j];
            }
        }
        for(int i = matrix.length/2 + 1; i < matrix.length; i++){
            for(int j = 0; j < matrix[0].length; j++){
                int newrow = i % (matrix.length/2 + 1);
                result[matrix.length/2 - newrow - 1][j] = Math.max(matrix[i][j], result[matrix.length/2 - newrow - 1][j]);
            }
        }
        return result;
    }

    public static int problemTwo(){
        int[][] matrix = generateArray();
        //filter out all folds to the following format: "x=7", "y=5"
        List<String> folds = generateFolds();
        System.out.println(folds.size());
        for(String s: folds){
            if(s.charAt(0) == 'x'){
                matrix = foldLeft(matrix);
            } else {
                matrix = foldUp(matrix);
            }
        }
        for(var xs: matrix){
            
            System.out.println(Arrays.toString(xs));
        }
        System.out.println();
        return 0;
    }
    
    public static int countdots(int[][] matrix){
        int sum = 0;
        for(var xs: matrix){
            for(Integer x: xs){
                sum += x;
            }
        }
        return sum;
    }


    public static List<String> generateFolds(){
        ArrayList<String> list = parseInputToArray();
        List<String> result = new ArrayList<>();
        for(String s: list){
            if(!s.isEmpty() && s.startsWith("fold")){
                result.add(s);
            }
        }
        result = result.stream().map(s -> s.split(" ")[2]).toList();
        // for(String s: result){
        //     System.out.println(s);
        // }
        return result;
    }

    public static int[][] generateArray(){
        ArrayList<String> list = parseInputToArray();
        ArrayList<String> coords = new ArrayList<>();
        for(String s: list){
            if(!s.isEmpty() && Character.isDigit(s.charAt(0))){
                coords.add(s);
            }
        }
        int[] dimensions = new int[2];
        for(String s: coords){
            int x = Integer.parseInt(s.split(",")[0]);
            int y = Integer.parseInt(s.split(",")[1]);
            dimensions[0] = Math.max(dimensions[0], x);
            dimensions[1] = Math.max(dimensions[1], y);
        }
        int[][] result = new int[dimensions[1] + 1][dimensions[0] + 1];
        for(String s: coords){
            int x = Integer.parseInt(s.split(",")[1]);
            int y = Integer.parseInt(s.split(",")[0]);
            result[x][y] = 1;
        }
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
            reader.close();
            return list;
        } catch (Exception e){
            System.out.println("error");
            return null;
        }
    }

}
