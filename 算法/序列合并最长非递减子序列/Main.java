import java.util.*;

/**
 * 题意：两个长度为 n 的序列 a、b，将 b 的元素依次插入 a（保持 b 的相对顺序不变，
 *       同时 a 的相对顺序也不变），问所有可能的合并结果中，最长非递减子序列的最大长度。
 *
 * 结论：答案 = LNDS(a) + LNDS(b)
 *
 * 证明：
 *   上界：任何合并序列的非递减子序列，其 a 部分是 a 的非递减子序列，b 部分是 b 的非递减子序列，
 *         故长度 ≤ LNDS(a) + LNDS(b)。
 *   下界：取 A' = a 的最长非递减子序列，B' = b 的最长非递减子序列，对两者做归并排序得到序列 M。
 *         可以构造一种合并方式，使得 M 作为子序列出现在合并结果中，因此可达到 LNDS(a) + LNDS(b)。
 */
public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int[] a = new int[n];
        int[] b = new int[n];
        for (int i = 0; i < n; i++) a[i] = sc.nextInt();
        for (int i = 0; i < n; i++) b[i] = sc.nextInt();

        System.out.println(lnds(a) + lnds(b));
    }

    // 最长非递减子序列（patience sorting，O(n log n)）
    // 用 upper_bound（第一个 > x 的位置）保证允许相等元素连续
    static int lnds(int[] arr) {
        int[] tails = new int[arr.length];
        int size = 0;
        for (int x : arr) {
            int pos = upperBound(tails, size, x);
            tails[pos] = x;
            if (pos == size) size++;
        }
        return size;
    }

    // 在 tails[0..size-1] 中找第一个 > x 的下标
    static int upperBound(int[] tails, int size, int x) {
        int lo = 0, hi = size;
        while (lo < hi) {
            int mid = (lo + hi) >>> 1;
            if (tails[mid] <= x) lo = mid + 1;
            else hi = mid;
        }
        return lo;
    }
}
