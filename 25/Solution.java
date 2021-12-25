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
        char[][] matrix = generateMatrix();
        char[][] before = copyArray(matrix);
        int i = 0;
        do {
            before = copyArray(matrix);
            matrix = oneStep(matrix);
            i++;
        } while(!Arrays.deepEquals(before, matrix));
        return i;
    }

    public static int problemTwo(){
        return 0;
    }

    public static char[][] oneStep(char[][] matrix){
        return move(move(matrix, true), false);
    }

    public static char[][] move(char[][] matrix, boolean east){
        char[][] newMatrix = copyArray(matrix);
        for(int i = 0; i < matrix.length; i++){
            for(int j = 0; j < matrix[0].length; j++){
                if((east && matrix[i][j] == '>') || (matrix[i][j] == 'v' && !east)){
                    char curr = east ? '>':'v';
                    //on edge
                    int edgeRow = east ? i:0; 
                    int edgeCol = east ? 0:j;
                    if((east && j == matrix[0].length - 1) || (!east && i ==matrix.length -1)){
                        if(matrix[edgeRow][edgeCol] == '.'){
                            newMatrix[edgeRow][edgeCol] = curr;
                            newMatrix[i][j] = '.';
                        }
                        continue;
                    }
                    int nextRow = east ? i:i+1;
                    int nextCol = east ? j+1:j;
                    if(matrix[nextRow][nextCol] == '.'){
                        newMatrix[nextRow][nextCol] = curr;
                        newMatrix[i][j] = '.';
                    }
                }
            }
        }
        return newMatrix;
    }

    public static char[][] copyArray(char[][] map){
        char[][] result = new char[map.length][map[0].length];
        for(int i = 0;i < map.length; i++){
            for(int j = 0; j < map[0].length; j++){
                result[i][j] = map[i][j];
            }
        }
        return result;
    }

    public static void printMatrix(char[][] matrix){
        for(var xs: matrix){
            for(var c: xs){
                System.out.print(c);
            }
            System.out.println();
        }
        System.out.println();
    }

    public static char[][] generateMatrix(){
        ArrayList<String> list = parseInputToArray();
        int rows = list.size();
        int cols = list.get(0).length();
        char[][] result = new char[rows][cols];
        for(int i = 0; i < rows; i++){
            String curr = list.get(i);
            for(int j = 0; j < cols; j++){
                char ch = curr.charAt(j);
                result[i][j] = ch;
                
            }
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
