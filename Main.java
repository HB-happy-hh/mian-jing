import java.util.*;

public class Main {

    // static class ListNode{
    //     int val;
    //     ListNode next;

    //     ListNode(int val){
    //         this.val = val;
    //     }
    // }
    public static class Node{
        int key,value;
        Node prev,next;
        Node(int key,int value){
            this.key = key;
            this.value = value;
        }
    }
      static Map<Integer,Node> map = new HashMap<>();
      static int capacity;
     static Node head;
     static Node tail;
    

    public static void LRUCache(int capacity) {
        Main.capacity = capacity;
         head = new Node(0,0 );
        tail = new Node(0,0 );
        head.next = tail;
        tail.prev = head;

    }

    public static int get(int key) {
        Node node = map.get(key);
        if(node==null){
            return -1;
        }
        moveToHead(node);
        return node.value;
    }

    public static void put(int key, int value) {
        Node node = map.get(key);
        if(node!=null){
            node.value = value;
            moveToHead(node);
        }else{
            Node newNode = new Node(key,value);
            addToHead(newNode);
            map.put(key,newNode);
            if(map.size()>capacity){
                Node lru = tail.prev;
                remove(lru);
                map.remove(lru.key);
            }
        }

    }

    public static void addToHead(Node node ) {
        node.next = head.next;
        head.next = node;
        node.prev = head;
        node.next.prev = node;
    }

    public static void remove(Node node ) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }

    public static void moveToHead(Node node ) {
        remove(node);
        addToHead(node);
    }


    public static void main(String[] args) {
        // 初始化容量为 2 的 LRU 缓存
        LRUCache(2);
    
        put(1, 1);   // 缓存: {1=1}
        put(2, 2);   // 缓存: {1=1, 2=2}
    
        System.out.println(get(1));  // 输出 1，访问 1，缓存: {2=2, 1=1}
    
        put(3, 3);   // 容量满了，2 最久未用，淘汰 2，缓存: {1=1, 3=3}
    
        System.out.println(get(2));  // 输出 -1，2 已被淘汰
    
        put(4, 4);   // 容量满了，1 最久未用，淘汰 1，缓存: {3=3, 4=4}
    
        System.out.println(get(1));  // 输出 -1，1 已被淘汰
        System.out.println(get(3));  // 输出 3
        System.out.println(get(4));  // 输出 4
    }
}



    // public static void main(String[] args) {
        // Scanner sc = new Scanner(System.in);
        // int n  = sc.nextInt();
        // int k  = sc.nextInt();
        // int[] nums = new int[n];
        // for(int i=0;i<n;i++){
        //     nums[i]= sc.nextInt();
        // }
        // System.out.print(findKthLargest(nums, k));
//     }   
// }

    // 构建链表
//      public static void main(String[] args) {
//         Scanner sc = new Scanner(System.in);
//         int n  = sc.nextInt();
//         int k  = sc.nextInt();


//         ListNode dummy = new ListNode(0);
//         ListNode cur = dummy;
//         for(int i=0;i<n;i++){
//             cur.next = new ListNode(sc.nextInt());
//             cur = cur.next;
//         }
//         ListNode head = kGroupReverse(dummy.next,k);
//         while(head!=null){
//             System.out.print(head.val);
//             head = head.next;
//         }
       

//     }
// }
