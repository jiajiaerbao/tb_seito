package dfsPruning;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("Duplicates")
public class L294 {
    /*
    * 坑 1: 坑BOSS: back track一定要紧紧地跟在dfs的后面, 绝对不能在没有back track的情况下返回到上一层dfs
    *       在返回到上一层的dfs之前, 我们要把input的状态back track会上一层的状态
    *         chars[i] = '-';
    *         chars[i + 1] = '-';
    *         boolean res = dfs(chars);
    *         if (!res) {
    *            return true;
    *         }
    *         chars[i] = '+';
    *         chars[i + 1] = '+';
    *       上面这种写法, 如果res是false的话, 直接返回到上一层true, 而没有执行back track,
    *       所以没有back track就直接返回了, (返回的早了 没有做到back track, 这时候就影响了其他branch结果)
    *       也就是从下一层返回到上一层的chars 和 从上一层进入到下一层的chars不一样
    *       因为用的char[]所以返回前一定要做back track 不管true或false.
    *       如果input是string的话, 当前状态下的string不会在下一个dfs里被改变, 因为string是immutable的
    *
    * 坑 2: 这里String的状态是离散的, 所以用HashMap来做pruning
    *       如果是Integer的Bit operation的话, 可以用Array
    *       具体参见everNote 文档
    */
    public boolean canWin(String s) {
        if (s == null || s.length() == 0) {
            return false;
        }
        return dfs(s.toCharArray(), new HashMap<>());
    }

    private boolean dfs(char[] chars, Map<String, Boolean> map) {
        if(map.containsKey(String.valueOf(chars))){
            return map.get(String.valueOf(chars));
        }
        for (int i = 0; i < chars.length - 1; i++) {
            if (chars[i] == '+' && chars[i + 1] == '+') {
                chars[i] = '-';
                chars[i + 1] = '-';
                boolean res = dfs(chars, map);
                chars[i] = '+';
                chars[i + 1] = '+';
                if (!res) {
                    map.put(String.valueOf(chars), true);
                    return true;
                }
            }
        }
        map.put(String.valueOf(chars), false);
        return false;
    }
}
