package Tests;

import Days.Day1;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class Day1Test {

    @Test
    void part1Example() {
        String input = getInput("Day1Example");
        int answer = new Day1().Part1(input);

        assertEquals(11, answer);
    }

    @Test
    void part1() {
        String input = getInput("Day1");
        int answer = new Day1().Part1(input);

        assertEquals(2344935, answer);
    }

    @Test
    void part2Example() {
        String input = getInput("Day1Example");
        int answer = new Day1().Part2(input);

        assertEquals(31, answer);
    }

    @Test
    void part2() {
        String input = getInput("Day1");
        int answer = new Day1().Part2(input);

        assertEquals(27647262, answer);
    }

    private String getInput(String filename){
        Path filePath = Paths.get("src/Tests/files/" + filename);
        try {
            return Files.readString(filePath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}