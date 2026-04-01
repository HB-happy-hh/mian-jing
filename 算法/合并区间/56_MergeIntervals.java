import java.util.*;

/**
 * LeetCode 56. 合并区间
 *
 * 思路：排序 + 贪心合并
 *  1. 按区间起始值升序排序，确保能重叠的区间一定相邻
 *  2. 遍历排序后的区间，与结果列表末尾区间比较：
 *     - 有重叠（next[0] <= cur[1]）→ 合并，终点取 max
 *     - 无重叠                     → 直接追加到结果
 *
 * 时间复杂度：O(n log n)  排序主导
 * 空间复杂度：O(n)        结果列表
 */
class Solution {
    public int[][] merge(int[][] intervals) {

        // 按每个区间的起始值从小到大排序
        // (a, b) -> a[0] - b[0] 是比较器：
        //   返回负数 → a 排前；返回正数 → b 排前；返回 0 → 不变
        // 排序后，能重叠的区间一定相邻，只需线性扫描一遍
        Arrays.sort(intervals, (a, b) -> a[0] - b[0]);

        // 存放最终合并结果的列表
        List<int[]> result = new ArrayList<>();

        for (int[] cur : intervals) {
            // 取结果列表中最后一个区间（若列表为空则为 null）
            int[] last = result.isEmpty() ? null : result.get(result.size() - 1);

            // 判断 cur 与 last 是否重叠：
            //   cur[0] > last[1] → cur 的起点在 last 终点右侧，无重叠
            //   cur[0] <= last[1] → 有重叠（端点相等也算重叠，如 [1,4] 和 [4,5]）
            if (last == null || last[1] < cur[0]) {
                // 无重叠：cur 作为新区间加入结果
                result.add(cur);
            } else {
                // 有重叠：把 last 的终点扩展到两者中较大的那个
                // 必须取 max，防止 cur 被 last 完全包含的情况（如 [1,10] 和 [2,3]）
                last[1] = Math.max(last[1], cur[1]);
            }
        }

        // 将 List<int[]> 转成题目要求的 int[][] 返回
        return result.toArray(new int[0][]);
    }
}

// ─── 测试 ───────────────────────────────────────────
class Main {
    public static void main(String[] args) {
        Solution sol = new Solution();

        // 示例 1：[[1,3],[2,6],[8,10],[15,18]] → [[1,6],[8,10],[15,18]]
        print(sol.merge(new int[][]{{1,3},{2,6},{8,10},{15,18}}));

        // 示例 2：[[1,4],[4,5]] → [[1,5]]（端点相邻也算重叠）
        print(sol.merge(new int[][]{{1,4},{4,5}}));

        // 示例 3：[[4,7],[1,4]] → [[1,7]]（输入未排序）
        print(sol.merge(new int[][]{{4,7},{1,4}}));

        // 嵌套：[[1,10],[2,3]] → [[1,10]]（后区间被完全包含）
        print(sol.merge(new int[][]{{1,10},{2,3}}));
    }

    static void print(int[][] res) {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < res.length; i++) {
            sb.append(Arrays.toString(res[i]));
            if (i < res.length - 1) sb.append(", ");
        }
        sb.append("]");
        System.out.println(sb);
    }
}
