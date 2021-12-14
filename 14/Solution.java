import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

public class Solution {
    public static void main(String[] args){
        System.out.println(problemOne());
        System.out.println(problemTwo());
    }
    
    public static long problemOne(){
        int iterations = 40;
        HashMap<String, String> templates = getTemplates(); // example : NN -> C
        HashMap<String,Long> pairCounter = generateInputPairs(); // convert input, ex. NNCB to [NN -> 1, NC -> 1, CB -> 1] 
        for(int i = 0; i < iterations; i++){ // run for iterations steps
            HashMap<String, Long> newPairCounter = new HashMap<>(pairCounter);

            //TODO: need to check for all of them not just one
            for(String pair: pairCounter.keySet()){
                if(templates.containsKey(pair)){
                    newPairCounter.put(pair, newPairCounter.get(pair) - pairCounter.get(pair)); // decrement the current pair which gets ruined
                    String left = pair.charAt(0) + templates.get(pair);
                    String right = templates.get(pair) + pair.charAt(1);
                    // put in both the new pairs
                    newPairCounter.put(left, newPairCounter.getOrDefault(left, 0L) + pairCounter.get(pair)); 
                    newPairCounter.put(right, newPairCounter.getOrDefault(right, 0L) + pairCounter.get(pair)); 
                }
            }
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
    
    public static int problemTwo(){
        return 0;
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
        ArrayList<String> list = parseInputToArray();
        ArrayList<String> templateList = new ArrayList<>();
        for(int i = 2; i < list.size(); i++){
            templateList.add(list.get(i));
        }
        HashMap<String, String> templates = new HashMap<>();
        for(String s: templateList){
            String from = s.split(" -> ")[0];
            String to = s.split(" -> ")[1];
            templates.put(from, to);
        }
        return templates;
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