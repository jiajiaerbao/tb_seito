package dp;

/*
//0...[i....j, j+1...len-1]
//i依赖于j, [i,len-1]当中, 找到最小的j
* 坑 1: j 的起始值 是 i, 而不是 i+1, 因为 j=i 的时候, 只包含单一一个字母, 也可以判断
* 坑 2: Math.min(dp[i], dp[j+1]+1) 这里是 dp[j+1]+1, 而不是dp[j], 因为[i,j]已经在isPal里面用完了,
*       剩下的字符串是从 j+1 开始
* 坑 3: 判断是否是palindrome的逻辑较繁琐:
*       如果i==j的话, 只包含一个字母
*       如果i!=j的话, i 和 j 的位置上面的字母相同, 并且 [i+1, j-1]也是palindrome
*                    i和j相邻, 因为如果i和j相邻的话, i+1,j-1之后, 就把两个字母错过去了
* 坑 4: 要开辟dp[len+1]大小的数组, 因为j的最大值是len-1, 而len-1依赖于len
* 坑 5: 最后的返回值 dp[0] - 1, 不要忘了减去 1, 因为dp[i]的含义是dp[i]里面包含多少个palindrome字符串, 而不是砍几刀
*
* */
@SuppressWarnings("Duplicates")
public class L132 {
    public int minCut(String s) {
        //corner case
        if (s == null || s.length() == 0) {
            return 0;
        }
        int len = s.length();
        boolean[][] isPal = new boolean[len][len];
        int[] dp = new int[len + 1];
        //pre processing: dp[i]里面包含多少个palindrome字符串
        for (int i = len - 1; i >= 0; i--) {
            dp[i] = len - i;
        }

        for (int i = len - 1; i >= 0; i--) {
            for (int j = i; j < len; j++) {
                if (i == j || (isPal[i + 1][j - 1] || j == i + 1) && s.charAt(i) == s.charAt(j)) {
                    isPal[i][j] = true;
                    dp[i] = Math.min(dp[i], dp[j + 1] + 1);
                }
            }
        }
        return dp[0] - 1;
    }
}
