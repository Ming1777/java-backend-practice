package day14;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileReadDemo {
    public static void main(String[] args) throws IOException {
        Path file = Path.of("data", "user.txt");

        String content = Files.readString(file);

        System.out.println("文件内容：");
        System.out.println(content);
    }
}