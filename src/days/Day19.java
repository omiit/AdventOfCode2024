package days;

import util.InputReader;

import java.util.ArrayList;
import java.util.HashMap;

public class Day19 {

    public HashMap<String, Long> combinationsCache = new HashMap<>();

    public int part1(String input) {
        String[] inputParts = InputReader.splitOnEmptyLines(input);
        String towels = inputParts[0];
        String desiredPatterns = inputParts[1];

        PatternSolver patternSolver = new PatternSolver(towels);
        patternSolver.removeSolvableTowels();

        int solvableTowelCount = 0;

        for(String pattern : desiredPatterns.lines().toList()){
            if(pattern.isEmpty()) continue; //todo: solve empty line
            if(patternSolver.isSolvable(pattern)){
                System.out.println(pattern + " is solvable");
                solvableTowelCount++;
            }
        }

        return solvableTowelCount;
    }

    public long part2(String input) {
        String[] inputParts = InputReader.splitOnEmptyLines(input);
        String desiredPatterns = inputParts[1];
        String towels = inputParts[0];

        long totalCombinationsOfAllPatterns = 0;

        for(String pattern : desiredPatterns.lines().toList()) {
            if(pattern.isEmpty()) continue; //todo: solve empty line

            totalCombinationsOfAllPatterns += new PatternSolveCounter(pattern, towels).getSolvableCombinations();
        }

        return totalCombinationsOfAllPatterns;
    }

    public class PatternSolveCounter {
        ArrayList<String> towelsInPattern = new ArrayList<>();
        String pattern;

        public PatternSolveCounter(String pattern, String input) {
            this.pattern = pattern;

            String[] towels = input.replace("\n", "").split(", ");
            for(String towel : towels){
                if(pattern.contains(towel)){
                    towelsInPattern.add(towel);
                }
            }

            System.out.println(pattern + " contains " + towelsInPattern.size() + " towels");
        }

        public long getSolvableCombinations(){
            long count = getSolvableCombinationsWithCache(pattern);

            System.out.println(pattern + " can be solved in " + count + " combinations");

            return count;
        }

        private long getSolvableCombinationsWithCache(String pattern){
            if(combinationsCache.containsKey(pattern)){
                return combinationsCache.get(pattern);
            }

            long combinations = getSolvableCombinations(pattern);

            combinationsCache.put(pattern, combinations);
            return combinations;
        }

        private long getSolvableCombinations(String pattern){
            long combinations = 0;
            for(String towel : towelsInPattern){
                if(pattern.startsWith(towel)){
                    if(pattern.equals(towel)){
                        combinations++;
                    } else {
                        String leftOverPattern = pattern.replaceFirst(towel, "");
                        combinations += getSolvableCombinationsWithCache(leftOverPattern);
                    }
                }
            }
            return combinations;
        }
    }

    public class PatternSolver {
        ArrayList<String> towels = new ArrayList<>();

        public PatternSolver(String input){
            String[] towels = input.split(", ");
            for(String towel : towels){
                addTowel(towel);
            }
        }

        public void addTowel(String towel){
            towels.add(towel);
        }

        public void removeSolvableTowels(){
            ArrayList<String> towelsCopy = new ArrayList<>(towels);

            for(String towel : towelsCopy){
                towels.remove(towel);
                if(!isSolvable(towel)){
                    towels.add(towel);
                }
            }
        }

        public boolean isSolvable(final String pattern) {
            if(pattern.equals("")){
                return true;
            }
            for(String towel : towels){
                if(pattern.startsWith(towel)){
                    String leftOverPattern = pattern.replaceFirst(towel, "");
                    if(isSolvable(leftOverPattern)){
                        return true;
                    }
                }
            }

            return false;
        }
    }
}
