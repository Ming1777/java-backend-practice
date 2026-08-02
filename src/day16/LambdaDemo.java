package day16;

import java.util.List;

public class LambdaDemo {
    public static void main(String[] args) {
        List<String> names = List.of(
                "小明",
                "小红",
                "小刚"
        );

        names.forEach(name -> {
            System.out.println("用户：" + name);
        });
    }
}
