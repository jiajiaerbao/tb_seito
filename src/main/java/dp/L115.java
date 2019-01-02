package dp;

/*
* 坑 1: 用 dfs + pruning的话, 超时了
*
* 坑 2: 注意 i 和 j 的开闭情况, 因为需要表示 s 和 t 为空的情况, 所以 i 和 j 是 左闭右开:
*       [0, i)
*       [0, j)
* 坑 3: dp 的初始化:
*       dp[0][0]: 表示 s 和 t 都为空, 也就是 空 匹配 空, 值是 1
*       dp[i][0]: 如果 s 有 零或者多个 字母, t 是 空, s 不选字母的情况下也可以匹配, 所以 值都是 1
* 坑 4: dp[i][j] += dp[i - 1][j]; 和 dp[i][j] = dp[i - 1][j]; 对最后的结果没有影响
* */
@SuppressWarnings("Duplicates")
public class L115 {
    public int numDistinct(String s, String t) {
        if (s.length() < t.length()) {
            return 0;
        }
        Integer[][] dp = new Integer[s.length() + 1][t.length() + 1];
        int[] res = new int[1];
        helper(s, 0, new StringBuilder(), t, 0, res, dp);
        return res[0];
    }

    private void helper(String s, int sIdx, StringBuilder path, String t, int tIdx, int[] res, Integer[][] dp) {
        if (tIdx == t.length() && path.toString().equals(t)) {
            res[0]++;
            dp[sIdx][tIdx] = res[0];
            return;
        }
        if (path.length() >= t.length() || sIdx >= s.length() || tIdx >= t.length()) {
            dp[sIdx][tIdx] = 0;
            return;
        }

        if (dp[sIdx][tIdx] != null) {
            res[0] += dp[sIdx][tIdx];
            return;
        }

        helper(s, sIdx + 1, path, t, tIdx, res, dp);

        if (s.charAt(sIdx) == t.charAt(tIdx)) {
            int size = path.length();
            path.append(s.charAt(sIdx));
            helper(s, sIdx + 1, path, t, tIdx + 1, res, dp);
            path.setLength(size);
        }
    }

    private int dp(String s, String t){
        if (s.length() < t.length()) {
            return 0;
        }
        int[][] dp = new int[s.length() + 1][t.length() + 1];
        for (int i = 0; i <= s.length(); i++) {
            dp[i][0] = 1;
        }
        for (int j = 0; j <= t.length(); j++) {
            dp[0][j] = 0;
        }
        dp[0][0] = 1;
        for (int i = 1; i <= s.length(); i++) {
            for (int j = 1; j <= t.length(); j++) {
                dp[i][j] += dp[i - 1][j];
                if (s.charAt(i - 1) == t.charAt(j - 1)) {
                    dp[i][j] += dp[i - 1][j - 1];
                }
            }
        }
        return dp[s.length()][t.length()];
    }
}
