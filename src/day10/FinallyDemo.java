package day10;

import java.util.Scanner;

public class FinallyDemo {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        try {
            System.out.println("请输入一个整数：");
            int number = sc.nextInt();
            System.out.println("你输入的是：" + number);
        } catch (Exception e) {
            System.out.println("输入格式不正确");
        } finally {
            sc.close();
            System.out.println("Scanner已经关闭");
        }

        System.out.println("程序结束");
    }
}