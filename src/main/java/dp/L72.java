package dp;

/*
* 坑 1: 如果 i, j 位置上的 字符 不相等的话, 从delete/insert/replace里面取出最小cost的那个, 最后别忘了加1
* 坑 2: 理解思路的基础上记忆, 主要是记忆如何推导出来的, 千万不要记代码
*
* */
@SuppressWarnings("Duplicates")
public class L72 {
    public int minDistance(String word1, String word2) {
        if (word1 == null || word1.length() == 0 || word2 == null || word2.length() == 0) {
            if (word1 != null && word1.length() != 0) {
                return word1.length();
            }
            if (word2 != null && word2.length() != 0) {
                return word2.length();
            }
            return 0;
        }
        int len1 = word1.length();
        int len2 = word2.length();
        int[][] dp = new int[len1 + 1][len2 + 1];
        dp[0][0] = 0;
        for (int i = 1; i <= len1; i++) {
            dp[i][0] = i;
        }
        for (int j = 1; j <= len2; j++) {
            dp[0][j] = j;
        }
        for (int i = 1; i <= len1; i++) {
            for (int j = 1; j <= len2; j++) {
                char c1 = word1.charAt(i - 1);
                char c2 = word2.charAt(j - 1);
                if (c1 == c2) {
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    dp[i][j] = Math.min(dp[i - 1][j], Math.min(dp[i][j - 1], dp[i - 1][j - 1])) + 1;
                }
            }
        }
        return dp[len1][len2];
    }
}
