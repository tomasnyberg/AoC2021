import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

public class Solution {
    public static HashSet<String> beacons = new HashSet<>();
    public static void main(String[] args){
        long start = System.currentTimeMillis();
        System.out.println(problemOne());
        System.out.println(problemTwo());
        System.out.println("This run took: "  + (System.currentTimeMillis() - start) +"ms");
    }
    
    public static int problemOne(){
        List<Scanner> scanners = generateScanners();
        Scanner s0 = scanners.get(0);
        s0.x = 0;
        s0.y = 0;
        s0.z = 0;
        s0.foundLocation = true;
        int aligned = 0;
        while(aligned < scanners.size() - 1){
            for(int i = 0; i < scanners.size(); i++){
                for(int j = 0; j < scanners.size(); j++){
                    if(i != j){
                        if((scanners.get(i).foundLocation && !(scanners.get(i).foundLocation && scanners.get(j).foundLocation))){
                            if(alignTwoScanners(scanners.get(i), scanners.get(j))){
                                aligned++;
                            }
                        }
                    }
                }
            }
        }
        for(Scanner scan: scanners){
            for(Signal sig: scan.signals){
                beacons.add((sig.x+scan.x) + "," + (sig.y+scan.y) + "," + (sig.z+scan.z));
            }
            System.out.println(scan);
        }
        System.out.println("Biggest manhattan: " + findBiggestDiff(scanners));
        return beacons.size();
    }

    public static int findBiggestDiff(List<Scanner> list){
        int biggest = 0;
        for(Scanner scan: list){
            for(Scanner scan2: list){
                int diffX = Math.abs(scan.x - scan2.x);
                int diffY = Math.abs(scan.y - scan2.y);
                int diffZ = Math.abs(scan.z - scan2.z);
                int dist = diffX + diffY + diffZ;
                biggest = dist > biggest ? dist:biggest;
            }
        }
        return biggest;
    }

    public static boolean alignTwoScanners(Scanner s1, Scanner s2){
        for(Signal sig1: s1.signals){
            for(Signal sig2: s2.signals){
                List<String> common = sig1.compare(sig2);
                if(common.size() >= 11){
                    List<int[]> coordsOne = new ArrayList<>();
                    List<int[]> coordsTwo = new ArrayList<>();
                    for(String s: common){
                        int indexInSig1 = sig1.relatives.indexOf(s);
                        int indexInSig2 = sig2.relatives.indexOf(s);
                        Signal first = s1.signals.get(indexInSig1);
                        Signal second = s2.signals.get(indexInSig2);
                        int[] xs  = {first.x, first.y, first.z};
                        int[] ys  = {second.x, second.y, second.z};
                        coordsOne.add(xs);
                        coordsTwo.add(ys);
                    }
                    int[][] rotations = rotations();
                    for(int[] rotation: rotations){
                        List<int[]> coordsTwoRotated = new ArrayList<>();
                        for(int[] coord: coordsTwo){
                            coordsTwoRotated.add(matrixMul(coord, rotation));
                        }
                        int diffX = coordsOne.get(0)[0] - coordsTwoRotated.get(0)[0];
                        int diffY = coordsOne.get(0)[1] - coordsTwoRotated.get(0)[1];
                        int diffZ = coordsOne.get(0)[2] - coordsTwoRotated.get(0)[2];
                        boolean allCorrect = true;
                        for(int i = 0; i < coordsOne.size(); i++){
                            int[] first = coordsOne.get(i);
                            int[] second = coordsTwoRotated.get(i);
                            int diffXnew = first[0] - second[0];
                            int diffYnew = first[1] - second[1];
                            int diffZnew = first[2] - second[2];
                            if(diffX != diffXnew || diffY != diffYnew || diffZnew != diffZ){
                                allCorrect = false;
                                break;
                            }
                        }
                        if(allCorrect){
                            if(s1.foundLocation){
                                s2.x = s1.x + diffX;
                                s2.y = s1.y + diffY;
                                s2.z = s1.z + diffZ;
                                s2.foundLocation = true;
                            } else if (s2.foundLocation){
                                s1.x = s2.x + diffX;
                                s1.y = s2.y + diffY;
                                s1.z = s2.z + diffZ;
                                s1.foundLocation = true;
                            }
                            // We have now found the correct rotation, so we rotate this scanners signals to match the correct rotation (relative to scanner 0)
                            for(Signal s: s2.signals){
                                int[] oldPos = {s.x, s.y, s.z};
                                oldPos = matrixMul(oldPos, rotation);
                                s.x = oldPos[0];
                                s.y = oldPos[1];
                                s.z = oldPos[2];
                            }
                        }
                    }
                    return true;
                }
            }
        }
        return false;
    }



