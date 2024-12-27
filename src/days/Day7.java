package days;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class Day7 {

    class Equation {

        ArrayList<Long> numbers;
        long answer;

        public Equation(long answer, ArrayList<Long> numbers) {
            this.answer = answer;
            this.numbers = numbers;
        }
    }

    public long part1(String input) {
        ArrayList<Equation> equations = getEquations(input);
        long answer = 0;

        for(Equation equation : equations) {
            if(isSolvable(equation)) {
                answer += equation.answer;
            }
        }

        return answer;
    }

    public long part2(String input) {
        ArrayList<Equation> equations = getEquations(input);
        long answer = 0;

        for(Equation equation : equations) {
            if(isSolvablePart2(equation)) {
                answer += equation.answer;
            }
        }

        return answer;
    }

    public boolean isSolvable(Equation equation){
        long firstNumber = equation.numbers.get(0);
        ArrayList<Long> numbers = equation.numbers;
        numbers.remove(0);

        return isSolvable(equation.answer, firstNumber,0, numbers);
    }

    public boolean isSolvable(long answer, long sumSoFar, int index, ArrayList<Long> numbers){
        //last number
        if(numbers.size()-1 == index){
            long multiplied = Math.multiplyExact(sumSoFar, numbers.get(index));
            long summed = Math.addExact(sumSoFar, numbers.get(index));

            return(multiplied == answer) || (summed == answer);
        }
        else{
            long multiplied = Math.multiplyExact(sumSoFar, numbers.get(index));
            long summed = Math.addExact(sumSoFar, numbers.get(index));

            return isSolvable(answer, multiplied, index+1, numbers) || isSolvable(answer, summed, index+1, numbers);
        }
    }

    public boolean isSolvablePart2(Equation equation){
        long firstNumber = equation.numbers.get(0);
        ArrayList<Long> numbers = equation.numbers;
        numbers.remove(0);

        return isSolvablePart2(equation.answer, firstNumber,0, numbers);
    }

    public boolean isSolvablePart2(long answer, long sumSoFar, int index, ArrayList<Long> numbers){
        //last number
        if(numbers.size()-1 == index) {
            long multiplied = Math.multiplyExact(sumSoFar, numbers.get(index));
            long summed = Math.addExact(sumSoFar, numbers.get(index));
            long piped = Long.parseLong(sumSoFar + "" + numbers.get(index));

            return(multiplied == answer) || (summed == answer) || (piped == answer);
        }
        else {
            long multiplied = Math.multiplyExact(sumSoFar, numbers.get(index));
            long summed = Math.addExact(sumSoFar, numbers.get(index));
            long piped = Long.parseLong(sumSoFar + "" + numbers.get(index));


            return isSolvablePart2(answer, multiplied, index+1, numbers) || isSolvablePart2(answer, summed, index+1, numbers) || isSolvablePart2(answer, piped, index+1, numbers);
        }
    }

    public ArrayList<Equation> getEquations(String input) {
        ArrayList<Equation> equations = new ArrayList<>();

        ArrayList<String> lines = input.lines().collect(Collectors.toCollection(ArrayList::new));
        for(String line : lines) {
            String[] split = line.split(": ");
            long answer = Long.parseLong(split[0]);
            String[] numbers = split[1].split(" ");
            ArrayList<Long> numberList = new ArrayList<>();
            for(int i = 0; i < numbers.length; i++) {
                numberList.add(Long.parseLong(numbers[i]));
            }
            equations.add(new Equation(answer, numberList));
        }

        return equations;
    }
}

