package days;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day4 {
    
    public int part1(String input){
        int answer = 0;

        char[][] characterGrid = getTwoDimArrayFromString(input);

        ArrayList<String> horizontalLines = new ArrayList<>();
        ArrayList<String> verticalLines = new ArrayList<>();
        ArrayList<String> diagonalLinesFromLeft = new ArrayList<>();
        ArrayList<String> diagonalLinesFromRight = new ArrayList<>();

        int totalRows = characterGrid.length;
        int totalColumns = characterGrid[0].length;

        //Get all horizontal lines
        for(int rowIndex = 0; rowIndex < totalRows; rowIndex++){
            horizontalLines.add(String.valueOf(characterGrid[rowIndex]));
        }

        //Get all vertical lines
        for(int columnIndex = 0; columnIndex < totalColumns; columnIndex++) {
            StringBuilder columnString = new StringBuilder();
            for(int rowIndex = 0; rowIndex < totalRows; rowIndex++) {
                columnString.append(String.valueOf(characterGrid[rowIndex][columnIndex]));
            }
            verticalLines.add(columnString.toString());
        }

        //Get all diagonal lines from left and top
        //From left bottom corner to left top corner
        for(int rowIndex = totalRows-1; rowIndex >= 0; rowIndex--) {
            StringBuilder diagonalString = new StringBuilder();
            //Amount of characters on this diagonal
            int charactersOnVerticalRow = totalRows - rowIndex;
            int rowIndexVertical = rowIndex;
            //Loop over these characters
            for(int columnIndex = 0; columnIndex < charactersOnVerticalRow; columnIndex++) {
                diagonalString.append(String.valueOf(characterGrid[rowIndexVertical][columnIndex]));
                rowIndexVertical++;
            }
            diagonalLinesFromLeft.add(diagonalString.toString());
        }

        //from left top, to right top, diagonal to bottom right
        //columnindex starts on 1 since the first line is a duplicate
        for(int columnIndex = 1; columnIndex < totalColumns; columnIndex++) {
            StringBuilder diagonalString = new StringBuilder();
            int charactersOnVerticalRow = totalRows - columnIndex;
            int columnIndexVertical = columnIndex;
            //Loop over characters
            for (int rowIndex = 0; rowIndex < charactersOnVerticalRow; rowIndex++) {
                diagonalString.append(String.valueOf(characterGrid[rowIndex][columnIndexVertical]));
                columnIndexVertical++;
            }
            diagonalLinesFromLeft.add(diagonalString.toString());
        }

        //From Right bottom corner to right top corner
        for(int rowIndex = totalRows-1; rowIndex >= 0; rowIndex--) {
            StringBuilder diagonalString = new StringBuilder();
            //Amount of characters on this diagonal
            int charactersOnVerticalRow = totalRows - rowIndex;
            int rowIndexVertical = rowIndex;
            //Loop over the characters
            for(int columnIndex = totalColumns-1; columnIndex >= (totalColumns-charactersOnVerticalRow); columnIndex--) {
                diagonalString.append(characterGrid[rowIndexVertical][columnIndex]);
                rowIndexVertical++;
            }
            diagonalLinesFromRight.add(diagonalString.toString());
        }

        //from left top, to right top, diagonal to bottom left
        //columnindex ends on -1 since the last line is a duplicate
        for(int columnIndex = 0; columnIndex < totalColumns-1; columnIndex++) {
            String diagonalString = "";
            int charactersOnVerticalRow = columnIndex+1;
            int columnIndexVertical = columnIndex;
            //Loop over characters
            for (int rowIndex = 0; rowIndex < charactersOnVerticalRow; rowIndex++) {
                diagonalString += (String.valueOf(characterGrid[rowIndex][columnIndexVertical]));
                columnIndexVertical--;
            }
            diagonalLinesFromRight.add(diagonalString);
        }

        ArrayList<String> allStrings = new ArrayList<>();
        allStrings.addAll(horizontalLines);
        allStrings.addAll(verticalLines);
        allStrings.addAll(diagonalLinesFromRight);
        allStrings.addAll(diagonalLinesFromLeft);

        for(String s: allStrings){
            Pattern patternxmas = Pattern.compile("XMAS");
            Matcher matcherxmas = patternxmas.matcher(s);

            Pattern patternsamx = Pattern.compile("SAMX");
            Matcher matchersamx = patternsamx.matcher(s);

            int count = (int)matcherxmas.results().count()+(int)matchersamx.results().count();

            answer += count;
        }

        return answer;
    }

    public int part2(String input){
        int answer = 0;
        char[][] characterGrid = getTwoDimArrayFromString(input);

        int rows = characterGrid.length;
        int columns = characterGrid[0].length;

        for(int rowIndex = 1; rowIndex < rows-1; rowIndex++) {
            for(int columnIndex = 1; columnIndex < columns-1; columnIndex++) {
                if (characterGrid[rowIndex][columnIndex] == 'A'){
                    if(isCenterOfCrossMas(rowIndex, columnIndex, characterGrid)){
                        answer++;
                    }
                }
            }
        }

        return answer;
    }

    private static boolean isCenterOfCrossMas(int rowIndex, int columnIndex, char[][] grid){

        return isCenterOfLeftTopToRightBottomMas(rowIndex, columnIndex, grid) && isCenterOfLeftBottomToRightTopMas(rowIndex, columnIndex, grid);
    }

    private static boolean isCenterOfLeftTopToRightBottomMas(int rowIndex, int columnIndex, char[][] grid){
        if((grid[rowIndex-1][columnIndex-1] == 'M') && (grid[rowIndex+1][columnIndex+1] == 'S'))
            return true;

        return (grid[rowIndex - 1][columnIndex - 1] == 'S') && (grid[rowIndex + 1][columnIndex + 1] == 'M');
    }

    private static boolean isCenterOfLeftBottomToRightTopMas(int rowIndex, int columnIndex, char[][] grid){
        if((grid[rowIndex+1][columnIndex-1] == 'M') && (grid[rowIndex-1][columnIndex+1] == 'S'))
            return true;

        return (grid[rowIndex + 1][columnIndex - 1] == 'S') && (grid[rowIndex - 1][columnIndex + 1] == 'M');
    }

    private static char[][] getTwoDimArrayFromString(String input){
        ArrayList<String> lines = input.lines().collect(Collectors.toCollection(ArrayList::new));

        char[][] characterGrid = new char[lines.size()][lines.get(0).length()];

        for(int rowIndex = 0; rowIndex < lines.size(); rowIndex++){
            String line = lines.get(rowIndex);
            for(int columnIndex = 0; columnIndex < line.length(); columnIndex++){
                characterGrid[rowIndex][columnIndex] = line.charAt(columnIndex);
            }
        }

        return characterGrid;
    }
}
