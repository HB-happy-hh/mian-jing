/**
 * 合并两个有序链表  LeetCode 21
 *
 * 题目：将两个升序链表合并为一个新的升序链表并返回。
 *       新链表是通过拼接给定的两个链表的所有节点组成的。
 *
 * 示例：1→2→4  +  1→3→4  →  1→1→2→3→4→4
 *
 * 方法一：迭代（虚拟头节点）
 * 方法二：递归
 */
public class MergeTwoLists {

    static class ListNode {
        int val;
        ListNode next;
        ListNode(int val) { this.val = val; }
    }

    // ─────────────────────────────────────────────
    // 方法一：迭代（推荐）
    //
    // 用虚拟头节点 dummy 省去对空链表的特判
    // cur 指针始终指向已合并链表的末尾
    //
    //   dummy → [合并中...]
    //              ↑ cur
    //   l1: 1→2→4
    //   l2: 1→3→4
    //
    //   比较 l1.val 和 l2.val，谁小接谁
    // ─────────────────────────────────────────────
    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        ListNode dummy = new ListNode(0); // 虚拟头节点，简化边界处理
        ListNode cur = dummy;             // cur 指向合并链表的尾部

        while (l1 != null && l2 != null) {
            if (l1.val <= l2.val) {
                cur.next = l1; // 接 l1 的节点
                l1 = l1.next;
            } else {
                cur.next = l2; // 接 l2 的节点
                l2 = l2.next;
            }
            cur = cur.next;    // cur 后移
        }

        // 某一链表遍历完后，直接接上另一链表剩余部分
        cur.next = (l1 != null) ? l1 : l2;

        return dummy.next; // 跳过虚拟头，返回真正的头节点
    }

    // ─────────────────────────────────────────────
    // 方法二：递归
    //
    // 每次比较两个头节点，较小的作为当前节点，
    // 其 next 由递归解决剩余部分
    //
    //   merge(1→2→4, 1→3→4)
    //     → l1.val(1) == l2.val(1)，取 l1
    //       l1.next = merge(2→4, 1→3→4)
    //         → l2.val(1) < l1.val(2)，取 l2
    //           l2.next = merge(2→4, 3→4)  ...
    // ─────────────────────────────────────────────
    public ListNode mergeTwoListsRecursive(ListNode l1, ListNode l2) {
        if (l1 == null) return l2; // l1 空了，直接返回 l2 剩余部分
        if (l2 == null) return l1; // l2 空了，直接返回 l1 剩余部分

        if (l1.val <= l2.val) {
            l1.next = mergeTwoListsRecursive(l1.next, l2); // l1 更小，取 l1
            return l1;
        } else {
            l2.next = mergeTwoListsRecursive(l1, l2.next); // l2 更小，取 l2
            return l2;
        }
    }

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
        MergeTwoLists sol = new MergeTwoLists();
        System.out.println(print(sol.mergeTwoLists(build(1,2,4), build(1,3,4))));
        // 1 → 1 → 2 → 3 → 4 → 4
        System.out.println(print(sol.mergeTwoLists(build(), build())));
        // （空）
        System.out.println(print(sol.mergeTwoLists(build(), build(0))));
        // 0
    }
}
