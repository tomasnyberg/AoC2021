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

    public static int[][] dijkstra(int[][] matrix){
        int[][] dp = new int[matrix.length][matrix[0].length];
        for(var xs: dp){
            Arrays.fill(xs, Integer.MAX_VALUE);
        }
        dp[0][0] = 0;
        int[][] visited = new int[matrix.length][matrix[0].length];
        // pq that sorts according to the shortest paths
        PriorityQueue<Integer[]> pq = new PriorityQueue<>((a,b) -> dp[a[0]][a[1]] - dp[b[0]][b[1]]);
        Integer[] topLeft = {0,0};
        pq.add(topLeft);
        while(!pq.isEmpty()){
            Integer[] currPos = pq.poll();
            int row = currPos[0];
            int col = currPos[1];
            if(row > 0 && visited[row-1][col] != 1){
                if(dp[row-1][col] > dp[row][col] + matrix[row-1][col]){
                    Integer[] newPos = {row-1, col};
                    dp[row-1][col] = dp[row][col] + matrix[row-1][col];
                    pq.add(newPos);
                }
            }
            if(row < matrix.length-1 && visited[row+1][col] != 1){
                if(dp[row+1][col] > dp[row][col] + matrix[row+1][col]){
                    Integer[] newPos = {row+1, col};
                    dp[row+1][col] = dp[row][col] + matrix[row+1][col];
                    pq.add(newPos);
                }
            }
            if(col > 0 && visited[row][col] != 1){
                if(dp[row][col-1] > dp[row][col] + matrix[row][col-1]){
                    Integer[] newPos = {row, col-1};
                    dp[row][col-1] = dp[row][col] + matrix[row][col-1];
                    pq.add(newPos);
                }
            }
            if(col < matrix[0].length-1 && visited[row][col + 1] != 1){
                if(dp[row][col+1] > dp[row][col] + matrix[row][col+1]){
                    Integer[] newPos = {row, col+1};
                    dp[row][col+1] = dp[row][col] + matrix[row][col+1];
                    pq.add(newPos);
                }
            }
            visited[row][col] = 1;
        }
        return dp;
    }
    
    public static int problemTwo(){
        int[][] bigMatrix = biggerMatrix();
        int[][] dp = dijkstra(bigMatrix);
        return dp[dp.length -1][dp[0].length -1];
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
