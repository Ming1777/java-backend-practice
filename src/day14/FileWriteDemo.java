package day14;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileWriteDemo {
    public static void main(String[] args) throws IOException {
        Path folder = Path.of("data");

        Files.createDirectories(folder);

        Path file = folder.resolve("user.txt");

        Files.writeString(
                file,
                "姓名：小明\n年龄：20\n学习方向：Java后端"
        );

        System.out.println("文件写入完成：");
        System.out.println(file.toAbsolutePath());
    }
}