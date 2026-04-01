
// 链表节点定义
class ListNode {
    int val;
    ListNode next;
    ListNode(int val) { this.val = val; }
}

class AddTwoNumbers {
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        // 虚拟头节点，避免单独处理结果链表为空的情况
        // dummy本身不存数据，最终返回 dummy.next 就是结果链表的头
        ListNode dummy = new ListNode(0);

        // current 始终指向结果链表的最后一个节点，用来往后追加新节点
        ListNode current = dummy;

        // 进位，初始为 0
        int carry = 0;

        // 只要 l1 或 l2 还有节点，或者还有进位没处理完，就继续循环
        while (l1 != null || l2 != null || carry != 0) {

            // 取当前位的值：如果链表已经走完，就用 0 代替
            int val1 = (l1 != null) ? l1.val : 0;
            int val2 = (l2 != null) ? l2.val : 0;

            // 三者相加：当前位的两个数字 + 上一位的进位
            int sum = val1 + val2 + carry;

            // 计算新的进位（sum >= 10 时进位为 1，否则为 0）
            carry = sum / 10;

            // 当前位的结果只保留个位数字
            int digit = sum % 10;

            // 创建新节点，值为当前位的结果，追加到结果链表末尾
            current.next = new ListNode(digit);

            // current 指针后移，指向刚追加的新节点
            current = current.next;

            // l1、l2 各自向后移动一位（如果还没走完的话）
            if (l1 != null) l1 = l1.next;
            if (l2 != null) l2 = l2.next;
        }

        // dummy.next 才是结果链表真正的头节点
        return dummy.next;
    }
}
