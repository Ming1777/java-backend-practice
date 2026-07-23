package day4;

import java.util.Scanner;

public class DiscountWarmup {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("请输入订单金额");
        double money = sc.nextDouble();

        double finalMoney;

        if (money < 0 ){
            System.out.println("金额不正确请重新输入");
        }else if (money >= 500){
            finalMoney = money * 0.8;
            System.out.printf("实付金额：%.2f元%n", finalMoney);
        } else if (money >= 300) {
            finalMoney = money * 0.9;
            System.out.printf("实付金额：%.2f元%n", finalMoney);
        } else {
            finalMoney = money;
            System.out.printf("实付金额：%.2f元%n", finalMoney);
        }

        sc.close();
        }
}
