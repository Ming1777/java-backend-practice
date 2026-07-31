package day14;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class BufferedReaderDemo {
    public static void main(String[] args) throws IOException {
        Path file = Path.of("data", "user.txt");

        try (BufferedReader reader = Files.newBufferedReader(file)) {
            String line;

            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        }
    }
}