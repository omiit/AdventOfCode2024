package tests;

import days.Day19;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Day19Test {

    @Test
    void part1Example() {
        String input = InputFileReader.getInput("Day19Example");
        int answer = new Day19().part1(input);

        assertEquals(6, answer);
    }

    @Test
    void part1() {
        String input = InputFileReader.getInput("Day19");
        int answer = new Day19().part1(input);

        assertEquals(206, answer);
    }

    @Test
    void part2Example() {
        String input = InputFileReader.getInput("Day19Example");
        long answer = new Day19().part2(input);

        assertEquals(16L, answer);
    }

    @Test
    void part2() {
        String input = InputFileReader.getInput("Day19");
        long answer = new Day19().part2(input);

        assertEquals(622121814629343L, answer);
    }
}