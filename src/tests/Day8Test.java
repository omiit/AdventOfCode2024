package tests;

import days.Day8;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Day8Test {

    @Test
    void part1Example() {
        String input = InputFileReader.getInput("Day8Example");

        long answer = new Day8().part1(input);

        assertEquals(14L, answer);
    }

    @Test
    void part1() {
        String input = InputFileReader.getInput("Day8");

        long answer = new Day8().part1(input);

        assertEquals(348L, answer);
    }

    @Test
    void part2Example() {
        String input = InputFileReader.getInput("Day8Example");

        long answer = new Day8().part2(input);

        assertEquals(34L, answer);
    }

    @Test
    void part2() {
        String input = InputFileReader.getInput("Day8");

        long answer = new Day8().part2(input);

        assertEquals(1221L, answer);
    }
}