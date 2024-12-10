package tests;

import days.Day1;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Day1Test {

    @Test
    void part1Example() {
        String input = InputFileReader.getInput("Day1Example");
        int answer = new Day1().part1(input);

        assertEquals(11, answer);
    }

    @Test
    void part1() {
        String input = InputFileReader.getInput("Day1");
        int answer = new Day1().part1(input);

        assertEquals(2344935, answer);
    }

    @Test
    void part2Example() {
        String input = InputFileReader.getInput("Day1Example");
        int answer = new Day1().part2(input);

        assertEquals(31, answer);
    }

    @Test
    void part2() {
        String input = InputFileReader.getInput("Day1");
        int answer = new Day1().part2(input);

        assertEquals(27647262, answer);
    }
}