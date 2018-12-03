package dfsNormal;

import java.util.*;

public class L40 {
    /*
     * 坑 1: 每个元素只能用一次
     * 坑 2: candidates里面可能有重复的元素
     * 坑 3: 在调用了下一层的 dfs 之后, 用 while 来去重复, 就是跳过接下来的 相同数值的元素 (前提是candidates有序)
     * */
    public List<List<Integer>> combinationSum2(int[] candidates, int target) {
        List<List<Integer>> res = new ArrayList<>();
        if (candidates == null || candidates.length == 0) {
            return res;
        }
        Arrays.sort(candidates);
        helper(candidates, target, res, new ArrayList<Integer>(), 0, 0);
        return res;
    }

    private void helper(int[] candidates, int target, List<List<Integer>> res, List<Integer> path, int curSum, int idx) {
        if (curSum == target) {
            res.add(new ArrayList<>(path));
            return;
        }
        if (curSum > target || idx >= candidates.length) {
            return;
        }
        for (int i = idx; i < candidates.length; i++) {
            path.add(candidates[i]);
            helper(candidates, target, res, path, curSum + candidates[i], i + 1);
            path.remove(path.size() - 1);
            //去掉 [1, 1, 2, 5] 产生 2 个 [1, 2, 5]的情况
            while (i < candidates.length - 1 && candidates[i] == candidates[i + 1]) {
                i++;
            }
        }
    }

    /*
    *  你自己的做法: 用 HashSet 来强制去重复
    public List<List<Integer>> combinationSum2(int[] candidates, int target) {
        List<List<Integer>> res = new ArrayList<>();
        if (candidates == null || candidates.length == 0) {
            return res;
        }
        Arrays.sort(candidates);
        Set<List<Integer>> set = new HashSet<>();
        helper(candidates, target, set, new ArrayList<Integer>(), 0, 0);
        return new ArrayList<>(set);
    }

    private void helper(int[] candidates, int target, Set<List<Integer>> res, List<Integer> path, int curSum, int idx) {
        if (curSum == target) {
            res.add(new ArrayList<>(path));
            return;
        }
        if (curSum > target || idx >= candidates.length) {
            return;
        }
        for (int i = idx; i < candidates.length; i++) {
            path.add(candidates[i]);
            helper(candidates, target, res, path, curSum + candidates[i], i + 1);
            path.remove(path.size() - 1);
        }
    }
    * */
}
