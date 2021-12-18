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
    
    public static int problemOne(){
        ArrayList<String> list = parseInputToArray();
        String s = list.get(0);
        for(int i = 1; i < list.size(); i++){
            s = add(s, list.get(i));
            s = reduce(s);
        }
        System.out.println(s);
        return wholeMagnitude(s);
    }

    public static int problemTwo(){
        int biggest = 0;
        ArrayList<String> list = parseInputToArray();
        for(int i = 0; i < list.size(); i++){
            for(int j = 0; j < list.size(); j++){
                if(i != j){
                    int ab = wholeMagnitude(reduce(add(list.get(i), list.get(j))));
                    int ba = wholeMagnitude(reduce(add(list.get(j), list.get(i))));
                    biggest = Math.max(ab, biggest);
                    biggest = Math.max(ba, biggest);
                }
            }
        }
        return biggest;
    }

    public static int wholeMagnitude(String s){
        while(s.length() >= 5){
            Matcher m = Pattern.compile("\\[\\d+,\\d+\\]").matcher(s);
            if(m.find()){
                String matchString = m.group();
                int magnitude = magnitude(matchString);
                // System.out.println(s);
                int startIndex = s.indexOf(matchString);
                s = s.substring(0, startIndex) + magnitude + s.substring(startIndex + matchString.length());
            }
        }
        return Integer.parseInt(s);
    }

    public static void testWholeMagnitude(){
        System.out.println(wholeMagnitude("[[1,2],[[3,4],5]]"));
    }

    // TODO: currently can't do big numbers (not longs either for that matter)
    public static int magnitude(String s){
        int left = 1;
        int biggerLeft = left;
        while(Character.isDigit(s.charAt(biggerLeft))){
            biggerLeft++;
        }
        int right = biggerLeft + 1;
        int biggerRight = right;
        while(Character.isDigit(s.charAt(biggerRight))){
            biggerRight++;
        } 
        int a = 3*Integer.parseInt(s.substring(left, biggerLeft));
        int b = 2*Integer.parseInt(s.substring(right, biggerRight));
        return a+b;
    }

    public static String reduce(String a){
        String newA = explode(a);
        while(!newA.equals(a)){
            a = newA;
            newA = explode(a);
        }
        newA = split(a);
        if(!newA.equals(a)){
            a = newA;
            a = reduce(a);
        }
        return a;
    }


    public static String add(String a, String b){
        if(a.isEmpty()){
            return b;
        }
        int i = 0;
        int depth = 0;
        do {
            depth += a.charAt(i) == '[' ? 1:0; 
            depth -= a.charAt(i) == ']' ? 1:0; 
            i++;
        } while(depth > 0);
        a = "[" + a.substring(0, i) + "," + b + "]";
        return a;
    }

    public static String explode(String a){
        int depth = 0;
        int left = 0;
        int right = 0;
        for(int i = 0; i < a.length(); i++){
            depth += a.charAt(i) == '[' ? 1:0;
            depth -= a.charAt(i) == ']' ? 1:0;
            if(depth == 5){ 
                int j = i;
                while(a.charAt(j) != ']'){
                    j++;
                }
                String[] xs = a.substring(i+1, j).split(",");
                left = Integer.parseInt(xs[0]);
                right = Integer.parseInt(xs[1]);
                a = a.substring(0, i) + "0"  + a.substring(j+1, a.length());
                a = explodeAdder(a, i, left, right);
                return a; 
            }
        }
        return a;
    }

    public static String explodeAdder(String a, int i, int left, int right){
        int j = i-1;
        while(j > 0 && !Character.isDigit(a.charAt(j))){
            j--;
        }
        int biggerj = j;
        while(biggerj > 0 && Character.isDigit(a.charAt(biggerj-1))){
            biggerj--;
        }
        if(j != 0){
            int newLeft = Integer.parseInt(a.substring(biggerj, j + 1)) + left;
            a = a.substring(0, biggerj) + newLeft + a.substring(j+1, a.length()); 
        } // If we didn't reach all the way to the end
        j = i + 2; // This maay cause problems at some point
        while(j < a.length() && !Character.isDigit(a.charAt(j))){
            j++;
        }
        biggerj = j;
        while(biggerj < a.length() && Character.isDigit(a.charAt(biggerj + 1))){
            biggerj++;
        }
        if(j < a.length()){ // Might not work?
            int newRight = Integer.parseInt(a.substring(j, biggerj+1)) + right;
            a = a.substring(0, j) + newRight + a.substring(biggerj + 1, a.length());
        }
        return a;
    }

    // currently not checking for three digit numbers since that feels unlikely
    public static String split(String a){
        int i = 0;
        while(i < a.length() - 1){
            if(Character.isDigit(a.charAt(i)) && Character.isDigit(a.charAt(i+1))){
                StringBuilder sb = new StringBuilder();
                int curr = Integer.parseInt("" + a.charAt(i) + a.charAt(i+1));
                int left = curr / 2;
                int right = curr % 2 == 1 ? curr/2+1:curr/2;
                sb.append("[");
                sb.append(left);
                sb.append(",");
                sb.append(right);
                sb.append("]");
                a = a.substring(0, i) + sb.toString() + a.substring(i + 2);
                break;
            } else {
                i++;
            }
        }
        return a;
    }

    // Seems to work??
    public static void testSplit(){
        System.out.println(split("[[[[0,7],4],[15,[0,13]]],[1,1]]"));
        System.out.println(split("[[[[0,7],4],[[7,8],[0,13]]],[1,1]]"));
    }

    // Seems to be working?
    public static void testExplode(){
        System.out.println(explode("[[[[[9,8],1],2],3],4]")); 
        System.out.println(explode("[7,[6,[5,[4,[3,2]]]]]")); 
        System.out.println(explode("[[[[0,7],4],[7,[[8,4],9]]],[1,1]]")); 
        System.out.println(explode("[[6,[5,[4,[3,2]]]],1]")); 
        System.out.println(explode("[[3,[2,[1,[7,3]]]],[6,[5,[4,[3,2]]]]]")); 
        System.out.println(explode("[[3,[2,[8,0]]],[9,[5,[4,[3,2]]]]]"));
    }

    // Seems to be working??
    public static void testAdd(){
        String a = "[1,1]";
        String b = "[2,2]";
        String c = "[3,3]";
        String d = "[4,4]";
        a = add(a,b);
        a = add(a,c);
        a = add(a,d);
        System.out.println(a);
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
