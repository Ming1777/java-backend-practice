package day5;

import java.util.Arrays;

public class ArrayReverse {
    public static void main(String[] args) {
        int []nums = {1,2,3,4,5};
        int[] reversed = new int[nums.length];
        for (int i = 0; i < nums.length; i++) {
            reversed [i] = nums [nums.length - i -1];
        }
        System.out.println("原数组：" + Arrays.toString(nums));
        System.out.println("反转后：" + Arrays.toString(reversed));
    }
}
