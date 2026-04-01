import java.util.ArrayList;
import java.util.List;

/**
 * 杨辉三角  LeetCode 118
 *
 * 题目：给定非负整数 numRows，生成杨辉三角的前 numRows 行。
 *
 * 规律：
 *   - 每行首尾都是 1
 *   - 中间每个数 = 上一行同列 + 上一行前一列
 *
 *        row 0：[1]
 *        row 1：[1, 1]
 *        row 2：[1, 2, 1]        2 = 1 + 1
 *        row 3：[1, 3, 3, 1]     3 = 1 + 2, 3 = 2 + 1
 *        row 4：[1, 4, 6, 4, 1]  4 = 1 + 3, 6 = 3 + 3
 *
 * 时间复杂度：O(numRows²)   空间复杂度：O(1)（结果数组不计）
 */、



 public class Solution {
    public List<List<Integer>> generate(int numRows) {
        // 初始化动态规划数组
     Integer[][] dp = new Integer[numRows][];
        // 遍历每一行
    for (int i = 0; i < numRows; i++) {
        // 初始化当前行
        dp[i] = new Integer[i + 1];
        // 每一行的第一个和最后一个元素总是 1
        dp[i][0] = dp[i][i] = 1;
        // 计算中间元素
        for (int j = 1; j < i; j++) {
            // 中间元素等于上一行的相邻两个元素之和
            dp[i][j] = dp[i - 1][j - 1] + dp[i - 1][j];
        }
    }

    // 将动态规划数组转换为结果列表
    List<List<Integer>> result = new ArrayList<>();
    for (Integer[] row : dp) {
        result.add(Arrays.asList(row));
    }
    // 返回结果列表
    return result;
}







public class PascalTriangle {

    public List<List<Integer>> generate(int numRows) {
        List<List<Integer>> result = new ArrayList<>();

        for (int i = 0; i < numRows; i++) {
            List<Integer> row = new ArrayList<>();

            for (int j = 0; j <= i; j++) {
                if (j == 0 || j == i) {
                    row.add(1);  // 首尾固定为 1
                } else {
                    // 中间的数 = 上一行同列 + 上一行前一列
                    List<Integer> prev = result.get(i - 1);
                    row.add(prev.get(j - 1) + prev.get(j));
                }
            }
            result.add(row);
        }
        return result;
    }

    public static void main(String[] args) {
        PascalTriangle sol = new PascalTriangle();

        sol.generate(5).forEach(System.out::println);
        // [1]
        // [1, 1]
        // [1, 2, 1]
        // [1, 3, 3, 1]
        // [1, 4, 6, 4, 1]

        System.out.println(sol.generate(1)); // [[1]]
        System.out.println(sol.generate(0)); // []
    }
}
