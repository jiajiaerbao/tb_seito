package dp;

/*
* 坑 1: 明确dp[i][j]的含义: dp[i][j]: 涂到了第 i 个房子, 用 j 颜色的话, 最小cost
* 坑 2: notVal 函数的意思是:
*               对于第 m 个房子, 扫描了 m 个房子的 val数组的个数 的颜色数值 之后,
*               每个位置上, 不涂该颜色的时候的最小值
*               从左向右扫描的时候, 只是取当前的最小值即可
*               当从左向右扫描完之后, 再从右向左扫描的时候, 取当前的最小值 和 dp[m][i]上面已经存在的值 中的 最小值
*               最后 dp[m][0] = rightMin 是说 当确定了 dp[m][1] 的最小值之后(向左扫 和 向右扫 都扫完), 才能确定dp[m][0]的值
* 坑 3: dp 数组的大小是 n+1, 因为for loop里面有i-1, 注意越界
* 坑 4: 两层 for loop 的地方还不是很明白: 这道题的状态转移还不是很清楚
*       这里的 i 的初始值是 1, 因为里面有 i-1 的地方, 否则会越界, 而要表示 n 个房子, 从 1 开始的话, 必须到 n+1
*       针对第 i 层:
*       第 1 层涂 j 颜色的之后的cost: i 的范围是 1 ~ n, 针对 cost 的 input 来说, i-1的范围是 0~n-1, 也就是第 i 层
*       而dp数组是1~n, i-1是上一层, 这里巧妙第利用了index的错位, 使两个放到了同样的 for loop里面
* */
@SuppressWarnings("Duplicates")
public class L265 {
    public int minCostII_抄的答案(int[][] costs) {
        //corner case
        if (costs == null || costs.length == 0 || costs[0] == null || costs[0].length == 0) {
            return 0;
        }
        int n = costs.length;
        int k = costs[0].length;

        if (k == 1) {
            return n == 1 ? costs[0][0] : 0;
        }

        int[][] dp = new int[n + 1][k];
        int[] val = new int[k];

        for (int i = 1; i <= n; i++) {
            //把 到前一个房子为止的 涂各种颜色的 cost 先算出来
            for (int j = 0; j < k; j++) {
                val[j] = costs[i - 1][j] + dp[i - 1][j];
            }
            //更新dp中第i行的0~k的值: 除了自己颜色之外 的 最小 cost
            getNotVal(dp, i, val);
        }
        int min = Integer.MAX_VALUE;
        //这里计算最后一次涂颜色时候的cost
        for (int i = 0; i < k; i++) {
            min = Math.min(min, dp[n][i]);
        }
        return min;
    }

    private void getNotVal(int[][] dp, int m, int[] val) {
        int len = val.length;
        int leftMin = val[0];
        int rightMin = val[len - 1];

        for (int i = 1; i < len; i++) {
            dp[m][i] = leftMin;
            leftMin = Math.min(leftMin, val[i]);
        }
        for (int i = len - 2; i > 0; i--) {
            dp[m][i] = Math.min(dp[m][i], rightMin);
            rightMin = Math.min(rightMin, val[i]);
        }
        dp[m][0] = rightMin;
    }

    /**********自己做的*******第一遍*****************/
    public int minCostII(int[][] costs) {
        //corner case
        if (costs == null || costs.length == 0 || costs[0] == null || costs[0].length == 0) {
            return 0;
        }
        int n = costs.length;
        int k = costs[0].length;

        //这里提前判断k=1, 是因为
        if (k == 1) {
            return n == 1 ? costs[0][0] : 0;
        }

        int[][] dp = new int[n + 1][k];
        int[] notVal = new int[k];

        //这里的 i 的初始值是 1, 因为里面有 i-1 的地方, 否则会越界, 而要表示 n 个房子, 从 1 开始的话, 必须到 n+1
        for (int i = 1; i <= n; i++) {
            for (int j = 0; j < k; j++) {
                //针对第 i 层:
                //第 1 层涂 j 颜色的之后的cost: i 的范围是 1 ~ n, 针对 cost 的 input 来说, i-1的范围是 0~n-1, 也就是第 i 层
                //而dp数组是1~n, i-1是上一层, 这里巧妙第利用了index的错位, 使两个放到了同样的 for loop里面
                notVal[j] = costs[i - 1][j] + dp[i - 1][j];
            }

            getMinNotVal(dp, notVal, i);
        }
        int res = Integer.MAX_VALUE;
        for (int i = 0; i < k; i++) {
            res = Math.min(res, dp[n][i]);
        }
        return res;
    }

    private void getMinNotVal(int[][] dp, int[] notVal, int m) {
        int len = notVal.length;
        int minLeft = notVal[0];
        int minRight = notVal[len - 1];
        for (int i = 1; i < len; i++) {
            dp[m][i] = minLeft;
            minLeft = Math.min(minLeft, notVal[i]);
        }
        //这里是 i > 0, 因为 i=0 的时候, 是最左面的元素了, i=0只是依赖于相邻的右边的元素, 需要单独处理
        for (int i = len - 2; i > 0; i--) {
            dp[m][i] = Math.min(minRight, dp[m][i]);
            minRight = Math.min(minRight, notVal[i]);
        }
        dp[m][0] = minRight;
    }
}






































