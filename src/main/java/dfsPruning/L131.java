package dfsPruning;

import java.util.ArrayList;
import java.util.List;

public class L131 {
    /*
    * 坑 1: boolean[][] isPal 主函数的里面计算 从 i 到 j 是否是 Palindrome 的两层 for loop 一定要掌握
    * 坑 2: boolean[] mem 是用来 pruning 的, 也就是判断 从 idx 开始 最终能否构成解, 用于剪枝
    * 坑 3: 填充 isPal 的时候,
    *           首先填充对角线: isPal[i][i] = true;
    *           其次, j == i - 1 是相邻的两个点
    *                 isPal[j][i] 的前一个是 isPal[j+1][i-1]
    * 坑 4: 在search里面, for loop 的里面是 mem[idx + length + 1], 也就是从新的起点开始的字符串能否构成 palindrome
    * */
    public List<List<String>> partition(String s) {
        List<List<String>> res = new ArrayList<>();
        if (s == null || s.length() == 0) {
            return res;
        }
        int len = s.length();
        boolean[] mem = new boolean[len + 1];
        for (int i = 0; i <= len; i++) {
            mem[i] = true;
        }
        boolean[][] isPal = new boolean[len][len];
        for (int i = 0; i < len; i++) {
            isPal[i][i] = true;
            for (int j = i - 1; j >= 0; j--) {
                isPal[j][i] = s.charAt(j) == s.charAt(i) && (j == i - 1 || isPal[j + 1][i - 1]);
            }
        }
        search(s, 0, new ArrayList<>(), res, isPal, mem);
        return res;
    }

    private void search(String s, int idx, List<String> one, List<List<String>> res, boolean[][] isPal, boolean[] mem) {
        if (idx == s.length()) {
            res.add(new ArrayList<>(one));
            return;
        }
        int curSize = res.size();
        for (int length = 0; idx + length < s.length(); length++) {
            if (mem[idx + length + 1] && isPal[idx][idx + length]) {
                String newStr = s.substring(idx, idx + length + 1);
                one.add(newStr);
                search(s, idx + length + 1, one, res, isPal, mem);
                one.remove(one.size() - 1);
            }
        }
        if (curSize == res.size()) {
            mem[idx] = false;
        }
    }
}
