package dfsPruning;

public class L44 {
    /*
    * 坑 1: 坑BOOS, 这道题跟L10不同的地方是  * 可以作为开头, 而且
    *       L10: '*' Matches zero or more of the preceding element, 就是*仅仅是可以复制任意多个(包括零个)前一个字符
    *       L44: '*' Matches any sequence of characters (including the empty sequence). 可以代表任意长度的字符串
    * 坑 2: 切入点: 当 idxP 不越界的前提下,
    *         以当前节点 idxP 的字符 是否 是P 来进行分叉:
    *             1. 如果是 * 的话, 则可以匹配任意 0~n 任意多个字符
    *             2. 如果不是 * 的话, 则只能匹配一个字符
    * 坑 3: while loop的i++注意一下写的位置
    * 坑 4: 如果不加pruning的话, 会超时
    * 坑 5: 这道题比L10简单
    *
    * */
    public boolean isMatch(String s, String p) {
        if (s == null || p == null) {
            return false;
        }
        int lenS = s.length();
        int lenP = p.length();
        Boolean[][] mem = new Boolean[lenS + 1][lenP + 1];
        return dfs(s, p, 0, 0, mem);
    }

    private boolean dfs(String s, String p, int idxS, int idxP, Boolean[][] mem) {
        if (mem[idxS][idxP] != null) {
            return mem[idxS][idxP];
        }
        if (idxP == p.length()) {
            return idxS == s.length();
        }
        if (p.charAt(idxP) == '*') {
            for (int i = idxS - 1; i < s.length(); i++) {               //可以吃掉零个和多个字符(i最多可以移动到末尾)
                if (dfs(s, p, i + 1, idxP + 1, mem)) {
                    mem[idxS][idxP] = true;
                    return true;
                }
            }
        } else {
            if (idxS < s.length() && (s.charAt(idxS) == p.charAt(idxP) || p.charAt(idxP) == '?')) { //这里的idxP不可能是*
                if (dfs(s, p, idxS + 1, idxP + 1, mem)) {
                    mem[idxS][idxP] = false;
                    return true;
                } else {
                    mem[idxS][idxP] = false;
                    return false;
                }
            }
        }
        mem[idxS][idxP] = false;
        return false;
    }
}
