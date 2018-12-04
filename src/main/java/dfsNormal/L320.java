package dfsNormal;

import java.util.ArrayList;
import java.util.List;

public class L320 {
    /*
    * 坑 1: base case success: 在idx最后走到string的末尾的时候, 记得把剩下的lastVal加到path里面, 然后再保存进res里面
    * 坑 2: set back: 在把lastVal加入到path, path保存进res里面之后, 记得把path的长度恢复到原来的状态, 这样返回上一层的时候, 才不会改变上一层path的状态
    * 坑 3: 在base case success的里面, 只有当 lastVal 大于 0 的时候, 才添加到 path 里面
    * */
    public List<String> generateAbbreviations(String word) {
        List<String> res = new ArrayList<>();
        if (word == null || word.length() == 0) {
            res.add("");
            return res;
        }
        helper(word, res, 0, new StringBuilder(), 0);
        return res;
    }

    private void helper(String s, List<String> res, int idx, StringBuilder path, int lastVal) {
        if (idx == s.length()) {
            int size = path.length();
            if (lastVal > 0) {
                path.append(lastVal);
            }
            res.add(path.toString());
            path.setLength(size);
            return;
        }
        /*if (idx >= s.length()) {
            return;
        }*/
        int size = path.length();
        //keep
        if (lastVal > 0) {
            path.append(lastVal);
        }
        path.append(s.charAt(idx));
        helper(s, res, idx + 1, path, 0);
        path.setLength(size);
        //change
        if (lastVal > 0) {
            helper(s, res, idx + 1, path, lastVal + 1);
        } else {
            helper(s, res, idx + 1, path, 1);
        }
    }
}
