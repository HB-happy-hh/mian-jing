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
public  class LRUCache {

    // ─────────────────────────────────────────────
    // 双向链表节点
    // ─────────────────────────────────────────────
    private  class Node {
        int key, val;
        Node prev, next;

        Node(int key, int val) {
            this.key = key;
            this.val = val;
        }
    }

      int capacity;
      Map<Integer, Node> map = new HashMap<>();  // key → Node，O(1) 查找
      Node head;                // 虚拟头节点（最近使用端）
      Node tail;                // 虚拟尾节点（最久未用端）

    public  LRUCache(int capacity) {
        this.capacity = capacity;

        // 初始化虚拟头尾节点并相互连接
        // 使用虚拟节点避免处理链表为空时的边界情况
        this.head = new Node(0, 0);
        this.tail = new Node(0, 0);
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
    // 测试
    // ─────────────────────────────────────────────
    public static void main(String[] args) {
        LRUCache lRUCache = new LRUCache(2);
       // 容量为 2
       lRUCache.put(1, 8); 
        lRUCache.put(2, 2);
        System.out.println(lRUCache.get(1));


    }
}

