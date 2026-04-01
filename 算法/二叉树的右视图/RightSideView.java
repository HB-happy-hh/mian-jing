import java.util.*;

/**
 * LeetCode 199. 二叉树的右视图
 * 给定一个二叉树的根节点 root，想象自己站在它的右侧，
 * 按照从顶部到底部的顺序，返回从右侧所能看到的节点值。
 *
 * 思路：BFS 层序遍历，取每层最后一个节点
 * ─────────────────────────────────────────
 * 站在右侧看树，每一层只能看到最右边的那个节点。
 * 因此对树做逐层 BFS，每层遍历完毕时，
 * 把该层 "最后出队" 的节点值加入结果即可。
 *
 * 关键点：
 * 1. 入队顺序：先左子节点、再右子节点
 *    → 同一层中右节点排在队尾，保证最后出队的就是最右节点
 * 2. 判断是否是当层最后一个节点：i == size - 1
 *
 * 举例（示例1）：root = [1, 2, 3, null, 5, null, 4]
 *        1          ← 第 0 层，只有 1，右视图看到 1
 *       / \
 *      2   3        ← 第 1 层，最右是 3，右视图看到 3
 *       \   \
 *        5   4      ← 第 2 层，最右是 4，右视图看到 4
 * 输出：[1, 3, 4]
 *
 * 时间复杂度：O(n)，每个节点入队/出队各一次
 * 空间复杂度：O(n)，队列最多同时存储最宽一层的节点（最坏 n/2）
 */
public class RightSideView {

    static class TreeNode {
        int val;
        TreeNode left, right;
        TreeNode(int val) { this.val = val; }
    }

    public List<Integer> rightSideView(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        if (root == null) return result;

        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);                          // 根节点入队，开始 BFS

        while (!queue.isEmpty()) {
            int size = queue.size();                // 当前层的节点总数（快照）

            for (int i = 0; i < size; i++) {
                TreeNode node = queue.poll();       // 取出当前节点

                // 只记录每层的最后一个节点（即最右侧可见节点）
                if (i == size - 1) {
                    result.add(node.val);
                }

                // 先入左子节点，再入右子节点
                // 这样同层中右节点始终排在左节点后面，最后出队
                if (node.left != null)  queue.offer(node.left);
                if (node.right != null) queue.offer(node.right);
            }
        }

        return result;
    }

    public static void main(String[] args) {
        RightSideView solution = new RightSideView();

        // 示例1: root = [1,2,3,null,5,null,4]
        //        1
        //       / \
        //      2   3
        //       \   \
        //        5   4
        // 期望输出: [1,3,4]
        TreeNode root1 = new TreeNode(1);
        root1.left  = new TreeNode(2);
        root1.right = new TreeNode(3);
        root1.left.right  = new TreeNode(5);
        root1.right.right = new TreeNode(4);
        System.out.println(solution.rightSideView(root1)); // [1, 3, 4]

        // 示例2: root = [1,2,3,4,null,null,null,5]
        //        1
        //       / \
        //      2   3
        //     /
        //    4
        //   /
        //  5
        // 期望输出: [1,3,4,5]
        TreeNode root2 = new TreeNode(1);
        root2.left  = new TreeNode(2);
        root2.right = new TreeNode(3);
        root2.left.left = new TreeNode(4);
        root2.left.left.left = new TreeNode(5);
        System.out.println(solution.rightSideView(root2)); // [1, 3, 4, 5]

        // 示例3: 只有根节点
        // 期望输出: [1]
        TreeNode root3 = new TreeNode(1);
        System.out.println(solution.rightSideView(root3)); // [1]

        // 示例4: 空树
        // 期望输出: []
        System.out.println(solution.rightSideView(null));  // []
    }
}
