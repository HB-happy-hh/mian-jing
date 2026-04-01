/**
 * 环形链表 II  LeetCode 142
 *
 * 题目：给定链表头节点 head，返回链表开始入环的第一个节点。
 *       无环则返回 null。不允许修改链表。
 *
 * 核心算法：Floyd 判圈算法（龟兔赛跑）
 *
 * 设：a = head 到入环点的距离
 *     b = 入环点到快慢指针相遇点的距离
 *     c = 相遇点回到入环点的距离（即剩余环长）
 *
 * 相遇时：
 *   slow 走了  a + b
 *   fast 走了  a + b + k*(b+c)，k ≥ 1（fast 多绕了 k 圈）
 *   fast = 2 × slow  →  a + b + k*(b+c) = 2(a+b)
 *                    →  a = k*(b+c) - b = (k-1)*(b+c) + c
 *
 * 当 k=1 时：a = c
 *   即"head 到入环点"的距离 = "相遇点到入环点"的距离
 *   → 一个指针从 head 出发，另一个从相遇点出发，同速前进，必在入环点相遇！
 *
 * 方法一：Floyd 快慢指针  O(n) 时间 O(1) 空间 —— 最优
 * 方法二：HashSet 记录访问过的节点  O(n) 时间 O(n) 空间
 * ─────────────────────────────────────────────────────
 */
public class DetectCycle {

    static class ListNode {
        int val;
        ListNode next;
        ListNode(int val) { this.val = val; }
    }

    // ─────────────────────────────────────────────
    // 方法一：Floyd 快慢指针（面试首选）
    //
    // 第一阶段：slow 每次走 1 步，fast 每次走 2 步
    //           若有环，必然相遇（fast 在环内追上 slow）
    //           若无环，fast 先到 null
    //
    // 第二阶段：一个指针重置到 head，另一个留在相遇点
    //           两者同速前进，相遇处即为入环节点
    // ─────────────────────────────────────────────
    public ListNode detectCycle(ListNode head) {
        ListNode slow = head, fast = head;

        // 第一阶段：判断是否有环，并找到相遇点
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast) {
                // 第二阶段：找入环点
                // a = c，所以 head 和相遇点同速出发，必在入环点相遇
                ListNode ptr = head;
                while (ptr != slow) {
                    ptr  = ptr.next;
                    slow = slow.next;
                }
                return ptr; // 入环点
            }
        }
        return null; // 无环
    }

    // ─────────────────────────────────────────────
    // 方法二：HashSet（更直观，空间换时间）
    //
    // 遍历链表，每个节点放入 Set；
    // 第一个已在 Set 中的节点就是入环点。
    // ─────────────────────────────────────────────
    public ListNode detectCycleHashSet(ListNode head) {
        java.util.Set<ListNode> visited = new java.util.HashSet<>();
        ListNode cur = head;
        while (cur != null) {
            if (visited.contains(cur)) return cur; // 第一次重复 = 入环点
            visited.add(cur);
            cur = cur.next;
        }
        return null;
    }

    // ─────────────────────────────────────────────
    // 测试
    // ─────────────────────────────────────────────
    public static void main(String[] args) {
        DetectCycle sol = new DetectCycle();

        // 构造 3 -> 2 -> 0 -> -4 -> (回到 2)，入环点 = 2
        ListNode n1 = new ListNode(3);
        ListNode n2 = new ListNode(2);
        ListNode n3 = new ListNode(0);
        ListNode n4 = new ListNode(-4);
        n1.next = n2; n2.next = n3; n3.next = n4; n4.next = n2; // 成环

        ListNode res = sol.detectCycle(n1);
        System.out.println("入环点值：" + (res == null ? "null" : res.val)); // 期望 2

        // 无环
        ListNode a = new ListNode(1);
        ListNode b = new ListNode(2);
        a.next = b;
        System.out.println("无环：" + sol.detectCycle(a)); // 期望 null
    }
}
