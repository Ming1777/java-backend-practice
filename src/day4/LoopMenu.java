package day4;

import java.util.Scanner;

public class LoopMenu {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("===== 商品管理系统 =====");
            System.out.println("1. 查看商品");
            System.out.println("2. 添加商品");
            System.out.println("3. 删除商品");
            System.out.println("0. 退出系统");
            System.out.print("请选择：");

            int choice = scanner.nextInt();

            if (choice == 0) {
                System.out.println("系统已退出");
                break;
            }

            switch (choice) {
                case 1:
                    System.out.println("正在查看商品");
                    break;
                case 2:
                    System.out.println("正在添加商品");
                    break;
                case 3:
                    System.out.println("正在删除商品");
                    break;
                default:
                    System.out.println("输入错误");
                    break;
            }

            System.out.println();
        }

        scanner.close();
    }
}