import java.util.PriorityQueue;

/**
 * 数组中第K个最大元素  LeetCode 215
 *
 * 题目：给定整数数组 nums 和整数 k，返回数组中第 k 个最大的元素。
 *       注意：是排序后第 k 大，不是第 k 个不同的元素。
 *
 * 示例：[3,2,1,5,6,4]，k=2  →  5
 *       [3,2,3,1,2,4,5,5,6]，k=4  →  4
 *
 * 方法一：小顶堆（推荐，O(n logk) 时间，O(k) 空间）
 * 方法二：快速选择（O(n) 平均，O(1) 空间，但最坏 O(n²)）
 */
public class FindKthLargest {

    // ─────────────────────────────────────────────
    // 方法一：小顶堆（大小固定为 k）
    //
    // 维护一个大小为 k 的小顶堆：
    //   - 堆满后，新元素比堆顶大才入堆（弹出最小的，保留 k 个最大）
    //   - 遍历完后，堆顶就是第 k 大的元素
    //
    //   例：[3,2,1,5,6,4]，k=2
    //   堆：[3] → [2,3] → [2,3]（1<2跳过）→ [3,5]（5>2弹2）
    //       → [5,6]（6>3弹3）→ [5,6]（4<5跳过）
    //   堆顶 = 5，即第2大 ✓
    // ─────────────────────────────────────────────
    public int findKthLargest(int[] nums, int k) {
        PriorityQueue<Integer> minHeap = new PriorityQueue<>(); // 默认小顶堆

        for (int num : nums) {
            minHeap.offer(num);
            if (minHeap.size() > k) {
                minHeap.poll(); // 弹出最小的，维持堆大小为 k
            }
        }
        return minHeap.peek(); // 堆顶即第 k 大
    }

    // ─────────────────────────────────────────────
    // 方法二：快速选择（基于快排的 partition）
    //
    // 快排每次 partition 后，pivot 落到最终位置
    // 第 k 大 = 从右往左第 k 个 = 下标 n-k
    //
    // 如果 pivot 下标：
    //   == n-k：找到，直接返回
    //   >  n-k：在左半部分找
    //   <  n-k：在右半部分找
    //
    // 平均 O(n)，最坏 O(n²)（可用随机化 pivot 优化）
    // ─────────────────────────────────────────────
    public int findKthLargestQuickSelect(int[] nums, int k) {
        int target = nums.length - k; // 第k大 = 升序排列后下标 n-k 的元素
        return quickSelect(nums, 0, nums.length - 1, target);
    }

    private int quickSelect(int[] nums, int lo, int hi, int target) {
        int pivotIdx = partition(nums, lo, hi);

        if (pivotIdx == target) return nums[pivotIdx];
        else if (pivotIdx > target) return quickSelect(nums, lo, pivotIdx - 1, target);
        else return quickSelect(nums, pivotIdx + 1, hi, target);
    }

    // 以 nums[hi] 为 pivot，将小于 pivot 的放左边，大于的放右边
    private int partition(int[] nums, int lo, int hi) {
        int pivot = nums[hi];
        int i = lo; // i 指向下一个小于 pivot 的元素放置位置

        for (int j = lo; j < hi; j++) {
            if (nums[j] <= pivot) {
                swap(nums, i, j);
                i++;
            }
        }
        swap(nums, i, hi); // pivot 放到最终位置
        return i;
    }

    private void swap(int[] nums, int a, int b) {
        int tmp = nums[a]; nums[a] = nums[b]; nums[b] = tmp;
    }

    public static void main(String[] args) {
        FindKthLargest sol = new FindKthLargest();

        System.out.println(sol.findKthLargest(new int[]{3,2,1,5,6,4}, 2));        // 5
        System.out.println(sol.findKthLargest(new int[]{3,2,3,1,2,4,5,5,6}, 4));  // 4

        System.out.println(sol.findKthLargestQuickSelect(new int[]{3,2,1,5,6,4}, 2));       // 5
        System.out.println(sol.findKthLargestQuickSelect(new int[]{3,2,3,1,2,4,5,5,6}, 4)); // 4
    }
}
