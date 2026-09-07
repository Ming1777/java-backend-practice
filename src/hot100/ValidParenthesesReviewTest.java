package hot100;

/** Regression cases for inputs containing only ()[]{}. */
public class ValidParenthesesReviewTest {
    public static void main(String[] args) {
        String[] inputs = {"", "()", "()[]{}", "([])", "([{}])", "(]", "([)]", "(", ")", "(()"};
        boolean[] expected = {true, true, true, true, true, false, false, false, false, false};
        for (int i = 0; i < inputs.length; i++) {
            boolean actual = ValidParenthesesReview.isValid(inputs[i]);
            if (actual != expected[i]) {
                throw new AssertionError("Input: " + inputs[i] + ", expected: " + expected[i] + ", actual: " + actual);
            }
            System.out.println("PASS [" + inputs[i] + "] -> " + actual);
        }
        System.out.println("All 10 cases passed.");
    }
}
