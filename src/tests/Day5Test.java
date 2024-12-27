package tests;

import days.Day5;
import org.junit.jupiter.api.Test;

import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class Day5Test {

    @Test
    void part1Example() {
        String rules = InputFileReader.getInput("Day5Example");
        String pages = InputFileReader.getInput("Day5Example-2");

        int answer = new Day5().part1(rules, pages);

        assertEquals(143, answer);
    }

    @Test
    void part1() {
        String rules = InputFileReader.getInput("Day5");
        String pages = InputFileReader.getInput("Day5-2");

        int answer = new Day5().part1(rules, pages);

        assertEquals(5329, answer);
    }

    @Test
    void part2Example() {
        String rules = InputFileReader.getInput("Day5Example");
        String pages = InputFileReader.getInput("Day5Example-2");

        int answer = new Day5().part2(rules, pages);

        assertEquals(123, answer);
    }

    @Test
    void part2() {
        String rules = InputFileReader.getInput("Day5");
        String pages = InputFileReader.getInput("Day5-2");

        int answer = new Day5().part2(rules, pages);

        assertEquals(5833, answer);
    }
}