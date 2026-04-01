import java.util.*;

/**
 * LeetCode 19. 删除链表的倒数第 N 个节点
 * 给你一个链表，删除链表的倒数第 n 个节点，并且返回链表的头节点。
 *
 * 提供两种解法：
 *   方法一：先数长度，再走到前驱位置（两次遍历）
 *   方法二：快慢双指针，一次遍历
 *
 * 两种方法都借助「哨兵（dummy）节点」统一处理删除头节点的边界情况。
 */
public class RemoveNthFromEnd {

    static class ListNode {
        int val;
        ListNode next;
        ListNode(int val) { this.val = val; }
    }

    // ══════════════════════════════════════════════════════════════
    // 方法一：先数长度，再走到前驱位置（两次遍历）
    // ══════════════════════════════════════════════════════════════
    /**
     * 思路：
     *   1. 第一次遍历：统计链表总长度 count。
     *   2. 倒数第 n 个节点 = 正数第 (count - n + 1) 个节点。
     *      其前驱 = 正数第 (count - n) 个节点。
     *   3. 第二次遍历：从 dummy 出发走 (count - n) 步，停在前驱。
     *   4. pre.next = pre.next.next，跳过待删节点。
     *
     * 常见错误：循环次数写成 count+1-n（多走一步），导致删错节点。
     *   — pre 从 dummy（位置 0）出发，需走 count-n 步才停在前驱位置。
     *   — 若写 count+1-n，pre 会停在待删节点本身，pre.next 指向待删节点的后继，
     *     相当于删掉了待删节点的下一个。
     *
     * 图示（head=[1,2,3,4,5], n=2，待删节点=4，前驱=3）：
     *
     *   dummy → 1 → 2 → 3 → 4 → 5 → null
     *           走 count-n = 3 步后：
     *                       ↑pre   ↑待删
     *   pre.next = pre.next.next → 跳过 4
     *
     * 时间复杂度：O(L)，两次遍历
     * 空间复杂度：O(1)
     */
    public ListNode removeNthFromEnd_v1(ListNode head, int n) {
        ListNode dummy = new ListNode(0);
        dummy.next = head;

        // 第一次遍历：统计链表长度
        int count = 0;
        ListNode temp = head;
        while (temp != null) {
            count++;
            temp = temp.next;
        }

        // 第二次遍历：走 count-n 步，停在前驱节点
        ListNode pre = dummy;
        for (int i = 0; i < count - n; i++) {  // 注意：是 count-n，不是 count+1-n
            pre = pre.next;
        }

        // 跳过待删节点
        pre.next = pre.next.next;

        return dummy.next;
    }

    // ══════════════════════════════════════════════════════════════
    // 方法二：快慢双指针，一次遍历
    // ══════════════════════════════════════════════════════════════
    /**
     * 思路：
     *   让 fast 指针比 slow 指针提前走 n+1 步，
     *   之后两者同步前进，当 fast == null 时，
     *   slow 恰好停在待删节点的【前驱】位置。
     *
     * 为什么是 n+1 步而不是 n 步？
     *   — fast 提前 n   步 → fast.next==null 时，slow 在待删节点本身。
     *   — fast 提前 n+1 步 → fast==null 时，slow 在待删节点的前驱。  ✓
     *   （我们需要前驱才能执行 slow.next = slow.next.next）
     *
     * 图示（head=[1,2,3,4,5], n=2，待删节点=4，前驱=3）：
     *
     *   Step1：fast 从 dummy 出发，先走 n+1=3 步
     *   dummy → 1 → 2 → 3 → 4 → 5 → null
     *   ↑slow              ↑fast
     *
     *   Step2：fast 和 slow 同步走，直到 fast==null
     *   dummy → 1 → 2 → 3 → 4 → 5 → null
     *                   ↑slow           ↑fast(null)
     *
     *   slow.next = slow.next.next → 跳过节点 4
     *
     * 时间复杂度：O(L)，只遍历一次
     * 空间复杂度：O(1)
     */
    public ListNode removeNthFromEnd_v2(ListNode head, int n) {
        ListNode dummy = new ListNode(0);
        dummy.next = head;

        ListNode fast = dummy;
        ListNode slow = dummy;

        // fast 先走 n+1 步
        for (int i = 0; i <= n; i++) {
            fast = fast.next;
        }

        // fast 和 slow 同步前进，直到 fast 到达链表末尾
        while (fast != null) {
            fast = fast.next;
            slow = slow.next;
        }

        // slow 现在指向待删节点的前驱，跳过待删节点
        slow.next = slow.next.next;

        return dummy.next;
    }

    // ── 辅助：将数组构建为链表 ──
    private static ListNode build(int... vals) {
        ListNode dummy = new ListNode(0);
        ListNode cur = dummy;
        for (int v : vals) { cur.next = new ListNode(v); cur = cur.next; }
        return dummy.next;
    }

    // ── 辅助：将链表转为字符串 ──
    private static String print(ListNode head) {
        StringBuilder sb = new StringBuilder("[");
        while (head != null) {
            sb.append(head.val);
            if (head.next != null) sb.append(",");
            head = head.next;
        }
        return sb.append("]").toString();
    }

    public static void main(String[] args) {
        RemoveNthFromEnd sol = new RemoveNthFromEnd();

        System.out.println("══ 方法一：先数长度 ══");
        // 示例1: head=[1,2,3,4,5], n=2  →  删除倒数第2个（节点4），期望 [1,2,3,5]
        System.out.println(print(sol.removeNthFromEnd_v1(build(1,2,3,4,5), 2))); // [1,2,3,5]
        // 示例2: head=[1], n=1  →  删除唯一节点（头节点），期望 []
        System.out.println(print(sol.removeNthFromEnd_v1(build(1), 1)));          // []
        // 示例3: head=[1,2], n=1  →  删除尾节点，期望 [1]
        System.out.println(print(sol.removeNthFromEnd_v1(build(1,2), 1)));        // [1]
        // 额外: head=[1,2], n=2  →  删除头节点，期望 [2]
        System.out.println(print(sol.removeNthFromEnd_v1(build(1,2), 2)));        // [2]

        System.out.println("══ 方法二：快慢双指针 ══");
        System.out.println(print(sol.removeNthFromEnd_v2(build(1,2,3,4,5), 2))); // [1,2,3,5]
        System.out.println(print(sol.removeNthFromEnd_v2(build(1), 1)));          // []
        System.out.println(print(sol.removeNthFromEnd_v2(build(1,2), 1)));        // [1]
        System.out.println(print(sol.removeNthFromEnd_v2(build(1,2), 2)));        // [2]
    }
}
