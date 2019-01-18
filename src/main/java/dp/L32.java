package dp;

/*
* 坑 1: 具体思路看 everNote的讲解
* 坑 2: [i, j] 是通过 [i+1, j-1] 来得到的
*       这里 j = max[i+1] + 1, 是这道括号题的特点
* 坑 3: 如果 s.charAt(i) 是 ")" 的话, 无法得到比 max[i+1] 更大的max
*       如果 s.charAt(i) 是 "(" 的话, 判断 s.charAt(j) 是否是 ")", 如果不是的话, 也无法延伸
*       注意这里的 j 是通过 max[i+1] 得到的
*       最后看能否接上 max[j+1], 这里要判断是否越界
* */
@SuppressWarnings("Duplicates")
public class L32 {
    public int longestValidParentheses(String s) {
        int res = 0;
        char left = '(';
        char right = ')';
        int len = s.length();
        int[] max = new int[len + 1]; // 题目求的是最大长度, 这里只需要记录 从i出发 的最大长度 即可
        //check 区间 [i, j] 是否满足条件
        for (int i = len - 1; i >= 0; i--) {
            if (s.charAt(i) == right) {
                max[i] = 0;
            } else if (s.charAt(i) == left) {
                //max[i+1]: 从 i+1 出发的最长长度
                int j = i + max[i + 1] + 1;
                if (j < len && s.charAt(j) == right) {
                    max[i] = max[i + 1] + 2;
                    if (j + 1 < len) {
                        max[i] += max[j + 1];
                    }
                }
            }
            res = Math.max(res, max[i]);
        }
        return res;
    }
}
