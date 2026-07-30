package hot100;

import java.util.HashMap;
import java.util.HashSet;

public class TwoSumMapReview {
    public static void main(String[] args) {
        int[] nums = {3, 2, 4};
        int target = 6;

        HashMap<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {

            int need = target - nums[i];

            if (map.containsKey(need)) {
                System.out.println("[" + map.get(need) + ", "+i + "]");


                break;
            }
            map.put(nums[i],i);
        }
        System.out.println(map);
    }
}