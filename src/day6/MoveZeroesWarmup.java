package day6;

import java.util.Arrays;

public class MoveZeroesWarmup {
    public static void main(String[] args) {
        int[] nums = {0, 1, 0, 3, 12};

        int i = 0;

        for (int num : nums) {
            if (num != 0){
                nums[i] = num;
                i++;
            }
        }
        while (i < nums.length){
            nums[i] = 0;
            i++;
        }

        System.out.println("数组最后的排序是" + Arrays.toString(nums));
    }
}
