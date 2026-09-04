package day20;

import java.util.Arrays;

public class BubbleSortDemo {

    public static void main(String[] args) {
        int[] nums = {5, 2, 4, 1};

        System.out.println("排序前：" + Arrays.toString(nums));

        for (int i = 0; i < nums.length - 1; i++) {

            for (int j = 0; j < nums.length - 1 - i; j++) {

                if (nums[j] > nums[j + 1]) {
                    int temp = nums[j];
                    nums[j] = nums[j + 1];
                    nums[j + 1] = temp;
                }
            }

            System.out.println(
                    "第" + (i + 1) + "轮：" + Arrays.toString(nums)
            );
        }

        System.out.println("排序后：" + Arrays.toString(nums));
    }
}