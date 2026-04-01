/**
 * 爬楼梯  LeetCode 70
 *
 * 题目：假设你正在爬楼梯，需要 n 阶才能到达楼顶。
 *       每次你可以爬 1 或 2 个台阶，有多少种不同的方法可以爬到楼顶？
 *
 * 示例：n=3  →  3（1+1+1 / 1+2 / 2+1）
 *
 * 核心思路：动态规划（本质是斐波那契数列）
 *
 *   到达第 n 阶只有两种来源：
 *     - 从第 n-1 阶爬 1 步上来
 *     - 从第 n-2 阶爬 2 步上来
 *
 *   所以 dp[n] = dp[n-1] + dp[n-2]
 *
 *   n:    1  2  3  4  5  6
 *   dp:   1  2  3  5  8  13
 */
public class ClimbStairs {

    // ─────────────────────────────────────────────
    // 方法一：动态规划（数组版）
    // 时间 O(n)  空间 O(n)
    // ─────────────────────────────────────────────
    public int climbStairsDP(int n) {
        if (n <= 2) return n;

        int[] dp = new int[n + 1];
        dp[1] = 1; // 1 阶只有 1 种走法
        dp[2] = 2; // 2 阶有 2 种走法（1+1 或 2）

        for (int i = 3; i <= n; i++) {
            dp[i] = dp[i - 1] + dp[i - 2]; // 状态转移方程
        }
        return dp[n];
    }

    // ─────────────────────────────────────────────
    // 方法二：空间优化（滚动变量）
    // 只需保存前两个状态，空间压缩到 O(1)
    //
    //   prev2 = dp[i-2]
    //   prev1 = dp[i-1]
    //   curr  = prev1 + prev2
    //   然后滚动：prev2=prev1, prev1=curr
    // ─────────────────────────────────────────────
    public int climbStairs(int n) {
        if (n <= 2) return n;

        int prev2 = 1; // dp[1]
        int prev1 = 2; // dp[2]

        for (int i = 3; i <= n; i++) {
            int curr = prev1 + prev2;
            prev2 = prev1;
            prev1 = curr;
        }
        return prev1;
    }

    public static void main(String[] args) {
        ClimbStairs sol = new ClimbStairs();
        System.out.println(sol.climbStairs(1));  // 1
        System.out.println(sol.climbStairs(2));  // 2
        System.out.println(sol.climbStairs(3));  // 3
        System.out.println(sol.climbStairs(5));  // 8
        System.out.println(sol.climbStairs(10)); // 89
    }
}
