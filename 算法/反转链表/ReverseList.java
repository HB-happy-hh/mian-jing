/**
 * 反转链表  LeetCode 206
 *
 * 题目：给你单链表的头节点 head，请你反转链表，并返回反转后的链表。
 *
 * 示例：1 → 2 → 3 → 4 → 5  →  5 → 4 → 3 → 2 → 1
 *
 * 方法一：迭代（推荐，O(n) 时间，O(1) 空间）
 * 方法二：递归（O(n) 时间，O(n) 栈空间）
 */
public class ReverseList {

    static class ListNode {
        int val;
        ListNode next;
        ListNode(int val) { this.val = val; }
    }

    // ─────────────────────────────────────────────
    // 方法一：迭代
    //
    // 用三个指针 prev / curr / next 逐步反转每条边
    //
    //  初始：null  1 → 2 → 3
    //         ↑prev ↑curr
    //
    //  第1步：null ← 1   2 → 3
    //              ↑prev ↑curr
    //
    //  第2步：null ← 1 ← 2   3
    //                  ↑prev ↑curr
    //  ...以此类推
    // ─────────────────────────────────────────────
    public ListNode reverseList(ListNode head) {
        ListNode prev = null;
        ListNode curr = head;

        while (curr != null) {
            ListNode next = curr.next; // 暂存下一个节点，防止断链后丢失
            curr.next = prev;          // 反转当前节点的指针
            prev = curr;               // prev 前进
            curr = next;               // curr 前进
        }

        return prev; // prev 最终停在原链表末尾，即新链表的头
    }

    // ─────────────────────────────────────────────
    // 方法二：递归
    //
    // 递归到链表末尾，回溯时逐步反转指针
    //
    //  reverseList(1→2→3→4→5)
    //    └─ reverseList(2→3→4→5)
    //         └─ ...
    //              └─ reverseList(5) → 返回 5（新头）
    //         回溯：5.next = 4，4.next = null ... 依此类推
    // ─────────────────────────────────────────────
    public ListNode reverseListRecursive(ListNode head) {
        if (head == null || head.next == null) return head; // 递归终止：到达末尾

        ListNode newHead = reverseListRecursive(head.next); // 递归反转后面部分
        head.next.next = head; // 让后一个节点指向当前节点（反转这条边）
        head.next = null;      // 断开当前节点原来的 next，防止成环

        return newHead; // 新头节点一路往上传递
    }

    public static void main(String[] args) {
        ReverseList sol = new ReverseList();

        // 构建链表 1→2→3→4→5
        ListNode head = new ListNode(1);
        head.next = new ListNode(2);
        head.next.next = new ListNode(3);
        head.next.next.next = new ListNode(4);
        head.next.next.next.next = new ListNode(5);

        ListNode result = sol.reverseList(head);
        StringBuilder sb = new StringBuilder();
        while (result != null) {
            sb.append(result.val);
            if (result.next != null) sb.append(" → ");
            result = result.next;
        }
        System.out.println(sb); // 5 → 4 → 3 → 2 → 1
    }
}
