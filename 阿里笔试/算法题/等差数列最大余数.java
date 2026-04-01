import java.util.*;
import java.io.*;

/**
 * 题目：等差数列最大余数
 *
 * 给定无穷项等差数列 a_i = a0 + i*d (i >= 0) 和正整数 m，
 * 定义余数为 a_i mod m（取值范围 [0, m)）。
 * 请计算在所有 i >= 0 中，a_i mod m 的最大可能值，并输出该最大值。
 *
 * 输入：T 组测试数据，每组 a0, d, m（0 <= a0, d <= 1e18，1 <= m <= 1e18）
 * 输出：每组输出一行，表示能得到的最大值
 *
 * 示例：
 * 输入：
 *   3
 *   5 4 7
 *   3 10 6
 *   100 100 100
 * 输出：
 *   6
 *   5
 *   0
 *
 * ======================== 解题思路 ========================
 *
 * 设 g = gcd(d, m)
 *
 * 集合 {k*d mod m | k >= 0} = {0, g, 2g, ..., m-g}（g 的所有倍数）
 *
 * 因此可达余数集合为：
 *   {(a0 + j*g) mod m | j >= 0}
 *   = [0, m) 内所有与 a0 模 g 同余的数
 *   = {a0%g, a0%g+g, a0%g+2g, ...}
 *
 * 最大值 = m - g + (a0 % g)
 *
 * 特殊情况：
 *   d=0 时，g=gcd(0,m)=m，答案 = m-m+(a0%m) = a0%m ✓
 *
 * 验证示例：
 *   a0=5, d=4, m=7：g=1, r=0, 答案=7-1+0=6 ✓
 *   a0=3, d=10, m=6：g=2, r=1, 答案=6-2+1=5 ✓
 *   a0=100, d=100, m=100：g=100, r=0, 答案=0 ✓
 *
 * 时间复杂度：O(log m) 每组，总体 O(T log m)
 */
public class Main {
    public static void main(String[] args) throws IOException {
        // BufferedReader 加速读取，避免 Scanner 在大数据量下超时
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine().trim());

        // StringBuilder 批量收集输出，最后一次性打印
        StringBuilder sb = new StringBuilder();

        Solution solution = new Solution();
        for (int i = 0; i < T; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            long a0 = Long.parseLong(st.nextToken()); // 等差数列首项
            long d  = Long.parseLong(st.nextToken()); // 等差数列公差
            long m  = Long.parseLong(st.nextToken()); // 模数

            sb.append(solution.maxRemainder(a0, d, m)).append('\n');
        }
        System.out.print(sb);
    }
}

class Solution {
    /**
     * 求等差数列 a_i = a0 + i*d (i >= 0) 中，a_i mod m 的最大可能值
     * 公式：m - gcd(d, m) + (a0 % gcd(d, m))
     */
    public long maxRemainder(long a0, long d, long m) {
        long g = gcd(d, m);  // 公差与模数的最大公约数，决定余数集合的"步长"
        long r = a0 % g;     // a0 对 g 取余，是余数集合中每个元素对 g 的固定余数
        return m - g + r;    // [0,m) 内最大的与 a0 模 g 同余的数
    }

    /**
     * 辗转相除法（欧几里得算法）求最大公约数
     * 时间复杂度 O(log(min(a,b)))
     * 注意：gcd(0, m) = m，由循环自然处理
     */
    private long gcd(long a, long b) {
        while (b != 0) {
            long t = b;
            b = a % b;
            a = t;
        }
        return a;
    }
}
