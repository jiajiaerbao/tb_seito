package dfsNormal;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("Duplicates")
public class L22 {
    /******************************第二遍********************************************/
    /*
    * 坑 1: 分叉: 加左括号或者加右括号
    * */
    public List<String> generateParenthesis(int n) {
        List<String> res = new ArrayList<>();
        if (n <= 0) {
            return res;
        }
        helper(0, 0, new StringBuilder(), res, n);
        return res;
    }

    public void helper(int leftCnt, int rightCnt, StringBuilder path, List<String> res, int n) {
        if (leftCnt == rightCnt && leftCnt == n) {
            res.add(path.toString());
        }
        if (leftCnt < rightCnt || leftCnt > n) {
            return;
        }
        int size = path.length();

        path.append("(");
        helper(leftCnt + 1, rightCnt, path, res, n);
        path.setLength(size);

        path.append(")");
        helper(leftCnt, rightCnt + 1, path, res, n);
        path.setLength(size);
    }
    /******************************第一遍********************************************/

    /*
    * 坑 1: 通过这道题要掌握括号匹配问题的特点:
    *           a. 定义一个 delta, 在中间的任意时刻, 左括号的数量一定大于等于右括号的数量
    *           b.结束的时候, delta一定是等于零
    * 坑 2: 理解分叉:
    *           a. 加左括号
    *           b. 加右括号
    * 坑 3: base case:
    *           a. 成功情况: 左括号和右括号的数量都是n, 刚好满足条件
    *           b. 失败情况: 左括号数量小于右括号数量(delta < 0), 或者 左括号数量大于n
    * */

    private String LEFT_PARENTHES = "(";
    private String RIGHT_PARENTHES = ")";
    public List<String> generateParenthesis_1(int n) {
        List<String> res = new ArrayList<>();
        if(n <= 0){
            return res; //这里要抛出异常, 像throw new IllegalArgumentException("Invalid Input!")
        }
        //using helper to construct result
        helper_1(n, n, 0, new StringBuilder(), res);
        return res;
    }
    private void helper_1(final int left, final int right, final int delta, StringBuilder path, final List<String> res){
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
            helper_1(left-1, right, delta+1, path, res);
            path.setLength(size);
        }
        //这里可以优化, 只有当左括号个数大于右括号的个数时, 才消耗右括号
        if(left < right){
            path.append(RIGHT_PARENTHES);
            helper_1(left, right-1, delta-1, path, res);
            path.setLength(size);
        }
    }

    /***********************************第二遍*****************************************************/

    public List<String> generateParenthesis_2(int n) {
        List<String> res = new ArrayList<>();
        dfs_2(n, 0, 0, res, new StringBuilder());
        return res;
    }

    private void dfs_2(int n, int left, int right, List<String> res, StringBuilder path) {
        if (left == n && right == n) {
            res.add(path.toString());
            return;
        }
        if (left < right || left > n) {
            return;
        }
        int size = path.length();
        path.append("(");
        dfs_2(n, left + 1, right, res, path);
        path.setLength(size);
        path.append(")");
        dfs_2(n, left, right + 1, res, path);
        path.setLength(size);
    }
}














































