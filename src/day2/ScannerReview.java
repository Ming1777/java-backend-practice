package day2;

import java.util.Scanner;

public class ScannerReview {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);



        System.out.print("请输入名字");
        String name = scanner.next();

        System.out.print("请输入年龄");
        int age = scanner.nextInt();

        System.out.print("请输入身高：");
        double height = scanner.nextDouble();

        System.out.print("请输入每天学习时间：");
        double studyHours = scanner.nextDouble();

        System.out.println("名字：" + name +  "     年龄：" + age);
        System.out.println("姓名：" + name);
        System.out.println("年龄：" + age);
        System.out.println("身高：" + height + "米");
        System.out.println("每天学习：" + studyHours + "小时");
        scanner.close();
    }




}
