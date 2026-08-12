package hot100;

public class BestTimeToBuyAndSellStock {

    public static void main(String[] args) {
        int[] prices = {7, 1, 5, 3, 6, 4};

        int result = maxProfit(prices);

        System.out.println("最大利润是：" + result);
    }

    // （记录历史最低价格，并计算当天卖出的利润）
    public static int maxProfit(int[] prices) {
        int minPrice = prices[0];
        int maxProfit = 0;

        for (int i = 1; i < prices.length; i++) {
            int todayProfit = prices[i] - minPrice;

            if (todayProfit > maxProfit) {
                maxProfit = todayProfit;
            }

            if (prices[i] < minPrice) {
                minPrice = prices[i];
            }
        }

        return maxProfit;
    }
}
