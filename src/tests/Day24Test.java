package tests;

import days.Day24;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Day24Test {

    @Test
    void part1Example() {
        String input = InputFileReader.getInput("Day24Example");
        long answer = new Day24().part1(input);

        assertEquals(2024L, answer);
    }

    @Test
    void part1() {
        String input = InputFileReader.getInput("Day24");
        long answer = new Day24().part1(input);

        assertEquals(58639252480880L, answer);
    }

    @Test
    void part2Example() {
        String input = InputFileReader.getInput("Day24Example2");
        long answer = new Day24().part1(input);

    }

    @Test
    void part2() {
        String input = InputFileReader.getInput("Day24Part2");
        long answer = new Day24().part1(input);

    }
}