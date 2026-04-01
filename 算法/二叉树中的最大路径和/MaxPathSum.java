/**
 * LeetCode 124. 二叉树中的最大路径和
 *
 * 路径定义：从树中任意节点出发，沿父子节点连接，到达任意节点的序列（不可重复经过同一节点）。
 * 路径和：路径上所有节点值之和。
 * 目标：返回整棵树所有路径中最大的路径和。
 *
 * ──────────────────────────────────────────────────────
 * 核心思路：后序遍历（DFS）+ 全局最大值记录
 * ──────────────────────────────────────────────────────
 *
 * 关键区分两个概念：
 *
 * 1. "单侧贡献值"（dfs 函数的返回值）
 *    - 以当前节点为起点，向下延伸到某一叶子的最大路径和
 *    - 只能选左子树或右子树其中一侧（因为父节点拼接后路径不能分叉）
 *    - 公式：gain(node) = node.val + max(gain(left), gain(right), 0)
 *      → 若子树贡献为负，不如不走，取 0（剪掉负收益分支）
 *
 * 2. "穿越当前节点的最优路径"（用于更新全局答案）
 *    - 路径可以同时经过左子树和右子树，形成一条完整弧线
 *    - 公式：left_gain + node.val + right_gain
 *    - 这个值不能向上返回（会变成分叉路径），只用来刷新全局最大值
 *
 * 举例（示例1）：root = [1, 2, 3]
 *        1
 *       / \
 *      2   3
 *  gain(2) = 2, gain(3) = 3
 *  穿越节点1的路径 = 2 + 1 + 3 = 6  ← 全局最大
 *  dfs(1) 向上返回 = 1 + max(2, 3) = 4（只能取一侧）
 *
 * 举例（示例2）：root = [-10, 9, 20, null, null, 15, 7]
 *       -10
 *       /  \
 *      9   20
 *         /  \
 *        15   7
 *  gain(9)=9, gain(15)=15, gain(7)=7
 *  穿越节点20的路径 = 15 + 20 + 7 = 42  ← 全局最大
 *
 * 时间复杂度：O(n)，每个节点恰好访问一次
 * 空间复杂度：O(h)，h 为树的高度，递归栈深度；最坏 O(n)（链状树）
 */
public class MaxPathSum {

    static class TreeNode {
        int val;
        TreeNode left, right;
        TreeNode(int val) { this.val = val; }
    }

    // 全局变量，记录所有路径中的最大路径和
    // 初始化为 Integer.MIN_VALUE，因为节点值可能全为负数
    private int maxSum = Integer.MIN_VALUE;

    public int maxPathSum(TreeNode root) {
        dfs(root);
        return maxSum;
    }

    /**
     * 后序 DFS：返回以 node 为起点向下延伸的最大单侧贡献值
     * 同时在内部用"左贡献 + 自身 + 右贡献"尝试更新全局最大值
     */
    private int dfs(TreeNode node) {
        if (node == null) return 0;

        // 递归计算左右子树的单侧贡献值
        // 若子树贡献为负，选择不走（取 0），避免拖累路径总和
        int leftGain  = Math.max(dfs(node.left),  0);
        int rightGain = Math.max(dfs(node.right), 0);

        // 以当前节点为"拱顶"的完整路径（左弧 + 节点 + 右弧）
        // 这条路径可以同时走左右两侧，但只用于更新全局答案，不能继续向上传递
        int pathThroughNode = leftGain + node.val + rightGain;
        maxSum = Math.max(maxSum, pathThroughNode);

        // 向父节点返回单侧最大贡献：只能选左或右其中一边
        return node.val + Math.max(leftGain, rightGain);
    }

    public static void main(String[] args) {
        // 示例1: root = [1,2,3]
        //     1
        //    / \
        //   2   3
        // 最优路径: 2 -> 1 -> 3，路径和 = 6
        MaxPathSum s1 = new MaxPathSum();
        TreeNode root1 = new TreeNode(1);
        root1.left  = new TreeNode(2);
        root1.right = new TreeNode(3);
        System.out.println(s1.maxPathSum(root1)); // 6

        // 示例2: root = [-10,9,20,null,null,15,7]
        //       -10
        //       /  \
        //      9   20
        //         /  \
        //        15   7
        // 最优路径: 15 -> 20 -> 7，路径和 = 42
        MaxPathSum s2 = new MaxPathSum();
        TreeNode root2 = new TreeNode(-10);
        root2.left  = new TreeNode(9);
        root2.right = new TreeNode(20);
        root2.right.left  = new TreeNode(15);
        root2.right.right = new TreeNode(7);
        System.out.println(s2.maxPathSum(root2)); // 42

        // 示例3: 全负数树 root = [-3]
        // 路径只能是单个节点，最大路径和 = -3
        MaxPathSum s3 = new MaxPathSum();
        TreeNode root3 = new TreeNode(-3);
        System.out.println(s3.maxPathSum(root3)); // -3

        // 示例4: root = [1,-2,-3,1,3,-2,null,-1]
        // 验证负值子树被正确剪掉
        //        1
        //       / \
        //     -2  -3
        //     / \   \
        //    1   3  -2
        //   /
        //  -1
        // 最优路径: 3 -> -2 -> 1（即右子1的路径），路径和 = 3
        MaxPathSum s4 = new MaxPathSum();
        TreeNode root4 = new TreeNode(1);
        root4.left  = new TreeNode(-2);
        root4.right = new TreeNode(-3);
        root4.left.left   = new TreeNode(1);
        root4.left.right  = new TreeNode(3);
        root4.right.right = new TreeNode(-2);
        root4.left.left.left = new TreeNode(-1);
        System.out.println(s4.maxPathSum(root4)); // 3
    }
}
