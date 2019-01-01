package dp;

/*
* 坑 1: 单纯地写 DFS 的话, 超时, 必须加 pruning
* 坑 2: 题目的隐含条件是 idx1 + idx2 = idx3, 所以可以把 三维mem 转换为 二维mem
* 坑 3: 如果非要DP的话, 可以根据 dfs_pruning 来转化成 DP
*
* 坑 4: 坑BOSS:
*       DP需要表示空的字符串, 所以是左闭右开区间, [0, i) 和 [, j)
*       这样的话, 开辟的二维数组需要时 dp[s1.length()+1][s2.length()+1]
*       dp[i][0] 和 dp[0][j] 表示, s1 为空 或者 s2 为空 的时候, 是否能fully match
* 坑 5: DP的边界初始化了之后, 剩下的就是里面的了, i 和 j 从 1 开始了
*       i 和 j 虽然从 1 开始, 但是 在获取字符的时候,
*       需要
*       s1.charAt( i-1 )
*       s2.charAt( j-1 )
*       s3.charAt( i-1 + j-1 + 1 )
*       来获取
* */
@SuppressWarnings("Duplicates")
public class L97 {
    public boolean isInterleave(String s1, String s2, String s3) {
        Boolean[][][] mem = new Boolean[s1.length() + 1][s2.length() + 1][s3.length() + 1];
        return dfs_pruning(s1.toCharArray(), 0, s2.toCharArray(), 0, s3.toCharArray(), 0, mem);
    }

    private boolean dfs_pruning(char[] s1, int idx1, char[] s2, int idx2, char[] s3, int idx3, Boolean[][][] mem) {
        if (mem[idx1][idx2][idx3] != null) {
            return mem[idx1][idx2][idx3];
        }
        if (idx1 == s1.length && idx2 == s2.length && idx3 == s3.length) {
            mem[idx1][idx2][idx3] = true;
            return true;
        }
        if (idx3 == s3.length) {
            mem[idx1][idx2][idx3] = false;
            return false;
        }
        char cur = s3[idx3];
        boolean res = false;
        if (idx1 < s1.length && cur == s1[idx1]) {
            res = dfs_pruning(s1, idx1 + 1, s2, idx2, s3, idx3 + 1, mem) || res;
        }
        if (idx2 < s2.length && cur == s2[idx2]) {
            res = dfs_pruning(s1, idx1, s2, idx2 + 1, s3, idx3 + 1, mem) || res;
        }
        mem[idx1][idx2][idx3] = res;
        return res;
    }
    /*
    * dp[i, j] = (s1[i-1] = s3[(i-1 + 1) + j-1] && dp[i-1, j])
    *            ||
    *            (s2[j-1] = s3[i-1 + (j-1 + 1)] && dp[i, j-1])
    * i: [0, i)
    * j: [0, j)
    * */
    private boolean dp(String s1, String s2, String s3) {
        if (s1.length() + s2.length() != s3.length()) {
            return false;
        }
        boolean[][] dp = new boolean[s1.length() + 1][s2.length() + 1];

        for (int i = 0; i <= s1.length(); i++) {
            dp[i][0] = s1.substring(0, i).equals(s3.substring(0, i));
        }
        for (int j = 0; j <= s2.length(); j++) {
            dp[0][j] = s2.substring(0, j).equals(s3.substring(0, j));
        }

        for (int i = 1; i <= s1.length(); i++) {
            for (int j = 1; j <= s2.length(); j++) {
                char c1 = s1.charAt(i - 1);
                char c2 = s2.charAt(j - 1);
                char c3 = s3.charAt(i + j - 1);
                dp[i][j] = (c1 == c3 && dp[i - 1][j])
                        ||
                        (c2 == c3 && dp[i][j - 1]);
            }
        }
        return dp[s1.length()][s2.length()];
    }
}
