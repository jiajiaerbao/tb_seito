package dp;


/*
* dp[i]: [i, len-1], the maximum value the current player can pick up from i to len-1 items
* dp[i] = max( values[i] + min( dp[i+2], dp[i+3]),
*              values[i] + values[i+1] + min( dp[i+3], dp[i+4])
*             )
* 坑 1: 坑 BOSS: 把上面的
*           dp[*] OR dp[*, *] 的含义定义清楚
*           状态转移公式写清楚
*        了之后, 转换成code就是几分钟的事情
* 坑 2: 因为有 dp[i+2], dp[i+3], dp[i+4] : 注意边界条件, 当 index 大于等于 len 的时候, 取 0 即可
 * */
@SuppressWarnings("Duplicates")
public class Lint395 {
    public boolean firstWillWin(int[] values) {
        // write your code here
        if (values == null || values.length == 0) {
            return false;
        }
        int len = values.length;
        int[] dp = new int[len];
        if (values.length <= 2) {
            return true;
        }
        dp[len - 1] = values[len - 1];
        dp[len - 2] = Math.max(values[len - 1], values[len - 1] + values[len - 2]);
        for (int i = len - 3; i >= 0; i--) {
            dp[i] = Math.max(
                    values[i] + Math.min(
                            dp[i + 2],
                            i == len - 3 ? 0 : dp[i + 3]),
                    values[i] + values[i + 1] + Math.min(
                            i == len - 3 ? 0 : dp[i + 3],
                            i >= len - 4 ? 0 : dp[i + 4])
            );
        }
        int total = 0;
        for (int value : values) {
            total += value;
        }
        return dp[0] >= total - dp[0];
    }
}
