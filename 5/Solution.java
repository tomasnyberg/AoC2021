import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.Arrays;

public class Solution {
    public static void main(String[] args){
        System.out.println(problemOne());
        System.out.println(problemTwo());
    }
    
    public static int problemOne(){
        ArrayList<String> input = parseInputToArray();
        int[][] matrix = new int[1000][1000];
        for(String s: input){
            int[] coords = new int[4];
            coords[0] = Integer.parseInt(s.split(" -> ")[0].split(",")[0]);
            coords[1] = Integer.parseInt(s.split(" -> ")[0].split(",")[1]);
            coords[2] = Integer.parseInt(s.split(" -> ")[1].split(",")[0]);
            coords[3] = Integer.parseInt(s.split(" -> ")[1].split(",")[1]);
            drawLineStraight(matrix, coords[0], coords[1], coords[2], coords[3]);
        }
        return calculateOverlap(matrix);
    }
    
    public static int problemTwo(){
        ArrayList<String> input = parseInputToArray();
        int[][] matrix = new int[1000][1000];
        for(String s: input){
            int[] coords = new int[4];
            coords[0] = Integer.parseInt(s.split(" -> ")[0].split(",")[0]);
            coords[1] = Integer.parseInt(s.split(" -> ")[0].split(",")[1]);
            coords[2] = Integer.parseInt(s.split(" -> ")[1].split(",")[0]);
            coords[3] = Integer.parseInt(s.split(" -> ")[1].split(",")[1]);
            drawLineStraight(matrix, coords[0], coords[1], coords[2], coords[3]);
            drawLineDiagonal(matrix, coords[0], coords[1], coords[2], coords[3]);
        }
        return calculateOverlap(matrix);
    }


    public static void drawLineDiagonal(int[][] matrix, int x1, int y1, int x2, int y2){
        if((x1 == x2 || y1 == y2) && !(allEqual(x1, x2, y1, y2))){
            return;
        }
        int startx = Math.min(x1, x2);
        int endx = Math.max(x1, x2);
        int starty = Math.min(y1, y2);
        int endy = Math.max(y1, y2);
        for(int i = 0; i <= endx - startx; i++){
            matrix[starty + i][startx + i]++;
        }
    }

    public static boolean allEqual(int a, int b, int c, int d){
        return a == b && b == c && c == d;
    }

    public static void drawLineStraight(int[][] matrix, int x1, int y1, int x2, int y2){
        if(!(x1 == x2 || y1 == y2)){
            return;
        }
        for(int row = Math.min(y1, y2); row <= Math.max(y1, y2); row++){
            for(int col = Math.min(x1, x2); col <= Math.max(x1, x2); col++){
                matrix[row][col]++;
            }
        }
    }

    public static int calculateOverlap(int[][] matrix){
        int count = 0;
        for(int[] xs: matrix){
            for(Integer x: xs){
                count += x >= 2 ? 1:0;
            }
        }
        return count;
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
            return list;
        } catch (Exception e){
            System.out.println("error");
            return null;
        }
    }

}
