import java.util.*;

public class Main {

    // static class ListNode{
    //     int val;
    //     ListNode next;

    //     ListNode(int val){
    //         this.val = val;
    //     }
    // }
public static String solution(String s){
    int max = 0;
    int mid =0;
    for(int i = 0; i<s.length();i++){
        int len1 = expand(s,i,i);
        int len2 = expand(s,i,i+1);

        int len = Math.max(len1,len2);
        if(len>max){
            max = len;
            mid = i;
        }
    }
    int start = mid -(max-1)/2;

    return s.substring(start,start+max);

}

public static int expand(String s,int left,int right){
    while(left>=0 && right<s.length() && s.charAt(left)==s.charAt(right)){
        left--;
        right++;
    }
    return right-left-1;
}







    public static void main(String[] args) {
        // Scanner sc = new Scanner(System.in);
        // int n  = sc.nextInt();
        // int k  = sc.nextInt();
        // int[] nums = new int[n];
        // for(int i=0;i<n;i++){
        //     nums[i]= sc.nextInt();
        // }
        String s = "abnkdifaaabbbaaa";
        System.out.print(solution(s));
    }   
}
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

