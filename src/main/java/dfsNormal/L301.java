package dfsNormal;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class L301 {
    /*
     * 坑 1: 坑BOSS: 必须要记下 invalid左括号的个数 和 invalid右括号的个数:
     *       这里左括号个数和右括号个数是 invalid 的括号数, 要保证 minimum 的话, 至少要去掉 所有的invalid左括号的和invalid右括号
     * 坑 2: base failure的情况是: 1.idx越界了; 2.rl:删多了; 2.rr:删多了; 3.delta:不满足条件;
     * 坑 3: 这里只是处理当前层的字符, 不要用for loop去遍历整个字符串, 仅仅是对当前字符而言, 留还是不留;
     * 坑 4: 有重复解, 必须去除重复, 用Set: 结果先存到HashSet里面, 用来去重复, 然后在导入到List里面
     * 坑 5: 结束条件是 idx >= s.length()
     * 坑 6: 必须记录delta, 否则的话, 无法确定中间过程和最终结果是否满足要求
     * 坑 7: 坑BOSS: DFS的精髓是只看下一层
     * 坑 8: input s不仅仅是 ( 和 ), 还有一些其他字符:
      *         在计算删除最少的 ( 和 )的时候, 要明确判断是否是 ( 和 )
      *         在dfs里面, 也要判断一些
     * */
    public List<String> removeInvalidParentheses(String s) {
        List<String> res = new ArrayList<>();
        if (s == null || s.length() == 0) {
            res.add("");
            return res;
        }

        int[] invalidRlRr = calRemove(s);

        Set<String> set = new HashSet<>();

        helper(s, set, invalidRlRr[0], invalidRlRr[1], 0, 0, new StringBuilder());

        return new ArrayList<>(set);
    }

    private void helper(String s, Set<String> res, int rl, int rr, int idx, int dta, StringBuilder path) {
        if (idx == s.length() && rl == 0 && rr == 0 && dta == 0) {
            res.add(path.toString());
            return;
        }
        if (idx >= s.length() || dta < 0 || rl < 0 || rr < 0 || dta < 0) {
            return;
        }
        int size = path.length();
        char c = s.charAt(idx);
        if (c == '(') {
            //move
            helper(s, res, rl - 1, rr, idx + 1, dta, path);
            //keep
            path.append(c);
            helper(s, res, rl, rr, idx + 1, dta + 1, path);
            path.setLength(size);

        } else if (c == ')') {
            //move
            helper(s, res, rl, rr - 1, idx + 1, dta, path);
            path.setLength(size);
            //keep
            path.append(c);
            helper(s, res, rl, rr, idx + 1, dta - 1, path);
            path.setLength(size);
        } else {
            path.append(c);
            helper(s, res, rl, rr, idx + 1, dta, path);
            path.setLength(size);
        }
    }

    private int[] calRemove(String s) {
        int rl = 0;
        int rr = 0;
        for (char c : s.toCharArray()) {
            if (c == '(') {
                rl++;
            } else if (c == ')') {
                if (rl > 0) {
                    rl--;
                } else {
                    rr++;
                }
            }
        }
        return new int[]{rl, rr};
    }

    /*******************************第二遍**********************************/
    public List<String> removeInvalidParentheses2(String s) {
        //corner case
        if (s == null || s.length() == 0) {
            List<String> res = new ArrayList<>();
            res.add("");
            return res;
        }

        int[] minLR = getMinParentheses(s);

        Set<String> res = new HashSet<>();

        helper(s, minLR[0], minLR[1], 0, res, new StringBuilder(), 0);

        return new ArrayList<>(res);
    }

    private void helper(String s, int minL, int minR, int delta, Set<String> res, StringBuilder path, int idx) {
        System.out.println("path: " + path.toString() + ";  idx: " + idx + ";  minL: " + minL + ";  minR: " + minR + ";  delte: " + delta);
        if (idx == s.length() && minL == 0 && minR == 0 && delta == 0) {
            res.add(path.toString());
            return;
        }
        if (idx >= s.length() || minL < 0 || minR < 0 || delta < 0) {
            return;
        }
        int size = path.length();
        char c = s.charAt(idx);
        if (s.charAt(idx) == '(') {
            //remove
            helper(s, minL - 1, minR, delta, res, path, idx + 1);
            //keep
            path.append("(");
            helper(s, minL, minR, delta + 1, res, path, idx + 1);
            path.setLength(size);
        } else if (s.charAt(idx) == ')') {
            //remove
            helper(s, minL, minR - 1, delta, res, path, idx + 1);
            //keep
            path.append(")");
            helper(s, minL, minR, delta - 1, res, path, idx + 1);
            path.setLength(size);
        } else {
            path.append(s.charAt(idx));
            helper(s, minL, minR, delta, res, path, idx + 1);
            path.setLength(size);
        }
    }

    private int[] getMinParentheses(String s) {
        int[] res = new int[2];
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '(') {
                res[0]++;
            } else if (c == ')') {
                if (res[0] > 0) {
                    res[0]--;
                } else {
                    res[1]++;
                }
            }
        }
        return res;
    }
}
















































