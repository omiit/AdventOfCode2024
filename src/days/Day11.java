package days;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Day11 {

    Map<String, Long> lookUp = new HashMap<>();

    public long part1(final String input){

        ArrayList<Long> stones = getStones(input);

        for(int i = 0; i<25; i++ ){
            stones = blink(stones);
        }

        return stones.size();
    }

    public long part2(final String input){

        ArrayList<Long> stones = getStones(input);

        long count = 0;

        for(Long stone : stones){
            count += getStoneCount(stone, 75);
        }

        return count;
    }

    private ArrayList<Long> blink(ArrayList<Long> stones){
        ArrayList<Long> newStones = new ArrayList<>();

        for(Long stone : stones){
            if(stone == 0){
                newStones.add(1L);
            } else if(stone.toString().length() % 2 == 0){
                String digitString = stone.toString();

                String leftDigits = digitString.substring(0, digitString.length() / 2);
                String rightDigits = digitString.substring(digitString.length() / 2);

                newStones.add(Long.parseLong(leftDigits));
                newStones.add(Long.parseLong(rightDigits));
            } else {
                newStones.add(stone * 2024);
            }
        }

        return newStones;
    }

    private long getStoneCount(Long engraving, int blinks){

        long count = 0L;

        String lookupKey = engraving.toString() +"|" + blinks;
        if(lookUp.containsKey(lookupKey)){
            return lookUp.get(lookupKey);
        }

        if(blinks == 0) {
            count = 1L;
        }
        else {
            if(engraving == 0L){
                count = getStoneCount(1L, blinks - 1);
            } else if(engraving.toString().length() % 2 == 0){
                String digitString = engraving.toString();

                String leftDigits = digitString.substring(0, digitString.length() / 2);
                String rightDigits = digitString.substring(digitString.length() / 2);

                count += getStoneCount(Long.parseLong(leftDigits), blinks-1);
                count += getStoneCount(Long.parseLong(rightDigits), blinks-1);
            } else {
                count = getStoneCount(engraving*2024, blinks-1);
            }
        }

        lookUp.put(lookupKey, count);
        return count;
    }

    private ArrayList<Long> getStones(String input){
        ArrayList<Long> stones = new ArrayList<>();

        for(String number : input.split(" ")){
            stones.add(Long.parseLong(number));
        }

        return stones;
    }
}
