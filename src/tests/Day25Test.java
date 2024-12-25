package tests;

import days.Day25;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Day25Test {

    @Test
    void part1Example() {
        String input = InputFileReader.getInput("Day25Example");
        long answer = new Day25().part1(input);

        assertEquals(3L, answer);
    }

    @Test
    void part1() {
        String input = InputFileReader.getInput("Day25");
        long answer = new Day25().part1(input);

        assertEquals(3671L, answer);
    }
}