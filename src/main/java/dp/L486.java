package dp;

/*
* dp[i, j] : 当前玩家在 [i, j] 范围内, 所能拿到的最大积分
* dp[i, j] <-- Math.max(
*               (sum[i+1, j] - dp[i+1, j]) + nums[i],
*               (sum[i, j-1] - dp[i, j-1]) + nums[j]
*               )
* 坑 1: sum[i, j] 的初始化 要注意:
*       j >= i
*       sum[i, j] = total[j] - total[i-1]
*       sum[0][j] 要单独初始化
* 坑 2: 算法哥给的答案是简化版, 包含了 如何省略计算 sum[i][j] 的 follow up:
*       (sum[i+1, j] - dp[i+1, j]) 和 (sum[i, j-1] - dp[i, j-1]) 里面的 dp[*,*] 都是 当前玩家 在 [i+1, j] 和 [i, j-1] 里面能取得的最大值
*       而 (sum[i+1, j] - dp[i+1, j]) 和 (sum[i, j-1] - dp[i, j-1]) 之后, 都变成了 取最小的那个, 这个说一下就可以了, 不用记了, 记不住
* 坑 3: 边界条件的思路还不是很清晰:
*       为什么当 i = len - 1 和 i = j 的时候, 需要单独处理?
*       因为有 i+1, 当 i=len-1 的时候, dp[len-1][*] 只能 touch 到最后一个球, 传给 后手玩家的是 空
*       因为有[i][j-1], 当 i=j 的时候, 只有一个球, 直接计算
* 坑 4: 写 dp 的时候, i 和 j 在的 for loop 里面, 哪个在外面哪个在里面: 严格按照 状态转移公式 来写,
*       并且 i 和 j 的 大小依赖关系 也严格 按照状态转移公式来定
*       总之, dp[i, j] 和 状态转移公式 定了之后, 整个程序也就定了
* 坑 5: 计算 sum[i][j] 的时候要小心, 逻辑要清晰: sum[i][j] 里面的 i 和 j 是如何变化的
 * */
@SuppressWarnings("Duplicates")
public class L486 {
    public boolean PredictTheWinner(int[] nums) {
        if (nums == null || nums.length == 0) {
            return false;
        }
        int len = nums.length;

        int[] total = new int[len];
        int all = 0;
        for (int i = 0; i < len; i++) {
            all += nums[i];
            total[i] = all;
        }
        int[][] sum = new int[len][len];
        for (int j = 0; j < len; j++) {
            sum[0][j] = total[j];
        }
        for (int i = 1; i < len; i++) {
            for (int j = i; j < len; j++) {
                sum[i][j] = total[j] - total[i - 1];
            }
        }

        int[][] dp = new int[len][len];
        for (int i = len - 1; i >= 0; i--) {
            for (int j = i; j < len; j++) {
                dp[i][j] = Math.max(
                        i == len - 1 ? nums[i] : sum[i + 1][j] - dp[i + 1][j] + nums[i],
                        i == j ? nums[i] : sum[i][j - 1] - dp[i][j - 1] + nums[j]
                );
            }
        }
        return dp[0][len - 1] >= all - dp[0][len - 1];
    }
}
