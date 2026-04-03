package 最大子数组和;
/**
 * LeetCode 53. 最大子数组和
 * 给你一个整数数组 nums，请你找出一个具有最大和的连续子数组（子数组最少包含一个元素），返回其最大和。
 *
 * 思路：动态规划（Kadane's Algorithm）
 * - 定义 dp[i]：以 nums[i] 结尾的连续子数组的最大和
 * - 转移方程：dp[i] = max(nums[i], dp[i-1] + nums[i])
 *   - 若 dp[i-1] < 0，带上前面只会更小，不如从 nums[i] 重新开始
 *   - 若 dp[i-1] >= 0，接上前面的子数组能让和更大
 * - 用滚动变量优化空间，只保留 curSum（上一个 dp 值）
 * - 遍历过程中维护全局最大值 maxSum
 *
 * 时间复杂度：O(n)
 * 空间复杂度：O(1)
 */
public class MaxSubArray {
// 方法一
    class Solution {
        public int maxSubArray(int[] nums) {
    
            int maxnum = nums[0];
            int[] dp = new int[nums.length];
            dp[0] = nums[0];
    
            for(int i= 1;i<nums.length; i++){
                dp[i] = Math.max(nums[i],dp[i-1] + nums[i]);
                maxnum = Math.max(dp[i],maxnum);
            }
            return maxnum;
        }
    }

    // 方法二
    public static int maxSubArray(int[] nums) {
        int curSum = nums[0];
        int maxSum = nums[0];

        for (int i = 1; i < nums.length; i++) {
            curSum = Math.max(nums[i], curSum + nums[i]);
            maxSum = Math.max(maxSum, curSum);
        }

        return maxSum;
    }

    public static void main(String[] args) {
        // MaxSubArray solution = new MaxSubArray();

        // 示例1: nums = [-2,1,-3,4,-1,2,1,-5,4]
        // 期望输出: 6  （子数组 [4,-1,2,1]）
        int[] nums1 = {-2, 1, -3, 4, -1, 2, 1, -5, 4};
        System.out.println(maxSubArray(nums1));

        // 示例2: nums = [1]
        // 期望输出: 1
        System.out.println(maxSubArray(new int[]{1}));

        // 示例3: nums = [5,4,-1,7,8]
        // 期望输出: 23
        System.out.println(maxSubArray(new int[]{5, 4, -1, 7, 8}));
    }
}
