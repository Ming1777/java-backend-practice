package day3;

import java.util.Scanner;

public class DiscountCalculator {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);


        System.out.println("请输入商品名称");
        String name = sc.next();

        System.out.println("请输入商品单价");
        double price = sc.nextDouble();

        System.out.println("请输入商品数量");
        int count = sc.nextInt();


        if (count <=0) {
            System.out.println("商品单价或购买数量不合法");
        }
        else {
            double originalPrice = price * count;
            double discount;

            if (originalPrice >= 500) {
                discount = 0.8;
            } else if (originalPrice >= 300) {
                discount = 0.9;
            } else {
                discount = 1.0;
            }

            double finalPrice = discount * originalPrice;

            System.out.println("商品名称：" + name);
            System.out.printf("商品原价：", originalPrice);
            System.out.println("折扣：" + discount);
            System.out.printf("实付金额："+ finalPrice);
        }
        }

    }
