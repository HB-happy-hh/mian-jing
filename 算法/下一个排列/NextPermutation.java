/**
 * LeetCode 31. 下一个排列
 *
 * 算法思路（三步法）：
 *  1. 从右往左找"拐点" i：第一个满足 nums[i] < nums[i+1] 的下标
 *  2. 从右往左找"替换者" j：第一个满足 nums[j] > nums[i] 的下标，交换 i 和 j
 *  3. 逆转 i+1 到末尾的部分（使后缀最小）
 *
 * 若找不到拐点（整体降序），直接整体逆转即可。
 *
 * 时间复杂度：O(n)
 * 空间复杂度：O(1)
 */
public class NextPermutation {

    public void nextPermutation(int[] nums) {
        int n = nums.length;

        // 第一步：从右往左找拐点 i（nums[i] < nums[i+1]）
        int i = n - 2;
        while (i >= 0 && nums[i] >= nums[i + 1]) {
            i--;
        }

        // 第二步：若找到拐点，从右往左找第一个比 nums[i] 大的元素，交换
        if (i >= 0) {
            int j = n - 1;
            while (nums[j] <= nums[i]) {
                j--;
            }
            swap(nums, i, j);
        }

        // 第三步：逆转 i+1 到末尾（此段一定是降序，逆转后变升序）
        reverse(nums, i + 1, n - 1);
    }

    private void swap(int[] nums, int i, int j) {
        int tmp = nums[i];
        nums[i] = nums[j];
        nums[j] = tmp;
    }

    private void reverse(int[] nums, int left, int right) {
        while (left < right) {
            swap(nums, left++, right--);
        }
    }

    // ==================== 测试 ====================
    public static void main(String[] args) {
        NextPermutation sol = new NextPermutation();

        int[][] cases = {
            {1, 2, 3},    // 期望: [1,3,2]
            {3, 2, 1},    // 期望: [1,2,3]
            {1, 1, 5},    // 期望: [1,5,1]
            {1},          // 期望: [1]
            {1, 5, 8, 4, 7, 6, 5, 3, 1}, // 期望: [1,5,8,5,1,3,4,6,7]
        };

        for (int[] nums : cases) {
            sol.nextPermutation(nums);
            System.out.print("[");
            for (int k = 0; k < nums.length; k++) {
                System.out.print(nums[k]);
                if (k < nums.length - 1) System.out.print(",");
            }
            System.out.println("]");
        }
    }
}
