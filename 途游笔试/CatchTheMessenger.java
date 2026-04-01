import java.util.*;
import java.io.*;

/**
 * 三国冰河时代 - 追及问题
 *
 * 信使胜利条件推导：
 *   阶段1：斥候到达拐角耗时 t1 = x/v1，期间信使走 p0 = v2*x/v1
 *   阶段2：信使到终点时间 = (y-p0)/v3，斥候追上时间 = p0/(v1-v3)
 *   信使胜 ⟺ (y-p0)/v3 ≤ p0/(v1-v3)，化简后得：
 *              y*(v1-v3) ≤ v2*x
 *   v1≤v3 时左边≤0，永远成立
 */
public class CatchTheMessenger {

    public String solve(long v1, long v2, long v3, long x, long y) {
        if (y * (v1 - v3) <= v2 * x) {
            return "Yes";
        } else {
            return "No";
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine().trim());
        StringBuilder sb = new StringBuilder();
        CatchTheMessenger solution = new CatchTheMessenger();
        while (T-- > 0) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            long v1 = Long.parseLong(st.nextToken());
            long v2 = Long.parseLong(st.nextToken());
            long v3 = Long.parseLong(st.nextToken());
            long x  = Long.parseLong(st.nextToken());
            long y  = Long.parseLong(st.nextToken());
            sb.append(solution.solve(v1, v2, v3, x, y)).append('\n');
        }
        System.out.print(sb);
    }
}
