package days;

import java.util.ArrayList;

public class Day18 {

    public long part1(String input, int xWidth, int yHeight, int bytesFallen) {

        ArrayList<ByteLocation> byteLocations = getByteLocations(input);
        MemoryMap memoryMap = new MemoryMap(xWidth, yHeight);

        for(int i = 0; i < bytesFallen; i++) {
            memoryMap.addByte(byteLocations.get(i));
        }

        memoryMap.exploreMap(0, 0);

        memoryMap.printMap();

        return memoryMap.getEndLocationCost();
    }

    public String part2(String input, int xWidth, int yHeight) {

        ArrayList<ByteLocation> byteLocations = getByteLocations(input);
        int maxBytes = 0;

        for(int i = 1; i < byteLocations.size(); i++) {

            MemoryMap memoryMap = new MemoryMap(xWidth, yHeight);
            for(int j = 0; j <= i; j++) {
                memoryMap.addByte(byteLocations.get(j));
            }
            memoryMap.exploreMap(0, 0);

            System.out.println("After " + i + " bytes, the quickest route is " + memoryMap.getEndLocationCost() + " steps");

            if(memoryMap.getEndLocationCost() == Integer.MAX_VALUE) {
                maxBytes = i;
                memoryMap.printMap();
                break;
            }
        }

        ByteLocation blockingByte = byteLocations.get(maxBytes);
        return blockingByte.x +","+ blockingByte.y;
    }

    private class MemoryMap {
        Location[][] memoryMap;

        public MemoryMap(int xWidth, int yHeight) {
            memoryMap = new Location[yHeight][xWidth];
        }

        public void exploreMap(int startX, int startY) {
            PathLocation startLocation = new PathLocation(startX, startY, 0);
            addPath(startLocation);

            ArrayList<PathLocation> pathsToExplore = new ArrayList<>();
            pathsToExplore.add(startLocation);

            while(!pathsToExplore.isEmpty()) {
                PathLocation nextLocation = pathsToExplore.remove(0);
                ArrayList<PathLocation> reachableLocations = getReachableLocations(nextLocation);
                for(PathLocation location : reachableLocations) {
                    addPath(location);
                    pathsToExplore.add(location);
                }
            }
        }

        public void addPath(PathLocation location) {
            memoryMap[location.y][location.x] = location;
        }

        public void addByte(ByteLocation byteLocation){
            memoryMap[byteLocation.y][byteLocation.x] = byteLocation;
        }

        public ArrayList<PathLocation> getReachableLocations(PathLocation location) {
            ArrayList<PathLocation> reachableLocations = new ArrayList<>();

            ArrayList<Location> potentialLocations = new ArrayList<>();

            addLocationToList(potentialLocations, location.x-1, location.y);
            addLocationToList(potentialLocations, location.x+1, location.y);
            addLocationToList(potentialLocations, location.x, location.y-1);
            addLocationToList(potentialLocations, location.x, location.y+1);

            for(Location potentialLocation : potentialLocations) {
                if(potentialLocation.visit(location.getCost()+1)){
                    reachableLocations.add((PathLocation)potentialLocation);
                }
            }

            return reachableLocations;
        }

        public void addLocationToList(ArrayList<Location> locations, int x, int y) {
            if(x >= 0 && x < getWidth() && y >= 0 && y < getHeight()) {
                if(memoryMap[y][x] != null) {
                    locations.add(memoryMap[y][x]);
                }
                else {
                    PathLocation location = new PathLocation(x, y, Integer.MAX_VALUE);
                    locations.add(location);
                }
            }
        }

        public int getEndLocationCost(){
            Location endLocation = memoryMap[memoryMap.length - 1][memoryMap[0].length - 1];
            if(endLocation != null && endLocation.getClass() == PathLocation.class) {
                return ((PathLocation) endLocation).getCost();
            }
            return Integer.MAX_VALUE;
        }

        public int getHeight(){
            return memoryMap.length;
        }

        public int getWidth(){
            return memoryMap[0].length;
        }

        public void printMap(){
            for(int i = 0; i < memoryMap.length; i++) {
                for(int j = 0; j < memoryMap[0].length; j++) {
                    if(memoryMap[i][j] != null) {
                        System.out.print(memoryMap[i][j].toString());
                    }
                    else{
                        System.out.print(".");
                    }
                }
                System.out.print("\n");
            }
        }
    }

    public abstract class Location {
        int x;
        int y;

        public Location(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public abstract boolean visit(int cost);

        public boolean isByte(){
            return false;
        }
    }

    public class ByteLocation extends Location {

        public ByteLocation(final int x, final int y) {
            super(x, y);
        }

        @Override
        public boolean visit(int cost){
            return false;
        }

        @Override
        public boolean isByte() {
            return true;
        }

        @Override
        public String toString() {
            return "#";
        }
    }

    public class PathLocation extends Location {
        int cost = Integer.MAX_VALUE;

        public PathLocation(final int x, final int y, int cost) {
            super(x, y);
            this.cost = cost;
        }

        @Override
        public boolean visit(int cost) {
            if(cost < this.cost) {
                this.cost = cost;
                return true;
            }
            return false;
        }

        public int getCost(){
            return cost;
        }

        @Override
        public String toString() {
            return "0";
        }
    }

    public ArrayList<ByteLocation> getByteLocations(String input){
        ArrayList<ByteLocation> byteLocations = new ArrayList<>();

        for(String line : input.lines().toList()){
            String[] numbers = line.split(",");
            ByteLocation byteLocation = new ByteLocation(Integer.parseInt(numbers[0]), Integer.parseInt(numbers[1]));
            byteLocations.add(byteLocation);
        }

        return byteLocations;
    }


}
