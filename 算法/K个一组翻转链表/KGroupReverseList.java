/**
 * 25. K 个一组翻转链表  LeetCode 25
 *
 * 思路：虚拟头节点 + 分组原地翻转
 *
 *   每次取出连续 k 个节点，将其翻转后接回原链表。
 *   若剩余节点不足 k 个，保持原顺序不变。
 *
 *   示意（k=2，链表 1→2→3→4→5）：
 *
 *   初始:   dummy → [1 → 2] → [3 → 4] → 5
 *   第1组:  dummy → [2 → 1] → [3 → 4] → 5
 *   第2组:  dummy → [2 → 1] → [4 → 3] → 5
 *   剩余:   5 不足 k，不翻转
 *   结果:   2 → 1 → 4 → 3 → 5
 */
public class KGroupReverseList {

    static class ListNode {
        int val;
        ListNode next;
        ListNode(int val) { this.val = val; }
    }

    // ─────────────────────────────────────────────
    // 主方法
    // ─────────────────────────────────────────────
    public ListNode reverseKGroup(ListNode head, int k) {
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode prevTail = dummy;      // 上一组的尾节点（初始指向 dummy）

        while (true) {
            // 1. 从 prevTail 往后数 k 步，找到本组第 k 个节点
            ListNode kth = getKth(prevTail, k);
            if (kth == null) break;     // 剩余不足 k 个，结束

            ListNode groupHead = prevTail.next;   // 本组头节点
            ListNode nextGroup = kth.next;        // 下一组头节点

            // 2. 断开本组与后续链表的连接，翻转本组
            kth.next = null;
            prevTail.next = reverse(groupHead);   // 翻转后 kth 成为新头，接到 prevTail

            // 3. 原来的组头（翻转后变为组尾）接上下一组
            groupHead.next = nextGroup;

            // 4. prevTail 前进到本组的尾（即翻转前的 groupHead）
            prevTail = groupHead;
        }

        return dummy.next;
    }

    // ─────────────────────────────────────────────
    // 从 node 之后数第 k 个节点，不足 k 个返回 null
    // ─────────────────────────────────────────────
    private ListNode getKth(ListNode node, int k) {
        while (node != null && k > 0) {
            node = node.next;
            k--;
        }
        return node;
    }

    // ─────────────────────────────────────────────
    // 翻转链表，返回新头节点
    //
    //   before: 1 → 2 → 3 → null
    //   after:  3 → 2 → 1 → null
    // ─────────────────────────────────────────────
    private ListNode reverse(ListNode head) {
        ListNode prev = null, curr = head;
        while (curr != null) {
            ListNode next = curr.next;
            curr.next = prev;
            prev = curr;
            curr = next;
        }
        return prev;
    }

    // ─────────────────────────────────────────────
    // 测试
    // ─────────────────────────────────────────────
    private static ListNode build(int... vals) {
        ListNode dummy = new ListNode(0), cur = dummy;
        for (int v : vals) { cur.next = new ListNode(v); cur = cur.next; }
        return dummy.next;
    }

    private static String print(ListNode head) {
        StringBuilder sb = new StringBuilder();
        while (head != null) {
            sb.append(head.val);
            if (head.next != null) sb.append(" → ");
            head = head.next;
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        KGroupReverseList sol = new KGroupReverseList();

        // 示例 1：[1,2,3,4,5], k=2  → 2 → 1 → 4 → 3 → 5
        System.out.println(print(sol.reverseKGroup(build(1, 2, 3, 4, 5), 2)));

        // 示例 2：[1,2,3,4,5], k=3  → 3 → 2 → 1 → 4 → 5
        System.out.println(print(sol.reverseKGroup(build(1, 2, 3, 4, 5), 3)));

        // 示例 3：[1,2], k=3（不足 k，不翻转）→ 1 → 2
        System.out.println(print(sol.reverseKGroup(build(1, 2), 3)));
    }
}
