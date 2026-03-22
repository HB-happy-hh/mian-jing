class ListNode {
    int val;
    ListNode next;
    ListNode(int val) { this.val = val; }
}

public class ReorderList {
    public void reorderList(ListNode head) {
        if (head == null || head.next == null) return;

        // ────────────────────────────────────────────
        // 第一步：找到链表的中间节点
        //   使用快慢指针：慢指针每次走 1 步，快指针每次走 2 步
        //   当快指针到达末尾时，慢指针恰好在中间
        //
        //   例：1 → 2 → 3 → 4 → 5
        //                 ↑ slow 最终停在 3（中间）
        // ────────────────────────────────────────────
        ListNode slow = head, fast = head;
        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }

        // slow 现在是前半段的最后一个节点，从 slow.next 开始是后半段
        ListNode secondHalf = slow.next;
        slow.next = null; // 断开前后两段

        // ────────────────────────────────────────────
        // 第二步：反转后半段链表
        //
        //   原后半段：4 → 5 → null
        //   反转后：  5 → 4 → null
        // ────────────────────────────────────────────
        secondHalf = reverse(secondHalf);

        // ────────────────────────────────────────────
        // 第三步：交替合并两段链表
        //
        //   前半段：1 → 2 → 3
        //   后半段：5 → 4
        //
        //   合并过程：
        //     取 1，接 5，接 2，接 4，接 3
        //   结果：1 → 5 → 2 → 4 → 3
        // ────────────────────────────────────────────
        ListNode first = head;
        ListNode second = secondHalf;
        while (second != null) {
            ListNode firstNext = first.next;   // 暂存前半段下一个节点
            ListNode secondNext = second.next; // 暂存后半段下一个节点

            first.next = second;       // 前半段当前节点 → 后半段当前节点
            second.next = firstNext;   // 后半段当前节点 → 前半段下一个节点

            first = firstNext;         // 前半段指针后移
            second = secondNext;       // 后半段指针后移
        }
    }

    // 反转链表，返回新的头节点
    private ListNode reverse(ListNode head) {
        ListNode prev = null;
        ListNode curr = head;
        while (curr != null) {
            ListNode next = curr.next;
            curr.next = prev;
            prev = curr;
            curr = next;
        }
        return prev;
    }

    // ─── 辅助方法：将数组构建为链表 ───
    private ListNode build(int[] vals) {
        ListNode dummy = new ListNode(0);
        ListNode cur = dummy;
        for (int v : vals) {
            cur.next = new ListNode(v);
            cur = cur.next;
        }
        return dummy.next;
    }

    // ─── 辅助方法：打印链表 ───
    private String print(ListNode head) {
        StringBuilder sb = new StringBuilder("[");
        while (head != null) {
            sb.append(head.val);
            if (head.next != null) sb.append(",");
            head = head.next;
        }
        return sb.append("]").toString();
    }

    public static void main(String[] args) {
        ReorderList sol = new ReorderList();

        // 示例1：[1,2,3,4] → [1,4,2,3]
        ListNode head1 = sol.build(new int[]{1, 2, 3, 4});
        sol.reorderList(head1);
        System.out.println("示例1: " + sol.print(head1)); // [1,4,2,3]

        // 示例2：[1,2,3,4,5] → [1,5,2,4,3]
        ListNode head2 = sol.build(new int[]{1, 2, 3, 4, 5});
        sol.reorderList(head2);
        System.out.println("示例2: " + sol.print(head2)); // [1,5,2,4,3]
    }
}
