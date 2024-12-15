package tests;

import days.Day15;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Day15Test {

    @Test
    void part1ExampleSmall() {
        String input = InputFileReader.getInput("Day15ExampleSmall");
        Long answer = new Day15().part1(input);

        assertEquals(2028L, answer);
    }

    @Test
    void part1Example() {
        String input = InputFileReader.getInput("Day15Example");
        Long answer = new Day15().part1(input);

        assertEquals(10092L, answer);
    }

    @Test
    void part1() {
        String input = InputFileReader.getInput("Day15");
        Long answer = new Day15().part1(input);

        assertEquals(1441031L, answer);
    }

    @Test
    void part2Example() {
        String input = InputFileReader.getInput("Day15Example");
        Long answer = new Day15().part2(input);

        assertEquals(9021L, answer);
    }

    @Test
    void part2() {
        String input = InputFileReader.getInput("Day15");
        Long answer = new Day15().part2(input);

        assertEquals(1425169L, answer);
    }
}