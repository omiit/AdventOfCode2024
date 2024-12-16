package days;

import java.util.ArrayList;
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
        return map.getCostsAtEnd();
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

        public long getCostsAtEnd() {
            Path end = (Path) getItem(endRowIndex, endColumnIndex);
            return end.cost;
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
