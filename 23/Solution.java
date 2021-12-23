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
        char[][] map = generateMap();
        HashMap<String, Integer> prevPositions = new HashMap<>();
        System.out.println("Initial map state:");
        printMap(map);
        Set<String> moves = possibleMoves(map);
        for(String s: moves){
            printMap(mapStringToMap(s));
            System.out.println();
        }
        return 0;
    }
    
    public static int problemTwo(){
        return 0;
    }

    public static Set<String> possibleMoves(char[][] map){
        HashSet<String> result = new HashSet<>();
        for(int i = 0; i < map.length; i++){
            for(int j = 0; j < map[0].length; j++){
                int moveType = canMove(map, i, j);
                if(moveType != -1){
                    result.addAll(generateMoves(map, moveType, i,j));
                }
            }
        }
        return result;
    }

    public static char[][] copyArray(char[][] map){
        char[][] result = new char[map.length][map[0].length];
        for(int i = 0;i < map.length; i++){
            for(int j = 0; j < map[0].length; j++){
                result[i][j] = map[i][j];
            }
        }
        return result;
    }

    public static Set<String> generateMoves(char[][] map, int moveType, int row, int col){
        char[][] newMap = copyArray(map);
        char curr = map[row][col];
        HashSet<String> result = new HashSet<>();
        // Move into room, seems to work
        if(moveType == 1){
            int dest = finalDestination().get(curr);
            if(map[3][dest] == '.'){
                newMap[3][dest] = curr;
            } else { // Move into top row
                newMap[2][dest] = curr;
            }
            newMap[row][col] = '.';
            result.add(mapString(newMap));
            newMap = copyArray(map);
            // Can move into bottom rowin room
        } else { // moveType 0, move out into corridor
            int left = col - 1;
            int right = col + 1;
            while(left > 1 && map[1][left-1] == '.'){
                left--;
            }
            // If we stopped on top of a room, go back one step
            if(Arrays.asList(3,5,7,9).contains(left)){
                left++;
            }
            while(right < 12 && map[1][right+1] == '.'){
                right++;
            }
            if(Arrays.asList(3,5,7,9).contains(right)){
                right--;
            }
            for(int i = left; i <= right; i++){
                if(!Arrays.asList(3,5,7,9).contains(i)){
                    newMap[row][col] = '.';
                    newMap[1][i] = curr;
                    result.add(mapString(newMap));
                    newMap = copyArray(map);
                }
            }
        }
        return result;
    }

    // -1 = no moves possible
    // 0 = can move out from room into hallway
    // 1 = can move into room
    // Probably works, did some tests
    public static int canMove(char[][] map, int row, int col){
        Map<Character, Integer> destinations = finalDestination();
        if(!Character.isLetter(map[row][col])){
            return -1;
        }
        //If in top row of room but can walk out onto hallway
        if(row == 2 && map[row-1][col-1] == '.' || map[row-1][col+1] == '.'){
            char curr = map[row][col];
            // If both curr and bottom row is correct then don't move
            if(destinations.get(curr) == col && map[row+1][col] == curr){
                return -1;
            } else {
                return 0;
            }
        }
        // In bottom in room and can walk out onto hallway
        if(row == 3 && map[row-1][col] == '.'){
            char curr = map[row][col];
            if(map[row-2][col-1] == '.' || map[row-2][col+1] == '.'){
                // If we are already in the right spot, then don't move
                if(destinations.get(curr) == col){
                    return -1;
                } else {
                    return 0;
                }
            }
        }
        // We are in the hallway
        if(row == 1){
            char curr = map[row][col];
            int destCol = destinations.get(curr);
            // curr's room is not occupied
            if(map[3][destCol] == '.' || (map[2][destCol] == '.' && map[3][destCol] == curr)){
                // Is there a clean path in the hallway to our room?
                // Tested and should work, but not entirely sure
                for(int i = Math.min(col, destCol) + 1; i < Math.max(col, destCol); i++){
                    if(map[1][i] != '.'){
                        return -1;
                    }
                }
                // Can move into the room from the hallway
                return 1;
            }
        }
        // Can't move neither out into the hallway nor into our room
        return -1;
    }

    public static char[][] mapStringToMap(String s){
        String[] split = s.split("\\]\\[");
        char[][] result = new char[5][13];
        int row = 0;
        int col = 0;
        int i = 0;
        while(row < 5){
            char curr = split[row].charAt(i);
            if(Arrays.asList((char) 0x2588, '.', 'A', 'B', 'C', 'D').contains(curr)){
                result[row][col] = curr;
                col++;
                if(col == 13){
                    col = 0;
                    row++;
                    i = -1;
                }
            }
            i++;
        }
        return result;
    }

    public static Map<Character, Integer> finalDestination(){
        Map<Character, Integer> result = new HashMap<>();
        result.put('A', 3);
        result.put('B', 5);
        result.put('C', 7);
        result.put('D', 9);
        return result;
    }
    
    public static void printMap(char[][] map){
        for(var xs: map){
            System.out.println(Arrays.toString(xs));
        }
    }

    public static String mapString(char[][] map){
        StringBuilder sb = new StringBuilder();
        for(var xs: map){
            sb.append(Arrays.toString(xs));
        }
        return sb.toString();
    }

    public static char[][] generateMap(){
        ArrayList<String> list = parseInputToArray();
        char[][] result = new char[5][13];
        for(var xs: result){
            Arrays.fill(xs, (char) 0x2588);
        }
        for(int i = 0; i < list.size(); i++){
            String curr = list.get(i);
            for(int j = 0; j < curr.length(); j++){
                if(curr.charAt(j) == '#'){
                    result[i][j] = (char) 0x2588;
                } else {
                    if(curr.charAt(j) == ' '){
                        continue;
                    } else {
                        result[i][j] = curr.charAt(j);
                    }
                }
            }
        }
        return result;
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
