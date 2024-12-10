package days;

import java.util.ArrayList;
import java.util.Arrays;

import static java.lang.Math.abs;

public class Day2 {

    public int part1(String input){
        int safeLines = 0;

        for(String line : input.lines().toList()){
            if(isLineSafe(line.split(" "))){
                safeLines++;
            }
        }

        return safeLines;
    }

    public int part2(String input){
        int safeLines = 0;

        for(String line : input.lines().toList()){

            boolean oneOfTriesIsSafe = false;
            String[] numbers = line.split(" ");

            for (int i = 0; i < numbers.length; i++) {
                ArrayList<String> numbersList = new ArrayList<>(Arrays.asList(numbers));
                numbersList.remove(i);
                String[] numbersTemp = numbersList.toArray(new String[numbersList.size()]);

                if(isLineSafe(numbersTemp)) {
                    oneOfTriesIsSafe = true;
                }
            }

            if(oneOfTriesIsSafe){
                safeLines++;
            }
        }

        return safeLines;
    }

    private boolean isLineSafe(String[] numbers) {
        boolean safe = true;

        boolean ascending = Integer.parseInt(numbers[0]) < Integer.parseInt(numbers[1]);
        for (int i = 0; i < numbers.length; i++) {
            if(i!=0){
                int difference = Integer.parseInt(numbers[i])-Integer.parseInt(numbers[i-1]);

                if ((ascending && (difference < 0)) || (!ascending && (difference > 0))) {
                    safe = false;
                }

                if (!(abs(difference)>0 && abs(difference)<4)) {
                    safe = false;
                }
            }
        }
        return safe;
    }

}
