package tests;

import days.Day18;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Day18Test {

    @Test
    void part1Example() {
        String input = InputFileReader.getInput("Day18Example");
        Long answer = new Day18().part1(input, 7, 7, 12);

        assertEquals(22, answer);
    }

    @Test
    void part1() {
        String input = InputFileReader.getInput("Day18");
        Long answer = new Day18().part1(input, 71, 71, 1024);

        assertEquals(356, answer);
    }

    @Test
    void part2Example() {
        String input = InputFileReader.getInput("Day18Example");
        String answer = new Day18().part2(input, 7, 7);

        assertEquals("6,1", answer);
    }

    @Test
    void part2() {
        String input = InputFileReader.getInput("Day18");
        String answer = new Day18().part2(input, 71, 71);

        assertEquals("22,33", answer);
    }
}