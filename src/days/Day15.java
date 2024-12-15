package days;

import util.InputReader;

import java.util.Arrays;

public class Day15 {

    public long part1(String input) {
        WarehouseNormal warehouse = getWarehouse(input);

        warehouse.doAllRobotMoves();

        return warehouse.getSumOfGoodPositioningSystem();
    }

    public long part2(String input) {
        WarehouseWide warehouse = getWideWareHouse(input);

        warehouse.doAllRobotMoves();

        return warehouse.getSumOfGoodPositioningSystem();
    }

    public abstract class Warehouse {
        char[][] warehouseMap;
        char[] robotMoves;
        int robotX;
        int robotY;

        public Warehouse(char[][] warehouseMap, char[] robotMoves) {
            this.warehouseMap = warehouseMap;
            this.robotMoves = robotMoves;
            setInitialRobotPosition();
        }

        public void doAllRobotMoves() {
            //printMap();
            for (char move : robotMoves) {
                //System.out.println("Do move: " + move);
                doRobotMove(move);
                //printMap();
            }
        }

        public long getSumOfGoodPositioningSystem() {
            long sum = 0;
            for (int y = 0; y < warehouseMap.length; y++) {
                for (int x = 0; x < warehouseMap[y].length; x++) {
                    if (isScorePosition(x, y)) {
                        sum += getPositionScore(x, y);
                    }
                }
            }
            return sum;
        }

        private void doRobotMove(char move) {
            if (move == '^') {
                tryRobotMove(this.robotX, this.robotY, 0, -1);
            } else if (move == 'v') {
                tryRobotMove(this.robotX, this.robotY, 0, +1);
            } else if (move == '<') {
                tryRobotMove(this.robotX, this.robotY, -1, 0);
            } else if (move == '>') {
                tryRobotMove(this.robotX, this.robotY, +1, 0);
            }
        }

        private void tryRobotMove(int robotX, int robotY, int xDirection, int yDirection) {
            boolean canMove;

            int desiredX = robotX + xDirection;
            int desiredY = robotY + yDirection;

            if (isBox(desiredX, desiredY)) {
                canMove = tryMoveBox(desiredX, desiredY, xDirection, yDirection);
            } else {
                canMove = !isWal(desiredX, desiredY);
            }

            if (canMove) {
                moveRobot(robotX, robotY, desiredX, desiredY);
            }
        }

        protected abstract boolean tryMoveBox(int boxX, int boxY, int xDirection, int yDirection);

        protected abstract boolean isBox(int x, int y);

        protected abstract boolean isScorePosition(int x, int y);

        protected void moveRobot(int robotX, int robotY, int newX, int newY) {
            warehouseMap[newY][newX] = '@';
            warehouseMap[robotY][robotX] = '.';
            this.robotX = newX;
            this.robotY = newY;
        }

        protected boolean isWal(int x, int y) {
            return warehouseMap[y][x] == '#';
        }


        private void setInitialRobotPosition() {
            for (int y = 0; y < warehouseMap.length; y++) {
                for (int x = 0; x < warehouseMap[y].length; x++) {
                    if (isRobot(x, y)) {
                        this.robotX = x;
                        this.robotY = y;
                        return;
                    }
                }
            }
        }

        private long getPositionScore(int x, int y) {
            return y * 100L + x;
        }

        private boolean isRobot(int x, int y) {
            return warehouseMap[y][x] == '@';
        }

        private void printMap() {
            for (int i = 0; i < warehouseMap.length; i++) {
                System.out.println(Arrays.toString(warehouseMap[i]));
            }
        }
    }

    public class WarehouseNormal extends Warehouse {

        public WarehouseNormal(char[][] warehouseMap, char[] robotMoves) {
            super(warehouseMap, robotMoves);
        }

        @Override
        protected boolean tryMoveBox(int boxX, int boxY, int xDirection, int yDirection) {
            boolean canMove;

            int desiredX = boxX + xDirection;
            int desiredY = boxY + yDirection;

            if (isBox(desiredX, desiredY)) {
                canMove = tryMoveBox(desiredX, desiredY, xDirection, yDirection);
            } else {
                canMove = !isWal(desiredX, desiredY);
            }

            if (canMove) {
                moveBox(boxX, boxY, desiredX, desiredY);
            }

            return canMove;
        }

        private void moveBox(int boxX, int boxY, int newX, int newY) {
            warehouseMap[newY][newX] = 'O';
            warehouseMap[boxY][boxX] = '.';
        }

        @Override
        protected boolean isBox(int x, int y) {
            return warehouseMap[y][x] == 'O';
        }

        @Override
        protected boolean isScorePosition(int x, int y) {
            return isBox(x, y);
        }
    }

