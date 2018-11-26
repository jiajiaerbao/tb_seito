package bfs;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class L77 {
    /*
    * 这里用DFS来解:
    *   1.不改变当前的path长度, 使当前value移动到下一个位置, 从下一个位置开始统计当前path的组合
    *   2.改变当前的path长度, 把当前value加到当前的path里面, 然后当前 value 移动到下一个位置
    *     ****这时注意要set bach回去
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
}
