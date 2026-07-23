package day2;

import java.util.Scanner;

public class ShoppingCalculator {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入商品名称  ");
        String name = scanner.next();

        System.out.println("请输入商品单价  ");
        double price = scanner.nextDouble();


        System.out.print("请输入购买数量：");
        int count = scanner.nextInt();

        System.out.print("请输入折扣：");
        double discount = scanner.nextDouble();

        double originalPrice = price * count;
        double finalPrice = price * discount;

        System.out.println("商品名称：" + name);
        System.out.println("商品原价：" + originalPrice);
        System.out.println("实付金额：" + finalPrice);

        scanner.close();

    }
}