    public class WarehouseWide extends Warehouse {

        public WarehouseWide(char[][] warehouseMap, char[] robotMoves) {
            super(warehouseMap, robotMoves);
        }

        @Override
        protected boolean tryMoveBox(int boxX, int boxY, int xDirection, int yDirection) {
           if(xDirection != 0){
               return tryMoveBoxHorizontal(boxX, boxY, xDirection);
           } else {
               boolean  canMove = tryMoveBoxVertical(boxX, boxY, yDirection, false);
               if(canMove) {
                   return tryMoveBoxVertical(boxX, boxY, yDirection, true);
               }
               else {
                   return false;
               }
           }
        }

        private boolean tryMoveBoxHorizontal(int boxX, int boxY, int xDirection){
            boolean canMove;

            int desiredX = boxX + 2 * xDirection;
            int desiredY = boxY;

            if (isBox(desiredX, desiredY)) {
                canMove = tryMoveBoxHorizontal(desiredX, desiredY, xDirection);
            } else {
                canMove = !isWal(desiredX, desiredY);
            }

            if (canMove) {
                moveBoxHorizontal(boxX, boxY, xDirection);
            }

            return canMove;
        }

        private boolean tryMoveBoxVertical(int boxX, int boxY, int yDirection, boolean doActualMove){

            boolean isLeftPart = isLeftBoxPart(boxX, boxY);
            int desiredXleft;
            int desiredXright;

            if(isLeftPart){
                desiredXleft = boxX;
                desiredXright = boxX + 1;
            } else {
                desiredXleft = boxX - 1;
                desiredXright = boxX;
            }
            int desiredY = boxY + yDirection;

            boolean leftCanMove = false;
            if (isBox(desiredXleft, desiredY)) {
                leftCanMove = tryMoveBoxVertical(desiredXleft, desiredY, yDirection, doActualMove);
            } else {
                leftCanMove = !isWal(desiredXleft, desiredY);
            }

            boolean rightCanMove = false;
            if (isBox(desiredXright, desiredY)) {
                rightCanMove = tryMoveBoxVertical(desiredXright, desiredY, yDirection, doActualMove);
            } else {
                rightCanMove = !isWal(desiredXright, desiredY);
            }

            if (leftCanMove & rightCanMove) {
                if(doActualMove){
                    moveBoxVertical(desiredXleft, desiredXright, boxY, yDirection);
                }
                return true;
            }
            else {
                return false;
            }
        }

        private void moveBoxVertical(int boxXLeft, int boxXRight, int boxY, int yDirection) {
            warehouseMap[boxY][boxXLeft] = '.';
            warehouseMap[boxY][boxXRight] = '.';

            warehouseMap[boxY + yDirection][boxXLeft] = '[';
            warehouseMap[boxY + yDirection][boxXRight] = ']';
        }

        private void moveBoxHorizontal(int boxX, int boxY, int xDirection) {
            if(xDirection < 0){
                warehouseMap[boxY][boxX] = '.';
                warehouseMap[boxY][boxX+xDirection] = ']';
                warehouseMap[boxY][boxX+xDirection*2] = '[';
            } else {
                warehouseMap[boxY][boxX] = '.';
                warehouseMap[boxY][boxX+xDirection] = '[';
                warehouseMap[boxY][boxX+xDirection*2] = ']';
            }
        }

        @Override
        protected boolean isBox(int x, int y) {
            return warehouseMap[y][x] == '[' || warehouseMap[y][x] == ']';
        }

        @Override
        protected boolean isScorePosition(int x, int y) {
            return isLeftBoxPart(x, y);
        }

        private boolean isLeftBoxPart(int x, int y) {
            return warehouseMap[y][x] == '[';
        }
    }

    public WarehouseNormal getWarehouse(String input) {
        String[] splits = InputReader.splitOnEmptyLines(input);

        char[][] map = InputReader.getTwoDimensionalCharArray(splits[0]);
        char[] moves = getMoves(splits[1]);

        return new WarehouseNormal(map, moves);
    }

    private WarehouseWide getWideWareHouse(String input) {
        String[] splits = InputReader.splitOnEmptyLines(input);

        String map = splits[0]
                .replace("#", "##")
                .replace("O", "[]")
                .replace(".", "..")
                .replace("@", "@.");

        char[][] wideMap = InputReader.getTwoDimensionalCharArray(map);
        char[] moves = getMoves(splits[1]);

        return new WarehouseWide(wideMap, moves);
    }

    private char[] getMoves(String split){
        String movesString = split.replace("\r", "").replace("\n", "");

        return movesString.toCharArray();
    }
}
