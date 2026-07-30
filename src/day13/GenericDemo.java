package day13;

import java.util.ArrayList;
import java.util.List;

public class GenericDemo {
    public static void main(String[] args) {


        Integer score = 90;
        int result = score;
        System.out.println(score);
        System.out.println(result);


        System.out.println("--------------------");
        List<String> names = new ArrayList<>();
        names.add("小明");
        names.add("小红");
        names.add("小刚");

        System.out.println(names);

        List<Integer> scores = new ArrayList<>();
        scores.add(90);
        scores.add(85);
        scores.add(100);

        System.out.println(scores);

        System.out.println("遍历姓名：");
        printList(names);
        System.out.println("遍历成绩：");
        printList(scores);
        System.out.println("----------------");
    }
    public static <T> void printList(List<T> list){
        for (T item : list) {
            System.out.println(item);

        }
    }
    }

