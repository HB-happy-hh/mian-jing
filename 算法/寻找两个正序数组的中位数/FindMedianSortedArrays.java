/**
 * 寻找两个正序数组的中位数  LeetCode 4
 *
 * 题目：给定两个正序（从小到大）数组 nums1（长度 m）和 nums2（长度 n），
 *       返回这两个正序数组的中位数。要求时间复杂度 O(log(m+n))。
 *
 * ─────────────────────────────────────────────────────────
 * 核心思路：二分切割
 *
 * 中位数的本质 = 把合并后的有序数组分成左右两半，左半 ≤ 右半的最优切割点。
 *
 * 设在 nums1 取 i 个元素放左半，在 nums2 取 j 个元素放左半：
 *   i + j = half = (m + n + 1) / 2
 *
 * 切割合法的充要条件（交叉比较）：
 *   nums1[i-1] ≤ nums2[j]   （左1 ≤ 右2）
 *   nums2[j-1] ≤ nums1[i]   （左2 ≤ 右1）
 *
 * 二分在较短数组上枚举 i：
 *   nums1[i-1] > nums2[j] → i 太大，hi = i - 1
 *   nums2[j-1] > nums1[i] → i 太小，lo = i + 1
 *
 * 时间复杂度：O(log(min(m,n)))   空间复杂度：O(1)
 * ─────────────────────────────────────────────────────────
 */
public class FindMedianSortedArrays {

    // ─────────────────────────────────────────────
    // 方法一：二分切割（最优解）
    //
    // 始终对较短数组做二分，保证 j = half - i 始终 >= 0。
    // ─────────────────────────────────────────────
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        // 确保 nums1 是较短的那个，减少二分范围
        if (nums1.length > nums2.length) {
            return findMedianSortedArrays(nums2, nums1);
        }

        int m = nums1.length, n = nums2.length;
        int half = (m + n + 1) / 2; // 左半部分的总元素个数

        int lo = 0, hi = m;

        while (lo <= hi) {
            int i = lo + (hi - lo) / 2; // nums1 的切割位置：左半取 i 个
            int j = half - i;            // nums2 的切割位置：左半取 j 个

            // 边界处理：越界时用正负无穷代替
            int maxLeft1  = (i == 0) ? Integer.MIN_VALUE : nums1[i - 1];
            int minRight1 = (i == m) ? Integer.MAX_VALUE : nums1[i];
            int maxLeft2  = (j == 0) ? Integer.MIN_VALUE : nums2[j - 1];
            int minRight2 = (j == n) ? Integer.MAX_VALUE : nums2[j];

            if (maxLeft1 <= minRight2 && maxLeft2 <= minRight1) {
                // ✓ 切割合法：左半最大 ≤ 右半最小
                if ((m + n) % 2 == 1) {
                    // 奇数：中位数 = 左半最大值
                    return Math.max(maxLeft1, maxLeft2);
                } else {
                    // 偶数：中位数 = (左半最大 + 右半最小) / 2
                    return (Math.max(maxLeft1, maxLeft2)
                          + Math.min(minRight1, minRight2)) / 2.0;
                }
            } else if (maxLeft1 > minRight2) {
                // nums1 左半取多了，i 右移
                hi = i - 1;
            } else {
                // nums2 左半取多了（即 nums1 左半取少了），i 左移
                lo = i + 1;
            }
        }

        return 0.0; // 不会到达（题目保证输入合法）
    }

    // ─────────────────────────────────────────────
    // 方法二：合并后取中位数（O(m+n) 时间，O(m+n) 空间）
    // 直观，但不满足题目要求，面试前可先想清楚再优化
    // ─────────────────────────────────────────────
    public double findMedianBrute(int[] nums1, int[] nums2) {
        int m = nums1.length;
        int n = nums2.length;
        int total = m + n;
        int[] merged = new int[total];

        // 归并两个有序数组
        int i = 0; // nums1 的指针
        int j = 0; // nums2 的指针
        int k = 0; // merged 的写入位置

        while (i < m && j < n) {
            if (nums1[i] <= nums2[j]) {
                merged[k] = nums1[i];
                i++;
            } else {
                merged[k] = nums2[j];
                j++;
            }
            k++;
        }

        // 把剩余的直接复制进去
        while (i < m) {
            merged[k] = nums1[i];
            i++;
            k++;
        }
        while (j < n) {
            merged[k] = nums2[j];
            j++;
            k++;
        }

        // 取中位数
        if (total % 2 == 1) {
            // 奇数：正中间那个
            return merged[total / 2];
        } else {
            // 偶数：中间两个的平均值
            int left  = merged[total / 2 - 1];
            int right = merged[total / 2];
            return (left + right) / 2.0;
        }
    }

    public static void main(String[] args) {
        FindMedianSortedArrays sol = new FindMedianSortedArrays();

        int[][][] cases = {
            {{1,3},{2}},           // 期望 2.0
            {{1,2},{3,4}},         // 期望 2.5
            {{0,0},{0,0}},         // 期望 0.0
            {{},{1}},              // 期望 1.0
            {{2},{1,3,4,5,6}},     // 期望 3.5
        };
        double[] expected = {2.0, 2.5, 0.0, 1.0, 3.5};

        for (int t = 0; t < cases.length; t++) {
            double res = sol.findMedianSortedArrays(cases[t][0], cases[t][1]);
            System.out.printf("二分=%.1f  暴力=%.1f  期望=%.1f  %s%n",
                res,
                sol.findMedianBrute(cases[t][0], cases[t][1]),
                expected[t],
                res == expected[t] ? "✓" : "✗");
        }
    }
}
