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
    //5326 answer for input2
    // Currently some bug where the edges aren't getting done properly and sometimes get a # for no reason
    public static int problemOne(){
        String template = parseInputToArray().get(0);
        char[][] currMatrix = getInitialMatrix();
        int steps = 2;
        currMatrix = stepMatrix(currMatrix, template, steps);
        return countHashTags(currMatrix);
    }

    public static char[][] stepMatrix(char[][] currMatrix, String template, int steps){
        for(var xs: currMatrix){
            System.out.println(Arrays.toString(xs));
        }
        System.out.println();
        for(int step = 0; step < steps; step++){
            char[][] newMatrix = new char[currMatrix.length + 2][currMatrix[0].length + 2];
            for(var xs: newMatrix){
                Arrays.fill(xs, '.');
            }
            for(int i = 0; i < currMatrix.length; i++){
                for(int j = 0; j < currMatrix.length; j++){
                    newMatrix[i + 1][j + 1] = currMatrix[i][j];
                }
            }
            for(var xs: newMatrix){
                System.out.println(Arrays.toString(xs));
            }
            System.out.println();
            for(int i = -1; i <= currMatrix.length; i++){
                for(int j = -1; j <= currMatrix[0].length; j++){
                    newMatrix[i+1][j+1] = template.charAt(calculateIndex(currMatrix, i, j));
                }
            }
            for(var xs: newMatrix){
                System.out.println(Arrays.toString(xs));
            }
            System.out.println();
            currMatrix = newMatrix;
        }
        return currMatrix;
    }

    public static int problemTwo(){
        return 0;
    }

    public static char[][] fillEdges(char[][] newMatrix, char filler){
        for(int i = 0; i < newMatrix.length; i++){
            newMatrix[i][0] = filler;
            newMatrix[i][newMatrix[0].length - 1] = filler;
        }
        // Fill left and right columns
        for(int j = 0; j < newMatrix[0].length; j++){
            newMatrix[0][j] = filler;
            newMatrix[newMatrix.length - 1][j] = filler;
        }
        for(var xs: newMatrix){
            System.out.println(Arrays.toString(xs));
        }
        return newMatrix;
    }

    public static int countHashTags(char[][] matrix){
        int count = 0;
        for(int i = 0; i < matrix.length; i++){
            for(int j = 0; j < matrix[0].length; j++){
                count += matrix[i][j] == '.' ? 0:1;
            }
        }
        return count;
    }

    public static int calculateIndex(char[][] matrix, int row, int col){
        String res = "";
        for(int i = -1; i <= 1; i++){
            for(int j = -1; j <= 1; j++){
                int nrow = row + i;
                int ncol = col + j;
                if(nrow >= 0 && ncol >= 0 && nrow < matrix.length && ncol < matrix[0].length){
                    res += matrix[nrow][ncol] == '.' ? '0':'1';
                } else {
                    res += '0';
                }
            }
        }
        return Integer.parseInt(res, 2);
    }

    public static boolean anyAdjacent(char[][] matrix, int row, int col){
        for(int i = -1; i <= 1; i++){
            for(int j = -1; j <= 1; j++){
                int nrow = row + i;
                int ncol = col + j;
                if(nrow >= 0 && ncol >= 0 && nrow < matrix.length && ncol < matrix[0].length){
                    if(matrix[nrow][ncol] == '#'){
                        return true;
                    }
                }
            }
        }
        return false;
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
