package day17;

import java.util.List;

public class StreamDemo {
    public static void main(String[] args) {
        List<Integer> ages = List.of(
                18,
                22,
                19,
                25,
                20
        );

        List<Integer> result = ages.stream()
                .filter(age -> age >= 20)
                .sorted()
                .toList();

        System.out.println("原集合：" + ages);
        System.out.println("处理结果：" + result);

        List<String> names = List.of(
                "小明",
                "小红",
                "小刚"
        );

        List<String> userLabels = names.stream()
                .map(name -> "用户：" + name)
                .toList();

        System.out.println(userLabels);
    }
}