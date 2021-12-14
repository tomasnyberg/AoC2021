import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

public class Solution {
    public static void main(String[] args){
        System.out.println(problemOne(10));
        System.out.println(problemTwo(40));
    }
    
    public static long problemOne(int iterations){
        HashMap<String, String> templates = getTemplates(); // example : NN -> C
        HashMap<String,Long> pairCounter = generateInputPairs(); // convert input, ex. NNCB to [NN -> 1, NC -> 1, CB -> 1] 
        for(int i = 0; i < iterations; i++){ // run for iterations steps
            // temporary map since we don't want to create new pairs until we've reached the end of the step
            HashMap<String, Long> newPairCounter = new HashMap<>(pairCounter);
            for(String pair: pairCounter.keySet()){
                if(templates.containsKey(pair)){
                    // count down  the current pair which gets ruined, one for every occurrence
                    newPairCounter.put(pair, newPairCounter.get(pair) - pairCounter.get(pair)); 
                    //Construct the new pairs that are created when we insert the letter
                    String left = pair.charAt(0) + templates.get(pair);
                    String right = templates.get(pair) + pair.charAt(1);
                    // put in both the new pairs
                    newPairCounter.put(left, newPairCounter.getOrDefault(left, 0L) + pairCounter.get(pair)); 
                    newPairCounter.put(right, newPairCounter.getOrDefault(right, 0L) + pairCounter.get(pair)); 
                }
            }
            // change the current map since we've reached the end of the step
            pairCounter = newPairCounter;
        }
        System.out.println(pairCounter);
        long[] counter = new long[26];
        String firstLine = parseInputToArray().get(0);
        counter[firstLine.charAt(firstLine.length() - 1) - 65]++;
        for(String key: pairCounter.keySet()){
            counter[key.charAt(0) - 65] += pairCounter.get(key);
        }
        return LongStream.of(counter).max().getAsLong() - LongStream.of(counter).filter(x -> x != 0).min().getAsLong();
    }
    
    public static long problemTwo(int iterations){
        return problemOne(iterations);
    }

    public static HashMap<String, Long> generateInputPairs(){
        String starting = parseInputToArray().get(0);
        HashMap<String, Long> pairCounter = new HashMap<>();
        for(int i = 0; i < starting.length() - 1; i++){
            String pair = starting.charAt(i) + "" + starting.charAt(i+1);
            pairCounter.put(pair, pairCounter.getOrDefault(pair, 0L) + 1);
        }
        return pairCounter;
    }
    
    public static HashMap<String, String> getTemplates(){
        List<String> list = parseInputToArray();
        list = list.subList(2, list.size());
        HashMap<String, String> templates = new HashMap<>();
        for(String s: list){
            String[] fromTo = s.split(" -> ");
            templates.put(fromTo[0], fromTo[1]);
        }
        return templates;
    }
    public static List<String> parseInputToArray(){
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
