package days;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day3 {

    public int part1(String input){

        return doMultiplicationsForInput(input);
    }

    public int part2(String wrongInput){
        boolean readInput = true;
        StringBuilder input = new StringBuilder();
        for (int i = 0; i < wrongInput.length(); i++) {
            if(wrongInput.startsWith("do()", i)){
                readInput = true;
            }
            if(wrongInput.startsWith("don't()", i)){
                readInput = false;
            }
            if(readInput){
                input.append(wrongInput.charAt(i));
            }
        }

        return doMultiplicationsForInput(input.toString());
    }

    private int doMultiplicationsForInput(String input){
        int answer = 0;

        Pattern pattern = Pattern.compile("mul\\(\\d*,\\d*\\)");
        Matcher matcher = pattern.matcher(input);
        while (matcher.find()) {
            String match = matcher.group(0);

            String number1 = match.substring(4, match.indexOf(","));
            String number2 = match.substring(match.indexOf(",") + 1, match.length()-1);

            answer += Math.multiplyExact(Integer.parseInt(number1), Integer.parseInt(number2));
        }

        return answer;
    }
}
