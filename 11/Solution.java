import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.Buffer;
import java.util.*;

public class Solution {
    public static void main(String[] args){
        System.out.println(problemOne());
        System.out.println(problemTwo());
    }

    // 1690 too high
    public static int problemOne(){
        int[][] matrix = parseInputToArray();
        boolean[][] alreadyFlashed = new boolean[matrix.length][matrix[0].length];
        int sum = 0;
        for(int i = 0; i < 100; i++){
            incAll(matrix);
            sum += oneStep(matrix, alreadyFlashed, 0);
        }
        printMatrix(matrix);
        return sum;
    }

    public static int problemTwo(){
        int[][] matrix = parseInputToArray();
        boolean[][] alreadyFlashed = new boolean[matrix.length][matrix[0].length];
        for(int i = 0; i < 1000; i++){
            incAll(matrix);
            if(oneStep(matrix, alreadyFlashed, 0) == matrix.length * matrix[0].length){
                return i;
            } 
        }
        return 0;
    }

    public static int oneStep(int[][] matrix, boolean[][] alreadyFlashed, int count){
        for(int i = 0; i < matrix.length; i++){
            for(int j = 0; j < matrix[0].length; j++){
                if(matrix[i][j] >= 10 && !alreadyFlashed[i][j]){
                    count++;
                    alreadyFlashed[i][j] = true;
                    flash(matrix, alreadyFlashed, i, j);
                    return oneStep(matrix, alreadyFlashed, count);
                }
            }
        }
        for(int i = 0; i < matrix.length; i++){
            for(int j = 0; j < matrix[0].length; j++){
                if(matrix[i][j] >= 10){
                    matrix[i][j] = 0;
                }
            }
        }
        for(var xs: alreadyFlashed){
            Arrays.fill(xs, false);
        }
        return count;
    }

    public static void flash(int[][] matrix, boolean[][] alreadyFlashed, int row, int col){
        for(int i = -1; i <= 1; i++){
            for(int j = -1; j <= 1; j++){
                int crow = row + i;
                int ccol = col + j;
                // inbounds check, and check that hasn't flashed already
                boolean inbounds = crow >= 0 && ccol >= 0 && crow < matrix.length && ccol < matrix[0].length;
                if(inbounds){
                    matrix[crow][ccol]++;
                }
            }
        }
    }

    public static void printMatrix(int[][] matrix){
        System.out.println();
        for(var xs: matrix){
            System.out.println(Arrays.toString(xs));
        }
    }

    public static void incAll(int[][] matrix){
        for(int i = 0; i < matrix.length; i++){
            for(int j = 0; j < matrix[0].length; j++){
                matrix[i][j]++;
            }
        }
    }

    public static int[][] parseInputToArray(){
        try {
            BufferedReader reader = new BufferedReader(new FileReader("input.txt"));
            ArrayList<String> list = new ArrayList<>();
            String line = reader.readLine();
            while(line != null){
                list.add(line);
                line = reader.readLine();
            }
            int[][] matrix = new int[list.size()][list.get(0).length()];
            for(int i = 0; i < list.size(); i++){
                for(int j = 0; j < list.get(0).length(); j++){
                    matrix[i][j] = Integer.parseInt(list.get(i).charAt(j) + "");
                }
            } 
            return matrix;
        } catch (Exception e){
            System.out.println("error");
            return null;
        }
    }

}
