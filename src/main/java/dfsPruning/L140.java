package dfsPruning;

import java.util.*;

public class L140 {
    /*
    *  坑 1: 坑BOSS: 对于这种求List<String>或者List<List<String>>的解的问题,
    *           在处理pruning的时候, 主要考虑的是:
    *               进入到下一层dfs之前 和 从下一层dfs出来之后: 解集合的 size 是否发生了变化
    *  坑 2: pruning的初始数据要都设置为true: Arrays.fill(mem, true);
    *  坑 3: 逻辑要清晰, 在dict.contains该word的前提下, 并且mem为true的前提下, 才继续进入到下一层dfs
    * */
    public List<String> wordBreak(String s, List<String> wordDict) {
        List<String> res = new ArrayList<>();
        if (s == null || s.length() == 0 || wordDict == null || wordDict.size() == 0) {
            return res;
        }
        Set<String> dict = new HashSet<>();
        for (String word : wordDict) {
            dict.add(word);
        }
        boolean[] mem = new boolean[s.length() + 1];
        Arrays.fill(mem, true);

        search(s, dict, 0, new StringBuilder(), res, mem);

        return res;
    }

    private void search(String s, Set<String> dict, int idx, StringBuilder path, List<String> res, boolean[] mem) {
        if (idx == s.length()) {
            res.add(path.toString());
            return;
        }
        int size = res.size();
        for (int i = idx + 1; i <= s.length(); i++) {
            String str = s.substring(idx, i);

            if (dict.contains(str) && mem[i]) {
                int len = path.length();
                if (path.length() == 0) {
                    path.append(str);
                } else {
                    path.append(" ").append(str);
                }
                search(s, dict, i, path, res, mem);
                path.setLength(len);
            }

        }
        if (size == res.size()) {
            mem[idx] = false;
        }
    }
}
