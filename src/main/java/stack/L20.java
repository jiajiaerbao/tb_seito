package stack;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

@SuppressWarnings("Duplicates")
public class L20 {

    /******************************第二遍********************************************/
    /*
    * 坑 1: 构建map的时候, 把左括号放在value的位置,
    *       因为扫描的时候, 当遇到了右括号, 需要通过map.get(右括号)来找左括号
    * */
    public boolean isValid(String s) {
        if (s == null || s.length() == 0) {
            return true;
        }
        char[] chars = s.toCharArray();
        Map<Character, Character> map = new HashMap<>();
        map.put(')', '(');
        map.put('}', '{');
        map.put(']', '[');
        Stack<Character> stack = new Stack<>();
        for (char curCh : chars) {
            if (map.values().contains(curCh)) {
                stack.push(curCh);
            } else {
                if (stack.isEmpty()) {
                    return false;
                }
                if (stack.peek() != map.get(curCh)) {
                    return false;
                }
                stack.pop();
            }
        }
        return stack.isEmpty();
    }
    /******************************第一遍********************************************/
    /*
    * 坑 1: 很笨重, 代码太长, 没用充分利用 map 和 左右括号对称的性质, 比如 MAP<Key:右括号, Value:左括号>
    *
    * */
    public boolean isValid_2(String s) {
        //'(', ')', '{', '}', '[' and ']'
        final char leftT1 = '(';
        final char rightT1 = ')';
        final char leftT2 = '{';
        final char rightT2 = '}';
        final char leftT3 = '[';
        final char rightT3 = ']';

        char[] chars = s.toCharArray();
        Stack<Character> stack = new Stack<>();

        for (char ch : chars) {
            if (ch == leftT1 || ch == leftT2 || ch == leftT3) {
                stack.push(ch);
            } else if (ch == rightT1) {
                if (stack.isEmpty()) {
                    return false;
                }
                if (stack.peek() != leftT1) {
                    return false;
                } else {
                    stack.pop();
                }
            } else if (ch == rightT2) {
                if (stack.isEmpty()) {
                    return false;
                }
                if (stack.peek() != leftT2) {
                    return false;
                } else {
                    stack.pop();
                }
            } else if (ch == rightT3) {
                if (stack.isEmpty()) {
                    return false;
                }
                if (stack.peek() != leftT3) {
                    return false;
                } else {
                    stack.pop();
                }
            } else {
                return false;
            }
        }
        return stack.isEmpty();
    }
}
