package tests;

import days.Day16;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Day16Test {

    @Test
    void part1Example1() {
        String input = InputFileReader.getInput("Day16Example1");
        Long answer = new Day16().part1(input);

        assertEquals(7036L, answer);
    }

    @Test
    void part1Example2() {
        String input = InputFileReader.getInput("Day16Example2");
        Long answer = new Day16().part1(input);

        assertEquals(11048L, answer);
    }

    @Test
    void part1() {
        String input = InputFileReader.getInput("Day16");
        Long answer = new Day16().part1(input);

        assertEquals(95444L, answer);
    }

    @Test
    void part2Example1() {
        String input = InputFileReader.getInput("Day16Example1");
        Long answer = new Day16().part2(input);

        assertEquals(45L, answer);
    }

    @Test
    void part2Example2() {
        String input = InputFileReader.getInput("Day16Example2");
        Long answer = new Day16().part2(input);

        assertEquals(64L, answer);
    }

    @Test
    void part2() {
        String input = InputFileReader.getInput("Day16");
        Long answer = new Day16().part2(input);

        assertEquals(0L, answer);
    }
}