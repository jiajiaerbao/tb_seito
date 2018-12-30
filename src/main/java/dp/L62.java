package dp;

/*
* 坑 1: 有点卡壳, 还是题做的少, 把思路转化为代码的能力太弱
* 坑 2: 注意边界条件, 前面两个for loop都是在处理边界条件的
* 坑 3: 最后返回的 dp[m-1][n-1]
* */
@SuppressWarnings("Duplicates")
public class L62 {
    public int uniquePaths(int m, int n) {
        if (m <= 0 || n <= 0) {
            return -1;
        }
        int[][] dp = new int[m][n];
        /*
         * dp[i][j] = dp[i-1][j] + dp[i][j-1]
         * */
        for (int i = 0; i < m; i++) {
            dp[i][0] = 1;
        }
        for (int j = 0; j < n; j++) {
            dp[0][j] = 1;
        }
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                dp[i][j] = dp[i - 1][j] + dp[i][j - 1];
            }
        }
        return dp[m - 1][n - 1];
    }
}