    public static int problemTwo(){
        return 0;
    }


    public static int[] matrixMul(int[] vec, int[] rot){
        int[] result = new int[3];
        int x = vec[0];
        int y = vec[1];
        int z = vec[2];
        result[0] = x*rot[0] + y*rot[3] + z*rot[6];
        result[1] = x*rot[1] + y*rot[4] + z*rot[7];
        result[2] = x*rot[2] + y*rot[5] + z*rot[8];
        return result;
    }

    public static int[][] rotations(){
        int[] xs = new int[9];
        int[][] result= new int[48][9];
        int pos = 0;
        for(int a = 0; a < 3; a++){
            for(int i = 0; i < 2; i++){
                xs[a] = i % 2 == 0 ? 1:-1;
                for(int b = 3; b < 6; b++){
                    if(b%3 != a%3){
                        for(int j = 0; j < 2; j++){
                            xs[b] = j % 2 == 0 ? 1:-1;
                            for(int c = 6; c < 9; c++){
                                if(c % 3 != a %3 && c % 3 != b%3){
                                    for(int k = 0; k < 2; k++){
                                        xs[c] = k % 2 == 0 ? 1:-1;
                                        result[pos] = xs.clone();
                                        pos++;
                                    }
                                    xs[c] = 0;
                                }
                            }
                        }
                        xs[b] = 0;
                    }
                }
            }
            xs[a] = 0;
        }
        return result;
    }

    public static List<Scanner> generateScanners(){
        ArrayList<String> list = parseInputToArray();
        List<Scanner> result = new ArrayList<>();
        Scanner currScan = null;
        int idx = 0;
        for(String s: list){
            if(s.isEmpty()){
                idx = 0;
                continue;
            }
            if(s.startsWith("---")){
                currScan = new Scanner();
                result.add(currScan);
            } else {
                // System.out.println("Adding scanner: " + s + " at idx: " + idx);
                currScan.addSignal(s, idx++);
            }
        }
        return result;
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

class Signal {
    public List<String> relatives = new ArrayList<>(Collections.nCopies(30, ""));
    public int x;
    public int y;
    public int z;
    public int idx;

    public Signal(int x, int y, int z, int idx){
        this.x = x;
        this.y = y;
        this.z = z;
        this.idx = idx;
    }

    //Adds the other signal to this signals relatives, by fingerprinting 
    public void align(Signal other){
        int dx = (int) Math.abs(x-other.x); 
        int dy = (int) Math.abs(y-other.y); 
        int dz = (int) Math.abs(z-other.z);
        // Not taking sqrt 
        int dist = dx*dx + dy*dy + dz*dz;
        StringBuilder sb = new StringBuilder();
        sb.append(Math.min(dx, Math.min(dy, dz)));
        sb.append(",");
        sb.append(Math.max(dx, Math.max(dy, dz)));
        sb.append(",");
        sb.append(dist);
        // Add the fingerprint at the correct index
        other.relatives.set(idx, sb.toString());
        relatives.set(other.idx, sb.toString());
    }

    public List<String> compare(Signal other){
        List<String> result = new ArrayList<>();
        for(String s: relatives){
            if(other.relatives.indexOf(s) != -1){
                result.add(s);
            }
        }
        return result;
    }

    @Override
    public String toString() {
        return "Signal at: " + x + ", " + y + ", " + z;
    }
}

class Scanner {
    public List<Signal> signals = new ArrayList<>();
    public int x;
    public int y;
    public int z;
    public boolean foundLocation = false;
    
    public void addSignal(String s, int idx){
        String[] split = s.split(",");
        int otherx = Integer.parseInt(split[0]);
        int othery = Integer.parseInt(split[1]);
        int otherz = Integer.parseInt(split[2]);
        Signal newSignal = new Signal(otherx, othery, otherz, idx);
        for(Signal sig: signals){
            sig.align(newSignal);
        }
        signals.add(newSignal);
    }

    @Override
    public String toString() {
        return "Scanner at: "  + x + ", " + y + ", " + z;
    }
}