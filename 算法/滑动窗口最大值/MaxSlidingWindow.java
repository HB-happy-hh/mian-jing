import java.util.ArrayDeque;
import java.util.Deque;
import java.util.PriorityQueue;

/**
 * LeetCode 239. 滑动窗口最大值
 * 给你一个整数数组 nums，有一个大小为 k 的滑动窗口从数组的最左侧移动到数组的最右侧。
 * 你只可以看到在滑动窗口内的 k 个数字。滑动窗口每次只向右移动一位。
 * 返回滑动窗口中的最大值。
 */
public class MaxSlidingWindow {

    // ==================== 暴力解法 O(n*k) ====================
    // 每次窗口滑动都重新遍历窗口内所有元素求最大值
    public int[] maxSlidingWindow_brute(int[] nums, int k) {
        int[] result = new int[nums.length - k + 1];
        int left = 0;
        int right = k; // 开区间，窗口为 [left, right-1]，共 k 个元素

        while (right <= nums.length) {
            int cur = Integer.MIN_VALUE;
            for (int i = left; i < right; i++) {
                cur = Math.max(nums[i], cur);
            }
            result[left] = cur;
            left++;
            right++;
        }

        return result;
    }

    // ==================== 最优解法 O(n) 单调队列 ====================
    // 维护一个单调递减的双端队列（存下标），队首始终是当前窗口最大值的下标
    // 核心思想：新元素入队前，把队尾所有比它小的元素弹出（既更老又更小，永远不会是答案）
    public int[] maxSlidingWindow_optimal(int[] nums, int k) {
        int n = nums.length;
        int[] result = new int[n - k + 1];
        Deque<Integer> deque = new ArrayDeque<>(); // 存下标

        for (int i = 0; i < n; i++) {
            // 队首元素已滑出窗口，移除
            while (!deque.isEmpty() && deque.peekFirst() < i - k + 1) {
                deque.pollFirst();
            }
            // 队尾所有比当前元素小的下标都弹出
            while (!deque.isEmpty() && nums[deque.peekLast()] < nums[i]) {
                deque.pollLast();
            }
            deque.offerLast(i);

            // 窗口形成后（i >= k-1），队首就是当前窗口最大值
            if (i >= k - 1) {
                result[i - k + 1] = nums[deque.peekFirst()];
            }
        }

        return result;
    }

    // ==================== 优先队列解法 O(n log n) ====================
    // 大根堆存 [值, 下标]，堆顶始终是堆中最大值
    // 堆里允许存在已滑出窗口的旧元素（懒删除），取结果时把堆顶过期元素弹掉
    public int[] maxSlidingWindow_pq(int[] nums, int k) {
        int n = nums.length;
        int[] result = new int[n - k + 1];
        // 大根堆：先按值降序，值相同按下标降序
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[0] != b[0] ? b[0] - a[0] : b[1] - a[1]);

        for (int i = 0; i < n; i++) {
            pq.offer(new int[]{nums[i], i});
            // 弹出所有已滑出窗口的堆顶元素（下标 < 窗口左边界）
            while (pq.peek()[1] < i - k + 1) {
                pq.poll();
            }
            if (i >= k - 1) {
                result[i - k + 1] = pq.peek()[0];
            }
        }

        return result;
    }

    // ==================== 测试 ====================
    public static void main(String[] args) {
        MaxSlidingWindow solution = new MaxSlidingWindow();

        int[] nums1 = {1, 3, -1, -3, 5, 3, 6, 7};
        int k1 = 3;
        // 期望输出：[3, 3, 5, 5, 6, 7]
        System.out.print("暴力解法: ");
        for (int v : solution.maxSlidingWindow_brute(nums1, k1)) System.out.print(v + " ");
        System.out.println();

        System.out.print("单调队列: ");
        for (int v : solution.maxSlidingWindow_optimal(nums1, k1)) System.out.print(v + " ");
        System.out.println();

        // 全负数测试
        int[] nums2 = {-3, -1, -2};
        int k2 = 2;
        // 期望输出：[-1, -1]
        System.out.print("全负数暴力: ");
        for (int v : solution.maxSlidingWindow_brute(nums2, k2)) System.out.print(v + " ");
        System.out.println();

        System.out.print("全负数单调队列: ");
        for (int v : solution.maxSlidingWindow_optimal(nums2, k2)) System.out.print(v + " ");
        System.out.println();

        System.out.print("优先队列:   ");
        for (int v : solution.maxSlidingWindow_pq(nums1, k1)) System.out.print(v + " ");
        System.out.println();

        System.out.print("全负数优先队列: ");
        for (int v : solution.maxSlidingWindow_pq(nums2, k2)) System.out.print(v + " ");
        System.out.println();
    }
}
