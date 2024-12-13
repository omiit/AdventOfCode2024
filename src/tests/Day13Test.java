package tests;

import days.Day13;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Day13Test {

    @Test
    void part1Example() {
        String input = InputFileReader.getInput("Day13Example");
        Long answer = new Day13().part1(input);

        assertEquals(480L, answer);
    }

    @Test
    void part1() {
        String input = InputFileReader.getInput("Day13");
        Long answer = new Day13().part1(input);

        assertEquals(29187, answer);
    }

    @Test
    void part2Example2() {
        String input = InputFileReader.getInput("Day13Example");
        Long answer = new Day13().part2(input);

        assertEquals(875318608908L, answer);
    }

    @Test
    void part2() {
        String input = InputFileReader.getInput("Day13");
        Long answer = new Day13().part2(input);

        assertEquals(99968222587852L, answer);
    }
}