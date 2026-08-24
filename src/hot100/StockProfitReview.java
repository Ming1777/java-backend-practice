package hot100;

public class StockProfitReview {
    public static void main(String[] args) {
        int[] prices = {7, 1, 5, 3, 6, 4};
        int result = maxprofit(prices);

        System.out.println("最大利润：" +result);
    }
    public static int maxprofit(int[] prices){
        int max= 0;
        int min = prices[0];
        for (int i = 0; i < prices.length; i++) {
            if (prices[i] < min){
                min = prices[i];
            }
            System.out.println(
                    "第" + i +"天购入价格最低为：" + min
            );
            int current = prices[i] - min;
            if (current > max){
                max = current;
            }
        }

        return max;
    }
}
