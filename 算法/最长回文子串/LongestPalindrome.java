/**
 * 最长回文子串  LeetCode 5
 *
 * 题目：给你一个字符串 s，找到 s 中最长的回文子串。
 *
 * 示例："babad"  →  "bab"（或 "aba"）
 *       "cbbd"   →  "bb"
 *
 * 方法一：中心扩展法（推荐，O(n²) 时间，O(1) 空间）
 * 方法二：动态规划（O(n²) 时间，O(n²) 空间）
 *
 * ─────────────────────────────────────────────────
 * 方法一 核心思路：
 *
 *   回文串有两种形式：
 *     奇数长度：a b a  → 以单字符为中心向两侧扩展
 *     偶数长度：a b b a → 以相邻两字符为中心向两侧扩展
 *
 *   对每个位置（共 2n-1 个中心）向两侧扩展，
 *   记录最长的回文区间。
 * ─────────────────────────────────────────────────
 */
public class LongestPalindrome {

    // 记录最长回文子串的起始位置和长度
    private int start = 0, maxLen = 1;

    // ─────────────────────────────────────────────
    // 方法一：中心扩展法
    // ─────────────────────────────────────────────
    public String longestPalindrome(String s) {
        if (s.length() < 2) return s;

        for (int i = 0; i < s.length() - 1; i++) {
            expand(s, i, i);     // 奇数长度：以 i 为中心
            expand(s, i, i + 1); // 偶数长度：以 i 和 i+1 之间为中心
        }
        return s.substring(start, start + maxLen);
    }

    // 从 left/right 向两侧扩展，更新最长回文记录
    private void expand(String s, int left, int right) {
        while (left >= 0 && right < s.length()
                && s.charAt(left) == s.charAt(right)) {
            left--;  // 向左扩展
            right++; // 向右扩展
        }
        // 退出循环时 left+1 ~ right-1 是当前回文串
        int len = right - left - 1;
        if (len > maxLen) {
            maxLen = len;
            start = left + 1;
        }
    }

    // ─────────────────────────────────────────────
    // 方法二：动态规划
    //
    // dp[i][j] = true 表示 s[i..j] 是回文串
    //
    // 状态转移：
    //   s[i]==s[j] 且 dp[i+1][j-1]==true  →  dp[i][j]=true
    //   特殊：j-i <= 2 时（长度1或2），只需 s[i]==s[j]
    //
    // 必须按子串长度从小到大填表（短的先算，长的依赖短的）
    // ─────────────────────────────────────────────
    public String longestPalindromeDP(String s) {
        int n = s.length();
        boolean[][] dp = new boolean[n][n];
        int resStart = 0, resLen = 1;

        // 所有单字符都是回文
        for (int i = 0; i < n; i++) dp[i][i] = true;

        // 按子串长度枚举
        for (int len = 2; len <= n; len++) {
            for (int i = 0; i <= n - len; i++) {
                int j = i + len - 1; // 子串右端点

                if (s.charAt(i) == s.charAt(j)) {
                    // 长度为 2，或内部子串也是回文
                    if (len == 2 || dp[i + 1][j - 1]) {
                        dp[i][j] = true;
                        if (len > resLen) {
                            resLen = len;
                            resStart = i;
                        }
                    }
                }
            }
        }
        return s.substring(resStart, resStart + resLen);
    }

    public static void main(String[] args) {
        LongestPalindrome sol = new LongestPalindrome();

        System.out.println(sol.longestPalindrome("babad")); // bab
        sol = new LongestPalindrome();
        System.out.println(sol.longestPalindrome("cbbd"));  // bb
        sol = new LongestPalindrome();
        System.out.println(sol.longestPalindrome("a"));     // a
        sol = new LongestPalindrome();
        System.out.println(sol.longestPalindrome("racecar")); // racecar

        // DP 版本
        System.out.println(sol.longestPalindromeDP("babad")); // bab
        System.out.println(sol.longestPalindromeDP("cbbd"));  // bb
    }
}
