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
        System.out.println(i);
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
        return ":("; // incase no explosion
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
    // "[[[[,1],2],3],4]"


    // Seems to be working?
    public static void testExplode(){
        System.out.println(explode("[[[[[9,8],1],2],3],4]")); // [9,8] expected
        System.out.println(explode("[7,[6,[5,[4,[3,2]]]]]")); // [3,2] expected
        System.out.println(explode("[[[[0,7],4],[7,[[8,4],9]]],[1,1]]")); // [3,2] expected
        System.out.println(explode("[[6,[5,[4,[3,2]]]],1]")); // [3,2]
        System.out.println(explode("[[3,[2,[1,[7,3]]]],[6,[5,[4,[3,2]]]]]")); // [7,3]
        System.out.println(explode("[[3,[2,[8,0]]],[9,[5,[4,[3,2]]]]]")); // [3,2]
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
