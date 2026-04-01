import java.util.*;

/**
 * LeetCode 102. 二叉树的层序遍历
 * 给你二叉树的根节点 root，返回其节点值的层序遍历（逐层地，从左到右访问所有节点）
 *
 * 思路：BFS（广度优先搜索）
 * - 用队列存储每一层的节点
 * - 每次循环开始时，queue.size() 即为当前层的节点数
 * - 取出当前层所有节点，收集值，并将其子节点加入队列（供下一层使用）
 *
 * 时间复杂度：O(n)，每个节点访问一次
 * 空间复杂度：O(n)，队列最多存储最宽一层的节点
 */
public class LevelOrder {

    static class TreeNode {
        int val;
        TreeNode left, right;
        TreeNode(int val) { this.val = val; }
    }

    public List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        if (root == null) return result;

        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);

        while (!queue.isEmpty()) {
            int size = queue.size();         // 当前层节点数
            List<Integer> level = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                TreeNode node = queue.poll();
                level.add(node.val);
                if (node.left != null)  queue.offer(node.left);
                if (node.right != null) queue.offer(node.right);
            }
            result.add(level);
        }

        return result;
    }

    public static void main(String[] args) {
        LevelOrder solution = new LevelOrder();

        // 示例1: root = [3,9,20,null,null,15,7]
        // 期望输出: [[3],[9,20],[15,7]]
        TreeNode root1 = new TreeNode(3);
        root1.left = new TreeNode(9);
        root1.right = new TreeNode(20);
        root1.right.left = new TreeNode(15);
        root1.right.right = new TreeNode(7);
        System.out.println(solution.levelOrder(root1));

        // 示例2: root = [1]
        // 期望输出: [[1]]
        TreeNode root2 = new TreeNode(1);
        System.out.println(solution.levelOrder(root2));

        // 示例3: root = []
        // 期望输出: []
        System.out.println(solution.levelOrder(null));
    }
}
