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

    public static void main(String[] args) {
        MaxProfit sol = new MaxProfit();
        System.out.println(sol.maxProfit(new int[]{7, 1, 5, 3, 6, 4})); // 5
        System.out.println(sol.maxProfit(new int[]{7, 6, 4, 3, 1}));    // 0（只跌不涨）
        System.out.println(sol.maxProfit(new int[]{2, 4, 1}));          // 2
    }
}
