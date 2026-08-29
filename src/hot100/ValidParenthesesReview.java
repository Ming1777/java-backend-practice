package hot100;

import java.util.ArrayDeque;
import java.util.Deque;

public class ValidParenthesesReview {

    public static void main(String[] args) {
        String s1 = "()[]{}";  // true
        String s2 = "([{}])";  // true
        String s3 = "(]";      // false
        String s4 = "([)]";    // false

        System.out.println(isValid(s1));
        System.out.println(isValid(s2));
        System.out.println(isValid(s3));
        System.out.println(isValid(s4));
    }

    public static boolean isValid(String s) {
        Deque<Character> stack = new ArrayDeque<>();

        for (char c : s.toCharArray()) {

            // 遇到左括号就压栈
            if (c == '(' || c == '[' || c == '{') {
                stack.push(c);
            } else {
                // 出现右括号时，栈不能为空
                if (stack.isEmpty()) {
                    return false;
                }

                // 取出最近放入的左括号
                char left = stack.pop();

                // 判断左右括号是否匹配
                if (c == ')' && left != '(') {
                    return false;
                }

                if (c == ']' && left != '[') {
                    return false;
                }

                if (c == '}' && left != '{') {
                    return false;
                }
            }
        }

        // 如果还有左括号没有匹配，结果也是 false
        return stack.isEmpty();
    }
}