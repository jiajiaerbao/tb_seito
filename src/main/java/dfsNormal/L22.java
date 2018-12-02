package dfsNormal;

import java.util.ArrayList;
import java.util.List;

public class L22 {
    /*
    * 通过这道题要掌握括号匹配问题的特点:
    * 1.定义一个 delta, 在中间的任意时刻, 左括号的数量一定大于等于右括号的数量
    * 2.结束的时候, delta一定是等于零
    * */

    private String LEFT_PARENTHES = "(";
    private String RIGHT_PARENTHES = ")";
    public List<String> generateParenthesis(int n) {
        List<String> res = new ArrayList<>();
        if(n <= 0){
            return res; //这里要抛出异常, 像throw new IllegalArgumentException("Invalid Input!")
        }
        //using helper to construct result
        helper(n, n, 0, new StringBuilder(), res);
        return res;
    }
    private void helper(final int left, final int right, final int delta, StringBuilder path, final List<String> res){
        if(left == 0 && right == 0 && delta == 0){
            res.add(path.toString());
        }
        if(delta < 0 || left < 0 || right < 0){
            return;
        }
        int size = path.length();

        //这里可以优化, 只有当左括号个数大于 1 个的时候, 才消耗左括号
        if(left > 0){
            path.append(LEFT_PARENTHES);
            helper(left-1, right, delta+1, path, res);
            path.setLength(size);
        }
        //这里可以优化, 只有当左括号个数大于右括号的个数时, 才消耗右括号
        if(left < right){
            path.append(RIGHT_PARENTHES);
            helper(left, right-1, delta-1, path, res);
            path.setLength(size);
        }
    }
}
