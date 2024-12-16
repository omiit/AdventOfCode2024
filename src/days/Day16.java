package days;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

public class Day16 {

    public long part1(String input){
        Map map = new Map(getGridFromInput(input));
        map.calculateCosts();
        return map.getCostsAtEnd();
    }

    public Long part2(final String input) {
        Map map = new Map(getGridFromInput(input));
        map.calculateCosts();

        char[][] shortestPathsMap = new char[map.maze.length][map.maze[0].length];
        getShortestPathsRecursive(map, shortestPathsMap, map.startRowIndex, map.startColumnIndex, 0L, map.startDirection);

        long sum = 0L;
        for(int i = 0; i< shortestPathsMap.length; i++){
            for(int j = 0; j < shortestPathsMap[0].length; j++){
                if(shortestPathsMap[i][j]== '0'){
                    sum++;
                }
            }
        }
        printMap(shortestPathsMap);
        return sum;
    }

    private boolean getShortestPathsRecursive(Map map, char[][] shortestPathsMap, int rowIndex, int colIndex, Long cost, Direction currentDirection){
        if(cost > map.getCostsAtEnd()){
            return false;
        }
        if(!map.isPath(rowIndex, colIndex)){
            return false;
        }
        if(map.isEnd(rowIndex, colIndex)){
            if(map.getEndItem().cost.equals(cost)){
                shortestPathsMap[rowIndex][colIndex] = '0';
                return true;
            } else {
                return false;
            }
        }
        Path path = (Path)map.getItem(rowIndex, colIndex);
        if(currentDirection.equals(path.costDirection)){
            if(path.cost < cost){
                return false;
            }
        }

        boolean oneOfDirectionsIsShortest = false;

        for(Direction direction : Direction.values()){
            int directionsRowIndex = rowIndex;
            int directionsColIndex = colIndex;
            if(direction == Direction.EAST){
                directionsColIndex++;
            } else if(direction == Direction.SOUTH){
                directionsRowIndex++;
            } else if (direction == Direction.NORTH){
                directionsRowIndex--;
            } else {
                directionsColIndex--;
            }
            if(direction == currentDirection){
                if(getShortestPathsRecursive(map, shortestPathsMap, directionsRowIndex, directionsColIndex, cost+1, direction)){
                    oneOfDirectionsIsShortest = true;
                }
            } else if(direction != Direction.getInverse(currentDirection)) {
                if(getShortestPathsRecursive(map, shortestPathsMap, directionsRowIndex, directionsColIndex, cost+1001, direction)){
                    oneOfDirectionsIsShortest = true;
                }
            }
        }

        if(oneOfDirectionsIsShortest){
            shortestPathsMap[rowIndex][colIndex] = '0';
            return true;
        } else {
            return false;
        }
    }

    private void printMap(char[][] map) {
        for (int i = 0; i < map.length; i++) {
            System.out.println(Arrays.toString(map[i]));
        }
    }

    private class Map {
        GridItem[][] maze;
        private final int startRowIndex;
        private final int startColumnIndex;
        private final Direction startDirection;
        private final int endRowIndex;
        private final int endColumnIndex;

        public Map(GridItem[][] maze) {
            this.maze = maze;
            this.startRowIndex = maze.length-2;
            this.startColumnIndex = 1;
            this.endRowIndex = 1;
            this.endColumnIndex = maze[0].length-2;
            this.startDirection = Direction.EAST;
        }

        public void calculateCosts(){
            ArrayList<Path> pathsToExplore = new ArrayList<>();
            Path start = getStartItem();
            pathsToExplore.add(start);

            while(!pathsToExplore.isEmpty()){
                Path toExplore = pathsToExplore.remove(0);
                ArrayList<Path> newPaths = tryMoves(toExplore);
                pathsToExplore.addAll(newPaths);
            }
        }

        public ArrayList<Path> tryMoves(Path toExplore){
            ArrayList<Path> paths = new ArrayList<>();

            Direction currentDirection = toExplore.costDirection;

            for(Direction direction : Direction.values()){
                if(direction == currentDirection){
                    Path gridItem = tryMove(toExplore.rowIndex, toExplore.colIndex, direction, toExplore.cost+1);
                    if(gridItem != null){
                        paths.add(gridItem);
                    }
                } else if(direction != Direction.getInverse(currentDirection)) {
                    Path gridItem = tryMove(toExplore.rowIndex, toExplore.colIndex, direction, toExplore.cost+1001);
                    if(gridItem != null){
                        paths.add(gridItem);
                    }
                }
            }

            return paths;
        }

