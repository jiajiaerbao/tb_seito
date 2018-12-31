package dp;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/*
 * 坑 1: 坑BOSS, 为什么做不出来, 到底卡在了哪一步?
 *          A. 状态转移公式的思路是对的:
 *                  dp[i] = min(dp[i - coin[i]]+1), j in [0, n-1]
 *          B. 对于 dp[i], 初始值是null(这里取-1), 当第一次设置dp[i]的时候, 直接设置为dp[i - coin[i]]+1, 否则取最小的
 *             上面操作的前提是 dp[i - coin[i]] 不是 -1, 也就是 dp[i - coin[i]]+1 已经设置过了
 *          C. 思路转换为代码的时候, 卡住了, 如何求出 Math.min 做的不对, 题做的太少了,
 *             从思路转换为代码的训练远远不够
 * 坑 2: dp[0] 代表不存在, 返回 0
 * 坑 3: 写 for loop 的时候, dp 的坐标变换要注意, 不是 dp[amount-coins[j]] 而是 dp[i-coins[j]]

 * */
@SuppressWarnings("Duplicates")
public class L322 {
    public int coinChange(int[] coins, int amount) {
        if (coins == null || coins.length == 0) {
            return -1;
        }
        /*
         *  [amount] = min(amount - coin[i]) i in [0, n-1]
         * */
        int len = coins.length;
        int[] dp = new int[amount + 1];
        Arrays.fill(dp, -1);
        dp[0] = 0;
        for (int i = 1; i <= amount; i++) {
            for (int j = 0; j < len; j++) {
                if ((i >= coins[j]) && dp[i - coins[j]] != -1) {
                    if (dp[i] == -1) {
                        dp[i] = dp[i - coins[j]] + 1;
                    } else {
                        dp[i] = Math.min(dp[i], dp[i - coins[j]] + 1);
                    }
                }
            }
        }
        return dp[amount];
    }
}
