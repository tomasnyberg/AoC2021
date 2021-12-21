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
        for(var xs: bigMatrix){
            Arrays.fill(xs, '.');
        }
        //copy the elements from initial array WORKS (or at least gets all of the hashtags)
        for(int i = 500; i < 500 + smallMatrix.length; i++){
            for(int j = 500; j < 500 + smallMatrix[0].length; j++){
                bigMatrix[i][j] = smallMatrix[i-500][j-500];
            }
        }
        bigMatrix = stepMatrix(bigMatrix, template, 2);
        return countHashTags(bigMatrix);
    }

    public static char[][] stepMatrix(char[][] matrix, String template, int steps){
        for(int step = 0; step < steps; step++){
            char[][] newMatrix = new char[matrix.length][matrix[0].length];
            for(var xs: newMatrix){
                Arrays.fill(xs, step % 2 == 1 ? '#':'.');
            }
            // the inner array 
            for(int i = 500-step; i < 600 + step; i++){
                for(int j = 500-step; j < 600+step; j++){
                    newMatrix[i][j] = matrix[i][j];
                }
            }
            for(int i = 0; i < newMatrix.length; i++){
                for(int j = 0; j < newMatrix[0].length; j++){
                    newMatrix[i][j] = calculateIndex(matrix, i, j, step, template);
                }
            }
            matrix = newMatrix;
        }
        return matrix;
    }

    public static char calculateIndex(char[][] matrix, int row, int col, int step, String template){
        String res = "";
        for(int i = -1; i <= 1; i++){
            for(int j = -1; j <= 1; j++){
                int nrow = row + i;
                int ncol = col + j;
                if(nrow >= 0 && ncol >= 0 && nrow < matrix.length && ncol < matrix[0].length){
                    res += matrix[nrow][ncol] == '#' ? '1':'0';
                } else {
                    res += step % 2 == 1 ? '1':'0';
                }
            }
        }
        return template.charAt(Integer.parseInt(res, 2));
    }


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
