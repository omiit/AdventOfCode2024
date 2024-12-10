package tests;

import days.Day10;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Day10Test {

    @Test
    void part1Example() {
        String input = InputFileReader.getInput("Day10Example");
        int answer = new Day10().part1(input);

        assertEquals(36, answer);
    }

    @Test
    void part1() {
        String input = InputFileReader.getInput("Day10");
        int answer = new Day10().part1(input);

        assertEquals(468, answer);
    }

    @Test
    void part2Example() {
        String input = InputFileReader.getInput("Day10Example");
        int answer = new Day10().part2(input);

        assertEquals(81, answer);
    }

    @Test
    void part2() {
        String input = InputFileReader.getInput("Day10");
        int answer = new Day10().part2(input);

        assertEquals(966, answer);
    }
}