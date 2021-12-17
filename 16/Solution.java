import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;
import java.math.BigInteger;

public class Solution {
    public static void main(String[] args){
        long start = System.currentTimeMillis();
        System.out.println(problemOne());
        System.out.println(problemTwo());
        System.out.println("This run took: "  + (System.currentTimeMillis() - start) +"ms");
    }
    
    // lentype 0 seems to be working
    public static int problemOne(){
        // String longBinaryString = hexToBinary(parseInputToArray().get(0));
        // longBinaryString = longBinaryString.substring(0, longBinaryString.length()-2);
        String longBinaryString = "1100000000000001010100000000000000000001011000010001010110100010111000001000000000101111000110000010001101000";
        int[] totals = parseOperatorPacket(longBinaryString);
        System.out.println(Arrays.toString(totals));
        return totals[1];
    }
    
    public static int[] parseOperatorPacket(String binaryString){
        int[] result = new int[3]; // first index length, second versionValue, third result (blank for now)
        int lenType = binaryString.charAt(6) == '1' ? 1:0;
        result[1] += Integer.parseInt(binaryString.substring(0, 3), 2); // add the version value to our sum
        int lenInBits = 0;
        int lenInPackets = 0;
        if(lenType == 0){
            lenInBits = Integer.parseInt(binaryString.substring(7, 22), 2);   
        } else {
            lenInPackets = Integer.parseInt(binaryString.substring(7, 18), 2);
        }
        int j = lenType == 0 ? 22:18;
        while(lenInPackets > 0 || (j < 22 + lenInBits && lenInBits != 0)){
            lenInPackets--;
            if(binaryString.substring(j+3, j+6).equals("100")){ // If we have a literal value
                int[] lit = parseLiteral(binaryString.substring(j));
                j += lit[0]; // Skip over the literal
                result[1] += lit[2]; // Increment our versionValue;
            } else {
                int[] op = parseOperatorPacket(binaryString.substring(j));
                j += op[0]; // skip the entire length of the operator
                result[1] += op[1]; // Add the total versionValue from the nested operator
            }
        } 
        result[0] = j;
        return result;
    }


    // Seems to work
    public static int[] parseLiteral(String binaryString){ 
        // Determines how long a literal is so that we know where the next packet starts
        // Also returns the value of the literal
        // Example : "11110011010110101101001010001111001000010000" should return [28, 43690]  since 
        // It gets split like this : "1111001101011010110100101000 1111001000000000"
        int len = 0;
        int version = Integer.parseInt(binaryString.substring(0, 3) , 2);
        StringBuilder sum = new StringBuilder();
        binaryString = binaryString.substring(6); // Cut off version ID and type id, len 6
        len += 6;
        int i = 0;
        while(binaryString.charAt(i) != '0'){
            sum.append(binaryString.substring(i+1, i+5));
            i += 5;
            len += 5;
        }
        sum.append(binaryString.substring(i+1, i+5));
        i += 5;
        len += 5;
        int value = Integer.parseInt(sum.toString(), 2);
        int[] result = {len, value, version};
        return result;
    }

    public static int problemTwo(){
        return 0;
    }


    public static String hexToBinary(String hex) {
        return new BigInteger(hex, 16).toString(2);
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
