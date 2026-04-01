import java.util.*;

// ══════════════════════════════════════════════════════
// LeetCode 103. 二叉树的锯齿形层序遍历
//
// 给你二叉树的根节点 root，返回其节点值的锯齿形层序遍历。
// 即先从左往右，再从右往左进行下一层遍历，以此类推，层与层之间交替进行。
//
// 示例：
//       3
//      / \
//     9  20
//        / \
//       15   7
// 输出：[[3],[20,9],[15,7]]
//
// ══════════════════════════════════════════════════════
// 思路：BFS 层序遍历 + 奇偶翻转
//
// 在普通层序遍历基础上，用 leftToRight 标记当前层方向：
//   奇数层（第1、3层...）→ 从左到右，addLast 追加到尾
//   偶数层（第2、4层...）→ 从右到左，addFirst 插入到头
//
// 入队顺序始终"先左后右"不变，只改收集节点的方式。
// LinkedList 支持 addFirst/addLast，切换方向只需一个布尔值。
//
// 时间 O(n)  空间 O(n)
// ══════════════════════════════════════════════════════

class TreeNode {
    int val;
    TreeNode left, right;
    TreeNode(int val) { this.val = val; }
}

class Solution {
    public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        if (root == null) return result;

        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        boolean leftToRight = true;   // true = 从左到右

        while (!queue.isEmpty()) {
            int size = queue.size();
            LinkedList<Integer> level = new LinkedList<>();

            for (int i = 0; i < size; i++) {
                TreeNode node = queue.poll();

                if (leftToRight) {
                    level.addLast(node.val);   // 正向：追加到尾
                } else {
                    level.addFirst(node.val);  // 反向：插入到头
                }

                if (node.left  != null) queue.offer(node.left);
                if (node.right != null) queue.offer(node.right);
            }

            result.add(level);
            leftToRight = !leftToRight;  // 每层翻转方向
        }

        return result;
    }
}

// ─── 测试 ────────────────────────────────────────────
class Main {
    public static void main(String[] args) {
        Solution sol = new Solution();

        // 示例1：[3,9,20,null,null,15,7] → [[3],[20,9],[15,7]]
        TreeNode root1 = new TreeNode(3);
        root1.left  = new TreeNode(9);
        root1.right = new TreeNode(20);
        root1.right.left  = new TreeNode(15);
        root1.right.right = new TreeNode(7);
        System.out.println(sol.zigzagLevelOrder(root1));
        // 期望：[[3], [20, 9], [15, 7]]

        // 示例2：[1] → [[1]]
        TreeNode root2 = new TreeNode(1);
        System.out.println(sol.zigzagLevelOrder(root2));

        // 示例3：[] → []
        System.out.println(sol.zigzagLevelOrder(null));
    }
}
