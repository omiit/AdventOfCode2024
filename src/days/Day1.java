package days;

import java.util.ArrayList;
import java.util.Collections;

import static java.lang.Math.abs;

public class Day1 {

    public int part1(String input){
        ArrayList<Integer> leftList = new ArrayList<>();
        ArrayList<Integer> rightList = new ArrayList<>();

        fillLists(input, leftList, rightList);

        Collections.sort(leftList);
        Collections.sort(rightList);

        int sum = 0;

        for(int i = 0; i<leftList.size(); i++){
            sum += abs(leftList.get(i) - rightList.get(i));
        }

        return sum;
    }

    public int part2(String input){
        ArrayList<Integer> leftList = new ArrayList<>();
        ArrayList<Integer> rightList = new ArrayList<>();

        fillLists(input, leftList, rightList);

        int sum = 0;

        for (Integer integer : leftList) {
            int count = Collections.frequency(rightList, integer);
            sum += integer * count;
        }

        return sum;
    }

    public void fillLists(String input, ArrayList<Integer> leftList, ArrayList<Integer> rightList){
        String lineSplit = " {3}";

        for(String line : input.lines().toList()){
            String[] numbers = line.split(lineSplit);
            leftList.add(Integer.parseInt(numbers[0]));
            rightList.add(Integer.parseInt(numbers[1]));
        }
    }
}
