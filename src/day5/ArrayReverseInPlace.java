package day5;

import java.util.Arrays;

public class ArrayReverseInPlace {
    public static void main(String[] args) {
        int []nums = {1,2,3,4,6};
        int left = 0;
        int right = nums.length-1;

        while (left < right)
        System.out.println(Arrays.toString(nums));

    }
}