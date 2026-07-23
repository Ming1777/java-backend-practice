package day6;

public class MethodParameter {
    public static void main(String[] args) {
        printUser("小明", 20);
        printUser("小红", 19);
    }

    public static void printUser(String name, int age) {
        System.out.println("姓名：" + name);
        System.out.println("年龄：" + age);
        System.out.println("----------------");
    }
}