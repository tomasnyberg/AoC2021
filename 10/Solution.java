import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.Buffer;
import java.util.*;

public class Solution {
    /*
    ): 3 points.
    ]: 57 points.
    }: 1197 points.
    >: 25137 points.
    */
    
    public static void main(String[] args){
        System.out.println(problemOne());
        System.out.println(problemTwo());
    }
    
    public static int problemOne(){
        ArrayList<String> list = parseInputToArray();
        HashMap<Character, Integer> values = valueMap();
        HashMap<Character, Character> pairs = pairMap();
        Stack<Character> stack = new Stack<>();
        ArrayList<Character> incorrects = new ArrayList<>();
        for(String s: list){
            for(Character c: s.toCharArray()){
                if(pairs.keySet().contains(c)){
                    stack.push(c);
                } else {
                    char next = pairs.get(stack.pop());
                    if(next != c){
                        incorrects.add(c);
                        break;
                    }
                }
            }
        }
        //rewrite as stream
        int sum = 0;
        for(Character c: incorrects){
            sum += values.get(c);
        }
        return sum;
    }
    
    public static HashMap<Character, Integer> valueMap(){
        HashMap<Character, Integer> wrongValues = new HashMap<>();
        wrongValues.put(')', 3);
        wrongValues.put(']', 57);
        wrongValues.put('}', 1197);
        wrongValues.put('>', 25137);
        return wrongValues;
    }
    public static HashMap<Character, Integer> otherValueMap(){
        HashMap<Character, Integer> wrongValues = new HashMap<>();
        wrongValues.put('(', 1);
        wrongValues.put('[', 2);
        wrongValues.put('{', 3);
        wrongValues.put('<', 4);
        return wrongValues;
    }
    
    public static HashMap<Character, Character> pairMap (){
        HashMap<Character, Character> result = new HashMap<>();
        result.put('(', ')');
        result.put('[', ']');
        result.put('{', '}');
        result.put('<', '>');
        return result;
    }
    
    
    // 337301424 too low
    public static Long problemTwo(){
        ArrayList<String> list = parseInputToArray();
        HashMap<Character, Integer> values = otherValueMap();
        HashMap<Character, Character> pairs = pairMap();
        Stack<Character> stack = new Stack<>();
        ArrayList<Long> scores = new ArrayList<>();
        for(String s: list){
            for(Character c: s.toCharArray()){
                if(pairs.keySet().contains(c)){
                    stack.push(c);
                } else {
                    char next = pairs.get(stack.pop());
                    if(next != c){
                        stack.clear();
                        break;
                    }
                }
            }
            String toAdd = "";
            Long score = 0L;
            while(!stack.isEmpty()){
                Character curr = stack.pop();
                score *= 5;
                score += values.get(curr);
                toAdd += pairs.get(curr);
            }
            if(score != 0){
                System.out.println(toAdd);
                scores.add(score);
            }
            stack.clear();
        }
        // Here we only have rows that are incomplete left
        scores.sort((a,b) -> a.compareTo(b));
        return scores.get(scores.size() / 2);
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
