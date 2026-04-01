/**
 * 接雨水  LeetCode 42
 *
 * 题目：给定 n 个非负整数表示每个宽度为 1 的柱子的高度图，
 *       计算按此排列的柱子，下雨之后能接多少雨水。
 *
 * 示例：[0,1,0,2,1,0,1,3,2,1,2,1]  →  6
 *
 * 核心公式：每个位置能接的水 = min(左边最高柱, 右边最高柱) - 当前高度
 *
 * 方法一：预处理数组（O(n) 时间 O(n) 空间）—— 最直观
 * 方法二：双指针   （O(n) 时间 O(1) 空间）—— 方法一的空间优化
 * 方法三：单调栈   （O(n) 时间 O(n) 空间）—— 横向计算凹槽积水
 * ─────────────────────────────────────────────────
 */
public class Trap {

    // ─────────────────────────────────────────────
    // 方法一：预处理数组（动态规划）
    //
    // 先把每个位置的"左边最高"和"右边最高"全部预算好存进数组，
    // 再一次遍历用公式累加。
    // ─────────────────────────────────────────────
    public int trapDP(int[] height) {
        int n = height.length;
        if (n == 0) return 0;

        int[] leftMax = new int[n];
        leftMax[0] = height[0];
        for (int i = 1; i < n; i++) {
            leftMax[i] = Math.max(leftMax[i - 1], height[i]);
        }

        int[] rightMax = new int[n];
        rightMax[n - 1] = height[n - 1];
        for (int i = n - 2; i >= 0; i--) {
            rightMax[i] = Math.max(rightMax[i + 1], height[i]);
        }

        int water = 0;
        for (int i = 0; i < n; i++) {
            water += Math.min(leftMax[i], rightMax[i]) - height[i];
        }
        return water;
    }

    // ─────────────────────────────────────────────
    // 方法二：双指针（方法一的空间优化）
    //
    // 不把 leftMax/rightMax 整个数组存下来，
    // 改用两个变量边走边更新，关键推论：
    //   height[left] <= height[right]
    //   → rightMax >= height[right] >= height[left]
    //   → left 位置水量 = leftMax - height[left]（rightMax 必然 >= leftMax）
    //   → 直接计算，left 右移；右侧同理
    // ─────────────────────────────────────────────
    public int trap(int[] height) {
        int left = 0, right = height.length - 1;
        int leftMax = 0, rightMax = 0;
        int water = 0;

        while (left < right) {
            if (height[left] <= height[right]) {
                // 左边更矮，左侧水量可以确定
                if (height[left] >= leftMax) {
                    leftMax = height[left]; // 更新左侧最大值，此处积不了水
                } else {
                    water += leftMax - height[left]; // 能积水
                }
                left++;
            } else {
                // 右边更矮，右侧水量可以确定
                if (height[right] >= rightMax) {
                    rightMax = height[right]; // 更新右侧最大值
                } else {
                    water += rightMax - height[right]; // 能积水
                }
                right--;
            }
        }
        return water;
    }

    // ─────────────────────────────────────────────
    // 方法三：单调栈
    //
    // 用栈维护一个递减序列的下标
    // 当遇到比栈顶高的柱子时，说明形成了凹槽，可以积水
    //
    //   凹槽示意：
    //   left_wall  bottom  right_wall
    //      ↑          ↑        ↑
    //    stack[-2]  stack[-1]  当前柱子
    //
    //   积水高度 = min(left_wall, right_wall) - bottom
    //   积水宽度 = right_wall 下标 - left_wall 下标 - 1
    // ─────────────────────────────────────────────
    public int trapStack(int[] height) {
        java.util.Deque<Integer> stack = new java.util.ArrayDeque<>();
        int water = 0;

        for (int i = 0; i < height.length; i++) {
            // 当前柱子比栈顶高，形成凹槽
            while (!stack.isEmpty() && height[i] > height[stack.peek()]) {
                int bottom = stack.pop();                  // 凹槽底部
                if (stack.isEmpty()) break;                // 左边没有墙，跳过
                int left = stack.peek();                   // 左边的墙

                int h = Math.min(height[left], height[i]) - height[bottom]; // 积水高度
                int w = i - left - 1;                      // 积水宽度
                water += h * w;
            }
            stack.push(i);
        }
        return water;
    }

    public static void main(String[] args) {
        Trap sol = new Trap();
        int[][] cases = {
            {0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1}, // 期望 6
            {4, 2, 0, 3, 2, 5},                     // 期望 9
            {3, 0, 2, 0, 4},                         // 期望 7
        };
        for (int[] h : cases) {
            System.out.printf("DP=%d  双指针=%d  单调栈=%d%n",
                sol.trapDP(h), sol.trap(h), sol.trapStack(h));
        }
    }
}
