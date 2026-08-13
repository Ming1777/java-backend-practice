package hot100;

public class MaximumSubarray {

    public static void main(String[] args) {
        int[] nums = {-2, 1, -3, 4, -1, 2, 1, -5, 4};
        int result = maxSubArray(nums);
        System.out.println("最大子数组和是：" + result);
    }

    // （计算连续子数组能够得到的最大和）
    public static int maxSubArray(int[] nums) {
        int currentSum = nums[0];
        int maxSum = nums[0];

        for (int i = 1; i < nums.length; i++) {
            currentSum = Math.max(nums[i], nums[i] + currentSum);

            maxSum = Math.max(currentSum, maxSum);
        }

        return maxSum;
    }
}
