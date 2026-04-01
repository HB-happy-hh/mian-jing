class LongestCommonSubsequence {
    public static int longestCommonSubsequence(String text1, String text2) {
        int m = text1.length();
        int n = text2.length();

        // dp[i][j] 表示 text1 的前 i 个字符 与 text2 的前 j 个字符 的最长公共子序列长度
        // 多开一行一列（第0行、第0列），表示空串，初始值默认为 0
        int[][] dp = new int[m + 1][n + 1];

        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {

                // 注意：dp 下标从 1 开始，对应字符串下标要减 1
                if (text1.charAt(i - 1) == text2.charAt(j - 1)) {
                    // 当前字符相等：公共子序列长度在之前的基础上 +1
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                } else {
                    // 当前字符不相等：text1 退一步 or text2 退一步，取较大值
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }

        // 右下角就是整个字符串的最长公共子序列长度
        return dp[m][n];
    }

    public static void main(String[] args) {
        // LongestCommonSubsequence sol = new LongestCommonSubsequence();

        // 示例1：text1="abcde", text2="ace"  →  3（公共子序列 "ace"）
        String a = "abfjsik";
        String b = "abfjiewinvdsn";
        System.out.println(longestCommonSubsequence(a, b));
        System.out.println("示例1: " + longestCommonSubsequence("abcde", "ace"));

        // 示例2：text1="abc", text2="abc"  →  3（公共子序列 "abc"）
        System.out.println("示例2: " + longestCommonSubsequence("abc", "abc"));

        // 示例3：text1="abc", text2="def"  →  0（无公共子序列）
        System.out.println("示例3: " + longestCommonSubsequence("abc", "def"));
    }
}
