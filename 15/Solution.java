import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

public class Solution {
    public static void main(String[] args){
        System.out.println(problemOne());
        System.out.println(problemTwo());
    }
    
    public static int problemOne(){
        int[][] matrix = parseToMatrix();
        int[][] dp = new int[matrix.length][matrix[0].length];
        for(var xs: dp){
            Arrays.fill(xs, Integer.MAX_VALUE);
        }
        fill(0,0, matrix, dp, 0);
        return dp[dp.length -1][dp[0].length -1] - matrix[0][0];
    }

    public static void fill(int row, int col, int[][] matrix, int[][] dp, int path){
        // OOB check
        if(row < 0 || col < 0 || row >= matrix.length || col >= matrix[0].length){
            return;
        }
        int currCost = path + matrix[row][col];
        if(currCost > dp[row][col]){
            return;
        }
        if(dp[row][col] > currCost){
            dp[row][col] = currCost;
            fill(row + 1, col, matrix, dp, currCost);
            fill(row, col + 1, matrix, dp, currCost);
            fill(row, col - 1, matrix, dp, currCost);
        }   
    }
    // 2850 too high
    public static int problemTwo(){
        int[][] bigMatrix = biggerMatrix();
        int[][] dp = new int[bigMatrix.length][bigMatrix[0].length];
        for(var xs: dp){
            Arrays.fill(xs, Integer.MAX_VALUE);
        }
        fill(0,0, bigMatrix, dp, 0);
        return dp[dp.length -1][dp[0].length -1] - bigMatrix[0][0];
    }

    public static int[][] parseToMatrix(){
        ArrayList<String> list = parseInputToArray();
        int[][] result = new int[list.size()][list.get(0).length()];
        for(int i = 0; i < list.size(); i++){
            for(int j = 0; j < list.get(0).length(); j++){
                result[i][j] = Integer.parseInt(list.get(i).charAt(j) + "");
            }
        }
        return result;
    }

    public static int[][] biggerMatrix(){
        int[][] original = parseToMatrix();
        int[][] bigger = new int[original.length * 5][original[0].length * 5];
        for(int i = 0; i < bigger.length; i++){
            for(int j = 0; j < bigger[0].length; j++){
                int multiplier = i / original.length + j / original[0].length;
                int newVal = (original[i % original.length][j % original[0].length] + multiplier);
                newVal += newVal / 10 >= 1 ? 1:0; 
                bigger[i][j] = newVal % 10;
            }
        }
        return bigger;
    }

    public static ArrayList<String> parseInputToArray(){
        try {
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
