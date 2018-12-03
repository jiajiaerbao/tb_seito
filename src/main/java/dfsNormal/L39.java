package dfsNormal;

import java.util.*;

public class L39 {
    /*
     *  坑 1: 每一个位置的元素都可以是重复的预案
     *  坑 2: candidates是无序的, 事先排序的话, 有助于提高效率, 但是不排序也不影响结果
     *  坑 3: helper 里面的 for loop, for loop 的 DFS 的candidates的位置是从 i 开始, 而不是从 0 开始, 这里你错的最大的地方
     * */

    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        List<List<Integer>> res = new ArrayList<>();
        if (candidates == null || candidates.length == 0) {
            return res;
        }
        //在做DFS之前先sort一下, 这样执行的效率高
        Arrays.sort(candidates);
        hepler(candidates, target, res, new ArrayList<Integer>(), 0, 0);
        return res;
    }

    /*
     * 对于candidates Array, 要记录当前的 index
     * for loop 的 i 的起始位置是 上一层的 扫到的 array 的位置 i (函数参数里面的 idx), 而不是从 0 开始
     * */
    private void hepler(int[] candidates, int target, List<List<Integer>> res, List<Integer> path, int curSum, int idx) {
        if (curSum == target) {
            res.add(new ArrayList<>(path));
            return;
        }
        if (curSum > target || idx >= candidates.length) {
            return;
        }
        //这里是关键, i 只是从 idx 开始寻找, 而不是从第 0 个位置开始寻找
        for (int i = idx; i < candidates.length; i++) {
            int val = candidates[i];
            path.add(val);
            hepler(candidates, target, res, path, curSum + val, i);
            path.remove(path.size() - 1);
        }
    }
}
