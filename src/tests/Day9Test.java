package tests;

import days.Day9;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Day9Test {

    @Test
    void part1Example() {
        String input = InputFileReader.getInput("Day9Example");
        Long answer = new Day9().part1(input);

        assertEquals(1928L, answer);
    }

    @Test
    void part1() {
        String input = InputFileReader.getInput("Day9");
        Long answer = new Day9().part1(input);

        assertEquals(6346871685398L, answer);
    }

    @Test
    void part2Example() {
        String input = InputFileReader.getInput("Day9Example");
        Long answer = new Day9().part2(input);

        assertEquals(2858L, answer);
    }

    @Test
    void part2() {
        String input = InputFileReader.getInput("Day9");
        Long answer = new Day9().part2(input);

        assertEquals(6373055193464L, answer);
    }
}