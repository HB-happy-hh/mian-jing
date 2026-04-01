import java.util.HashMap;
import java.util.Map;

/**
 * LRU 缓存  LeetCode 146
 *
 * 核心结构：哈希表 + 双向链表
 *
 *   哈希表：key → Node，O(1) 定位节点
 *   双向链表：维护访问顺序
 *     - 头部（head.next）= 最近使用
 *     - 尾部（tail.prev）= 最久未使用
 *
 *   链表结构示意（capacity=3，已存 3 个元素）：
 *
 *   [dummy_head] ↔ [key=3,最近] ↔ [key=1] ↔ [key=2,最久] ↔ [dummy_tail]
 *
 *   每次 get/put：将命中节点移到 head.next
 *   容量满时：删除 tail.prev 节点
 */
public class LRUCache {

    // ─────────────────────────────────────────────
    // 双向链表节点
    // ─────────────────────────────────────────────
    private static class Node {
        int key, val;
        Node prev, next;

        Node(int key, int val) {
            this.key = key;
            this.val = val;
        }
    }

    private  int capacity;
    private  Map<Integer, Node> map = new HashMap<>();  // key → Node，O(1) 查找
    private  Node head;                // 虚拟头节点（最近使用端）
    private  Node tail;                // 虚拟尾节点（最久未用端）

    public LRUCache(int capacity) {
        this.capacity = capacity;

        // 初始化虚拟头尾节点并相互连接
        // 使用虚拟节点避免处理链表为空时的边界情况
        head = new Node(0, 0);
        tail = new Node(0, 0);
        head.next = tail;
        tail.prev = head;
    }

    // ─────────────────────────────────────────────
    // get：查询 key 对应的值
    // ─────────────────────────────────────────────
    public int get(int key) {
        Node node = map.get(key);
        if (node == null) return -1;    // 不存在返回 -1

        moveToHead(node);               // 访问了就移到链表头（标记为最近使用）
        return node.val;
    }

    // ─────────────────────────────────────────────
    // put：插入或更新 key-value
    // ─────────────────────────────────────────────
    public void put(int key, int value) {
        Node node = map.get(key);

        if (node != null) {
            // key 已存在：更新值，并移到链表头
            node.val = value;
            moveToHead(node);
        } else {
            // key 不存在：创建新节点
            Node newNode = new Node(key, value);
            map.put(key, newNode);
            addToHead(newNode);         // 新节点放到链表头（最近使用）

            if (map.size() > capacity) {
                // 超出容量：删除链表尾部节点（最久未使用）
                Node lru = tail.prev;   // tail.prev 就是最久未使用的节点
                removeNode(lru);
                map.remove(lru.key);    // 同步从哈希表中删除
            }
        }
    }

    // ─────────────────────────────────────────────
    // 辅助方法：将节点插入到链表头部（head 之后）
    //
    //   before: [head] ↔ [first] ↔ ...
    //   after:  [head] ↔ [node] ↔ [first] ↔ ...
    // ─────────────────────────────────────────────
    private void addToHead(Node node) {
        node.prev = head;
        node.next = head.next;
        head.next.prev = node;  // 原来的第一个节点的 prev 指向新节点
        head.next = node;       // head 的 next 指向新节点
    }

    // ─────────────────────────────────────────────
    // 辅助方法：从链表中摘除节点（不删除，只断开连接）
    //
    //   before: [prev] ↔ [node] ↔ [next]
    //   after:  [prev] ↔ [next]
    // ─────────────────────────────────────────────
    private void removeNode(Node node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }

    // ─────────────────────────────────────────────
    // 辅助方法：将已存在于链表中的节点移到头部
    //   = 先摘除，再插入头部
    // ─────────────────────────────────────────────
    private void moveToHead(Node node) {
        removeNode(node);
        addToHead(node);
    }

    // ─────────────────────────────────────────────
    // 打印当前链表状态（从最近到最久）
    // ─────────────────────────────────────────────
    private String printList() {
        StringBuilder sb = new StringBuilder("[最近]");
        Node cur = head.next;
        while (cur != tail) {
            sb.append(" → (").append(cur.key).append(",").append(cur.val).append(")");
            cur = cur.next;
        }
        sb.append(" → [最久]");
        return sb.toString();
    }

    // ─────────────────────────────────────────────
    // 测试
    // ─────────────────────────────────────────────
    public static void main(String[] args) {
        LRUCache cache = new LRUCache(2);   // 容量为 2

        cache.put(1, 1);
        System.out.println("put(1,1)  " + cache.printList());
        // [最近] → (1,1) → [最久]

        cache.put(2, 2);
        System.out.println("put(2,2)  " + cache.printList());
        // [最近] → (2,2) → (1,1) → [最久]

        System.out.println("get(1)=" + cache.get(1) + "  " + cache.printList());
        // get 命中 1，移到头部
        // [最近] → (1,1) → (2,2) → [最久]

        cache.put(3, 3);
        System.out.println("put(3,3)  " + cache.printList());
        // 容量满，淘汰尾部 (2,2)
        // [最近] → (3,3) → (1,1) → [最久]

        System.out.println("get(2)=" + cache.get(2));
        // 返回 -1，key=2 已被淘汰

        cache.put(4, 4);
        System.out.println("put(4,4)  " + cache.printList());
        // 容量满，淘汰尾部 (1,1)
        // [最近] → (4,4) → (3,3) → [最久]

        System.out.println("get(1)=" + cache.get(1));  // -1，已淘汰
        System.out.println("get(3)=" + cache.get(3));  // 3
        System.out.println("get(4)=" + cache.get(4));  // 4
    }
}
