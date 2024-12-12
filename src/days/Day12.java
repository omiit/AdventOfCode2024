package days;

import util.InputReader;

import java.util.ArrayList;
import java.util.HashMap;

import static java.lang.Math.abs;

public class Day12 {

    enum Direction {
        TOP,
        BOTTOM,
        LEFT,
        RIGHT
    }

    private class Plot {
        int rowIndex;
        int colIndex;
        char plantType;
        private ArrayList<Direction> fences;
        ArrayList<int[]> neighbourLocations;

        public Plot(int rowIndex, int colIndex, final PlantMap plantMap) {
            this.rowIndex = rowIndex;
            this.colIndex = colIndex;
            this.plantType = plantMap.getPlant(rowIndex, colIndex);
            this.fences = new ArrayList<>();
            neighbourLocations = new ArrayList<>();
            setFencesAndNeighbours(plantMap);
        }

        public boolean hasFence(Direction direction) {
            return fences.contains(direction);
        }

        private void setFencesAndNeighbours(final PlantMap plantMap) {
            setFenceOrNeighbourForDirection(plantMap, Direction.LEFT, this.rowIndex, this.colIndex - 1);
            setFenceOrNeighbourForDirection(plantMap, Direction.RIGHT, this.rowIndex, this.colIndex + 1);
            setFenceOrNeighbourForDirection(plantMap, Direction.TOP, this.rowIndex - 1, this.colIndex);
            setFenceOrNeighbourForDirection(plantMap, Direction.BOTTOM, this.rowIndex + 1, this.colIndex);
        }

        private void setFenceOrNeighbourForDirection(final PlantMap plantMap, Direction direction, int rowIndexForDirection, int colIndexForDirection) {
            if (!plantMap.inBounds(rowIndexForDirection, colIndexForDirection)) {
                //Edge of map, set fence
                fences.add(direction);
            } else if (plantMap.getPlant(rowIndexForDirection, colIndexForDirection) == plantType) {
                //Same plant, add neighbour
                neighbourLocations.add(new int[]{rowIndexForDirection, colIndexForDirection});
            } else {
                //Must be another plant, add fence
                fences.add(direction);
            }
        }
    }

    private class PlantMap {
        private final char[][] grid;
        private final int rows;
        private final int cols;

        public PlantMap(char[][] grid) {
            this.grid = grid;
            this.rows = grid.length;
            this.cols = grid[0].length;
        }

        public char getPlant(int rowIndex, int colIndex) {
            return grid[rowIndex][colIndex];
        }

        public boolean inBounds(int rowIndex, int colIndex) {
            return isRowInBounds(rowIndex) && isColInBounds(colIndex);
        }

        public int getRows() {
            return rows;
        }

        public int getCols() {
            return cols;
        }

        private boolean isRowInBounds(int rowIndex) {
            return 0 <= rowIndex && rowIndex < rows;
        }

        private boolean isColInBounds(int colIndex) {
            return 0 <= colIndex && colIndex < cols;
        }
    }

    private class Region {
        ArrayList<Plot> plots;
        Character plantType;

        public Region(Character plantType) {
            this.plantType = plantType;
            this.plots = new ArrayList<>();
        }

        public void addPlot(Plot plot) {
            this.plots.add(plot);
        }

        public int getFencePrice() {
            int fences = 0;
            for (Plot plot : plots) {
                fences += plot.fences.size();
            }

            return fences * plots.size();
        }

        public int getFencePriceWithDiscount(PlantMap plantMap) {

            return calculateSides(plantMap) * plots.size();
        }

        public int calculateSides(PlantMap plantMap) {
            ArrayList<Plot> plotsWithLeftFence = new ArrayList<>(plots.stream().filter(plot -> plot.hasFence(Direction.LEFT)).toList());
            ArrayList<Plot> plotsWithRightFence = new ArrayList<>(plots.stream().filter(plot -> plot.hasFence(Direction.RIGHT)).toList());
            ArrayList<Plot> plotsWithTopFence = new ArrayList<>(plots.stream().filter(plot -> plot.hasFence(Direction.TOP)).toList());
            ArrayList<Plot> plotsWithBottomFence = new ArrayList<>(plots.stream().filter(plot -> plot.hasFence(Direction.BOTTOM)).toList());

            ArrayList<ArrayList<Plot>> leftSides = getSides(Direction.LEFT, plotsWithLeftFence, plantMap);
            ArrayList<ArrayList<Plot>> rightSides = getSides(Direction.RIGHT, plotsWithRightFence, plantMap);
            ArrayList<ArrayList<Plot>> topSides = getSides(Direction.TOP, plotsWithTopFence, plantMap);
            ArrayList<ArrayList<Plot>> downSides = getSides(Direction.BOTTOM, plotsWithBottomFence, plantMap);

            return leftSides.size() + rightSides.size() + topSides.size() + downSides.size();
        }

