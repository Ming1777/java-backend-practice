package hot100;

public class BinarySearchReview {
    public static void main(String[] args) {
        int[] nums = {10, 20, 30, 40, 50, 60, 70};

        int result = binarySearch(nums,30);
        System.out.println(result);
    }
    public static int binarySearch(int[] nums, int target) {
        int left = 0;
        int right = nums.length -1;

        while (left <= right){
            int mid = left + (right - left) / 2;

            if (nums[mid] == target){
                return mid;
            }
            else if (nums[mid] < target){
                left = mid + 1;
            }
            else {
                right = mid - 1;
            }
        }
        return -1;
    }
}
