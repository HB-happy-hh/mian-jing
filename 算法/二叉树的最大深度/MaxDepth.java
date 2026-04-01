import java.util.LinkedList;
import java.util.Queue;

/**
 * 二叉树的最大深度  LeetCode 104
 *
 * 题目：给定一个二叉树 root，返回其最大深度。
 *       最大深度是从根节点到最远叶子节点的最长路径上的节点数。
 *
 * 示例：
 *        3
 *       / \
 *      9  20
 *         / \
 *        15   7
 *
 *   最大深度 = 3
 *
 * 方法一：DFS 递归（后序遍历）
 * 方法二：BFS 迭代（层序遍历计层数）
 */
public class MaxDepth {

    static class TreeNode {
        int val;
        TreeNode left, right;
        TreeNode(int val) { this.val = val; }
    }

    // ─────────────────────────────────────────────
    // 方法一：DFS 递归（推荐，最简洁）
    //
    // 当前节点的深度 = max(左子树深度, 右子树深度) + 1
    //
    //   depth(3) = max(depth(9), depth(20)) + 1
    //            = max(1, 2) + 1 = 3
    //
    // 时间 O(n)  空间 O(h)，h 为树高（递归栈）
    // ─────────────────────────────────────────────
    public int maxDepth(TreeNode root) {
        if (root == null) return 0; // 空节点深度为 0

        int leftDepth  = maxDepth(root.left);  // 左子树最大深度
        int rightDepth = maxDepth(root.right); // 右子树最大深度

        return Math.max(leftDepth, rightDepth) + 1; // 取较大值加上当前层
    }

    // ─────────────────────────────────────────────
    // 方法二：BFS 层序遍历
    //
    // 用队列逐层遍历，每遍历完一层 depth++
    //
    //   层1: [3]
    //   层2: [9, 20]
    //   层3: [15, 7]
    //   → 共 3 层
    //
    // 时间 O(n)  空间 O(n)（队列最多存一层节点）
    // ─────────────────────────────────────────────
    public int maxDepthBFS(TreeNode root) {
        if (root == null) return 0;

        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        int depth = 0;

        while (!queue.isEmpty()) {
            int size = queue.size(); // 当前层的节点数
            depth++;

            for (int i = 0; i < size; i++) {
                TreeNode node = queue.poll();
                if (node.left  != null) queue.offer(node.left);
                if (node.right != null) queue.offer(node.right);
            }
        }
        return depth;
    }

    public static void main(String[] args) {
        MaxDepth sol = new MaxDepth();

        //      3
        //     / \
        //    9  20
        //       / \
        //      15   7
        TreeNode root = new TreeNode(3);
        root.left  = new TreeNode(9);
        root.right = new TreeNode(20);
        root.right.left  = new TreeNode(15);
        root.right.right = new TreeNode(7);

        System.out.println(sol.maxDepth(root));    // 3
        System.out.println(sol.maxDepthBFS(root)); // 3

        // 只有根节点
        System.out.println(sol.maxDepth(new TreeNode(1))); // 1

        // 空树
        System.out.println(sol.maxDepth(null)); // 0
    }
}
