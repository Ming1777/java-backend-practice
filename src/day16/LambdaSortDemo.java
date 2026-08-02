package day16;

import day13.Student;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class LambdaSortDemo {
    public static void main(String[] args) {
        List<Student> students = new ArrayList<>();

        students.add(new Student("小明", 22, 90.5));
        students.add(new Student("小红", 19, 95.0));
        students.add(new Student("小刚", 21, 88.5));

        students.sort(Comparator.comparingInt(Student::getAge));

        students.forEach(System.out::println);
    }
}
