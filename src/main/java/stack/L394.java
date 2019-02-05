package stack;

import java.util.Stack;

/*
* 坑 1: 处理右括号的逻辑不清晰
*           从 number stack 里面 pop 出来数字 NUM
*           从 String stack 里面 pop 出来字符串 STR
*           用 一个新的 StringBuilder 来生成 重复字符串STR  固定次数NUM 的 字符串
*
*           再 append 到 String stack的最上面的字符串上面
* */
@SuppressWarnings("Duplicates")
public class L394 {
    public String decodeString(String s) {
        if (s == null || s.length() == 0) {
            return s;
        }
        Stack<Integer> integerStack = new Stack<>();
        Stack<StringBuilder> builderStack = new Stack<>();
        //这里预先压栈, 压进去一个, 防止栈被击穿 (3[a]2[bc])
        builderStack.push(new StringBuilder());
        int curVal = 0;
        for (char curCh : s.toCharArray()) {
            if (curCh >= '0' && curCh <= '9') {
                curVal = curVal * 10 + curCh - '0';
            } else if (curCh == '[') {
                integerStack.push(curVal);
                curVal = 0;
                builderStack.push(new StringBuilder());
            } else if (curCh == ']') {
                //这里的逻辑不清晰, 宁可多定义几个变量, 也要把逻辑屡清楚
                String subStr = builderStack.pop().toString();
                int subCnt = integerStack.pop();
                StringBuilder subSb = new StringBuilder();

                while (subCnt > 0) {
                    subSb.append(subStr);
                    subCnt--;
                }
                builderStack.peek().append(subSb);
            } else {
                builderStack.peek().append(curCh);
            }
        }
        return builderStack.peek().toString();
    }
}
