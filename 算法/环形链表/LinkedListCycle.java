/**
 * 环形链表  LeetCode 141 / 142
 *
 * 141 题目：给你一个链表，判断链表中是否有环。
 * 142 题目：如果有环，返回环的入口节点。
 *
 * 思路：Floyd 快慢指针（龟兔赛跑算法）
 *
 *   慢指针每次走 1 步，快指针每次走 2 步
 *   - 无环：快指针先到 null
 *   - 有环：快慢指针必然在环内相遇
 *
 *   找入口（142）的数学推导：
 *     设链表头到环入口距离为 a，
 *     环入口到相遇点距离为 b，
 *     相遇点到环入口距离为 c（即环长 = b+c）
 *
 *     相遇时：慢指针走了 a+b
 *             快指针走了 a+b + n*(b+c)
 *             快 = 慢 × 2  →  a = n*(b+c) - b = (n-1)(b+c) + c
 *
 *     所以：a = c（当 n=1 时）
 *     即：相遇后让一个指针回到 head，两个指针同速走，再次相遇即为入口。
 */
public class LinkedListCycle {

    static class ListNode {
        int val;
        ListNode next;
        ListNode(int val) { this.val = val; }
    }

    // ─────────────────────────────────────────────
    // LeetCode 141：判断是否有环
    // ─────────────────────────────────────────────
    public boolean hasCycle(ListNode head) {
        ListNode slow = head;
        ListNode fast = head;

        while (fast != null && fast.next != null) {
            slow = slow.next;       // 慢指针走 1 步
            fast = fast.next.next;  // 快指针走 2 步
            if (slow == fast) return true; // 相遇则有环
        }
        return false; // fast 到 null，无环
    }

    // ─────────────────────────────────────────────
    // LeetCode 142：返回环的入口节点
    // ─────────────────────────────────────────────
    public ListNode detectCycle(ListNode head) {
        ListNode slow = head;
        ListNode fast = head;

        // 第一阶段：找到相遇点
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast) break; // 相遇
        }

        // 无环
        if (fast == null || fast.next == null) return null;

        // 第二阶段：找环入口
        // 让 slow 回到 head，fast 留在相遇点
        // 两者同速（每次 1 步），再次相遇即为环入口
        slow = head;
        while (slow != fast) {
            slow = slow.next;
            fast = fast.next;
        }
        return slow; // 环的入口节点
    }

    public static void main(String[] args) {
        LinkedListCycle sol = new LinkedListCycle();

        // 构建有环链表：3 → 2 → 0 → -4 → (回到2)
        ListNode n1 = new ListNode(3);
        ListNode n2 = new ListNode(2);
        ListNode n3 = new ListNode(0);
        ListNode n4 = new ListNode(-4);
        n1.next = n2; n2.next = n3; n3.next = n4;
        n4.next = n2; // 形成环，入口是 n2（val=2）

        System.out.println("有环: " + sol.hasCycle(n1));           // true
        System.out.println("入口: " + sol.detectCycle(n1).val);    // 2

        // 无环链表：1 → 2
        ListNode a = new ListNode(1);
        a.next = new ListNode(2);
        System.out.println("有环: " + sol.hasCycle(a));            // false
        System.out.println("入口: " + sol.detectCycle(a));         // null
    }
}
