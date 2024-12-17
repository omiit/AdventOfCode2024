package tests;

import days.Day17;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Day17Test {

    @Test
    void part1MiniExample() {
        String output = new Day17().part1(2024, new int[]{0,1,5,4,3,0});

        assertEquals("4,2,5,6,7,7,7,7,3,1,0", output);
    }

    @Test
    void part1Example() {
        String output = new Day17().part1(729, new int[]{0,1,5,4,3,0});

        assertEquals("4,6,3,5,6,3,5,2,1,0", output);
    }

    @Test
    void part1() {
        String output = new Day17().part1(63687530, new int[]{2,4,1,3,7,5,0,3,1,5,4,1,5,5,3,0});

        assertEquals("1,6,7,4,3,0,5,0,6", output);
    }

}