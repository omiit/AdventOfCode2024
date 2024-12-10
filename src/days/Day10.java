package days;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class Day10 {
    private static final int TRAILHEAD = 0;
    private static final int PEAK = 9;

    public int part1(final String testInput){
        int[][] grid = getTwoDimArrayFromString(testInput);

        return getTrailHeadScoreForGrid(grid, false);
    }

    public int part2(final String testInput){
        int[][] grid = getTwoDimArrayFromString(testInput);

        return getTrailHeadScoreForGrid(grid, true);
    }

    private int getTrailHeadScoreForGrid(final int[][] grid, final boolean allowMultipleRoutesToSameTop) {
        int score = 0;

        for (int rowIndex = 0; rowIndex < grid.length; rowIndex++) {
            for(int colIndex = 0; colIndex < grid[0].length; colIndex++) {
                if(grid[rowIndex][colIndex] == TRAILHEAD){
                    int trailHeadScore = getScoreForTrailHead(grid, rowIndex, colIndex, allowMultipleRoutesToSameTop);
                    score += trailHeadScore;
                }
            }
        }

        return score;
    }

    private int getScoreForTrailHead(int[][] grid, final int rowIndex, final int colIndex, boolean allowMultipleRoutesToSameTop) {
        ArrayList<String> tops = getReachableTopsInAllDirections(grid, rowIndex, colIndex, 0);

        if(allowMultipleRoutesToSameTop){
            return tops.size();
        }
        else{
            return (int)tops.stream().distinct().count();
        }
    }

    private ArrayList<String> tryMoveInDirection(int[][] grid, final int rowIndex, final int colIndex, int heightToMatch) {
        if(inBounds(grid, rowIndex, colIndex)) {
            int height = grid[rowIndex][colIndex];

            if(height == heightToMatch) {
                if(height == PEAK) {
                    ArrayList<String> top = new ArrayList<>();
                    top.add(rowIndex + "," + colIndex);

                    return top;
                }
                else {

                    return getReachableTopsInAllDirections(grid, rowIndex, colIndex, height);
                }
            }
        }

        return new ArrayList<>();
    }

    private ArrayList<String> getReachableTopsInAllDirections(final int[][] grid, final int rowIndex, final int colIndex, final int height) {
        ArrayList<String> tops = new ArrayList<>();
        tops.addAll(tryMoveInDirection(grid, rowIndex-1, colIndex, height+1));
        tops.addAll(tryMoveInDirection(grid, rowIndex+1, colIndex, height+1));
        tops.addAll(tryMoveInDirection(grid, rowIndex, colIndex-1, height+1));
        tops.addAll(tryMoveInDirection(grid, rowIndex, colIndex+1, height+1));

        return tops;
    }

    private boolean inBounds(int[][] grid, int rowIndex, int colIndex) {

        return rowIndex < grid.length && colIndex < grid[0].length && rowIndex >= 0 && colIndex >= 0;
    }

    private static int[][] getTwoDimArrayFromString(String input) {
        ArrayList<String> lines = input.lines().collect(Collectors.toCollection(ArrayList::new));

        int[][] grid = new int[lines.size()][lines.get(0).length()];

        for (int rowIndex = 0; rowIndex < lines.size(); rowIndex++) {
            String line = lines.get(rowIndex);
            for (int columnIndex = 0; columnIndex < line.length(); columnIndex++) {
                grid[rowIndex][columnIndex] = Integer.parseInt(String.valueOf(line.charAt(columnIndex)));
            }
        }

        return grid;
    }
}
