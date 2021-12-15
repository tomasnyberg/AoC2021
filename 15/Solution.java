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
    
    public static int problemOne(){
        int[][] matrix = parseToMatrix();
        return dijkstra(matrix)[matrix.length - 1][matrix[0].length - 1];
    }

    public static int[][] dijkstra(int[][] matrix){
        int[][] dp = new int[matrix.length][matrix[0].length];
        int[][] visited = new int[matrix.length][matrix[0].length];
        int[][] dirs = {{0,1}, {1,0}, {0, -1}, {-1, 0}};
        for(var xs: dp){
            Arrays.fill(xs, Integer.MAX_VALUE);
        }
        dp[0][0] = 0;
        // pq that sorts according to the shortest paths
        PriorityQueue<Integer[]> pq = new PriorityQueue<>((a,b) -> dp[a[0]][a[1]] - dp[b[0]][b[1]]);
        Integer[] topLeft = {0,0};
        pq.add(topLeft);
        while(!pq.isEmpty()){
            Integer[] currPos = pq.poll();
            int row = currPos[0];
            int col = currPos[1];
            //Check every direction
            for(var dir: dirs){
                int newRow = row + dir[0];
                int newCol = col + dir[1];
                //Inbounds check
                if(newRow >= 0 && newRow < matrix.length && newCol >= 0 && newCol < matrix[0].length){
                    int thisWay = dp[row][col] + matrix[newRow][newCol];
                    //If the current way to get to position (newRow, newCol) is greater than going this way,
                    //update it to use thisway instead
                    if(dp[newRow][newCol] > thisWay){
                        Integer[] newPos = {newRow, newCol};
                        dp[newRow][newCol] = thisWay;
                        pq.add(newPos);
                    }
                }
            }
            visited[row][col] = 1;
        }
        return dp;
    }

    public static int problemTwo(){
        int[][] bigMatrix = biggerMatrix();
        return dijkstra(bigMatrix)[bigMatrix.length -1][bigMatrix[0].length -1];
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
