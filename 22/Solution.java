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
    
    public static long problemOne(){
        return solve(false);
    }

    
    public static long problemTwo(){
        return solve(true);
    }

    public static long solve(boolean bigNumbers){
        ArrayList<Integer[]> intervals = getIntervals(bigNumbers);
        ArrayList<Integer[]> newIntervals = new ArrayList<>();
        for(var fromInitial: intervals){
            ArrayList<Integer[]> toAdd = new ArrayList<>();
            for(var fromAdded: newIntervals){
                Integer[] intersection = generateIntersection(fromAdded, fromInitial);
                if(intersection != null){
                    toAdd.add(intersection);
                }
            }
            if(fromInitial[6] == 1){
                toAdd.add(fromInitial);
            }
            newIntervals.addAll(toAdd);
            toAdd.clear();
        }
        return calculateArea(newIntervals);
    }

    public static long calculateArea(ArrayList<Integer[]> intervals) {
        return intervals
            .stream().
            mapToLong(xs -> (long) (xs[1] - xs[0] + 1) * (xs[3] - xs[2] + 1) * (xs[5] - xs[4] + 1) * xs[6])
            .sum();
    }

    public static Integer[] generateIntersection(Integer[] a, Integer[] b){
        int x0 = Math.max(a[0], b[0]);
        int x1 = Math.min(a[1], b[1]);
        int y0 = Math.max(a[2], b[2]);
        int y1 = Math.min(a[3], b[3]);
        int z0 = Math.max(a[4], b[4]);
        int z1 = Math.min(a[5], b[5]);
        if(x0 <= x1 && y0 <= y1 && z0 <= z1){
            Integer[] result = {x0, x1, y0, y1, z0, z1, -a[6]};
            return result;
        } else {
            return null;
        }
    }

    // Function for testing solution, expected sum of area here is 39
    public static ArrayList<Integer[]> getIntervals2(boolean big){
        ArrayList<Integer[]> result = new ArrayList<>();
        Integer[] a = {10, 12,10,12,10,12, 1};
        Integer[] b = {11, 13, 11,13,11,13, 1};
        Integer[] c = {9,11,9,11,9,11,-1};
        Integer[] d = {10,10,10,10,10,10, 1};
        result.add(a);
        result.add(b);
        result.add(c);
        result.add(d);
        return result;
    }


    public static ArrayList<Integer[]> getIntervals(boolean big){
        ArrayList<String> list = parseInputToArray();
        ArrayList<Integer[]> allCoords = new ArrayList<>();
        for(String s: list){
            if(!big && countDigits(s) > 12){
                return allCoords;
            }
            Integer[] coords = new Integer[7];
            Matcher m = Pattern.compile("-?\\d+").matcher(s);
            int i = 0;
            while(m.find()){
                coords[i] = Integer.parseInt(m.group());
                i++;
            }
            coords[6] = s.contains("on") ? 1:-1;
            allCoords.add(coords);
        }
        return allCoords;
    }

    public static int countDigits(String s){
        return (int) s.chars().filter(x -> x >= 48 && x <= 57).count();
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
