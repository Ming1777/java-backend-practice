package hot100;

import java.util.HashMap;
import java.util.Map;

public class MajorityElement {

    public static void main(String[] args) {
        int[] nums = {2, 2, 1, 1, 1, 2, 2};

        int result = majorityElement(nums);
        System.out.println("多数元素是：" + result);
    }

    // （使用HashMap统计每个数字出现的次数）
    public static int majorityElement(int[] nums) {
        Map<Integer, Integer> countMap = new HashMap<>();

        for (int num : nums) {
            int count = countMap.getOrDefault(num, 0) + 1;
            countMap.put(num, count);

            if (count > nums.length / 2) {
                return num;
            }
        }

        return -1;
    }
}
