package dp;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/*
* 坑 1: 第一层 for loop 必须包括 所有的字符: s.subString(i, idx), 所以 idx = len
* 坑 2: 初始值是 dp[len], 而不是dp[len-1], 也就是dfs pruning的base case
* 坑 3: i 的变化返回是 i=idx+1; i <= len, 也就是 idx 的下一个字母, 到最后一个字母为止
* 坑 4: dp[idx] = dp[idx] || dp[i] 表示 一通百通, 就是 [0...idx...i...len] 之间,
*       只要有一个 dp[i]=true 满足条件的话, dp[idx]就是true, 表示可以分割
* */
@SuppressWarnings("Duplicates")
public class L139 {
    public boolean wordBreak(String s, List<String> wordDict) {
        //corner case
        if (s == null || s.length() == 0) {
            return false;
        }
        //convert List<String> to Set<String>
        Set<String> dict = new HashSet<>(wordDict);

        int len = s.length();

        boolean[] dp = new boolean[len + 1];

        //init val: 也就是dfs pruning的base case
        dp[len] = true;

        for (int idx = len; idx >= 0; idx--) {
            for (int i = idx + 1; i <= len; i++) {
                String subStr = s.substring(idx, i);
                if (dict.contains(subStr)) {
                    dp[idx] = dp[idx] || dp[i];
                }
            }
        }
        return dp[0];
    }
}
