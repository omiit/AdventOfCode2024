package days;

import util.InputReader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Day8 {

    public class Location {
        int rowIndex;
        int colIndex;

        public Location(int rowindex, int colindex){
            this.rowIndex = rowindex;
            this.colIndex = colindex;
        }

        public boolean equals(Object obj) {
            if (obj instanceof Location) {
                Location pt = (Location)obj;
                return (rowIndex == pt.rowIndex) && (colIndex == pt.colIndex);
            }
            return super.equals(obj);
        }

    }

    public int part1(String input) {
        int answer = 0;

        char[][] antennas = InputReader.getTwoDimensionalCharArray(input);
        char[][] antinodes = new char[antennas.length][antennas[0].length];

        Map<Character, ArrayList<Location>> frequencies = new HashMap<>();

        for(int i = 0; i < antennas.length; i++){
            for(int j = 0; j < antennas[0].length; j++){
                if(antennas[i][j] != '.'){
                    addAntenna(frequencies, new Location(i, j), antennas[i][j]);
                }
            }
        }

        //Loop over characters
        for(char c : frequencies.keySet()){
            ArrayList<Location> frequencyAntennas = frequencies.get(c);

            //Loop over every combination
            for(Location location1 : frequencyAntennas){
                for(Location location2: frequencyAntennas){
                    //Exclude the match with the location itself
                    if(!location1.equals(location2)){

                        int antiNodeRowIndex1 = location1.rowIndex + (location1.rowIndex - location2.rowIndex);
                        int antiNodeColIndex1 = location1.colIndex + (location1.colIndex - location2.colIndex);
                        Location antiNode1 = new Location(antiNodeRowIndex1, antiNodeColIndex1);

                        int antiNodeRowIndex2 = location2.rowIndex + (location2.rowIndex - location1.rowIndex);
                        int antiNodeColIndex2 = location2.colIndex + (location2.colIndex - location1.colIndex);
                        Location antiNode2 = new Location(antiNodeRowIndex2, antiNodeColIndex2);

                        antinodes = addAntinodeIfInbounds(antinodes, antiNode1);
                        antinodes = addAntinodeIfInbounds(antinodes, antiNode2);
                    }
                }
            }
        }

        for(int i = 0; i < antinodes.length; i++){
            for(int j = 0; j < antinodes[0].length; j++) {
                if(antinodes[i][j] == '#'){
                    answer++;
                }
            }
        }

        return answer;
    }

    public int part2(String input) {
        int answer = 0;

        char[][] antennas = InputReader.getTwoDimensionalCharArray(input);
        char[][] antinodes = new char[antennas.length][antennas[0].length];

        Map<Character, ArrayList<Location>> frequencies = new HashMap<>();

        for(int i = 0; i < antennas.length; i++){
            for(int j = 0; j < antennas[0].length; j++){
                if(antennas[i][j] != '.'){
                    antinodes[i][j] = antennas[i][j];
                    addAntenna(frequencies, new Location(i, j), antennas[i][j]);
                }
            }
        }

        //Loop over characters
        for(char c : frequencies.keySet()){
            ArrayList<Location> frequencyAntennas = frequencies.get(c);

            //Loop over every combination
            for(Location location1 : frequencyAntennas){
                for(Location location2: frequencyAntennas){
                    //Exclude the match with the location itself
                    if(!location1.equals(location2)){

                        //one way
                        for(int i = 0; i < antinodes.length && i < antinodes[0].length; i++){
                            int antiNodeRowIndex1 = location1.rowIndex + i*(location1.rowIndex - location2.rowIndex);
                            int antiNodeColIndex1 = location1.colIndex + i*(location1.colIndex - location2.colIndex);
                            Location antiNode1 = new Location(antiNodeRowIndex1, antiNodeColIndex1);
                            if(antiNodeInBounds(antinodes, antiNode1)){
                                antinodes = addAntinode(antinodes, antiNode1);
                            }
                            else {
                                break;
                            }
                        }

                        //the other way
                        for(int i = 0; i < antinodes.length && i < antinodes[0].length; i++){
                            int antiNodeRowIndex2 = location2.rowIndex + i*(location2.rowIndex - location1.rowIndex);
                            int antiNodeColIndex2 = location2.colIndex + i*(location2.colIndex - location1.colIndex);
                            Location antiNode2 = new Location(antiNodeRowIndex2, antiNodeColIndex2);
                            if(antiNodeInBounds(antinodes, antiNode2)){
                                antinodes = addAntinode(antinodes, antiNode2);
                            }
                            else {
                                break;
                            }
                        }
                    }
                }
            }
        }

        for(int i = 0; i < antinodes.length; i++){
            for(int j = 0; j < antinodes[0].length; j++) {
                if(antinodes[i][j] == '#'){
                    answer++;
                }
            }
        }

        return answer;
    }

    public boolean antiNodeInBounds(char[][] grid, Location location){
        if(location.rowIndex >= 0 && location.rowIndex < grid.length){
            if(location.colIndex >= 0 && location.colIndex < grid[0].length) {
                return true;
            }
        }
        return false;
    }

    public char[][] addAntinode(char[][] grid, Location location){
        grid[location.rowIndex][location.colIndex] = '#';
        return grid;
    }

    public char[][] addAntinodeIfInbounds(char[][] antinodes, Location location){
        if(location.rowIndex >= 0 && location.rowIndex < antinodes.length){
            if(location.colIndex >= 0 && location.colIndex < antinodes[0].length) {
                antinodes[location.rowIndex][location.colIndex] = '#';
            }
        }
        return antinodes;
    }

    public void addAntenna(Map<Character, ArrayList<Location>> frequencies, Location antenna, char frequency){
        if(frequencies.containsKey(frequency)){
            frequencies.get(frequency).add(antenna);
        }
        else{
            ArrayList<Location> locations = new ArrayList<>();
            locations.add(antenna);
            frequencies.put(frequency, locations);
        }
    }
}

