package day6;

public class MethodReturn {
    public static void main(String[] args) {
        // 调用add方法
        int addResult = add(10, 20);
        System.out.println("两数之和：" + addResult);

        // 准备数组
        int[] nums = {3, 7, 2, 9, 6};

        // 调用sumArray方法
        int total = sumArray(nums);
        System.out.println("数组总和：" + total);
    }

    // 计算两个整数的和，并返回结果
    public static int add(int a, int b) {
        int sum = a + b;

        return sum;
    }

    // 计算数组中所有元素的和，并返回结果
    public static int sumArray(int[] nums) {
        int sum = 0;

        for (int num : nums) {
            sum = sum + num;
        }

        return sum;
    }
}