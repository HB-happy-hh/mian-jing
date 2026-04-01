import java.util.ArrayList;
import java.util.List;

/**
 * 全排列  LeetCode 46
 *
 * 题目：给定一个不含重复数字的数组 nums，返回其所有可能的全排列。
 *
 * 示例：[1, 2, 3]  →  [[1,2,3],[1,3,2],[2,1,3],[2,3,1],[3,1,2],[3,2,1]]
 *
 * 思路：回溯（Backtracking）
 *
 *   把问题看成一棵决策树，每层选一个还没用过的数：
 *
 *              []
 *           /   |   \
 *         [1]  [2]  [3]
 *         / \   / \   / \
 *       [1,2][1,3][2,1][2,3][3,1][3,2]
 *        |    |    |    |    |    |
 *      [1,2,3][1,3,2]... （叶子节点，收集结果）
 *
 *   关键步骤：
 *     1. 选择：将 nums[i] 加入当前路径，标记为已使用
 *     2. 递归：继续选下一个位置
 *     3. 撤销：回溯时将 nums[i] 从路径移除，标记为未使用（恢复现场）
 *
 * 时间复杂度：O(n × n!)   空间复杂度：O(n)（递归栈深度）
 */
public class Permutations {

    public List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        boolean[] used = new boolean[nums.length]; // 标记哪些数已被使用
        backtrack(nums, used, new ArrayList<>(), result);
        return result;
    }

    // ─────────────────────────────────────────────
    // 回溯核心方法
    //   path：当前已选择的数字路径
    //   used：标记每个数是否已在 path 中
    // ─────────────────────────────────────────────
    private void backtrack(int[] nums, boolean[] used,
                           List<Integer> path, List<List<Integer>> result) {

        // 终止条件：path 长度等于 nums 长度，一个完整排列产生
        if (path.size() == nums.length) {
            result.add(new ArrayList<>(path)); // 注意要拷贝，否则后续修改会影响结果
            return;
        }

        for (int i = 0; i < nums.length; i++) {
            if (used[i]) continue; // 已使用，跳过

            // ── 做选择 ──
            path.add(nums[i]);
            used[i] = true;

            // ── 递归下一层 ──
            backtrack(nums, used, path, result);

            // ── 撤销选择（回溯，恢复现场）──
            path.remove(path.size() - 1);
            used[i] = false;
        }
    }

    public static void main(String[] args) {
        Permutations sol = new Permutations();

        List<List<Integer>> res = sol.permute(new int[]{1, 2, 3});
        res.forEach(System.out::println);
        // [1, 2, 3]
        // [1, 3, 2]
        // [2, 1, 3]
        // [2, 3, 1]
        // [3, 1, 2]
        // [3, 2, 1]

        System.out.println(sol.permute(new int[]{1}).size()); // 1
    }
}
