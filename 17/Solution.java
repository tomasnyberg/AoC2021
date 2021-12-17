import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;
import java.util.regex.*;

public class Solution {
    public static void main(String[] args){
        long start = System.currentTimeMillis();
        System.out.println(problemOne());
        System.out.println(problemTwo());
        System.out.println("This run took: "  + (System.currentTimeMillis() - start) +"ms");
    }
    
    //4950 too low
    public static int problemOne(){
        int[] coords = generateCoords();
        System.out.println(Arrays.toString(coords));
        int highest = Integer.MIN_VALUE;
        for(int x = 0; x < 74; x++){
            for(int y = -1000; y < 1000; y++){
                highest = Math.max(highest, throwProbe(x, y, coords));
            }
        }
        return highest;
    }

    public static int problemTwo(){
        int[] coords = generateCoords();
        System.out.println(Arrays.toString(coords));
        int count = 0;
        for(int x = 0; x < 74; x++){
            for(int y = -1000; y < 1000; y++){
                count += throwProbe(x, y, coords) > Integer.MIN_VALUE ? 1:0;
            }
        }
        return count;
    }

   

    public static int throwProbe(int xvel, int yvel, int[] area){
        int xpos = 0;
        int ypos = 0;
        int highPoint = 0;
        while(ypos >= area[2]){
            xpos += xvel;
            ypos += yvel;
            xvel -= xvel > 0 ? 1:0;
            yvel--;
            highPoint = Math.max(ypos, highPoint); // update the highest point
            // String posString = "[" + xpos + ","  + ypos + "]";
            // String velString = "xvel:" + xvel + " yvel: " + yvel;
            if(withinArea(area, xpos, ypos)){
                // System.out.println("Within area!!!" + " (pos = " + posString + ")");
                return highPoint;
            } 
            // System.out.println("Current pos: " + posString + " " + velString);
        }
        // System.out.println("Below area, not going to hit it anymore");
        return Integer.MIN_VALUE;
    }
    
    public static boolean withinArea(int[] area, int x, int y){
        return x >= area[0] && x <= area[1] && y >= area[2] && y <= area[3];
    }

    public static int[] generateCoords(){
        String s = parseInputToArray().get(0);
        Matcher m = Pattern.compile("\\d+").matcher(s);
        ArrayList<String> matches = new ArrayList<>();
        while(m.find()){
            matches.add(m.group());
        }
        int[] result = matches.stream().mapToInt(Integer::parseInt).toArray();
        result[2] = -result[2];
        result[3] = -result[3];
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
