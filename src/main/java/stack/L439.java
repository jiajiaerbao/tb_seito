package stack;

import java.util.Stack;

/*
* 坑 1: 坑BOSS, 在while loop里面, 要确保 i 在跳出每个 condition 之后, 都会顺次向左移动
*       如果是 : 的话, 什么也不做
*       如果是 ? 的话, 根据 ? 的前一个字符, 来判断是 重新push进去 conT 还是 conF
*       如果既不是 : 也不是 ? 的话, 无脑连接起来, 然后push进去
* 坑 2: 思路要清晰, 把不同的字符进行分类, 对每一个分类, stack需要进行什么操作, 要考虑清楚
* */
@SuppressWarnings("Duplicates")
public class L439 {
    public String parseTernary(String expression) {
        //corner case
        int i = expression.length() - 1;
        char[] chars = expression.toCharArray();
        Stack<String> stack = new Stack<>();
        while (i >= 0) {
            char curCh = chars[i];
            if (curCh == ':') {
                i--;
            } else if (curCh == '?') {
                String conT = stack.pop();
                String conF = stack.pop();
                char chFlg = chars[--i];
                if (chFlg == 'T') {
                    stack.push(conT);
                } else if (chFlg == 'F') {
                    stack.push(conF);
                } else {
                    throw new IllegalArgumentException();
                }
                //这里是坑, 千万不要忘记写了
                i--;
            } else {
                StringBuilder sb = new StringBuilder();
                while (i >= 0 && chars[i] != '?' && chars[i] != ':') {
                    sb.append(chars[i]);
                    //这里是坑
                    i--;
                }
                stack.push(sb.reverse().toString());
            }
        }
        return stack.pop();
    }
}
