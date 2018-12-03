package dfsNormal;

import java.util.ArrayList;
import java.util.List;

public class L93 {
    /*
     *  坑 1: 坑 BOSS: 拼数思想: 拼的是数字, 因为IP总共能分成四部分数字, 每部分数字最多有三个数字
     *  坑 2: dfs 的 body, for loop 是依次累加取出之后的三个数字, 然后依次判断能否进入到下一层的 dfs, 这里最坑
     *  坑 3: 在取 subString 之前, 先判断是否越界, idx+i <= s.length()
     *  坑 4: 在 call 进入下一层 dfs 之后, 还要 set back
     *  坑 5: 加点".": 因为每一次都加点, 最后放入结果集的时候, 要把最后一个点给去掉
     * */
    public List<String> restoreIpAddresses(String s) {
        List<String> res = new ArrayList<>();
        //corner case
        if (s == null || s.length() == 0) {
            return res;
        }
        dfs(res, new StringBuilder(), s, 0, 0);
        return res;
    }

    private void dfs(List<String> res, StringBuilder curPath, String str, int idx, int part) {
        if (part == 4 && idx == str.length()) {
            curPath.setLength(curPath.length() - 1);
            res.add(curPath.toString());
            return;
        }
        if (part > 4 || idx >= str.length()) {
            return;
        }
        int size = curPath.length();
        for (int i = 1; i <= 3; i++) {
            //越界判断是判断是否大于str.length(), 而不包括等于
            if (idx + i > str.length()) {
                break;
            }
            int val = Integer.valueOf(str.substring(idx, idx + i));
            if (val >= 0 && val < 256) {
                curPath.append(val).append(".");
                //这里是 idx + i, 因为 i 只是取接下来的 3 个数字
                //这里只是判断下一部分(总共四部分)
                dfs(res, curPath, str, idx + i, part + 1);
                curPath.setLength(size);
            }
            if (val == 0) {
                break;
            }
        }
    }
}
