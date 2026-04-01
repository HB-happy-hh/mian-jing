import java.util.*;
import java.io.*;

/**
 * 途游笔试 - g 函数（十进制不进位加法）
 *
 * 目标：构造 b 数组，使得对所有前缀 r，g(A_r, B_r) = 2（质数）
 *   其中 A_r = g(a1,...,ar)，B_r = g(b1,...,br)
 *
 * 策略：令 B_r = complement(A_r)，即 g(A_r, B_r) = 2
 *   再令 b_r = B_r 按位减 B_{r-1}（mod 10），保证 g(b1,...,br) = B_r
 *
 * 由于 a_r >= 1，b_r 必然 >= 1，且 < 10^7
 */
public class GFunction {

    // g(a, b)：各位数字相加 mod 10，不进位
    static int digitAdd(int a, int b) {
        int res = 0, place = 1;
        for (int i = 0; i < 7; i++) {
            res += ((a / place + b / place) % 10) * place;
            place *= 10;
        }
        return res;
    }

    // 求 B 使得 g(A, B) = 2
    static int complement(int A) {
        int[] target = {2, 0, 0, 0, 0, 0, 0};
        int res = 0, place = 1;
        for (int i = 0; i < 7; i++) {
            res += (target[i] - (A / place) % 10 + 10) % 10 * place;
            place *= 10;
        }
        return res;
    }

    // 按位相减 mod 10
    static int digitMinus(int a, int b) {
        int res = 0, place = 1;
        for (int i = 0; i < 7; i++) {
            res += ((a / place - b / place + 10) % 10) * place;
            place *= 10;
        }
        return res;
    }

    public int[] solve(int n, int[] a) {
        int[] b = new int[n];
        int A = 0;
        int BPrev = 0;

        for (int i = 0; i < n; i++) {
            A = digitAdd(A, a[i]);
            int BCurr = complement(A);
            b[i] = digitMinus(BCurr, BPrev);
            BPrev = BCurr;
        }
        return b;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine().trim());
        StringTokenizer st = new StringTokenizer(br.readLine());
        int[] a = new int[n];
        for (int i = 0; i < n; i++) a[i] = Integer.parseInt(st.nextToken());

        GFunction sol = new GFunction();
        int[] b = sol.solve(n, a);

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            if (i > 0) sb.append(' ');
            sb.append(b[i]);
        }
        System.out.println(sb);
    }
}
