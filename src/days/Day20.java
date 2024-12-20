package days;

import util.InputReader;

import java.util.ArrayList;
import java.util.HashMap;

import static java.lang.Math.abs;

public class Day20 {

    public int part1(String input, int minShortcutSave) {
        Racetrack racetrack = new Racetrack(InputReader.getTwoDimensionalCharArray(input));
        racetrack.exploreMap();

        HashMap<Integer, Integer> secondsSavedAndCount = racetrack.getShortCuts(minShortcutSave, 2);
        int shortcuts = 0;
        for (int value : secondsSavedAndCount.values()) {
            shortcuts += value;
        }
        return shortcuts;
    }

    public int part2(String input, int minShortcutSave, int cheatPicoseconds) {
        Racetrack racetrack = new Racetrack(InputReader.getTwoDimensionalCharArray(input));
        racetrack.exploreMap();

        HashMap<Integer, Integer> secondsSavedAndCount = racetrack.getShortCuts(minShortcutSave, cheatPicoseconds);
        int shortcuts = 0;
        for (int value : secondsSavedAndCount.values()) {
            shortcuts += value;
        }
        return shortcuts;
    }

    public int getEndLocationCost(String input) {
        Racetrack racetrack = new Racetrack(InputReader.getTwoDimensionalCharArray(input));
        racetrack.exploreMap();
        return racetrack.getEndLocationCost();
    }

    private class Racetrack {
        Location[][] locationGrid;
        Location startLocation;
        Location endLocation;

        public Racetrack(char[][] charGrid) {
            locationGrid = new Location[charGrid.length][charGrid[0].length];

            for (int i = 0; i < charGrid.length; i++) {
                for (int j = 0; j < charGrid[0].length; j++) {
                    if (charGrid[i][j] == '.') {
                        locationGrid[i][j] = new Location(i, j, false);
                    } else if (charGrid[i][j] == '#') {
                        locationGrid[i][j] = new Location(i, j, true);
                    } else if (charGrid[i][j] == 'S') {
                        this.startLocation = new Location(i, j, false);
                        locationGrid[i][j] = startLocation;
                        startLocation.setCost(0);
                    } else if (charGrid[i][j] == 'E') {
                        this.endLocation = new Location(i, j, false);
                        locationGrid[i][j] = endLocation;

                    }
                }
            }
        }

        public void exploreMap() {
            ArrayList<Location> pathsToExplore = new ArrayList<>();
            pathsToExplore.add(startLocation);

            while (!pathsToExplore.isEmpty()) {
                Location nextLocation = pathsToExplore.removeFirst();
                pathsToExplore.addAll(getReachableLocations(nextLocation));
            }
        }

        public HashMap<Integer, Integer> getShortCuts(int minShortcutSave, int cheatPicoseconds) {
            HashMap<Integer, Integer> secondsSavedAndCount = new HashMap<>();

            for (int i = 0; i < locationGrid.length; i++) {
                for (int j = 0; j < locationGrid[0].length; j++) {
                    Location shortcutStart = getLocation(i, j);
                    if (!shortcutStart.isWall() && shortcutStart.cost != Integer.MAX_VALUE) {
                        //This is a valid path location from where we can start a cheat

                        for (int i2 = 0; i2 < locationGrid.length; i2++) {
                            for (int j2 = 0; j2 < locationGrid[0].length; j2++) {
                                if (abs(i - i2) + abs(j - j2) <= cheatPicoseconds) {
                                    //This is a reachable location in the seconds we have
                                    Location shortcutEnd = getLocation(i2, j2);
                                    int secondsSaved = tryCheat(shortcutStart, shortcutEnd, abs(i - i2) + abs(j - j2), minShortcutSave);
                                    if (secondsSaved != 0) {
                                        addShortcut(secondsSavedAndCount, secondsSaved);
                                    }
                                }

                            }
                        }

                    }
                }
            }
            return secondsSavedAndCount;
        }

        public int tryCheat(Location startLocation, Location endLocation, int moves, int minShortcutSave) {
            if (endLocation.isWall() || endLocation.getCost() == Integer.MAX_VALUE) {
                return 0;
            }
            return endLocation.tryShortcutSave(startLocation.getCost() + moves, minShortcutSave);
        }

        public void addShortcut(HashMap<Integer, Integer> secondsSavedAndCount, int secondsSaved) {
            if (secondsSavedAndCount.containsKey(secondsSaved)) {
                int count = secondsSavedAndCount.get(secondsSaved);
                secondsSavedAndCount.put(secondsSaved, count + 1);
            } else {
                secondsSavedAndCount.put(secondsSaved, 1);
            }
        }

        public ArrayList<Location> getReachableLocations(Location location) {
            ArrayList<Location> reachableLocations = new ArrayList<>();

            if (tryLocation(location.rowIndex - 1, location.colIndex, location.getCost())) {
                reachableLocations.add(getLocation(location.rowIndex - 1, location.colIndex));
            }
            if (tryLocation(location.rowIndex, location.colIndex - 1, location.getCost())) {
                reachableLocations.add(getLocation(location.rowIndex, location.colIndex - 1));
            }
            if (tryLocation(location.rowIndex + 1, location.colIndex, location.getCost())) {
                reachableLocations.add(getLocation(location.rowIndex + 1, location.colIndex));
            }
            if (tryLocation(location.rowIndex, location.colIndex + 1, location.getCost())) {
                reachableLocations.add(getLocation(location.rowIndex, location.colIndex + 1));
            }

            return reachableLocations;
        }

        public boolean tryLocation(int rowIndex, int colIndex, int cost) {
            Location locationToCheck = locationGrid[rowIndex][colIndex];
            if (locationToCheck.isWall()) {
                return false;
            }
            return locationToCheck.setCost(cost + 1);
        }

        public Location getLocation(int rowIndex, int colIndex) {
            return locationGrid[rowIndex][colIndex];
        }

        public int getEndLocationCost() {
            return endLocation.getCost();
        }
    }

    private class Location {
        int rowIndex;
        int colIndex;
        int cost;
        boolean wall;

        public Location(int rowIndex, int colIndex, boolean wall) {
            this.rowIndex = rowIndex;
            this.colIndex = colIndex;
            this.cost = Integer.MAX_VALUE;
            this.wall = wall;
        }

        public boolean setCost(int cost) {
            if (this.cost > cost) {
                this.cost = cost;
                return true;
            }
            return false;
        }

        public int tryShortcutSave(int cost, int minimalSave) {
            if (this.cost == Integer.MAX_VALUE) {
                return 0;
            }
            if (minimalSave <= (this.cost - cost)) {
                return this.cost - cost;
            }
            return 0;
        }

        public int getCost() {
            return this.cost;
        }

        public boolean isWall() {
            return wall;
        }

    }

}
