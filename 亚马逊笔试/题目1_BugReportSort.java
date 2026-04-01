import java.util.*;

/**
 * 题目：Amazon Bug Report Sort（按频率排序）
 *
 * 题意：
 *   给你一堆 bug 编号，按"重要性"排序输出。
 *   规则1：出现次数越少 → 越重要 → 排越前面
 *   规则2：次数一样时 → bug 编号越小 → 排越前面
 *
 * 思路：
 *   1. 用 HashMap 统计每个 bug 编号出现几次
 *   2. 用自定义排序规则：先按频率升序，再按编号升序
 *
 * 时间复杂度：O(n log n)
 * 空间复杂度：O(n)
 *
 * 示例：
 *   输入：[3, 1, 2, 2, 4]
 *   输出：1 3 4 2 2
 *   （1、3、4各出现1次，2出现2次；频率低的排前面，频率相同按编号升序）
 */

class Solution1 {
    public List<Integer> sortBugReportFrequencies(List<Integer> bugs) {

        // 第一步：统计每个 bug 编号出现几次
        Map<Integer, Integer> freq = new HashMap<>();
        for (int bug : bugs) {
            freq.put(bug, freq.getOrDefault(bug, 0) + 1);
        }

        // 第二步：自定义排序
        // - 频率小的排前面
        // - 频率相同时，编号小的排前面
        Collections.sort(bugs, (a, b) -> {
            int fa = freq.get(a);
            int fb = freq.get(b);
            if (fa != fb) return fa - fb;
            return a - b;
        });

        return bugs;
    }
}

public class 题目1_BugReportSort {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();
        List<Integer> bugs = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            bugs.add(sc.nextInt());
        }

        Solution1 solution = new Solution1();
        List<Integer> result = solution.sortBugReportFrequencies(bugs);

        StringBuilder sb = new StringBuilder();
        for (int bug : result) {
            sb.append(bug).append('\n');
        }
        System.out.print(sb);
    }
}
