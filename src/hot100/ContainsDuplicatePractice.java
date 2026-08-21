package hot100;

import java.util.HashSet;
import java.util.Set;

public class ContainsDuplicatePractice {

    public static void main(String[] args) {
        int[] nums = {1, 2, 3, 1};
        boolean result = containsDuplicate(nums);

        System.out.println(result);
    }

    // 【手敲】使用HashSet判断数组中是否存在重复元素
    public static boolean containsDuplicate(int[] nums) {
        Set<Integer> hashSet = new HashSet<>();

        for (int num : nums) {
            if (!hashSet.add(num)) {
                return true;
            }
        }

        return false;
    }
}
