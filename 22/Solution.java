import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;
import java.util.regex.*;

public class Solution {
    public static void main(String[] args){
        long start = System.currentTimeMillis();
        // System.out.println(problemOne());
        System.out.println(problemTwo());
        System.out.println("This run took: "  + (System.currentTimeMillis() - start) +"ms");
    }
    
    public static int problemOne(){
        ArrayList<Integer[]> intervals = getIntervals(false);
        HashMap<String, Integer> m = new HashMap<>();
        StringBuilder sb = new StringBuilder();
        for(var xs: intervals){
            for(int i = xs[0]; i <= xs[1]; i++){
                for(int j = xs[2]; j <= xs[3]; j++){
                    for(int k = xs[4]; k <= xs[5]; k++){
                        sb.append(i);
                        sb.append(",");
                        sb.append(j);
                        sb.append(",");
                        sb.append(k);
                        sb.append(",");
                        if(xs[6] == 1){
                            m.put(sb.toString(), 1);
                        } else {
                            if(m.containsKey(sb.toString())){
                                m.remove(sb.toString());
                            }
                        }
                        sb.setLength(0);
                    }
                }
            }
        }
        return m.size();
    }

    
    public static long problemTwo(){
        ArrayList<Integer[]> intervals = getIntervals2(true);
        int len = intervals.size();
        for(int i = 0; i < len; i++){
            for(int j = i - 1; j >= 0; j--){
                Integer[] b = intervals.get(i);
                Integer[] a = intervals.get(j);
                Integer[] intersect = generateIntersection(a, b);
                if(intersect != null){
                    //Current cube is an on cube
                    if(b[6] == 1){
                        intervals.add(intersect);
                    } else {
                        if(a[6] == 1){
                            intersect[6] = -1;
                            intervals.add(intersect);
                        }
                    }
                }
            }
        }
        long sum = 0;
        for(var xs: intervals){
            System.out.println(Arrays.toString(xs));
            if(xs[6] == 1){
                sum += (xs[1] - xs[0] + 1) * (xs[3] - xs[2] + 1) * (xs[5] - xs[4] + 1); 
            } else {
                sum -= (xs[1] - xs[0] + 1) * (xs[3] - xs[2] + 1) * (xs[5] - xs[4] + 1); 
            }
        }
        return sum;
    }

    public static Integer[] generateIntersection(Integer[] a, Integer[] b){
        int x0 = Math.max(a[0], b[0]);
        int x1 = Math.min(a[1], b[1]);
        int y0 = Math.max(a[2], b[2]);
        int y1 = Math.min(a[3], b[3]);
        int z0 = Math.max(a[4], b[4]);
        int z1 = Math.min(a[5], b[5]);
        if(x0 <= x1 && y0 <= y1 && z0 <= z1){
            Integer[] result = {x0, x1, y0, y1, z0, z1, -b[6]};
            return result;
        } else {
            return null;
        }
    }

    public static ArrayList<Integer[]> getIntervals2(boolean big){
        ArrayList<Integer[]> result = new ArrayList<>();
        Integer[] a = {10, 12,10,12,10,12, 1};
        Integer[] b = {11, 13, 11,13,11,13, 1};
        Integer[] c = {9,11,9,11,9,11,-1};
        result.add(a);
        result.add(b);
        result.add(c);
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
