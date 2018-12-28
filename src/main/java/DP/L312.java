package DP;


/*
 * 坑 1: 首先考虑清楚这里是一个二维DP
 *       把图画出来, 0...i-1, i, ...k-1, k, k+1..., j, j+1...len-1
 *       把 dp[i][j]的含义弄清楚: 从 i 到 j 的 max value
 * 坑 2: 坑 BOSS: 反向思考, 从 先炸掉k 转变成 先炸掉[i,k-1]和[k+1,j], 最后炸掉k
 *       这时[i,k-1]和[k+1,j]这两段都已经炸没了, 只剩下 0...i-1, k, j+1...len-1 了
 *       这时就可以把 关于某一次的 所有状态 的相关参数都放到 一个函数里面了 (如果先炸掉 k 的话, 在[i, k-1]那一段无法获得 k+1 的值)
 * 坑 3: i <= k <= j 的各个参数的大小依赖关系一定要清楚, 然后写出for loop即可
 * 坑 4: 因为有 k-1 和 k+1, 要考虑越界: 如果 k == i 或者 k == j 的话, dp[i][i-1]和dp[j+1][j]不可能存在, 返回 0
 * 坑 5: 考虑 i 和 j 的边界: 因为有[i-1]和[j+1], 所以如果 i=0 或者 j=len-1 的话, 证明到达边界之外的那个值了, 这时默认边界之外的那个值是1,
 *       也就是 muns[0]的左边的一个 和 nums[len-1]右边的一个, 默认是 1, 这样赋值的话, 不影响计算结果
 * */

@SuppressWarnings("Duplicates")
public class L312 {
    public int maxCoins(int[] nums) {
        //corner case
        if (nums == null || nums.length == 0) {
            return 0;
        }
        /*
         * dp[i, j]:
         *       between[i, j], the max value, the border is i-1 and j+1
         *       [i, k-1], k, [k+1, j] traverse k, to get the max k
         *       i <= k <= j
         * */
        int len = nums.length;
        int[][] dp = new int[len][len];
        for (int i = len - 1; i >= 0; i--) {
            for (int j = i; j < len; j++) {
                for (int k = i; k <= j; k++) {
                    int val = 0;
                    //计算 i...k-1 这一段
                    val += k == i ? 0 : dp[i][k - 1];
                    //计算 k+1...j 这一段
                    val += k == j ? 0 : dp[k + 1][j];
                    //计算 i-1, k, j+1 的结果
                    val += nums[k] * (i == 0 ? 1 : nums[i - 1]) * (j == len - 1 ? 1 : nums[j + 1]);
                    dp[i][j] = Math.max(dp[i][j], val);
                }
            }
        }
        return dp[0][len - 1];
    }
}
