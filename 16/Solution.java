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
    
    public static int problemOne(){
        int versionSum = 0;
        int i = 0;
        // String longBinaryString = hexToBinary(parseInputToArray().get(0));
        String longBinaryString = "11101110000000001101010000001100100000100011000001100000";
        while(i < longBinaryString.length()){
            String currVersion = longBinaryString.substring(i, i + 3);
            String currType = longBinaryString.substring(i + 3, i + 6);
            // Parse for literal values works now, going to extract to function that gives the length of them
            if(Integer.parseInt(currType, 2) == 4){ 
                int[] litres = parseLiteral(longBinaryString.substring(i)); // skip over the length of the whole literal value
                versionSum += litres[2];
                i += litres[0];
            } else { // Else we have an operator packet
                int[] res = parseOperatorPacket(longBinaryString.substring(i));
                i += res[0];
                versionSum += res[1];
            }
        }
        return versionSum;
    }
    
    public static int[] parseOperatorPacket(String binaryString){
        int[] result = new int[3]; // first index length, second versionValue, third result (blank for now)
        int lenType = binaryString.charAt(7) == '1' ? 1:0;
        result[1] += Integer.parseInt(binaryString.substring(0, 3)); // add the version value to our sum
        if(lenType == 0){ // len type where the next 15 bits are 
            int lenInBits = Integer.parseInt(binaryString.substring(7, 22), 2);
            result[0] = 22 + lenInBits;
            int j = 22;
            while(j < 22 + lenInBits){
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
        } else { // lentype packets
            int lenInPackets = Integer.parseInt(binaryString.substring(7, 18), 2);
            int j = 18;
            result[0] = 18; // need to manually set this unlike above
            while(j < binaryString.length() && lenInPackets > 0){
                lenInPackets--;
                if(binaryString.substring(j+3, j+6).equals("100")){ // If we have a literal value
                    int[] lit = parseLiteral(binaryString.substring(j));
                    j += lit[0]; // Skip over the literal
                    result[0] += lit[0];
                    result[1] += lit[2]; // Increment our versionValue;
                } else {
                    int[] op = parseOperatorPacket(binaryString.substring(j));
                    j += op[0]; // skip the entire length of the operator
                    result[0] += op[0];
                    result[1] += op[1]; // Add the total versionValue from the nested operator
                }
            }
        }
        return result;
    }


    // 111100 11010 11010 11010 01010 00

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
        int[] result = {len, Integer.parseInt(sum.toString(), 2), version};
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
