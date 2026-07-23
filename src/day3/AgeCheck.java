package day3;

import java.util.Scanner;

public class AgeCheck {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("请输入年龄：");
        int age = scanner.nextInt();

        if (age >= 18) {
            System.out.println("已经成年");
        } else {
            System.out.println("尚未成年");
        }

        scanner.close();
    }
}