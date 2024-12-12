package util;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class InputReader {

    private InputReader(){

    }

    public static char[][] getTwoDimensionalCharArray(String input) {
        ArrayList<String> lines = input.lines().collect(Collectors.toCollection(ArrayList::new));

        char[][] characterGrid = new char[lines.size()][lines.get(0).length()];

        for (int rowIndex = 0; rowIndex < lines.size(); rowIndex++) {
            String line = lines.get(rowIndex);
            for (int columnIndex = 0; columnIndex < line.length(); columnIndex++) {
                characterGrid[rowIndex][columnIndex] = line.charAt(columnIndex);
            }
        }

        return characterGrid;
    }

}
