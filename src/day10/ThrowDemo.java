package day10;

public class ThrowDemo {
    public static void main(String[] args) {

        int age = 5;

        if (age < 0) {
            throw new IllegalArgumentException("年龄不能小于0");
        }

        System.out.println("年龄：" + age);
    }
}