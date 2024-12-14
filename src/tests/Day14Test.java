package tests;

import days.Day14;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Day14Test {

    @Test
    void part1Example() {
        String input = InputFileReader.getInput("Day14Example");
        Long answer = new Day14().part1(input, 11, 7);

        assertEquals(12L, answer);
    }

    @Test
    void part1() {
        String input = InputFileReader.getInput("Day14");
        Long answer = new Day14().part1(input, 101, 103);

        assertEquals(215987200L, answer);
    }
}