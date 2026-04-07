// ══════════════════════════════════════════════════════
// LeetCode 160. 相交链表
//
// 给你两个单链表的头节点 headA 和 headB，找出并返回
// 两个链表相交的起始节点。若不存在交点，返回 null。
// ══════════════════════════════════════════════════════

class ListNode {
    int val;
    ListNode next;
    ListNode(int x) { val = x; }
}

// ══════════════════════════════════════════════════════
// 方法一：哈希表  时间 O(m+n)  空间 O(m)
//
// 1. 遍历链表 A，将所有节点存入 HashSet
// 2. 遍历链表 B，若当前节点已在 HashSet 中 → 即为交点
// ══════════════════════════════════════════════════════
class SolutionHashSet {
    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        java.util.Set<ListNode> visited = new java.util.HashSet<>();

        ListNode cur = headA;
        while (cur != null) {
            visited.add(cur);
            cur = cur.next;
        }

        cur = headB;
        while (cur != null) {
            if (visited.contains(cur)) return cur;
            cur = cur.next;
        }

        return null;
    }
}

// ══════════════════════════════════════════════════════
// 方法二：双指针  时间 O(m+n)  空间 O(1)  ← 最优解
//
// 设链表 A 长 a，链表 B 长 b，公共尾段长 c：
//   pA 走完 A 后接着走 B：总路程 = a + b
//   pB 走完 B 后接着走 A：总路程 = b + a
// 两者路程相等，若有交点则必在交点处相遇；
// 若无交点，最终两指针同时变为 null（a-c + b-c = b-c + a-c）。
// ══════════════════════════════════════════════════════
class Solution {
    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        ListNode pA = headA, pB = headB;

        // pA 与 pB 不相等时持续移动
        // 走完自己的链表后，切换到对方链表的头部
        while (pA != pB) {
            if (pA == null) {
                pA = headB;
            } else {
                pA = pA.next;
            }
            if (pB == null) {
                pB = headA;
            } else {
                pB = pB.next;
            }
        }

        // pA == pB：要么是交点，要么都是 null（无交点）
        return pA;
    }
}

// ─── 测试 ────────────────────────────────────────────
class Main {
    public static void main(String[] args) {
        Solution sol = new Solution();

        // 示例 1：A = [4,1,8,4,5]  B = [5,6,1,8,4,5]  交点 val=8
        // 构造公共尾段 [8 -> 4 -> 5]
        ListNode common = new ListNode(8);
        common.next = new ListNode(4);
        common.next.next = new ListNode(5);

        ListNode headA = new ListNode(4);
        headA.next = new ListNode(1);
        headA.next.next = common;

        ListNode headB = new ListNode(5);
        headB.next = new ListNode(6);
        headB.next.next = new ListNode(1);
        headB.next.next.next = common;

        ListNode res = sol.getIntersectionNode(headA, headB);
        System.out.println("示例1 交点 val = " + (res != null ? res.val : "null")); // 8

        // 示例 2：不相交
        ListNode a2 = new ListNode(2);
        a2.next = new ListNode(6);
        a2.next.next = new ListNode(4);

        ListNode b2 = new ListNode(1);
        b2.next = new ListNode(5);

        ListNode res2 = sol.getIntersectionNode(a2, b2);
        System.out.println("示例2 交点 val = " + (res2 != null ? res2.val : "null")); // null
    }
}
