package tests;

import days.Day20;
import days.Day21;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Day21Test {

    @Test
    void part1Example() {
        String input = InputFileReader.getInput("Day21Example");
        long answer = new Day21().part1(input, 2);

        assertEquals(126384L, answer);
    }

    @Test
    void part1() {
        String input = InputFileReader.getInput("Day21");
        long answer = new Day21().part1(input, 2);

        assertEquals(157908L, answer);
    }

    @Test
    void part2() {
        String input = InputFileReader.getInput("Day21");
        long answer = new Day21().part1(input, 25);
        //79517805098402 too low
        //79583019497968 also wrong
        //197810519284786 too high
        //197972747156722 also wrong
        assertEquals(196910339808654L, answer);
    }


}