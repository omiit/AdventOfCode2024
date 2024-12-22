package tests;

import days.Day22;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Day22Test {

    @Test
    void part1Example() {
        String input = InputFileReader.getInput("Day22Example");
        long answer = new Day22().part1(input, 2000);

        assertEquals(37327623, answer);
    }

    @Test
    void part1() {
        String input = InputFileReader.getInput("Day22");
        long answer = new Day22().part1(input, 2000);

        assertEquals(13004408787L, answer);
    }

    @Test
    void part2Example() {
        String input = InputFileReader.getInput("Day22Example2");
        int answer = new Day22().part2(input, 2000);

        assertEquals(23, answer);
    }

    @Test
    void part2() {
        String input = InputFileReader.getInput("Day22");
        int answer = new Day22().part2(input, 2000);

        assertEquals(1455, answer);
    }
}