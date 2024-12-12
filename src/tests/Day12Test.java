package tests;

import days.Day12;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Day12Test {

    @Test
    void part1Example() {
        String input = InputFileReader.getInput("Day12Example");
        int answer = new Day12().part1(input);

        assertEquals(1930, answer);
    }

    @Test
    void part1() {
        String input = InputFileReader.getInput("Day12");
        int answer = new Day12().part1(input);

        assertEquals(1359028, answer);
    }

    @Test
    void part2Example2() {
        String input = InputFileReader.getInput("Day12Example2");
        int answer = new Day12().part2(input);

        assertEquals(236, answer);
    }

    @Test
    void part2() {
        String input = InputFileReader.getInput("Day12");
        int answer = new Day12().part2(input);

        assertEquals(839780, answer);
    }
}