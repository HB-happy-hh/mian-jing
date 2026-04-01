import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 三数之和  LeetCode 15
 *
 * 题目：给你一个整数数组 nums，判断是否存在三元组 [nums[i], nums[j], nums[k]]
 *       满足 i != j、i != k、j != k，且 nums[i] + nums[j] + nums[k] == 0
 *       返回所有不重复的三元组。
 *
 * 示例：[-1, 0, 1, 2, -1, -4]  →  [[-1,-1,2], [-1,0,1]]
 *
 * 思路：排序 + 双指针
 *   1. 先排序，使重复元素相邻，方便去重
 *   2. 固定第一个数 nums[i]，用左右双指针在剩余部分找两数之和 = -nums[i]
 *   3. 找到后，左右指针跳过重复元素
 *
 * 时间复杂度：O(n²)  空间复杂度：O(1)（不算输出）
 */
public class ThreeSum {

    public List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        Arrays.sort(nums); // 排序是双指针去重的前提

        for (int i = 0; i < nums.length - 2; i++) {

            // 剪枝：最小的数都 > 0，三数之和必然 > 0，直接退出
            if (nums[i] > 0) break;

            // 去重：i > 0 时，若与上一个元素相同，跳过（避免结果重复）
            if (i > 0 && nums[i] == nums[i - 1]) continue;

            int left = i + 1;
            int right = nums.length - 1;
            int target = -nums[i]; // 转化为两数之和问题：找 left+right == target

            while (left < right) {
                int sum = nums[left] + nums[right];

                if (sum == target) {
                    result.add(Arrays.asList(nums[i], nums[left], nums[right]));

                    // 去重：跳过左指针后续重复元素
                    while (left < right && nums[left] == nums[left + 1]) left++;
                    // 去重：跳过右指针前续重复元素
                    while (left < right && nums[right] == nums[right - 1]) right--;

                    left++;  // 找下一组
                    right--;

                } else if (sum < target) {
                    left++;  // 和太小，左指针右移增大
                } else {
                    right--; // 和太大，右指针左移减小
                }
            }
        }
        return result;
    }

    public static void main(String[] args) {
        ThreeSum sol = new ThreeSum();
        System.out.println(sol.threeSum(new int[]{-1, 0, 1, 2, -1, -4}));
        // [[-1, -1, 2], [-1, 0, 1]]
        System.out.println(sol.threeSum(new int[]{0, 0, 0}));
        // [[0, 0, 0]]
        System.out.println(sol.threeSum(new int[]{1, 2, -2, -1}));
        // []
    }
}
