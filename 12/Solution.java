import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

public class Solution {
    public static void main(String[] args){
        System.out.println(problemOne());
        System.out.println(problemTwo());
    }
    
    public static int problemOne(){
        HashMap<String, Set<String>> edges = edgeMap();
        traverse(edges, "", "start", 0, new HashSet<>());
        return 0;
    }

    public static void traverse(HashMap<String, Set<String>> edges, String path, String curr, int smallVisited, Set<String> visited){
        if(curr.equals("end")){
            System.out.println("Found path: " + path + ", end");
            return;
        }
        visited.add(curr);
        path += ", " + curr;
        // TODO: check for small caves, and also make it possible to visit nodes twice so long as they have a new unvisited node

        for(String s: edges.get(curr)){
            //might need to create new set??
            Set<String> sset = new HashSet<>(edges.get(s));
            if(!visited.contains(s)){
                // System.out.println("current path: " + path);
                traverse(edges, path, s, smallVisited, visited);
            }
        }
        visited.remove(curr);
    }

    public static int problemTwo(){
        return 0;
    }

    public static HashMap<String, Set<String>> edgeMap(){
        ArrayList<String> list = parseInputToArray();
        HashMap<String, Set<String>> edges = new HashMap<>();
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
        //     System.out.print("From: " + entry.getKey() + " ");
        //     System.out.print("To: " + entry.getValue());
        //     System.out.println();
        // }
        return edges;
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
