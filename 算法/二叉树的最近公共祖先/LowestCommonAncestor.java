
class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    TreeNode(int x) {
        val = x;
    }
}

 class LowestCommonAncestor {

    /**
     * 求二叉树中 p 和 q 的最近公共祖先（后序遍历递归）
     *
     * 对每个节点，先递归查询左右子树是否包含 p 或 q，
     * 再根据返回结果判断当前节点是否为 LCA。
     */
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {

        // 当前节点为空：此路径上没有 p 或 q
        if (root == null) return null;

        // 当前节点就是 p 或 q 本身，直接返回（节点可以是自身的祖先）
        if (root == p || root == q) return root;

        // 递归查询左、右子树
        TreeNode left  = lowestCommonAncestor(root.left,  p, q);
        TreeNode right = lowestCommonAncestor(root.right, p, q);

        // left 和 right 都不为空 → p、q 分列两侧 → 当前节点就是 LCA
        if (left != null && right != null) return root;

        // 只有一侧不为空，则 LCA 在那一侧；两侧都为空则返回 null
        return left != null ? left : right;
    }

    public static void main(String[] args) {
        //        3
        //       / \
        //      5   1
        //     / \ / \
        //    6  2 0  8
        //      / \
        //     7   4
        TreeNode n3 = new TreeNode(3);
        TreeNode n5 = new TreeNode(5);
        TreeNode n1 = new TreeNode(1);
        TreeNode n6 = new TreeNode(6);
        TreeNode n2 = new TreeNode(2);
        TreeNode n0 = new TreeNode(0);
        TreeNode n8 = new TreeNode(8);
        TreeNode n7 = new TreeNode(7);
        TreeNode n4 = new TreeNode(4);

        n3.left = n5; n3.right = n1;
        n5.left = n6; n5.right = n2;
        n1.left = n0; n1.right = n8;
        n2.left = n7; n2.right = n4;

        LowestCommonAncestor sol = new LowestCommonAncestor();

        // 示例1：p=5, q=1 → LCA=3（分列根节点两侧）
        System.out.println("示例1: " + sol.lowestCommonAncestor(n3, n5, n1).val); // 3

        // 示例2：p=5, q=4 → LCA=5（p 本身就是 q 的祖先）
        System.out.println("示例2: " + sol.lowestCommonAncestor(n3, n5, n4).val); // 5

        // 示例3：p=6, q=4 → LCA=5（都在左子树，汇聚于节点5）
        System.out.println("示例3: " + sol.lowestCommonAncestor(n3, n6, n4).val); // 5
    }
}
