/**
 * 括号生成  LeetCode 22
 *
 * 题目：数字 n 代表生成括号的对数，设计一个函数，
 *       用于能够生成所有可能的并且有效的括号组合。
 *
 * 示例：n=3 → ["((()))","(()())","(())()","()(())","()()()"]
 *
 * 核心约束（剪枝条件）：
 *   ① 左括号随时可以放，只要用掉的左括号数 open < n
 *   ② 右括号只有在 close < open 时才能放（保证有左括号可以匹配）
 *   ③ open == n && close == n 时，一条完整路径结束
 *
 * 方法一：回溯（DFS）—— 最经典，面试首选
 * 方法二：动态规划  —— 利用 f(i) 由 f(0..i-1) 组合而来
 * ─────────────────────────────────────────────────
 */
import java.util.ArrayList;
import java.util.List;

public class GenerateParentheses {

    // ─────────────────────────────────────────────
    // 方法一：回溯（DFS + 剪枝）
    //
    // 把构造过程想象成一棵决策树：
    //   每一步要么加 '('，要么加 ')'
    //   用 open（已放左括号数）和 close（已放右括号数）剪掉无效分支
    //
    //                          ""
    //                         /
    //                        "("          open=1, close=0
    //                      /     \
    //                  "(("       "()"     open=2/1, close=0/1
    //                 /   \         \
    //              "((("  "(()"    "()()"  ...
    //              ...
    //
    // 时间复杂度：O(4^n / sqrt(n))，即第 n 个卡特兰数 * n
    // 空间复杂度：O(n)  递归栈深度最大 2n
    // ─────────────────────────────────────────────
    public List<String> generateParenthesis(int n) {
        List<String> result = new ArrayList<>();
        backtrack(result, new StringBuilder(), 0, 0, n);
        return result;
    }

    private void backtrack(List<String> result, StringBuilder current,
                           int open, int close, int n) {
        // 终止条件：左右括号都放满了
        if (current.length() == 2 * n) {
            result.add(current.toString());
            return;
        }

        // 分支 1：放左括号（只要左括号还没放满）
        if (open < n) {
            current.append('(');
            backtrack(result, current, open + 1, close, n);
            current.deleteCharAt(current.length() - 1); // 撤销
        }

        // 分支 2：放右括号（只有右括号数 < 左括号数时才合法）
        if (close < open) {
            current.append(')');
            backtrack(result, current, open, close + 1, n);
            current.deleteCharAt(current.length() - 1); // 撤销
        }
    }

    // ─────────────────────────────────────────────
    // 方法二：动态规划（卡特兰数递推）
    //
    // 核心思路：对于 n 对括号，第一个字符一定是 '('，
    //   设其对应的 ')' 在位置 2k（0-based），则：
    //   - 括号内部  有 k 对括号，方案数 = f(k)
    //   - ')' 右侧   有 n-1-k 对括号，方案数 = f(n-1-k)
    //
    //   f(n) = ∑ f(k) × f(n-1-k)，k 从 0 到 n-1
    //
    // 时间复杂度：O(4^n / sqrt(n))
    // 空间复杂度：O(4^n / sqrt(n))  存储所有结果字符串
    // ─────────────────────────────────────────────
    public List<String> generateParenthesisDP(int n) {
        // dp[i] 存储 i 对括号的所有合法组合
        List<List<String>> dp = new ArrayList<>();
        dp.add(List.of(""));          // dp[0] = [""]（空串，0 对括号）

        for (int i = 1; i <= n; i++) {
            List<String> cur = new ArrayList<>();
            for (int k = 0; k < i; k++) {
                // 枚举"第一个 '(' 匹配的 ')'"的位置
                // 内部 k 对括号 × 右侧 (i-1-k) 对括号
                for (String inner : dp.get(k)) {
                    for (String outer : dp.get(i - 1 - k)) {
                        cur.add("(" + inner + ")" + outer);
                    }
                }
            }
            dp.add(cur);
        }
        return dp.get(n);
    }

    // ─────────────────────────────────────────────
    // 验证：检查括号串是否合法（用于测试）
    // ─────────────────────────────────────────────
    private boolean isValid(String s) {
        int balance = 0;
        for (char c : s.toCharArray()) {
            if (c == '(') balance++;
            else balance--;
            if (balance < 0) return false;
        }
        return balance == 0;
    }

    public static void main(String[] args) {
        GenerateParentheses sol = new GenerateParentheses();

        for (int n = 1; n <= 4; n++) {
            List<String> res1 = sol.generateParenthesis(n);
            List<String> res2 = sol.generateParenthesisDP(n);

            // 验证所有结果都合法
            boolean allValid = res1.stream().allMatch(sol::isValid);
            System.out.printf("n=%d → 回溯: %d种  DP: %d种  全部合法: %b%n",
                n, res1.size(), res2.size(), allValid);
            if (n <= 3) {
                System.out.println("  " + res1);
            }
        }
    }
}
