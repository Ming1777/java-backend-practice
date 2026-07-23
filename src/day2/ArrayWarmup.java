package day2;


public class ArrayWarmup {
    public static void main(String[] args) {
        int [] nums = {3,5,-6,6,0};
        int sum = 0;

        int max = nums[0];

        int evenCount = 0;

        for ( int num : nums ){

        sum = sum + num;


        if (num % 2 ==0){

            evenCount++;
        }



        if (num > max ){

            max = num ;
        }


        }
        System.out.println("数组的和是" + sum);
        System.out.println("最大值是"  +  max);
        System.out.println("偶数数量是" + evenCount);
    }
}
