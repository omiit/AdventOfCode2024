package tests;

import days.Day11;
import org.junit.jupiter.api.Test;

import java.util.Timer;

import static org.junit.jupiter.api.Assertions.*;

class Day11Test {

    @Test
    void part1Example() {
        String input = InputFileReader.getInput("Day11Example");
        long answer = new Day11().part1(input);

        assertEquals(55312, answer);
    }

    @Test
    void part1() {
        String input = InputFileReader.getInput("Day11");
        long answer = new Day11().part1(input);

        assertEquals(217443, answer);
    }

    @Test
    void part2Example() {
        String input = InputFileReader.getInput("Day11Example");
        long answer = new Day11().part2(input);

        assertEquals(65601038650482L, answer);
    }

    @Test
    void part2() {

        String input = InputFileReader.getInput("Day11");
        long answer = new Day11().part2(input);

        assertEquals(257246536026785L, answer);
    }
}