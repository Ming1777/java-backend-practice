package hot100;

import java.util.Arrays;

public class MoveZeroes {
    public static void main(String[] args) {
        int[] nums = {0, 1, 0, 3, 12};
        int i = 0;
        for ( int num : nums){

            if ( num != 0 ){
                nums [i] = num;
                i++;
            }
        }//for循环结束i=3

            while (i < nums.length) {
                nums[i] = 0;
                i++;
            }



        System.out.println(Arrays.toString(nums));


    }
}
