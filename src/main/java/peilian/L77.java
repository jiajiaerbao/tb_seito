package peilian;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("Duplicates")
public class L77 {
    /******************************第一遍********************************************/
    /*
    * 坑 1: backtracking, path上加了一个, 再减去一个
    * 坑 2: for loop 里面的DFS, 进入下一层的函数是 helper(n, k, path, res, i);
    *       上一句是 path.add(i);, 所以下一层的时候, for loop需要从 idx+1 开始计算
    * */
    public List<List<Integer>> combine(int n, int k) {
        List<List<Integer>> res = new ArrayList<>();
        if (n <= 0 || n < k) {
            return res;
        }
        helper(n, k, new ArrayList<>(), res, 0);
        return res;
    }

    private void helper(int n, int k, List<Integer> path, List<List<Integer>> res, int idx) {
        if (path.size() == k) {
            res.add(new ArrayList<>(path));
            return;
        }
        for (int i = idx + 1; i <= n; i++) {
            path.add(i);
            helper(n, k, path, res, i);
            path.remove(path.size() - 1);
        }
    }
}
