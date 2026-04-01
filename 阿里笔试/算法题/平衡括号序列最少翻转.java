import java.util.*;
import java.io.*;

/**
 * 题目：平衡括号序列最少翻转
 *
 * 给定一个只由 '(' 和 ')' 组成、长度为 n（偶数）的字符串 s。
 * 每次操作可选择一个下标 i（1<=i<=n），将 s[i] 的类型镜像翻转：
 *   '(' → ')'，')' → '('
 * 请计算使 s 变为平衡括号序列所需的最少操作次数，
 * 并输出任意一组达到最少次数的操作下标序列。
 *
 * 输入：T 组数据，每组第一行 n，第二行字符串 s
 * 输出：每组输出两行：第一行 k（最少操作数），第二行 k 个操作位置（1-indexed，k=0 则第二行留空）
 *
 * 示例：
 * 输入：
 *   3
 *   2
 *   )(
 *   4
 *   ()()
 *   4
 *   ))((
 * 输出：
 *   2
 *   1 2
 *   0
 *
 *   2
 *   1 4
 *
 * ======================== 解题思路 ========================
 *
 * 贪心两阶段处理：
 *
 * 【阶段一】从左到右扫描，维护余量 balance（'(' 数 - ')' 数）。
 *   当 balance==0 遇到 ')'：此 ')' 必须翻转为 '('，否则前缀余量变负（不合法）。
 *   翻转后 balance 从 -1 变为 +1（净 +2）。记录此位置。
 *
 * 【阶段二】阶段一结束后，balance 为多余的 '(' 数量（偶数）。
 *   需翻转 balance/2 个多余的 '(' 为 ')'。
 *   从右往左扫描，找最右边的"未匹配的 '('"依次翻转。
 *   （"未匹配的 '('"：从右向左扫描时 counter==0 时遇到的 '('）
 *
 * 【为什么最优】
 *   - 阶段一的 a 次翻转是必须的：每个导致前缀余量变负的 ')' 只能靠翻转自身解决
 *   - 阶段二的 balance/2 次翻转：翻转最右边的未匹配 '('，
 *     其前缀余量恰好等于当前剩余 balance >= 2，翻转后仍 >= 0，结构安全
 *   - 总翻转数 = a + balance/2，已是理论最小
 *
 * 时间复杂度：O(n log n)，n 总和 <= 2×10^5
 */
public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine().trim());
        StringBuilder sb = new StringBuilder();

        Solution solution = new Solution();
        for (int i = 0; i < T; i++) {
            int n = Integer.parseInt(br.readLine().trim());
            String s = br.readLine().trim();
            List<Integer> flips = solution.solve(n, s);

            sb.append(flips.size()).append('\n');
            for (int j = 0; j < flips.size(); j++) {
                if (j > 0) sb.append(' ');
                sb.append(flips.get(j));
            }
            sb.append('\n');
        }
        System.out.print(sb);
    }
}

class Solution {
    public List<Integer> solve(int n, String s) {
        char[] arr = s.toCharArray();
        List<Integer> flips = new ArrayList<>();
        int balance = 0;

        // 阶段一：从左到右，翻转导致余量为负的 ')'
        // 当 balance==0 遇到 ')'，若不翻转则前缀余量变负，必须翻转
        for (int i = 0; i < n; i++) {
            if (arr[i] == '(') {
                balance++;
            } else {
                if (balance == 0) {
                    flips.add(i + 1);   // 记录翻转位置（1-indexed）
                    arr[i] = '(';       // 标记为已翻转
                    balance++;          // ')' 变 '(' 后余量 +1（从-1变+1，净+2）
                } else {
                    balance--;          // 正常匹配，余量 -1
                }
            }
        }

        // 阶段二：从右到左，翻转 balance/2 个最右边的未匹配 '('
        // 未匹配的 '(' 定义：从右往左扫描时，counter==0 时遇到的 '('
        // 安全性：最右边未匹配 '(' 的前缀余量 = 当前剩余 balance >= 2，翻转后仍 >= 0
        int needed = balance / 2;
        int counter = 0;
        for (int i = n - 1; i >= 0 && needed > 0; i--) {
            if (arr[i] == ')') {
                counter++;              // 从右向左遇 ')'，等待左边的 '(' 来匹配
            } else {
                if (counter > 0) {
                    counter--;          // 该 '(' 与右边某 ')' 配对，已匹配
                } else {
                    flips.add(i + 1);  // counter==0，该 '(' 无右侧 ')' 匹配，翻转
                    needed--;
                }
            }
        }

        Collections.sort(flips);        // 按位置升序排列
        return flips;
    }
}
