package tests;

import days.Day6;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Day6Test {

    @Test
    void part2Example() {
        String input = InputFileReader.getInput("Day6Example");

        int answer = new Day6().part2(input);

        assertEquals(6, answer);
    }

    @Test
    void part2() {
        String input = InputFileReader.getInput("Day6");

        int answer = new Day6().part2(input);

        assertEquals(1748, answer);
    }
}