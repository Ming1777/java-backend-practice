package day14;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class FileAppendDemo {
    public static void main(String[] args) throws IOException {
        Path file = Path.of("data", "students.txt");

        try (BufferedWriter writer = Files.newBufferedWriter(
                file,
                StandardOpenOption.CREATE,
                StandardOpenOption.APPEND
        )) {
            if (Files.size(file) > 0) {
                writer.newLine();
            }

            writer.write("小李,22,Java后端");
        }

        System.out.println("数据追加完成");
    }
}