package 有效的括号;

import java.util.Stack;

class Solution {

    public boolean isValid(String s) {

        // 用栈来追踪还未匹配的左括号
        // 每次遇到左括号就压栈；遇到右括号就检查栈顶是否匹配
        Stack<Character> stack = new Stack<>();

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            // ── 遇到左括号：直接压入栈中，等待后续的右括号来匹配 ──
            if (c == '(' || c == '[' || c == '{') {
                stack.push(c);

            // ── 遇到右括号：检查栈顶是否是对应的左括号 ──
            } else {

                // 如果栈为空，说明此右括号没有对应的左括号 → 非法
                if (stack.isEmpty()) {
                    return false;
                }

                char top = stack.pop(); // 取出栈顶左括号

                // 判断取出的左括号与当前右括号是否配对
                if (c == ')' && top != '(') return false;
                if (c == ']' && top != '[') return false;
                if (c == '}' && top != '{') return false;
            }
        }

        // 遍历结束后：
        //   栈为空  → 所有左括号都已匹配 → 有效
        //   栈不为空 → 还有未匹配的左括号 → 无效
        return stack.isEmpty();
    }

    public static void main(String[] args) {
        Solution sol = new Solution();

        // 示例1：() → true（最简单的匹配）
        System.out.println("示例1: " + sol.isValid("()"));       // true

        // 示例2：()[]{} → true（三对各自独立匹配）
        System.out.println("示例2: " + sol.isValid("()[]{}"));   // true

        // 示例3：(] → false（括号类型不匹配）
        System.out.println("示例3: " + sol.isValid("(]"));       // false

        // 示例4：([)] → false（嵌套顺序错误）
        System.out.println("示例4: " + sol.isValid("([)]"));     // false

        // 示例5：{[]} → true（正确嵌套）
        System.out.println("示例5: " + sol.isValid("{[]}"));     // true
    }
}
