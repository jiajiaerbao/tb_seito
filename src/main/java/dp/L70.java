package dp;


/*
* 坑 1: 坑BOSS: 写不出来DP的话, 写DFS+Pruning也可以
* 坑 2: base case 注意:
*       边界条件的判断就是看状态转移公式里面的 n 有哪些不能取
*       这里的话, 因为状态转移公式里面有 n-1 和 n-2
*       所以 n 是 0 和 1 的时候, n-1 和 n-2 是负数, 不能取, 需要在base case里面cover住
*       最后用n=0,1,2,3来检验一下, n无法达到-1, 因为-1的话, n必须是0或者1, 已经在base case里面了
* 坑 3: 如果写 dp 的话:
*       1. 状态转移公式 要明白
*       2. index的 大小依赖关系 要弄清楚
* */
@SuppressWarnings("Duplicates")
public class L70 {

    public int climbStairs(int n) {
        if (n == 0 || n == 1) {
            return 1;
        }
        int[] dp = new int[n];
        dp[0] = 1;
        dp[1] = 2;
        for (int i = 2; i < n; i++) {
            dp[i] = dp[i - 1] + dp[i - 2];
        }
        return dp[n - 1];
    }

    /*********************************************************************/
    public int climbStairs_2(int n) {
        return helper_2(n, new int[n + 1]);
    }

    private int helper_2(int n, int[] mem) {
        if (n == 0) {
            mem[n] = 1;
            return mem[n];
        }
        if (n == 1) {
            mem[n] = 1;
            return mem[n];
        }
        /*if (n == 2) {
            mem[n] = 2;
            return mem[n];
        }*/
        if (mem[n] > 0) {
            return mem[n];
        }
        mem[n] = helper_2(n - 2, mem) + helper_2(n - 1, mem);
        return mem[n];
    }
}
