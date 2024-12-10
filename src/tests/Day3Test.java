package tests;

import days.Day3;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Day3Test {

    @Test
    void part1Example() {
        String input = InputReader.getInput("Day3Example");
        int answer = new Day3().part1(input);

        assertEquals(161, answer);
    }

    @Test
    void part1() {
        String input = InputReader.getInput("Day3");
        int answer = new Day3().part1(input);

        assertEquals(164730528, answer);
    }

    @Test
    void part2Example() {
        String input = InputReader.getInput("Day3Example");
        int answer = new Day3().part2(input);

        assertEquals(48, answer);
    }

    @Test
    void part2() {
        String input = InputReader.getInput("Day3");
        int answer = new Day3().part2(input);

        assertEquals(70478672, answer);
    }
}