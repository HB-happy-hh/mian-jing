import java.util.*;

/**
 * 题目：AWS Server Computational Power（最小花费使数组非递减）
 *
 * 题意：
 *   给你一排服务器的算力值，每次操作可以选一段连续的服务器统一加 x，花费是 x。
 *   目标：让整个数组变成非递减的，求总花费最小是多少。
 *
 * 关键结论：
 *   答案 = 所有相邻"下降量"之和
 *   即：sum of max(0, power[i-1] - power[i]) for i from 1 to n-1
 *
 * 为什么？
 *   每一处"下降"都必须被填平，且不同位置的下降互相独立，每处至少花费下降量。
 *   通过把操作区间延伸到右边，可以做到恰好只花费每处的下降量，不产生新的下降。
 *
 * 时间复杂度：O(n)
 * 空间复杂度：O(1)
 *
 * 示例：
 *   输入：[3, 4, 1, 6, 2]
 *   4→1 下降3，6→2 下降4
 *   输出：7
 *
 * 注意：用 long 防止溢出（n最大1e5，值最大1e9，总和可能超过int范围）
 */

class Solution2 {
    public long findMinimumSum(List<Integer> power) {
        long sum = 0;
        for (int i = 1; i < power.size(); i++) {
            // 如果当前值比前一个小，说明出现了"下降"，累加下降量
            if (power.get(i) < power.get(i - 1)) {
                sum += power.get(i - 1) - power.get(i);
            }
        }
        return sum;
    }
}

public class 题目2_ServerPower {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();
        List<Integer> power = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            power.add(sc.nextInt());
        }

        Solution2 solution = new Solution2();
        System.out.println(solution.findMinimumSum(power));
    }
}
