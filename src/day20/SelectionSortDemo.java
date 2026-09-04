package day20;

import java.util.Arrays;

public class SelectionSortDemo {

    public static void main(String[] args) {
        int[] nums = {4, 3, 1, 2};

        System.out.println("排序前：" + Arrays.toString(nums));

        for (int i = 0; i < nums.length - 1; i++) {

            int minIndex = i;


            for (int j = i + 1; j < nums.length; j++) {
                if (nums[j] < nums[minIndex]) {
                    minIndex = j;
                }
            }

            if (minIndex != i) {
                int temp = nums[i];
                nums[i] = nums[minIndex];
                nums[minIndex] = temp;
            }

            System.out.println(
                    "第" + (i + 1) + "轮：" + Arrays.toString(nums)
            );
        }

        System.out.println("排序后：" + Arrays.toString(nums));
    }
}
