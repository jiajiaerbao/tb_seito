package bfs;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class L77 {
    /*
    * 坑 1: 这里用DFS来解:
    *           a.不改变当前的path长度, 使当前value移动到下一个位置, 从下一个位置开始统计当前path的组合
    *           b.改变当前的path长度, 把当前value加到当前的path里面, 然后当前 value 移动到下一个位置
    *               ****这时注意要set bach回去
    * 坑 2: 这道题跟你Uber电面挂掉的那道题很像, 数字的分叉问题, 逻辑要清晰, 每一位都要分叉, 放入到path里面, 然后想好base case
    * 坑 3: 出错误的地方就是进入下一层dfs的时候, 不是把cur放到里面, 而是把i放到dfs里面
    * */
    public List<List<Integer>> combine(int n, int k) {
        List<List<Integer>> res = new ArrayList<>();
        if (n <= 0 || k <= 0 || n < k) {
            return res;
        }
        //注意: current value 是 1, current size 是 0
        dfs(res, new ArrayList<Integer>(), n, 1, 0, k);
        return res;
    }

    private void dfs(List<List<Integer>> res, List<Integer> path, int n, int curVal, int curSize, int k) {
        if (curSize == k) {
            res.add(new ArrayList<>(path));
            return;
        }

        if (curSize > k || curVal > n) {
            return;
        }

        dfs(res, path, n, curVal + 1, curSize, k);

        path.add(curVal);
        dfs(res, path, n, curVal + 1, curSize + 1, k);
        path.remove(path.size() - 1);
    }


    /*******************************************************/
    public List<List<Integer>> combine2(int n, int k) {
        List<List<Integer>> res = new ArrayList<>();
        if (n < k) {
            return res;
        }
        dfs(n, k, 1, new ArrayList<>(), res);
        return res;
    }

    private void dfs(int n, int k, int cur, List<Integer> path, List<List<Integer>> res) {
        if (path.size() == k) {
            res.add(new ArrayList<>(path));
            return;
        }
        for (int i = cur; i <= n; i++) {
            path.add(i);
            dfs(n, k, i + 1, path, res);
            path.remove(path.size() - 1);
        }
    }
}





































