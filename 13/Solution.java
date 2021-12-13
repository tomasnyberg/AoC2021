import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;
import java.util.stream.IntStream;

public class Solution {
    public static void main(String[] args){
        System.out.println(problemOne());
        problemTwo();
    }
    
    public static int problemOne(){
        int[][] matrix = generateArray();
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
        }
        for(int i = 0; i < matrix.length; i++){
            //copy the ones that are on the right side to the left
            for(int j = matrix[0].length/2 + 1; j < matrix[0].length; j++){
                int newcol = matrix[0].length/2 - j % (matrix[0].length/2 + 1) - 1;
                //Math max since we don't want to remove previous ones
                int val = Math.max(matrix[i][j], result[i][newcol]);
                result[i][newcol] = val; 
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
                int newrow = matrix.length/2 - i % (matrix.length/2 + 1) - 1;
                int val = Math.max(matrix[i][j], result[newrow][j]);
                result[newrow][j] = val;
            }
        }
        return result;
    }

    public static void problemTwo(){
        int[][] matrix = generateArray();
        List<String> folds = generateFolds();
        for(String s: folds){
            if(s.charAt(0) == 'x'){
                matrix = foldLeft(matrix);
            } else {
                matrix = foldUp(matrix);
            }
        }
        prettyPrint(matrix);
    }
    
    public static void prettyPrint(int[][] matrix){
        for(int i = 0;i < matrix.length; i++){
            for(int j = 0; j < matrix[0].length; j++){
                if(j % 5 == 0 && j != 0){
                    System.out.print("   ");
                }
                System.out.print(matrix[i][j] == 1 ? "X":" ");
            }
            System.out.println();
        }
    }
    
    public static int countdots(int[][] matrix){
        return Arrays.stream(matrix).map(xs -> IntStream.of(xs).sum())
                                        .mapToInt(x -> (int) x).sum();
    }

    //filter out all folds to the following format: "x=7", "y=5"
    public static List<String> generateFolds(){
        return parseInputToArray().stream()
                .filter(s -> !s.isEmpty() && s.startsWith("fold"))
                .map(s -> s.split(" ")[2])
                .toList();
    }

    public static int[][] generateArray(){
        List<String> list = parseInputToArray();
        list = list.stream().filter(s -> !s.isEmpty() && Character.isDigit(s.charAt(0))).toList();
        //get max dimensions
        int[] dimensions = new int[2];
        dimensions[0] = list.stream().mapToInt(s -> Integer.parseInt(s.split(",")[0])).max().getAsInt();
        dimensions[1] = list.stream().mapToInt(s -> Integer.parseInt(s.split(",")[1])).max().getAsInt();
        //Insert all ones
        int[][] result = new int[dimensions[1] + 1][dimensions[0] + 1];
        for(String s: list){
            int x = Integer.parseInt(s.split(",")[1]);
            int y = Integer.parseInt(s.split(",")[0]);
            result[x][y] = 1;
        }
        return result;
    }

    public static List<String> parseInputToArray(){
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
