package days;

import util.InputReader;

import java.util.ArrayList;
import java.util.List;

public class Day6 {

    class Guard {
        int rowIndex;
        int columnIndex;
        int direction;
        boolean looping = false;
        char[][] grid;
        Integer[][] visited;

        public Guard(int[] position, int direction, char[][] grid) {
            this.rowIndex = position[0];
            this.columnIndex = position[1];
            this.direction = direction;
            this.visited = new Integer[grid.length][grid[0].length];
            this.grid = newGrid(grid);
        }

        public Guard(int rowIndex, int colIndex, int direction, char[][] grid) {
            this.rowIndex = rowIndex;
            this.columnIndex = colIndex;
            this.direction = direction;
            this.visited = new Integer[grid.length][grid[0].length];
            this.grid = newGrid(grid);
        }

        public char[][] newGrid(char[][] grid) {
            char[][] newGrid = new char[grid.length][grid[0].length];
            for (int i = 0; i < grid.length; i++) {
                for (int j = 0; j < grid[0].length; j++) {
                    newGrid[i][j] = grid[i][j];
                }
            }
            return newGrid;
        }

        public void turn(){
            direction++;
            direction %= 4;
        }

        public boolean inBounds(char[][] grid){
            return rowIndex >= 0 && rowIndex <= grid.length - 1 && columnIndex >= 0 && columnIndex <= grid[0].length - 1;
        }

        public boolean inBounds(Integer[][] grid){
            return rowIndex >= 0 && rowIndex <= grid.length - 1 && columnIndex >= 0 && columnIndex <= grid[0].length - 1;
        }

        public void setPosition(int rowIndex, int columnIndex) {
            this.rowIndex = rowIndex;
            this.columnIndex = columnIndex;

            if(inBounds(visited)) {
                if (visited[rowIndex][columnIndex] != null && visited[rowIndex][columnIndex] == direction) {
                    looping = true;
                }
                visited[rowIndex][columnIndex] = direction;
            }
        }

        public boolean looping(){
            return looping;
        }

        public void setObstacle(int rIndex, int cIndex){
            if(rIndex >= 0 && rIndex <= this.grid.length - 1 && cIndex >= 0 && cIndex <= this.grid[0].length - 1){
                this.grid[rIndex][cIndex] = '#';
            }
        }
    }

    public int part2(String input) {
        char[][] characterGrid = InputReader.getTwoDimensionalCharArray(input);
        ArrayList<String> obstaclePositions = new ArrayList<>();
        Guard guard = new Guard(findPosition(characterGrid, '^'), 0, characterGrid);

        while (guard.inBounds(characterGrid)) {
            doMove(guard);

            if(!guard.inBounds(characterGrid)) break;

            String positionOrNull = checkForLoopIfObstacle(guard);
            if(positionOrNull != null){
                addStringToArrayWithoutDups(obstaclePositions, positionOrNull);
            }
        }

        List<String> i = obstaclePositions.stream().distinct().toList();

        return i.size();
    }

    private String checkForLoopIfObstacle(final Guard guard) {
        if(!guard.inBounds(guard.grid)) {
            return null;
        }

        boolean loop = false;
        //imagine there was an obstacle in front
        int[] obstaclePosition = getNextPosition(guard.rowIndex, guard.columnIndex, guard.direction);

        Guard tempGuard = new Guard(guard.rowIndex, guard.columnIndex, guard.direction, guard.grid);

        tempGuard.setObstacle(obstaclePosition[0], obstaclePosition[1]);
        tempGuard.turn(); //We turn this temporary guard in the current position as if there was an obstacle

        while (tempGuard.inBounds(tempGuard.grid)) {
            doMove(tempGuard);
            if(tempGuard.looping()){
                loop = true;
                break;
            }
        }

        if(loop){
            int[] currentPosition = new int[2];
            currentPosition[0] = guard.rowIndex;
            currentPosition[1] = guard.columnIndex;

            return obstaclePosition[0] + "," + obstaclePosition[1];
        }
        return null;
    }

    public static void addStringToArrayWithoutDups(ArrayList<String> list, String s) {
        if(!list.contains(s)){
            list.add(s);
        }
    }

    private static void doMove(Guard guard) {
        int[] currentPosition = new int[2];
        currentPosition[0] = guard.rowIndex;
        currentPosition[1] = guard.columnIndex;

        int[] newPosition = getNextPosition(currentPosition, guard.direction);

        if (checkPositionIsObstacle(guard.grid, newPosition)) {
            //no movement
            guard.turn();
            doMove(guard);
        } else {
            guard.setPosition(newPosition[0], newPosition[1]);
        }
    }

    public static boolean checkPositionIsObstacle(final char[][] characterGrid, final int[] position) {
        if(checkPositionInBounds(characterGrid, position)){
            return characterGrid[position[0]][position[1]] == '#';
        }
        return false;
    }

    public static boolean checkPositionInBounds(final char[][] characterGrid, final int[] position) {
        //rows
        return position[0] >= 0 && position[0] <= characterGrid.length - 1 && position[1] >= 0 && position[1] <= characterGrid[0].length - 1;
    }

    public static int[] getNextPosition(int rowIndex, int colIndex, int direction) {
        int[] position = new int[2];
        position[0] = rowIndex;
        position[1] = colIndex;
        return getNextPosition(position, direction);
    }

    public static int[] getNextPosition(int[] currentPosition, int direction) {
        int[] newPosition = new int[2];

        if (direction == 0) {
            newPosition[0] = currentPosition[0] - 1;
            newPosition[1] = currentPosition[1];
        } else if (direction == 1) {
            newPosition[0] = currentPosition[0];
            newPosition[1] = currentPosition[1] + 1;
        } else if (direction == 2) {
            newPosition[0] = currentPosition[0] + 1;
            newPosition[1] = currentPosition[1];
        } else if (direction == 3) {
            newPosition[0] = currentPosition[0];
            newPosition[1] = currentPosition[1] - 1;
        }

        return newPosition;
    }

    public static int[] findPosition(char[][] grid, char characterToFind) {
        int[] position = new int[2];

        for (int rowIndex = 0; rowIndex < grid.length; rowIndex++) {
            for (int columnIndex = 0; columnIndex < grid[0].length; columnIndex++) {
                if (grid[rowIndex][columnIndex] == characterToFind) {
                    position[0] = rowIndex;
                    position[1] = columnIndex;
                    return position;
                }
            }
        }

        return position;
    }
}
