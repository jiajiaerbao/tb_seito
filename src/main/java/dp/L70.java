package dp;


/*
* 坑 1: 坑BOSS: 写不出来DP的话, 写DFS+Pruning也可以
* 坑 2: base case 注意: n=1和n=2都是base case
* 坑 3: 如果写 dp 的话:
*       1. 状态转移公式 要明白
*       2. index的 大小依赖关系 要弄清楚
*
* */
@SuppressWarnings("Duplicates")
public class L70 {
    public int climbStairs(int n) {
        return helper(n, new int[n + 1]);
    }

    private int helper(int n, int[] mem) {
        /*if (n == 0) {
            mem[n] = 0;
            return mem[n];
        }*/
        if (n == 1) {
            mem[n] = 1;
            return mem[n];
        }
        if (n == 2) {
            mem[n] = 2;
            return mem[n];
        }
        if (mem[n] > 0) {
            return mem[n];
        }
        mem[n] = helper(n - 2, mem) + helper(n - 1, mem);
        return mem[n];
    }
}
