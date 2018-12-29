package dp;

/*
* 坑 1: 逆向思维, 反过来思考, 问的是左上的最小值, 思考的时候, 从右下向左上填值
* 坑 2: 任何时刻都是 严格大于零, 不是大于等于零
* 坑 3: 坑BOSS: 识破马甲, 这是一道迷宫类的题目
*       从 对角线的一边 走到 对角线的另一边, 有方向性, 只能走两个方向 (没有环)
*       求最小
*       任何时刻都要大于零
*       初始值是[len-1][len-1]
* 坑 4: 要保证每一次填值的时候, dp[i][j]都要大于等于 0
* 坑 5: 搞清楚 i 和 j 的大小依赖关系:  [i, j] 是从 [i, j+1] 或者 [i+1, j] 得来的, 小的依赖于大的
* 坑 6: 边界: 因为有i+1和j+1, 而 0 <= i,j <= len-1, 所以二维数组的大小是[len+1][len+1]
*            因为是求的最小值, 所以把 右边界 和 下边界 初始化为 最大值 即可
* */
@SuppressWarnings("Duplicates")
public class L174 {
    public int calculateMinimumHP(int[][] dungeon) {
        if (dungeon == null || dungeon.length == 0 || dungeon[0] == null || dungeon[0].length == 0) {
            return 0;
        }
        int row = dungeon.length;
        int col = dungeon[0].length;

        int[][] dp = new int[row + 1][col + 1];

        for (int i = 0; i < row; i++) {
            dp[i][col] = Integer.MAX_VALUE;
        }
        for (int j = 0; j < col; j++) {
            dp[row][j] = Integer.MAX_VALUE;
        }
        //dp[row - 1][col - 1] = Math.max(0, -dungeon[row - 1][col - 1]);
        for (int i = row - 1; i >= 0; i--) {
            for (int j = col - 1; j >= 0; j--) {
                if (i == row - 1 && j == col - 1) {
                    //如果在前面初始化的话, 在这里会被覆盖掉
                    //初始条件(DFS的base case), 如果dp[row-1][col-1]为正, 则不需要消耗; 如果为负, 则需要消耗对应的
                    dp[row - 1][col - 1] = Math.max(0, -dungeon[row - 1][col - 1]);
                } else {
                    dp[i][j] = Math.max(0, Math.min(dp[i + 1][j], dp[i][j + 1]) - dungeon[i][j]);
                }
            }
        }
        return dp[0][0] + 1;
    }
}
