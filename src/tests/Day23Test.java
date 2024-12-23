package tests;

import days.Day23;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Day23Test {

    @Test
    void part1Example() {
        String input = InputFileReader.getInput("Day23Example");
        int answer = new Day23().part1(input);

        assertEquals(7, answer);
    }

    @Test
    void part1() {
        String input = InputFileReader.getInput("Day23");
        int answer = new Day23().part1(input);

        assertEquals(1194, answer);
    }

    @Test
    void part2() {
        String input = InputFileReader.getInput("Day23");
        String answer = new Day23().part2(input);

        assertEquals("bd,bu,dv,gl,qc,rn,so,tm,wf,yl,ys,ze,zr", answer);
    }
}