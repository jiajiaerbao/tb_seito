package stack;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

@SuppressWarnings("Duplicates")
public class L772 {
    /******************************第一遍********************************************/
    /*
    * 坑 1: 坑 BOSS, 要学会把这个问题break down, 比如
    *       把 输入的字符 分类, 数字  /  运算符和括号  /  其他
    *       数字: 拼数
    *       运算符和括号: 单独函数处理, 其中计算加减乘除的逻辑也单独处理
    *       其他: 比如 空格, 跳过
    *           运算符和括号:
    *                 ( : 左括号, 压栈
    *                 ) : 右括号, 执行计算逻辑, 知道遇到左括号为止
    *                 运算符: 比栈顶的 低 优先级的话, 压栈;
    *                        比栈顶的 高 优先级的话, 把结果先计算出来,
    *
    * 坑 2: 拼数的代码还是不熟练:
    *                //用来存放暂时计算出来的值
    *                int val = 0;
    *                //这里要用 s.charAt(i) 而不能用 curCh, 因为 i 在这里放生了变化
    *                while (i < s.length() && s.charAt(i) >= '0' && s.charAt(i) <= '9') {
    *                    //这里是 val = 而不是 val+=
    *                    val = val * 10 + (s.charAt(i) - '0');
    *                    i++;
    *                }
    *                //碰到了非零的数或者走到了尽头之后, 放入到数字的stack里面
    *                digitStack.push(val);
    * */
    public int calculate(String s) {
        if (s == null) {
            throw new IllegalArgumentException("123");
        }
        Map<Character, Integer> operatorMap = new HashMap<>();
        Stack<Integer> digitStack = new Stack<>();
        Stack<Character> operatorStack = new Stack<>();

        operatorMap.put('-', 1);
        operatorMap.put('+', 1);
        operatorMap.put('*', 2);
        operatorMap.put('/', 2);
        operatorMap.put('$', 0);

        int i = 0;
        while (i < s.length()) {
            char curCh = s.charAt(i);
            if (curCh >= '0' && curCh <= '9') {
                int val = 0;
                while (i < s.length() && s.charAt(i) >= '0' && s.charAt(i) <= '9') {
                    val = val * 10 + (s.charAt(i) - '0');
                    i++;
                }
                digitStack.push(val);
            } else if (curCh == '(' || curCh == ')' || operatorMap.keySet().contains(curCh)) {
                addOptr(curCh, digitStack, operatorStack, operatorMap);
                i++;
            } else if (curCh == ' ') {
                i++;
            } else {
                throw new IllegalArgumentException("1212");
            }
        }
        addOptr('$', digitStack, operatorStack, operatorMap);
        return digitStack.peek();
    }

    private void addOptr(char curCh, Stack<Integer> digitStack, Stack<Character> operatorStack, Map<Character, Integer> operatorMap) {
        if (curCh == '(') {
            operatorStack.push(curCh);
        } else if (curCh == ')') {
            while (!operatorStack.isEmpty()) {
                char optr = operatorStack.pop();
                if (optr == '(') {
                    break;
                }
                int num2 = digitStack.pop();
                int num1 = digitStack.pop();
                digitStack.push(cal(num1, num2, optr));
            }
        } else if (operatorMap.containsKey(curCh)) {
            int optrWeight = operatorMap.get(curCh);
            while (!operatorStack.isEmpty()) {
                char top = operatorStack.peek();
                Integer weight = operatorMap.get(top);
                //这里还是没有理解, 那具体例子来说明
                //1+2*3: 从左向右扫描, 当+已经在栈里, 即将入栈的是*, 直接压栈
                //1*2+3: 从左向右扫描, 当*已经在栈里, 即将入栈的是+, 先计算运算符栈里的结果
                if (weight == null || weight < optrWeight) {
                    break;
                }
                int num2 = digitStack.pop();
                int num1 = digitStack.pop();
                char optr = operatorStack.pop();
                digitStack.push(cal(num1, num2, optr));
            }
            operatorStack.push(curCh);
        } else {
            throw new IllegalArgumentException();
        }
    }

    private int cal(int num1, int num2, char optr) {
        switch (optr) {
            case '+':
                return num1 + num2;
            case '-':
                return num1 - num2;
            case '*':
                return num1 * num2;
            case '/':
                return num1 / num2;
            default:
                throw new IllegalStateException();
        }
    }

    public static void main(String[] str) {
        L772 l772 = new L772();
        System.out.println(l772.calculate("2147483647"));
    }
}
