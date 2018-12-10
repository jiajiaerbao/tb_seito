package dfsPruning;

public class L10 {
    /*
    * 坑 1: base case的切入点:
    *       不是从idxS 而是从 idxP:
    *       如果 idxS 走到末尾的话, 说明 s 都被 consume 光了, 这时候 idxP 不需要走到末尾 (后面可以剩下类似: a*b*c*之类的pattern)
    *       反过来思考, 如果 idxP 走到头的话, idxS 一定是在走到末尾的情况下, 才能说明匹配成功
    *       对idxP进行分叉:
    *               idxP == p.length
    *               idxP == p.length - 1 || p.charAt(idxP + 1) != * (如果idxP的下一个字符不是*的话, 则idxP必须匹配)
    *               p.charAt(idxP + 1) == * (这里进行分叉)
    * 坑 2: 坑BOSS: 处理分叉: 主要看p的下一个字符是什么? 如果是星号的话, 需要考虑分叉; 分叉的时候是一通百通
    * 坑 3: 着眼于p的下一个位置的字符是不是*进行分叉
    * 坑 4: 写 index 的时候, idxS, idxP, 记住: 前面的idx不要变
    * 坑 5: 每一个判断之前都要考虑 index的越界问题, 因为第一个if已经判断了 idxP的越界了,
    *       所有后面的所有 if 都要判断 idxS 的越界问题
    * 坑 5: 加了pruning之后, 进入到dfs之后, 首先判断memorization的数组或者hashMap里面是否有对应的结算结果
    * 坑 6: 处理分叉的时候, 主要考虑的是基于 i 坐标的对 s 的遍历
    * 坑 7: i < idxS || s.charAt(i) == p.charAt(idxP) || p.charAt(idxP) == '.'的逻辑一定要清晰:
    *       i 在idxS的前一个: ok
    *       i 在idxS上, 并且跟idxP的字符相同: ok; 注意: 在前一个i相同的前提下, i++: 这里i可以顺次向后移动
    *       如果idxP是.
    * 坑 8: 空串也可以匹配, 前面的corner case只是判断是否为 null 即可
    * */
    public boolean isMatch(String s, String p) {
        if (s == null || p == null) {
            return false;
        }
        int lenS = s.length();
        int lenP = p.length();
        Boolean[][] men = new Boolean[lenS + 1][lenP + 1];
        return dfs(s, p, 0, 0, men);
    }

    private boolean dfs(String s, String p, int idxS, int idxP, Boolean[][] men) {
        if (men[idxS][idxP] != null) {
            return men[idxS][idxP];
        }

        if (idxP == p.length()) {
            if (idxS == s.length()) {
                men[idxS][idxP] = true;
            } else {
                men[idxS][idxP] = false;
            }
            return men[idxS][idxP];
        }else if (idxP == p.length() - 1 || p.charAt(idxP + 1) != '*') {
            if(idxS < s.length() && (p.charAt(idxP) == '.' || s.charAt(idxS) == p.charAt(idxP))){
                if (dfs(s, p, idxS + 1, idxP + 1, men)) {
                    men[idxS][idxP] = true;
                    return true;
                } else {
                    men[idxS][idxP] = false;
                    return false;
                }
            }
        } else {
            int i = idxS - 1;
            while (i < s.length() && (i < idxS || s.charAt(i) == p.charAt(idxP) || p.charAt(idxP) == '.')) {
                if (dfs(s, p, i + 1, idxP + 2, men)) {
                    men[idxS][idxP] = true;
                    return true;
                }
                i++;
            }
        }
        men[idxS][idxP] = false;
        return false;
    }
}
