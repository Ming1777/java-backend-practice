package hot100;

public class SingleNumberReview {
    public static void main(String[] args) {
        int[] nums = {4, 1, 2, 1, 2};

        int result = singleNumber(nums);
        System.out.println("只出现一次的数字是：" + result);
    }

    public static int singleNumber(int[] nums) {
        int result = 0;
        for (int num : nums) {
            result = result ^ num;
        }

        return result;
    }

}
