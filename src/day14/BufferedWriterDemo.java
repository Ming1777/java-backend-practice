package day14;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class BufferedWriterDemo {
    public static void main(String[] args) throws IOException {
        Path file = Path.of("data", "students.txt");

        Files.createDirectories(file.getParent());

        try (BufferedWriter writer = Files.newBufferedWriter(file)) {
            writer.write("小明,20,Java后端");
            writer.newLine();

            writer.write("小红,19,前端开发");
            writer.newLine();

            writer.write("小刚,21,软件测试");
        }

        System.out.println("学生数据写入完成");
        System.out.println(file.toAbsolutePath());
    }
}