        public Path tryMove(int rowIndex, int colIndex, Direction direction, Long cost){
            GridItem griditem;
            if(direction == Direction.EAST){
              griditem = maze[rowIndex][colIndex+1];
            } else if(direction == Direction.SOUTH){
                griditem = maze[rowIndex+1][colIndex];
            } else if (direction == Direction.NORTH){
                griditem = maze[rowIndex-1][colIndex];
            } else {
                griditem = maze[rowIndex][colIndex-1];
            }

            if(griditem.getClass() == Path.class){
                Path path = (Path) griditem;
                boolean isMinimal = path.setCost(cost, direction);
                if(isMinimal){
                    return path;
                }
            }
            return null;
        }

        public GridItem getItem(int rowIndex, int columnIndex) {
            return maze[rowIndex][columnIndex];
        }

        public boolean isWall(int rowIndex, int columnIndex) {
            return maze[rowIndex][columnIndex].getClass() == Wall.class;
        }

        public boolean isPath(int rowIndex, int columnIndex) {
            return maze[rowIndex][columnIndex].getClass() == Path.class;
        }

        public Path getStartItem(){
            Path startItem = (Path)getItem(startRowIndex, startColumnIndex);
            startItem.setCost(0L, startDirection);
            return startItem;
        }

        public Path getEndItem(){
            Path endItem = (Path)getItem(endRowIndex, endColumnIndex);
            return endItem;
        }

        public long getCostsAtEnd() {
            Path end = (Path) getItem(endRowIndex, endColumnIndex);
            return end.cost;
        }

        public Path getItemInDirection(int rowIndex, int colIndex, Direction direction) {
            if(direction == Direction.NORTH){
                return (Path)getItem(rowIndex-1, colIndex);
            }
            if(direction == Direction.EAST){
                return (Path)getItem(rowIndex, colIndex+1);
            }
            if (direction == Direction.SOUTH){
                return (Path)getItem(rowIndex+1, colIndex);
            }
            return (Path)getItem(rowIndex, colIndex-1);
        }

        public boolean isEnd(int rowIndex, int colIndex) {
            return rowIndex == endRowIndex && colIndex == endColumnIndex;
        }
    }

    private enum Direction {
        NORTH,
        EAST,
        SOUTH,
        WEST;

        public static Direction getInverse(Direction direction){
            if(direction == Direction.NORTH){
                return Direction.SOUTH;
            }
            if(direction == Direction.EAST){
                return Direction.WEST;
            }
            if (direction == Direction.SOUTH){
                return Direction.NORTH;
            }
            return EAST;
        }
    }

    private abstract class GridItem {
        int rowIndex;
        int colIndex;

        GridItem(int rowIndex, int colIndex) {
            this.rowIndex = rowIndex;
            this.colIndex = colIndex;
        }

    }

    private class Wall extends GridItem {

        Wall(final int rowIndex, final int colIndex) {
            super(rowIndex, colIndex);
        }

        @Override
        public String toString() {
            return "#";
        }
    }

    private class Path extends GridItem {

        Long cost = Long.MAX_VALUE;
        Direction costDirection = null;

        Path(final int rowIndex, final int colIndex) {
            super(rowIndex, colIndex);
        }

        public boolean setCost(Long cost, Direction direction) {
            if(this.cost > cost){
                this.cost = cost;
                this.costDirection = direction;
                return true;
            }
            return false;
        }

        public boolean setCostWithOrigin(Long cost, Direction direction, Path origin) {
            if(this.cost > cost+1000){
                this.cost = cost;
                this.costDirection = direction;
                return true;
            }
            return false;
        }

        @Override
        public String toString() {
            return ".";
        }
    }

    public GridItem[][] getGridFromInput(String input) {
        ArrayList<String> lines = input.lines().collect(Collectors.toCollection(ArrayList::new));

        GridItem[][] grid = new GridItem[lines.size()][lines.get(0).length()];

        for (int rowIndex = 0; rowIndex < lines.size(); rowIndex++) {
            String line = lines.get(rowIndex);
            for (int columnIndex = 0; columnIndex < line.length(); columnIndex++) {
                if(line.charAt(columnIndex) == '#') {
                    grid[rowIndex][columnIndex] = new Wall(rowIndex, columnIndex);
                }
                else {
                    grid[rowIndex][columnIndex] = new Path(rowIndex, columnIndex);
                }
            }
        }

        return grid;
    }

}
