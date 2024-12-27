package tests;

import days.Day7;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Day7Test {

    @Test
    void part1Example() {
        String input = InputFileReader.getInput("Day7Example");

        long answer = new Day7().part1(input);

        assertEquals(3749L, answer);
    }

    @Test
    void part1() {
        String input = InputFileReader.getInput("Day7");

        long answer = new Day7().part1(input);

        assertEquals(882304362421L, answer);
    }

    @Test
    void part2Example() {
        String input = InputFileReader.getInput("Day7Example");

        long answer = new Day7().part2(input);

        assertEquals(11387L, answer);
    }

    @Test
    void part2() {
        String input = InputFileReader.getInput("Day7");

        long answer = new Day7().part2(input);

        assertEquals(145149066755184L, answer);
    }
}