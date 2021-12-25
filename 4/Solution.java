import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.*;

public class Solution {
    public static void main(String[] args){
        System.out.println(problemOne());
        System.out.println(problemTwo());
    }
    
    public static int problemOne(){
        return solve(false);
    }
    
    public static int problemTwo(){
        return solve(true);
    }

    public static int solve(boolean second){
        List<Integer[][]> boards = generateBoards();
        List<String> list = parseInputToArray();
        int[] inputs = Arrays.stream(list.remove(0).split(",")).mapToInt(Integer::parseInt).toArray();
        for(Integer x: inputs){
            for(Integer[][] board: boards){
                for(int i = 0; i < board.length; i++){
                    for(int j = 0; j < board[0].length; j++){
                        if(board[i][j] == x){ // Integer so should be fine to do check like this
                            board[i][j] = -1;
                        }
                    }
                }
            }
            if(second){
                if(boards.size() == 1 && multipleCheckBingo(boards).size() == 1){
                    return bingoScore(boards.get(0), x);
                }
                for(Integer[][] m: multipleCheckBingo(boards)){
                    boards.remove(m);
                    if(boards.size() == 1){
                        break;
                    }
                }
            } else {
                Integer[][] bingoBoard = checkBingo(boards);
                if(bingoBoard != null){
                    return bingoScore(bingoBoard, x);
                }
            }
        }
        return 0;
    }
    
    //calculates the winning boards score
    public static int bingoScore(Integer[][] board, int curr){
        int sum = 0;
        for(Integer[] xs: board){
            for(Integer x: xs){
                if(x >= 0){
                    sum += x;
                }
            }
        }
        return sum*curr;
    }

    public static List<Integer[][]> multipleCheckBingo(List<Integer[][]> boards){
        List<Integer[][]> result = new ArrayList<>();
        for(Integer[][] m: boards){
            // horizontally
            for(int i = 0; i < m.length; i++){
                boolean horizontally = true;
                boolean vertically = true;
                for(int j = 0; j < m[0].length; j++){
                    if(m[i][j] >= 0){
                        horizontally = false;
                    }
                    if(m[j][i] >= 0){
                        vertically = false;
                    }
                }
                if(horizontally || vertically){
                    result.add(m);
                }
            }
        }
        return result;
    }

    // checks if we have bingo, and returns that board if we do
    public static Integer[][] checkBingo(List<Integer[][]> boards){
        for(Integer[][] m: boards){
            // horizontally
            for(int i = 0; i < m.length; i++){
                boolean horizontally = true;
                boolean vertically = true;
                for(int j = 0; j < m[0].length; j++){
                    if(m[i][j] >= 0){
                        horizontally = false;
                    }
                    if(m[j][i] >= 0){
                        vertically = false;
                    }
                }
                if(horizontally || vertically){
                    return m;
                }
            }
        }
        return null;
    }


    public static List<Integer[][]> generateBoards(){
        List<String> list = parseInputToArray();
        // parse first line into an int array
        int[] inputs = Arrays.stream(list.remove(0).split(",")).mapToInt(Integer::parseInt).toArray();
        ArrayList<Integer[][]> boards = new ArrayList<>();
        list = list.stream().filter(x -> !x.isEmpty()).toList();
        Integer[][] m = new Integer[1][1];
        int row = 0;
        int col = 0;
        for(int i = 0; i < list.size(); i++){
            if(i % 5 == 0){
                m = new Integer[5][5];
                boards.add(m);
                row = 0;
                col = 0;
            }
            for(String s: list.get(i).split(" ")){
                if(!s.isEmpty()){
                    m[row][col] = Integer.parseInt(s);
                    col++;
                }
            }
            col = 0;
            row++;
        }
        return boards;
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
