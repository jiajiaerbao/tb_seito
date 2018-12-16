package dfsNormal;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("Duplicates")
public class L282 {
    /*
     * 坑 0: BOSS 坑: curSum是当前path表达式的值; lastVal是curPath里面的最后的一个独立的数
     * 坑 1: 中间结果用 long 来保存
     * 坑 2: 拼数的 value 是从 num 里面 用 charAt() 一个数字一个数字 取出来之后计算的, 而不是用 subString 计算的
     * 坑 3: 针对坑2, 用 charAt() 取出了 char 之后, 记得 减去 '0', 计算出数值
     * 坑 4: 当第一次有数字加入到结果集当中的时候, 特殊处理一下
     * 坑 5: 当前处理的是 index 是 i, 接下来处理的 i+1 位置上的元素
     * 坑 6: 每次在加, 减, 乘法 后面加的数字是 curPath里面最新的最后一个数字, 乘法的时候尤其注意, 传到下一层dfs的lastVal是lastVal * curVal, append到curPath的是curVal
     * 坑 7: 计算 curSum 的时候, 千万千万要小心: 用 curSum +/- curVal, 而不是 lastVal, 因为 lastVal已经包含在 curSum 里面了; 而 * 的时候, 需要先减去 -lastVal 再加上 +lastVal * curVal, 因为乘法的优先级高
     * 坑 8: 中间结果的 curVal 也要是 long 类型的, 否则会溢出
     * **************************第二遍*****************************
     * 坑 1: idx: 下一个要取出来的数字
     * 坑 2: 坑BOSS: path里面已经包含了lastVal, curSum的值就是path的表达式的值
     * 坑 3: 可以多个digits组成一个数字
     * 坑 4: 运算符加到path上面的时候, 运算符加在path的前面: 区分成两种情况: 第一组数字  第二组及后面的数字
     * 坑 5: 在将字符转换为数字的时候, 注意越界, Integer不够用, 需要用Long, 而且 lastVal和 sum都要用 Long
     *       这里要注意转换的公式也要变成: Long.valueOf()
     * 坑 6: 在调用下一层的dfs的时候, 参数千万不要传递错了: 比如该调用val的地方, 你写成了lastVal
     * 坑 7: 这道题的特殊test case: if (val == 0) {break;}
     *      遇到val是0的时候, 直接退出, 防止出现以0开头的多位数字的情况, 比如"105" --> "1*5"的情况是不对的
     * */
    public List<String> addOperators(String num, int target) {
        List<String> res = new ArrayList<>();
        if (num == null || num.length() == 0) {
            return res;
        }
        helper(num, target, res, 0, 0, new StringBuilder(), 0);
        return res;
    }

    private void helper(String num, int target, List<String> res, long lastVal, int idx, StringBuilder curPath, long curSum) {
        if (idx == num.length() && curSum == target) {
            /*System.out.println("curPath0:" + curPath);*/
            res.add(curPath.toString());
            return;
        }
        if (idx >= num.length()) {
            return;
        }
        long curVal = 0;
        int size = curPath.length();
        for (int i = idx; i < num.length(); i++) {
            curVal = curVal * 10 + (num.charAt(i) - '0');
            if (size == 0) {
                curPath.append(curVal);
                helper(num, target, res, curVal, i + 1, curPath, curSum + curVal);
                curPath.setLength(size);
            } else {
                //plus
                curPath.append("+").append(curVal);
                helper(num, target, res, curVal, i + 1, curPath, curSum + curVal);
                /*System.out.println("curPath1:" + curPath + ",   curSum:" + (curSum + curVal));*/
                curPath.setLength(size);
                //minus
                curPath.append("-").append(curVal);
                helper(num, target, res, -curVal, i + 1, curPath, curSum - curVal);
                /*System.out.println("curPath2:" + curPath + ",   curSum:" + (curSum - curVal));*/
                curPath.setLength(size);
                //product
                curPath.append("*").append(curVal);
                helper(num, target, res, lastVal * curVal, i + 1, curPath, curSum - lastVal + lastVal * curVal);
                /*System.out.println("curPath3:" + curPath + ",   curSum:" + (curSum - lastVal + lastVal * curVal));*/
                curPath.setLength(size);
            }
            if (curVal == 0) {
                break;
            }
        }
    }

    /*************************************第二遍***************************************************/
    public List<String> addOperators2(String num, int target) {
        List<String> res = new ArrayList<>();
        if (num == null || num.length() == 0) {
            return res;
        }
        dfs(num, target, 0, 0, new StringBuilder(), 0, res);
        return res;
    }

    private void dfs(String s, int target,
                     long sum, long lastVal,
                     StringBuilder path, int idx,
                     List<String> res) {
        if (idx == s.length() && sum == target) {
            res.add(path.toString());
            return;
        }
        if (idx >= s.length()) {
            return;
        }
        int size = path.length();
        for (int i = idx + 1; i <= s.length(); i++) {
            long val = Long.valueOf(s.substring(idx, i));
            if (size == 0) {
                path.append(val);
                dfs(s, target, val, val, path, i, res);
                path.setLength(size);
            } else {
                //+
                path.append("+").append(val);
                dfs(s, target, sum + val, val, path, i, res);
                path.setLength(size);
                //-
                path.append("-").append(val);
                dfs(s, target, sum - val, -val, path, i, res);
                path.setLength(size);
                //*
                path.append("*").append(val);
                dfs(s, target, sum - lastVal + lastVal * val, val * lastVal, path, i, res);
                path.setLength(size);
            }
            if (val == 0) {
                break;
            }
        }
    }
}













































