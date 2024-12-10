package tests;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class InputReader {

    private InputReader(){

    }

    public static String getInput(String filename){
        Path filePath = Paths.get("src/Tests/files/" + filename);
        try {
            return Files.readString(filePath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
