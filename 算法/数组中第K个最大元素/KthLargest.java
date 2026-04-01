import java.util.Arrays;

/**
 * 215. 数组中的第 K 个最大元素  LeetCode 215
 *
 * 方法一：排序法
 *
 * 核心思路：
 *   把数组从小到大排序，第 K 大的元素就是排序后
 *   倒数第 K 个，即下标 nums.length - k 处的元素。
 *
 *   示例：nums = [3,2,1,5,6,4], k = 2
 *   排序后：[1,2,3,4,5,6]
 *                       ↑ 倒数第2个 = 下标 6-2=4 → 值为 5 ✓
 *
 * 时间复杂度：O(n log n)  — Arrays.sort 底层用双轴快速排序
 * 空间复杂度：O(log n)    — 排序递归栈
 *
 * 注意：题目要求 O(n)，排序法不满足，但写法最简单，
 *       适合理解题意后再进阶到快速选择（QuickSelect）。
 */
public class KthLargest {

    public int findKthLargest(int[] nums, int k) {

        // 第一步：对数组升序排序
        // 排序后最小值在下标 0，最大值在下标 n-1
        Arrays.sort(nums);

        // 第二步：直接取倒数第 k 个元素
        // 下标推导：第1大 → n-1，第2大 → n-2，第k大 → n-k
        return nums[nums.length - k];
    }

    // ─────────────────────────────────────────────
    // 测试
    // ─────────────────────────────────────────────
    public static void main(String[] args) {
        KthLargest sol = new KthLargest();

        // 示例1：[3,2,1,5,6,4], k=2  → 5
        System.out.println(sol.findKthLargest(new int[]{3, 2, 1, 5, 6, 4}, 2)); // 5

        // 示例2：[3,2,3,1,2,4,5,5,6], k=4  → 4
        System.out.println(sol.findKthLargest(new int[]{3, 2, 3, 1, 2, 4, 5, 5, 6}, 4)); // 4
    }
}
