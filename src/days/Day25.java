package days;

import util.InputReader;
import java.util.ArrayList;

public class Day25 {

    public long part1(String input) {
        long uniqueLockKeyPairs = 0;

        ArrayList<int[]> keys = new ArrayList<>();
        ArrayList<int[]> locks = new ArrayList<>();

        String[] keysAndLocks = input.split("\r\n\r\n");
        for(String keyOrLock : keysAndLocks){
            if(keyOrLock.startsWith("#####") || keyOrLock.startsWith("\r\n#####")){
                locks.add(getLock(keyOrLock));
            } else if(keyOrLock.startsWith(".....") || keyOrLock.startsWith("\r\n.....")){
                keys.add(getKey(keyOrLock));
            }
        }

        for(int[] key : keys){
            for(int[] lock : locks){
                boolean fits = true;
                for(int i = 0; i<5; i++){
                    if(key[i]+lock[i]>5){
                        fits = false;
                    }
                }
                if(fits){
                    uniqueLockKeyPairs++;
                }
            }
        }

        return uniqueLockKeyPairs;
    }

    public int[] getKey(String input){
        System.out.println("Parsing key:");
        System.out.println(input);

        char[][] inputAsGrid = InputReader.getTwoDimensionalCharArray(input);

        int[] key = new int[5];
        for(int i = 5; i>0; i--){
            for(int j = 0; j<5; j++){
                if(inputAsGrid[i][j] == '#'){
                    key[j] = 6-i;
                }
            }
        }

        return key;
    }

    public int[] getLock(String input){
        System.out.println("Parsing lock:");
        System.out.println(input);

        char[][] inputAsGrid = InputReader.getTwoDimensionalCharArray(input);

        int[] lock = new int[5];
        for(int i = 1; i<6; i++){
            for(int j = 0; j<5; j++){
                if(inputAsGrid[i][j] == '#'){
                    lock[j] = i;
                }
            }
        }

        return lock;
    }
}
