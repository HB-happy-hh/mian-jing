/**
 * 买卖股票的最佳时机  LeetCode 121
 *
 * 题目：给定一个数组 prices，prices[i] 是第 i 天的股票价格。
 *       你只能买卖一次，求最大利润。如果不能盈利，返回 0。
 *
 * 示例：[7, 1, 5, 3, 6, 4]  →  5（第2天买入价1，第5天卖出价6）
 *
 * 思路：贪心 / 一次遍历
 *
 *   遍历过程中维护两个变量：
 *     minPrice：遇到过的最低买入价
 *     maxProfit：遇到过的最大利润
 *
 *   每天的最大利润 = 当天价格 - 历史最低价
 *   不断更新两者即可。
 *
 * 时间复杂度：O(n)   空间复杂度：O(1)
 */
public class MaxProfit {

    public int maxProfit(int[] prices) {
        int minPrice  = Integer.MAX_VALUE; // 历史最低买入价，初始为正无穷
        int maxProfit = 0;                 // 最大利润，初始为 0（不交易）

        for (int price : prices) {
            if (price < minPrice) {
                minPrice = price;          // 遇到更低价，更新买入点
            } else if (price - minPrice > maxProfit) {
                maxProfit = price - minPrice; // 今天卖出的利润更大，更新
            }
        }
        return maxProfit;
    }

    /**
     * DP 写法
     *
     * dp[i] 表示第 i 天卖出时能获得的最大利润
     * 转移方程：dp[i] = max(dp[i-1], prices[i] - minPrice)
     *   - dp[i-1]：今天不卖，利润沿用昨天的最大值
     *   - prices[i] - minPrice：今天卖出，利润 = 今日价 - 历史最低价
     *
     * 时间复杂度：O(n)   空间复杂度：O(n)
     */
    public int maxProfitDP(int[] prices) {
        int n = prices.length;
        int[] dp = new int[n];   // dp[0] = 0，第0天无法卖出，利润为0
        int minPrice = prices[0];

        for (int i = 1; i < n; i++) {
            minPrice = Math.min(minPrice, prices[i]);
            dp[i] = Math.max(dp[i - 1], prices[i] - minPrice);
        }
        return dp[n - 1];
    }

    public static void main(String[] args) {
        MaxProfit sol = new MaxProfit();
        int[] a = {7, 1, 5, 3, 6, 4};
        int[] b = {7, 6, 4, 3, 1};
        int[] c = {2, 4, 1};

        System.out.println("贪心：" + sol.maxProfit(a)   + "  DP：" + sol.maxProfitDP(a));  // 5  5
        System.out.println("贪心：" + sol.maxProfit(b)   + "  DP：" + sol.maxProfitDP(b));  // 0  0
        System.out.println("贪心：" + sol.maxProfit(c)   + "  DP：" + sol.maxProfitDP(c));  // 2  2
    }
}
