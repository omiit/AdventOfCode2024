package tests;

import days.Day20;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Day20Test {

    @Test
    void getEndLocationCostExample() {
        String input = InputFileReader.getInput("Day20Example");
        int answer = new Day20().getEndLocationCost(input);

        assertEquals(84, answer);
    }

    @Test
    void part1Example() {
        String input = InputFileReader.getInput("Day20Example");
        int answer = new Day20().part1(input, 1);

        assertEquals(44, answer);
    }

    @Test
    void part1() {
        String input = InputFileReader.getInput("Day20");
        int answer = new Day20().part1(input, 100);

        assertEquals(1332, answer);
    }

    @Test
    void part2Example() {
        String input = InputFileReader.getInput("Day20Example");
        int answer = new Day20().part2(input, 50, 20);

        assertEquals(285, answer);
    }

    @Test
    void part2() {
        String input = InputFileReader.getInput("Day20");
        int answer = new Day20().part2(input, 100, 20);

        assertEquals(987695, answer);
    }
}