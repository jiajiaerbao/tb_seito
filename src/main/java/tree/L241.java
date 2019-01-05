package tree;

import java.util.ArrayList;
import java.util.List;

/*
* 坑 1: 如果input里面没有运算符的话, 只是返回当前范围内的数:
* 坑 2: 对于input的 index, 区间是 [0, input.length), 是左闭右开
* 坑 3: input.charAt的范围是(0, input.length()-1)
* */
@SuppressWarnings("Duplicates")
public class L241 {
    public List<Integer> diffWaysToCompute(String input) {
        return dfs(input, 0, input.length());
    }

    //左闭右开 [start, end)
    private List<Integer> dfs(String input, int start, int end) {
        List<Integer> res = new ArrayList<>();
        if (start > end) {
            return res;
        }
        for (int i = start; i < end; i++) {
            char ch = input.charAt(i);
            if (ch == '+' || ch == '-' || ch == '*') {
                //左闭右开 [start, i)
                List<Integer> resLeft = dfs(input, start, i);
                //左闭右开 [i+1, end)
                List<Integer> resRight = dfs(input, i + 1, end);
                for (int valLeft : resLeft) {
                    for (int valRight : resRight) {
                        res.add(cal(ch, valLeft, valRight));
                    }
                }
            }
        }
        if (res.size() == 0) {
            //左闭右开 [start, end)
            res.add(Integer.valueOf(input.substring(start, end)));
            return res;
        }
        return res;
    }

    private Integer cal(char ch, int left, int right) {
        if (ch == '+') {
            return left + right;
        }
        if (ch == '-') {
            return left - right;
        }
        if (ch == '*') {
            return left * right;
        }
        return null;
    }
}
