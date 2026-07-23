package day5;

import java.util.Scanner;

public class ArraySearch {
    public static void main(String[] args) {
        int[] nums = {8, 3, 6, 2, 9};

        Scanner sc = new Scanner(System.in);

        System.out.print("请输入一个数字：");
        int number = sc.nextInt();

        int index = -1;

        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == number) {
                index = i;
                break;
            }
        }

        if (index == -1) {
            System.out.println("数组中没有这个数字");
        } else {
            System.out.println("数字的下标是：" + index);
        }

        sc.close();
    }
}