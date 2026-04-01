import java.util.*;
import java.io.*;

/**
 * 题目：变根 LCA（换根最近公共祖先）
 *
 * 给定一棵由 n 个节点构成的树，处理 q 次查询。
 * 对于每次查询，给定三个节点 (r, u, v)：
 *   如果将节点 r 作为整棵树的根，那么节点 u 和 v 的最近公共祖先是哪个节点？
 *
 * 输入：T 组测试数据，每组 n, q；n-1 条边；q 次查询 (r, u, v)
 * 输出：每组输出 q 行，每行一个整数代表该次查询的 LCA
 *
 * 约束：1<=T<=2e5，1<=n,q<=2e5，单测试文件 n 之和、q 之和均<=2e5
 *
 * 示例（树结构：1为根，2,3为1的子节点，4,5为2的子节点，6,7为3的子节点）：
 * 查询 (1,4,7)=1，查询 (4,1,7)=1，查询 (2,4,5)=2，查询 (5,1,2)=2，查询 (6,4,7)=3
 *
 * ======================== 解题思路 ========================
 *
 * 【核心结论】
 * 以固定根（节点1）预处理 LCA，对于查询 (r, u, v)：
 *
 *   LCA_r(u, v) = 深度最大的节点，候选为：
 *     lca(u, v)，lca(u, r)，lca(v, r)
 *
 * 即三个候选节点中深度最大（离固定根最远）的那个即为答案。
 *
 * 【正确性直觉】
 * 换根后，u 和 v 的 LCA 是三条路径（u-v, u-r, v-r）在原树中 LCA 中最深的一个，
 * 因为最深的那个节点一定位于新根 r 到 u 和 r 到 v 的路径上，
 * 是 u 和 v 在新树结构中的共同祖先里距离叶子最近的。
 *
 * 【预处理】二进制提升（Binary Lifting）
 *   up[v][j] = v 的 2^j 级祖先
 *   预处理 O(n log n)，每次 LCA 查询 O(log n)
 *
 * 时间复杂度：O((n+q) log n) 每组测试，总体满足约束
 */
public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine().trim());
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < T; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int n = Integer.parseInt(st.nextToken());
            int q = Integer.parseInt(st.nextToken());

            // 建图
            List<List<Integer>> adj = new ArrayList<>();
            for (int j = 0; j <= n; j++) adj.add(new ArrayList<>());

            for (int j = 0; j < n - 1; j++) {
                st = new StringTokenizer(br.readLine());
                int x = Integer.parseInt(st.nextToken());
                int y = Integer.parseInt(st.nextToken());
                adj.get(x).add(y);
                adj.get(y).add(x);
            }

            Solution solution = new Solution(n, adj);

            for (int j = 0; j < q; j++) {
                st = new StringTokenizer(br.readLine());
                int r = Integer.parseInt(st.nextToken());
                int u = Integer.parseInt(st.nextToken());
                int v = Integer.parseInt(st.nextToken());
                sb.append(solution.query(r, u, v)).append('\n');
            }
        }
        System.out.print(sb);
    }
}

class Solution {
    private static final int MAXLOG = 18; // log2(2e5) < 18，足够表示所有祖先层级

    private int n;
    private int[] depth;       // depth[v] = 节点 v 到固定根（节点1）的深度
    private int[][] up;        // up[v][j] = 节点 v 的 2^j 级祖先

    public Solution(int n, List<List<Integer>> adj) {
        this.n = n;
        depth = new int[n + 1];
        up = new int[n + 1][MAXLOG];

        // BFS 从节点 1 出发，计算每个节点的深度和直接父节点
        int[] parent = new int[n + 1];
        parent[1] = 1;  // 根节点的父节点指向自身，防止二进制提升时越界
        boolean[] visited = new boolean[n + 1];
        Queue<Integer> queue = new LinkedList<>();
        queue.add(1);
        visited[1] = true;

        while (!queue.isEmpty()) {
            int v = queue.poll();
            for (int u : adj.get(v)) {
                if (!visited[u]) {
                    visited[u] = true;
                    depth[u] = depth[v] + 1;
                    parent[u] = v;
                    queue.add(u);
                }
            }
        }

        // 构建二进制提升表
        for (int v = 1; v <= n; v++) up[v][0] = parent[v]; // 直接父节点
        for (int j = 1; j < MAXLOG; j++) {
            for (int v = 1; v <= n; v++) {
                // v 的 2^j 级祖先 = v 的 2^(j-1) 级祖先的 2^(j-1) 级祖先
                up[v][j] = up[up[v][j - 1]][j - 1];
            }
        }
    }

    /**
     * 在固定根（节点1）下求 u 和 v 的 LCA
     * 步骤：
     *   1. 将较深节点提升到与另一节点同深度
     *   2. 若相等则返回（一个是另一个的祖先）
     *   3. 同步向上跳到 LCA 的子节点层，返回其父节点
     */
    private int lca(int u, int v) {
        // 保证 u 比 v 更深（或相同）
        if (depth[u] < depth[v]) { int t = u; u = v; v = t; }

        // 将 u 提升 diff 层，与 v 同深度
        int diff = depth[u] - depth[v];
        for (int j = 0; j < MAXLOG; j++) {
            if (((diff >> j) & 1) == 1) u = up[u][j];
        }
        if (u == v) return u; // u 已是 v 的祖先

        // 同步向上跳，找到 LCA 正下方的位置
        for (int j = MAXLOG - 1; j >= 0; j--) {
            if (up[u][j] != up[v][j]) {
                u = up[u][j];
                v = up[v][j];
            }
        }
        return up[u][0]; // LCA 是当前位置的父节点
    }

    /**
     * 以 r 为根时，u 和 v 的 LCA
     *
     * 结论：LCA_r(u,v) = 深度最大的节点，候选为 lca(u,v)、lca(u,r)、lca(v,r)
     *
     * 验证（以示例 Query5 为例）：r=6, u=4, v=7
     *   lca(4,7)=1, depth=0
     *   lca(4,6)=1, depth=0
     *   lca(7,6)=3, depth=1  ← 最深，答案=3 ✓
     */
    public int query(int r, int u, int v) {
        int a = lca(u, v); // 固定根下 u,v 的 LCA
        int b = lca(u, r); // 固定根下 u,r 的 LCA
        int c = lca(v, r); // 固定根下 v,r 的 LCA

        // 返回深度最大（离固定根最远，即换根后最接近叶子）的节点
        if (depth[a] >= depth[b] && depth[a] >= depth[c]) return a;
        if (depth[b] >= depth[c]) return b;
        return c;
    }
}
