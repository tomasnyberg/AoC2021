import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

public class Solution {
    public static int count;
    public static long count1;
    public static Map<String, Set<String>> edges = edgeMap();
    public static void main(String[] args){
        System.out.println(problemOne());
        System.out.println(problemTwo());
    }
    
    public static int problemOne(){
        Map<String, Set<String>> edges = edgeMap();
        traverse(edges, "", "start", new HashSet<>());
        return count;
    }

    public static void traverse(Map<String, Set<String>> edges, String path, String curr, Set<String> visited){
        if(curr.equals("end")){
            count++;
            return;
        }
        path += ", " + curr;
        boolean small = curr.charAt(0) >= 97 && curr.charAt(0) <= 122;
        if(small){
            visited.add(curr);
        }
        for(String s: edges.get(curr)){
            if(!visited.contains(s)){
                traverse(edges, path, s, visited);
            }
        }
        visited.remove(curr);
    }
    public static void traverseTwo(String a, Set<String> seen, boolean can_twice){
        if(a.equals("end")){
            count1++;
            return;
        }
        for(String b: edges.get(a)){
            if(b.toLowerCase().equals(b)){
                if(!seen.contains(b)){
                    Set<String> newSet = new HashSet<>(seen);
                    newSet.add(b);
                    traverseTwo(b, newSet, can_twice);
                } else if (can_twice && !(b.equals("start") || b.equals("end"))) {
                    Set<String> newSet = new HashSet<>(seen);
                    newSet.add(b);
                    traverseTwo(b, seen, false);
                }
            } else {
                traverseTwo(b, seen, can_twice);
            }
        }
    }

    public static long problemTwo(){
        HashSet<String> set = new HashSet<>();
        set.add("start");
        traverseTwo("start", set, true);
        return count1;
    }

    public static Map<String, Set<String>> edgeMap(){
        List<String> list = parseInputToArray();
        Map<String, Set<String>> edges = new HashMap<>();
        for(String s: list){
            String a = s.split("-")[0];
            String b = s.split("-")[1];
            Set<String> aSet = edges.getOrDefault(a, new HashSet<>());
            Set<String> bSet = edges.getOrDefault(b, new HashSet<>());
            aSet.add(b);
            bSet.add(a);
            edges.put(a, aSet);
            edges.put(b, bSet);
        }
        //check that the edge map is correct
        // for(var entry: edges.entrySet()){
            // System.out.print("From: " + entry.getKey() + " ");
            // System.out.print("To: " + entry.getValue());
            // System.out.println();
        // }
        return edges;
    }

    public static List<String> parseInputToArray(){
        try {
            BufferedReader reader = new BufferedReader(new FileReader("input.txt"));
            List<String> list = new ArrayList<>();
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
