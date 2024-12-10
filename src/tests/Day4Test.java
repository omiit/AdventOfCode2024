package tests;

import days.Day4;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Day4Test {

    @Test
    void part1Example() {
        String input = InputReader.getInput("Day4Example");
        int answer = new Day4().part1(input);

        assertEquals(18, answer);
    }

    @Test
    void part1() {
        String input = InputReader.getInput("Day4");
        int answer = new Day4().part1(input);

        assertEquals(2454, answer);
    }

    @Test
    void part2Example() {
        String input = InputReader.getInput("Day4Example");
        int answer = new Day4().part2(input);

        assertEquals(9, answer);
    }

    @Test
    void part2() {
        String input = InputReader.getInput("Day4");
        int answer = new Day4().part2(input);

        assertEquals(1858, answer);
    }
}