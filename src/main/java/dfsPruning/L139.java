package dfsPruning;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SuppressWarnings("Duplicates")
public class L139 {
    public boolean wordBreak(String s, List<String> wordDict) {
        if (s == null || s.length() == 0 || wordDict == null || wordDict.size() == 0) {
            return false; //or throw Exception
        }
        Set<String> dict = new HashSet<>();
        for (String word : wordDict) {
            dict.add(word);
        }
        Boolean[] mem = new Boolean[s.length() + 1];
        return search(s, dict, 0, mem);
    }

    private boolean search(String s, Set<String> dict, int idx, Boolean[] mem) {
        if (idx == s.length()) {
            return true;
        }
        if (mem[idx] != null) {
            return mem[idx];
        }
        for (int i = idx + 1; i <= s.length(); i++) {
            String temp = s.substring(idx, i);
            //一通百通的逻辑, 当有一条DFS返回true的时候, 就返回true
            if (dict.contains(temp) && search(s, dict, i, mem)) {
                mem[idx] = true;
                return true;
            }
        }
        mem[idx] = false;
        return false;
    }
}
