package days;

import java.util.ArrayList;
import java.util.HashMap;

public class Day21 {

    HashMap<String, Long> moveCounts = new HashMap<>();

    public long part1(String input, int robots){
        long sum = 0L;

        for(String inputLine : input.lines().toList()){
            ArrayList<String> keypadMovesToExplore = new ArrayList<>();
            if(inputLine.equals("029A")){
                keypadMovesToExplore.add("<A^A>^^AvvvA");
                keypadMovesToExplore.add("<A^A^^>AvvvA");
            } else if(inputLine.equals("980A")){
                keypadMovesToExplore.add("^^^A<AvvvA>A");
            } else if(inputLine.equals("179A")){
                keypadMovesToExplore.add("^<<A^^A>>AvvvA");
            } else if(inputLine.equals("456A")){
                keypadMovesToExplore.add("^^<<A>A>AvvA");
            } else if(inputLine.equals("379A")){
                keypadMovesToExplore.add("^A<<^^A>>AvvvA");
                keypadMovesToExplore.add("^A^^<<A>>AvvvA");
            } else if(inputLine.equals("341A")){
                keypadMovesToExplore.add("^A<<^AvA>>vA");
                keypadMovesToExplore.add("^A^<<AvA>>vA");
            } else if(inputLine.equals("803A")){
                keypadMovesToExplore.add("^^^<AvvvA^>AvA");
                keypadMovesToExplore.add("<^^^AvvvA>^AvA");
            } else if(inputLine.equals("149A")){
                keypadMovesToExplore.add("^<<A^A>>^AvvvA");
                keypadMovesToExplore.add("^<<A^A^>>AvvvA");
            } else if(inputLine.equals("683A")){
                keypadMovesToExplore.add("^^A<^A>vvAvA");
                keypadMovesToExplore.add("^^A^<A>vvAvA");
                keypadMovesToExplore.add("^^A<^Avv>AvA");
                keypadMovesToExplore.add("^^A^<Avv>AvA");
            } else if(inputLine.equals("208A")){
                keypadMovesToExplore.add("^<AvA^^^A>vvvA");
                keypadMovesToExplore.add("<^AvA^^^A>vvvA");
                keypadMovesToExplore.add("^<AvA^^^Avvv>A");
                keypadMovesToExplore.add("<^AvA^^^Avvv>A");
            }

            long minimalMoves = Long.MAX_VALUE;

            for(String numpadMoves : keypadMovesToExplore){
                //System.out.println("Checking the move string:  " + numpadMoves);

                long movesCount = getMinimalMovesForMoveString(numpadMoves, robots);
                if(movesCount < minimalMoves){
                    minimalMoves = movesCount;
                }

            }

            System.out.println("Moves to " + inputLine + " count: " + minimalMoves);

            long number = Long.parseLong(inputLine.replaceAll("[^0-9]", ""));

            sum += minimalMoves * number;
        }
        return sum;
    }

    public long getMinimalMovesForMoveString(String moves, int robots){
        long sumOfMoves = 0L;

        char prevMove = 'A';
        for(char c : moves.toCharArray()){
            sumOfMoves += getMinimalMovesForMove(prevMove, c, robots);
            prevMove = c;
        }

        return sumOfMoves;
    }

    public long getMinimalMovesForMove(char previousMove, char nextMove, int robots){

        String hash = previousMove + " - " + nextMove + " - " + robots;

        if(moveCounts.containsKey(hash)){
            return moveCounts.get(hash);
        }

        //The last robot, you, is just one move.
        if(robots == 0){
            moveCounts.put(hash, 1L);
            return 1L;
        }

        //Get possible moves to perform this move
        ArrayList<String> stringsOfMoves = new DirectionKeyPad().getPossibleMovesTo(previousMove, nextMove);

        long minimalSum = Long.MAX_VALUE;
        //Try every posibility for the minimal
        for(String moves : stringsOfMoves){
            char[] movesArray  = moves.toCharArray();
            long sumOfMoves = 0;
            char preMove = 'A';
            for(char move: movesArray){
                sumOfMoves += getMinimalMovesForMove(preMove, move, robots-1);
                preMove = move;
            }

            if(sumOfMoves < minimalSum){
                minimalSum = sumOfMoves;
            }
        }
        //System.out.println(robots + " " +nextMove + ": " + minimalSum);
        moveCounts.put(hash, minimalSum);
        return minimalSum;
    }

    public static class DirectionKeyPad {

        char[][] keyboardGrid = {   {'X', '^', 'A'},
                                    {'<', 'v', '>'}
                                };

        public ArrayList<String> getPossibleMovesTo(char previousMove, char character) {

            ArrayList<String> possibleMoves = new ArrayList<>();

            //We start at A, always?
            if (previousMove == 'A') {
                if (character == 'A') {
                    possibleMoves.add("A");
                } else if (character == '^') {
                    possibleMoves.add("<A");
                } else if (character == '>') {
                    possibleMoves.add("vA");
                } else if (character == '<') {
                    possibleMoves.add("v<<A");
                } else if (character == 'v') {
                    possibleMoves.add("v<A");
                    possibleMoves.add("<vA");
                }
            }
            //We start at ^
            if (previousMove == '^') {
                if (character == 'A') {
                    possibleMoves.add(">A");
                } else if (character == '^') {
                    possibleMoves.add("A");
                } else if (character == '>') {
                    possibleMoves.add(">vA");
                    possibleMoves.add("v>A");
                } else if (character == '<') {
                    possibleMoves.add("v<A");
                } else if (character == 'v') {
                    possibleMoves.add("vA");
                }
            }

            //We start at <
            if (previousMove == '<') {
                if (character == 'A') {
                    possibleMoves.add(">>^A");
                } else if (character == '^') {
                    possibleMoves.add(">^A");
                } else if (character == '>') {
                    possibleMoves.add(">>A");
                } else if (character == '<') {
                    possibleMoves.add("A");
                } else if (character == 'v') {
                    possibleMoves.add(">A");
                }
            }

            //We start at v
            if (previousMove == 'v') {
                if (character == 'A') {
                    possibleMoves.add(">^A");
                    possibleMoves.add("^>A");
                } else if (character == '^') {
                    possibleMoves.add("^A");
                } else if (character == '>') {
                    possibleMoves.add(">A");
                } else if (character == '<') {
                    possibleMoves.add("<A");
                } else if (character == 'v') {
                    possibleMoves.add("A");
                }
            }

            //We start at >
            if (previousMove == '>') {
                if (character == 'A') {
                    possibleMoves.add("^A");
                } else if (character == '^') {
                    possibleMoves.add("<^A");
                    possibleMoves.add("^<A");
                } else if (character == '>') {
                    possibleMoves.add("A");
                } else if (character == '<') {
                    possibleMoves.add("<<A");
                } else if (character == 'v') {
                    possibleMoves.add("<A");
                }
            }

            return possibleMoves;
        }
    }


}


