package day5;

import java.util.Arrays;

public class ArrayCopyDemo {
    public static void main(String[] args) {
        int[] nums = {1, 2, 3};
        int[] copy = Arrays.copyOf(nums,nums.length);

        copy[0] = 100;

        System.out.println("nums：" + Arrays.toString(nums));
        System.out.println("copy：" + Arrays.toString(copy));
    }
}