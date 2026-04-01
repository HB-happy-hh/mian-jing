import java.util.PriorityQueue;

/**
 * LeetCode 23. 合并 K 个升序链表
 *
 * 给你一个链表数组，每个链表都已经按升序排列，
 * 请你将所有链表合并到一个升序链表中，返回合并后的链表。
 *
 * ══════════════════════════════════════════════════════
 * 方法一：最小堆（优先队列）  时间 O(N log k)  空间 O(k)
 *
 *   将 k 条链表的头节点放入最小堆，每次取出最小节点接到结果，
 *   再将该节点的 next 补入堆，持续到堆为空。
 *
 *   示意（lists = [[1,4,5],[1,3,4],[2,6]]）：
 *   堆：[1(L0),1(L1),2(L2)] → 取1 补4 → [1(L1),2(L2),4(L0)] → ...
 *   最终：1 → 1 → 2 → 3 → 4 → 4 → 5 → 6
 *
 * ══════════════════════════════════════════════════════
 * 方法二：分治合并            时间 O(N log k)  空间 O(log k)
 *
 *   类似归并排序，将 k 条链表两两配对合并，每轮减半，直到剩一条。
 *
 *   第1轮：merge(L0,L1)  merge(L2,L3) ...
 *   第2轮：merge(上轮结果) ...
 *   共 log k 轮，每轮处理 N 个节点，总计 O(N log k)
 * ══════════════════════════════════════════════════════
 */
public class MergeKSortedLists {

    static class ListNode {
        int val;
        ListNode next;
        ListNode(int val) { this.val = val; }
    }

    // ══════════════════════════════════════════════════════
    // 方法一：最小堆
    // ══════════════════════════════════════════════════════
    public ListNode mergeKLists(ListNode[] lists) {
        if (lists == null || lists.length == 0) return null;

        PriorityQueue<ListNode> minHeap = new PriorityQueue<>((a, b) -> a.val - b.val);

        for (ListNode head : lists) {
            if (head != null) {
                minHeap.offer(head);
            }
        }

        ListNode dummy = new ListNode(0);
        ListNode tail = dummy;

        while (!minHeap.isEmpty()) {
            ListNode node = minHeap.poll();
            tail.next = node;
            tail = tail.next;

            if (node.next != null) {
                minHeap.offer(node.next);
            }
        }

        return dummy.next;
    }

    // ══════════════════════════════════════════════════════
    // 方法二：分治合并
    // ══════════════════════════════════════════════════════
    public ListNode mergeKListsDivide(ListNode[] lists) {
        if (lists == null || lists.length == 0) return null;
        return mergeRange(lists, 0, lists.length - 1);
    }

    // 分治：递归合并 lists[left..right]
    private ListNode mergeRange(ListNode[] lists, int left, int right) {
        if (left == right) return lists[left];
        int mid = (left + right) / 2;
        ListNode l1 = mergeRange(lists, left, mid);
        ListNode l2 = mergeRange(lists, mid + 1, right);
        return mergeTwoLists(l1, l2);
    }

    // 合并两条有序链表（双指针）
    private ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        ListNode dummy = new ListNode(0);
        ListNode cur = dummy;

        while (l1 != null && l2 != null) {
            if (l1.val <= l2.val) {
                cur.next = l1;
                l1 = l1.next;
            } else {
                cur.next = l2;
                l2 = l2.next;
            }
            cur = cur.next;
        }

        cur.next = (l1 != null) ? l1 : l2;
        return dummy.next;
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
        return "[" + sb + "]";
    }

    public static void main(String[] args) {
        MergeKSortedLists sol = new MergeKSortedLists();

        System.out.println("── 方法一：最小堆 ──");
        // 示例 1：[[1,4,5],[1,3,4],[2,6]] → [1,1,2,3,4,4,5,6]
        System.out.println(print(sol.mergeKLists(new ListNode[]{ build(1,4,5), build(1,3,4), build(2,6) })));
        // 示例 2：[] → []
        System.out.println(print(sol.mergeKLists(new ListNode[]{})));
        // 示例 3：[[]] → []
        System.out.println(print(sol.mergeKLists(new ListNode[]{ build() })));

        System.out.println("── 方法二：分治合并 ──");
        // 示例 1：[[1,4,5],[1,3,4],[2,6]] → [1,1,2,3,4,4,5,6]
        System.out.println(print(sol.mergeKListsDivide(new ListNode[]{ build(1,4,5), build(1,3,4), build(2,6) })));
        // 示例 2：[] → []
        System.out.println(print(sol.mergeKListsDivide(new ListNode[]{})));
        // 示例 3：[[]] → []
        System.out.println(print(sol.mergeKListsDivide(new ListNode[]{ build() })));
    }
}
