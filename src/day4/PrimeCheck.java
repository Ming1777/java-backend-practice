package day4;

import java.util.Scanner;

public class PrimeCheck {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("请输入一个整数");
        int number = sc.nextInt();
        boolean zhishu = true;

        if(number <= 1){
            zhishu = false;
        }

        else{
            for (int i = 2; i < number; i++) {
                if (number % i == 0) {
                    zhishu = false;
                    break;

                }
            }
        }

        if (zhishu){
            System.out.println(number + "是质数");
        }
        else {
            System.out.println(number + "不是质数");
        }
        sc.close();
    }

}
