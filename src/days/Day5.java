package days;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class Day5 {

    public static int part1(String orderingRules, String updatePagesInput){

        int answer = 0;

        HashMap<Integer, ArrayList<Integer>> rulesPerNumber = getMapOfRules(orderingRules);

        ArrayList<String> updatePagesInputAsStringPerLine = updatePagesInput.lines().collect(Collectors.toCollection(ArrayList::new));
        ArrayList<ArrayList<Integer>> pagesPerUpdate = getNumbersFromStrings(updatePagesInputAsStringPerLine, ",");

        for(ArrayList<Integer> update : pagesPerUpdate){
            boolean correctUpdate = true;

            ArrayList<Integer> numbersPassed = new ArrayList<>();

            for(Integer currentNumber : update){
                ArrayList<Integer> rulesForCurrentNumber = rulesPerNumber.containsKey(currentNumber) ? rulesPerNumber.get(currentNumber) : new ArrayList<>();
                for(Integer rule : rulesForCurrentNumber){
                    if(numbersPassed.contains(rule)){
                        System.out.println(update + " is false since number " + rule + " already occurred before " + currentNumber);

                        correctUpdate = false;
                    }
                }
                numbersPassed.add(currentNumber);
            }

            if(correctUpdate){
                int middleNumber = update.get(update.size()/2);
                answer += middleNumber;
            }
        }

        return answer;
    }

    public static int part2(String orderingRules, String updatePagesInput){

        int answer = 0;

        HashMap<Integer, ArrayList<Integer>> rulesPerNumber = getMapOfRules(orderingRules);

        ArrayList<String> updatePagesInputAsStringPerLine = updatePagesInput.lines().collect(Collectors.toCollection(ArrayList::new));
        ArrayList<ArrayList<Integer>> pagesPerUpdate = getNumbersFromStrings(updatePagesInputAsStringPerLine, ",");

        for(ArrayList<Integer> update : pagesPerUpdate){
            boolean correctUpdate = true;

            ArrayList<Integer> numbersPassed = new ArrayList<>();

            for(Integer currentNumber : update){
                ArrayList<Integer> rulesForCurrentNumber = rulesPerNumber.containsKey(currentNumber) ? rulesPerNumber.get(currentNumber) : new ArrayList<>();
                for(Integer rule : rulesForCurrentNumber){
                    if(numbersPassed.contains(rule)) {
                        correctUpdate = false;
                        break;
                    }
                }
                numbersPassed.add(currentNumber);
            }

            if(!correctUpdate) {
                int middleNumber = fixUpdateAndGetMiddleNumber(rulesPerNumber, update);
                answer += middleNumber;
            }
        }

        return answer;
    }

    private static int fixUpdateAndGetMiddleNumber(final HashMap<Integer, ArrayList<Integer>> rulesPerNumber, final ArrayList<Integer> update) {

        boolean allgood = false;

        while(!allgood) {
            boolean goodSoFar = true;
            ArrayList<Integer> numbersPassed = new ArrayList<>();
            for (int i = 0; i < update.size(); i++) {
                if(!goodSoFar) {
                    break;
                }

                int currentNumber = update.get(i);
                ArrayList<Integer> rulesForCurrentNumber = rulesPerNumber.containsKey(currentNumber) ? rulesPerNumber.get(currentNumber) : new ArrayList<>();

                for (Integer rule : rulesForCurrentNumber) {
                    if(numbersPassed.contains(rule)) {
                        //This number is in the wrong position!
                        update.remove(i);
                        update.add(i-1, currentNumber);
                        goodSoFar = false;
                        break;
                    }
                }
                numbersPassed.add(currentNumber);
            }

            if(goodSoFar){
                allgood = true;
            }
        }

        return update.get(update.size()/2);
    }

    private static ArrayList<ArrayList<Integer>> getNumbersFromStrings(final ArrayList<String> updatePagesInputAsStringPerLine, String splitString) {
        ArrayList<ArrayList<Integer>> updates = new ArrayList<>();
        for(String string : updatePagesInputAsStringPerLine) {
            ArrayList<Integer> update = Stream.of(string.split(splitString)).mapToInt(Integer::parseInt).boxed().collect(Collectors.toCollection(ArrayList::new));

            updates.add(update);
        }
        return updates;
    }

    public static HashMap<Integer, ArrayList<Integer>> getMapOfRules(String orderingRulesInput){
        HashMap<Integer, ArrayList<Integer>> rules = new HashMap();

        ArrayList<String> lines = orderingRulesInput.lines().collect(Collectors.toCollection(ArrayList::new));

        for(String line : lines){
            String[] split = line.split("\\|");
            int leftNumber = Integer.parseInt(split[0]);
            int rightNumber = Integer.parseInt(split[1]);

            if(rules.containsKey(leftNumber)){
                rules.get(leftNumber).add(rightNumber);
            }
            else {
                ArrayList<Integer> rightNumbers = new ArrayList<>();
                rightNumbers.add(rightNumber);
                rules.put(leftNumber, rightNumbers);
            }
        }

        return rules;
    }
}