        private ArrayList<ArrayList<Plot>> getSides(final Direction direction, final ArrayList<Plot> plotsWithSameDirectionFence, PlantMap plantMap) {
            ArrayList<ArrayList<Plot>> sides = new ArrayList<>();

            while (!plotsWithSameDirectionFence.isEmpty()) {
                ArrayList<Plot> side = new ArrayList<>();
                side.add(plotsWithSameDirectionFence.remove(0));
                boolean newPlotsInSide = true;

                while (newPlotsInSide) {
                    newPlotsInSide = false;

                    ArrayList<Plot> plotsToLoop = new ArrayList<>(plotsWithSameDirectionFence);

                    for (Plot plotsWithSameFence : plotsToLoop) {

                        ArrayList<Plot> sidesToLoop = new ArrayList<>(side);

                        for (Plot sidePlots : sidesToLoop) {
                            if (direction == Direction.LEFT || direction == Direction.RIGHT) {
                                if ((plotsWithSameFence.colIndex == sidePlots.colIndex) && (abs(plotsWithSameFence.rowIndex - sidePlots.rowIndex) == 1)) {
                                        side.add(new Plot(plotsWithSameFence.rowIndex, plotsWithSameFence.colIndex, plantMap));
                                        plotsWithSameDirectionFence.remove(plotsWithSameFence);
                                        newPlotsInSide = true;
                                }
                            } else {
                                if ((plotsWithSameFence.rowIndex == sidePlots.rowIndex) && (abs(plotsWithSameFence.colIndex - sidePlots.colIndex) == 1)) {
                                        side.add(plotsWithSameFence);
                                        plotsWithSameDirectionFence.remove(plotsWithSameFence);
                                        newPlotsInSide = true;
                                }
                            }
                        }
                    }
                }
                sides.add(side);
            }

            return sides;
        }
    }

    public int part1(String input) {
        PlantMap plantMap = getMap(input);
        HashMap<Character, ArrayList<Region>> regionsPerPlant = getRegions(plantMap);
        int price = 0;

        for (ArrayList<Region> regions : regionsPerPlant.values()) {
            for (Region region : regions) {
                price += region.getFencePrice();
            }
        }

        return price;
    }

    public int part2(String input) {
        PlantMap plantMap = getMap(input);
        HashMap<Character, ArrayList<Region>> regionsPerPlant = getRegions(plantMap);
        int price = 0;

        for (ArrayList<Region> regions : regionsPerPlant.values()) {
            for (Region region : regions) {
                price += region.getFencePriceWithDiscount(plantMap);
            }
        }

        return price;
    }

    private HashMap<Character, ArrayList<Region>> getRegions(final PlantMap plantMap) {

        HashMap<Character, ArrayList<Region>> regionsPerPlant = new HashMap<>();
        int[][] visitedLocations = new int[plantMap.getRows()][plantMap.getCols()];

        for (int i = 0; i < plantMap.getRows(); i++) {
            for (int j = 0; j < plantMap.getCols(); j++) {
                if (visitedLocations[i][j] == 1) {
                    //This location is already in the regionsPerPlant map
                    continue;
                }

                visitedLocations[i][j] = 1;

                Plot plot = new Plot(i, j, plantMap);
                Region region = new Region(plot.plantType);

                ArrayList<Plot> plotsToLoop = new ArrayList<>();
                plotsToLoop.add(plot);

                while (!plotsToLoop.isEmpty()) {
                    Plot currentPlot = plotsToLoop.remove(0);
                    region.addPlot(currentPlot);
                    ArrayList<int[]> neighbourLocations = currentPlot.neighbourLocations;
                    for (int[] neighbourLocation : neighbourLocations) {
                        int neighbourRowIndex = neighbourLocation[0];
                        int neighbourColIndex = neighbourLocation[1];
                        if (visitedLocations[neighbourRowIndex][neighbourColIndex] != 1) {
                            //This plot is not already in a region
                            Plot neighbourPlot = new Plot(neighbourRowIndex, neighbourColIndex, plantMap);
                            plotsToLoop.add(neighbourPlot);
                            visitedLocations[neighbourRowIndex][neighbourColIndex] = 1;
                        }
                    }
                }
                addRegionToRegions(regionsPerPlant, region);
            }
        }
        return regionsPerPlant;
    }

    private void addRegionToRegions(HashMap<Character, ArrayList<Region>> regionsPerPlant, Region region) {
        if (regionsPerPlant.containsKey(region.plantType)) {
            regionsPerPlant.get(region.plantType).add(region);
        } else {
            ArrayList<Region> regions = new ArrayList<>();
            regions.add(region);
            regionsPerPlant.put(region.plantType, regions);
        }
    }

    private PlantMap getMap(String input) {
        char[][] grid = InputReader.getTwoDimensionalCharArray(input);
        return new PlantMap(grid);
    }
}
