import java.util.*;

// ══════════════════════════════════════════════════════
// 方法一：动态规划  时间 O(n²)  空间 O(n)
//
// 定义 dp[i] = 以 nums[i] 结尾的最长严格递增子序列长度
// 转移：对每个 j < i，若 nums[j] < nums[i]
//       则 dp[i] = max(dp[i], dp[j] + 1)
// 答案：max(dp[0..n-1])
// ══════════════════════════════════════════════════════
class SolutionDP {
    public int lengthOfLIS(int[] nums) {
        int n = nums.length;

        // dp[i]：以 nums[i] 结尾的最长递增子序列长度，初始都为 1
        int[] dp = new int[n];
        Arrays.fill(dp, 1);

        int ans = 1;

        for (int i = 1; i < n; i++) {
            // 向左扫描所有 j < i 的位置
            for (int j = 0; j < i; j++) {
                if (nums[j] < nums[i]) {
                    // nums[i] 可以接在 nums[j] 后面，更新 dp[i]
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }
            ans = Math.max(ans, dp[i]);
        }

        return ans;
    }
}

// ══════════════════════════════════════════════════════
// 方法二：贪心 + 二分查找  时间 O(n log n)  空间 O(n)
//
// 维护 tails 数组：tails[i] = 长度为 i+1 的递增子序列的最小末尾值
// 贪心策略：末尾值越小，后续越容易接上新元素
//
// 对每个 num：
//   二分查找 tails 中第一个 >= num 的位置 lo
//   tails[lo] = num（替换或追加）
//   若 lo == 当前长度，说明是追加，LIS 长度 +1
// ══════════════════════════════════════════════════════
class Solution {
    public int lengthOfLIS(int[] nums) {
        // tails[i] = 长度为 i+1 的递增子序列的最小末尾值
        int[] tails = new int[nums.length];
        int len = 0;   // tails 的有效长度，即当前 LIS 长度

        for (int num : nums) {
            // 二分查找：在 tails[0..len-1] 中找第一个 >= num 的位置
            int lo = 0, hi = len;
            while (lo < hi) {
                int mid = (lo + hi) / 2;
                if (tails[mid] < num) lo = mid + 1;
                else                  hi = mid;
            }

            // 替换（让那个长度的末尾值更小）或追加（LIS 变长）
            tails[lo] = num;
            if (lo == len) len++;
        }

        return len;
    }
}

// ─── 测试 ────────────────────────────────────────────
class LISMain {
    // public static void main(String[] args) {
        Solution sol = new Solution();

        // 示例1：[10,9,2,5,3,7,101,18] → 4（子序列 [2,3,7,101]）
        System.out.println(sol.lengthOfLIS(new int[]{10, 9, 2, 5, 3, 7, 101, 18}));

        // 示例2：[0,1,0,3,2,3] → 4
        System.out.println(sol.lengthOfLIS(new int[]{0, 1, 0, 3, 2, 3}));

        // 示例3：[7,7,7,7,7,7,7] → 1（全部相等，严格递增所以只能选 1 个）
        System.out.println(sol.lengthOfLIS(new int[]{7, 7, 7, 7, 7, 7, 7}));
    }
}
