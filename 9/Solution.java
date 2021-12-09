import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.*;

public class Solution {
    public static void main(String[] args){
        System.out.println(problemOne());
        System.out.println(problemTwo());
    }
    
    public static int problemOne(){
        int[][] matrix = parseInputToArray();
        int sum = 0;
        for(int i = 0; i < matrix.length; i++){
            for(int j = 0; j < matrix[0].length; j++){
                sum += lowPoint(matrix, i, j) ? matrix[i][j]+1:0;
            }
        }
        return sum;
    }

    
    public static int problemTwo(){
        ArrayList<Integer> basins = new ArrayList<>();
        int[][] matrix = parseInputToArray();
        boolean[][] visited = new boolean[matrix.length][matrix[0].length];
        for(int i = 0; i < matrix.length; i++){
            for(int j = 0; j < matrix[0].length; j++){
                if(lowPoint(matrix, i, j)){
                    int basinSize = expandBasin(matrix, visited, i, j, matrix[i][j]-1);
                    basins.add(basinSize);
                }
            }
        }
        basins.sort((a,b) -> b-a);
        System.out.println(basins);
        return basins.get(0)*basins.get(1)*basins.get(2);
    }

    // i = row, j = col

    public static int expandBasin(int[][] matrix, boolean[][] visited, int i, int j, int prev){
        if(i < 0 || i>= matrix.length || j < 0 || j >= matrix[0].length || visited[i][j]){
            return 0;
        }
        int val = matrix[i][j];
        if(val == 9){
            return 0;
        }
        visited[i][j] = true;
        int top = expandBasin(matrix, visited, i-1, j, val);
        int bottom = expandBasin(matrix, visited, i+1, j, val);
        int right = expandBasin(matrix, visited, i, j+1, val);
        int left = expandBasin(matrix, visited, i, j-1, val);
        return 1 + top + bottom + right + left;
        
    }

    public static boolean lowPoint(int[][] matrix, int row, int col){
        int val = matrix[row][col];
        boolean top = row == 0 || matrix[row-1][col] > val; 
        boolean bottom = row == matrix.length-1 || matrix[row+1][col] > val; 
        boolean left = col == 0 || matrix[row][col-1] > val; 
        boolean right = col == matrix[0].length -1 || matrix[row][col+1] > val; 
        return top && bottom && left && right;
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
            for(int i = 0; i < matrix.length; i++){
                for(int j = 0; j < matrix[0].length; j++){
                    matrix[i][j] = Integer.parseInt(list.get(i).charAt(j) + "");
                }
            }
            return matrix;
        } catch (Exception e){
            System.out.println("error");
            return new int[100][100];
        }
    }

}
