package tests;

import days.Day2;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day2Test {

    @Test
    void part1Example() {
        String input = InputFileReader.getInput("Day2Example");
        int answer = new Day2().part1(input);

        assertEquals(2, answer);
    }

    @Test
    void part1() {
        String input = InputFileReader.getInput("Day2");
        int answer = new Day2().part1(input);

        assertEquals(432, answer);
    }

    @Test
    void part2Example() {
        String input = InputFileReader.getInput("Day2Example");
        int answer = new Day2().part2(input);

        assertEquals(4, answer);
    }

    @Test
    void part2() {
        String input = InputFileReader.getInput("Day2");
        int answer = new Day2().part2(input);

        assertEquals(488, answer);
    }
}