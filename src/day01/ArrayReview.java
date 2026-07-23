package day01;

public class ArrayReview {
    public static void main(String[] args) {
        int[] nums = {-12, 6, 7, 8, 0, 2, 9};

        int evenCount = 0;

        for (int num : nums) {
            if (num % 2 == 0 )
                    evenCount++;{
            }
        }

        System.out.println("偶数数量：" + evenCount);
    }
}