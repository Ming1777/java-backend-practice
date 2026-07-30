package day13;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class StudentSortDemo {
    public static void main(String[] args) {
        List<Student> students = new ArrayList<>();

        students.add(new Student("小明", 20, 90.5));
        students.add(new Student("小红", 19, 95.0));
        students.add(new Student("小刚", 20, 88.5));
        students.add(new Student("小李", 20, 96.0));

        System.out.println("排序前");
        for (Student student : students){
            System.out.println(student);
        }
        System.out.println("--------------------");

        students.sort(Comparator.comparingInt(Student::getAge).thenComparing(Comparator.comparingDouble(Student::getScore).reversed()));
        System.out.println("按照年龄大小排序后：");
        for (Student student : students){
            System.out.println(student);
        }

    }
}
