package hot100;

import java.util.HashSet;
import java.util.Set;

public class ContainsDuplicateReview {

    public static void main(String[] args) {
        int[] nums = {1, 2, 3, 1};

        boolean result = containsDuplicate(nums);

        System.out.println(result);
    }

    // 【手敲】使用HashSet判断数组中是否存在重复元素
    public static boolean containsDuplicate(int[] nums) {
        Set<Integer> seen = new HashSet<>();
        for (int i = 0; i < nums.length; i++) {
            if (seen.contains(nums[i])) {
                return true;
            }
            seen.add(nums[i]);
        }
        return false;
    }
}
