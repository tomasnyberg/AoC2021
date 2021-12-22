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
        
        return 0;
    }

    public static int problemTwo(){
        return 0;
    }


    public static int[] matrixMul(int[] vec, int[] rot){
        int[] result = new int[3];
        int x = vec[0];
        int y = vec[1];
        int z = vec[2];
        result[0] = x*rot[0] + y*rot[3] + z*rot[6];
        result[1] = x*rot[1] + y*rot[4] + z*rot[7];
        result[2] = x*rot[2] + y*rot[5] + z*rot[8];
        return result;
    }

    public static int[][] rotations(){
        int[] xs = new int[9];
        int[][] result= new int[48][9];
        int pos = 0;
        for(int a = 0; a < 3; a++){
            for(int i = 0; i < 2; i++){
                xs[a] = i % 2 == 0 ? 1:-1;
                for(int b = 3; b < 6; b++){
                    if(b%3 != a%3){
                        for(int j = 0; j < 2; j++){
                            xs[b] = j % 2 == 0 ? 1:-1;
                            for(int c = 6; c < 9; c++){
                                if(c % 3 != a %3 && c % 3 != b%3){
                                    for(int k = 0; k < 2; k++){
                                        xs[c] = k % 2 == 0 ? 1:-1;
                                        result[pos] = xs.clone();
                                        pos++;
                                    }
                                    xs[c] = 0;
                                }
                            }
                        }
                        xs[b] = 0;
                    }
                }
            }
            xs[a] = 0;
        }
        return result;
    }

    public static HashMap<Integer, Integer[][][]> generateScanners(){
        
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
