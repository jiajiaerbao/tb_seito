package dfsNormal;

import java.util.*;

@SuppressWarnings("Duplicates")
public class L40 {
    /******************************第二遍********************************************/
    /*
    * 坑 1: 首先DFS的函数参数的含义要明确, 比如 idx, path, res, curSum, 尤其是 idx 和 curSum
    *       尤其是 idx 和 cuSum, 这里是每次传入到下一层dfs的时候, curSum里面已经包括了idx位置上的值了
    * 坑 2: 去除重复的位置要注意, 一定是在backtrack之后, 也就是idx位置上面的值, 已经放入过path里面,
    *       而且已经调用过下一层的dfs了, 在这之后再用while来去除重复的值
    * */
    public List<List<Integer>> combinationSum2_2(int[] candidates, int target) {
        List<List<Integer>> res = new ArrayList<>();
        if (candidates == null || candidates.length == 0) {
            return res;
        }
        Arrays.sort(candidates);
        helper_2(candidates, target, 0, new ArrayList<>(), res, 0);
        return res;
    }

    private void helper_2(int[] candidates, int target, int idx, List<Integer> path, List<List<Integer>> res, int curSum) {
        if (curSum == target) {
            res.add(new ArrayList<>(path));
            return;
        }
        if (curSum > target) {
            return;
        }
        //i包括idx, 并且 path里面也包括idx的值
        for (int i = idx; i < candidates.length; i++) {
            path.add(candidates[i]);
            //进入下一层的时候, 是从i+1开始的
            helper_2(candidates, target, i + 1, path, res, curSum + candidates[i]);
            path.remove(path.size() - 1);
            //这里去除重复要注意
            while (i < candidates.length - 1 && candidates[i] == candidates[i + 1]) {
                i++;
            }
        }
    }
    /******************************第一遍********************************************/
    /*
     * 坑 1: 每个元素只能用一次
     * 坑 2: candidates里面可能有重复的元素
     * 坑 3: 在调用了下一层的 dfs 之后, 用 while 来去重复, 就是跳过接下来的 相同数值的元素 (前提是candidates有序)
     * */
    public List<List<Integer>> combinationSum2_1(int[] candidates, int target) {
        List<List<Integer>> res = new ArrayList<>();
        if (candidates == null || candidates.length == 0) {
            return res;
        }
        Arrays.sort(candidates);
        helper_1(candidates, target, res, new ArrayList<Integer>(), 0, 0);
        return res;
    }

    private void helper_1(int[] candidates, int target, List<List<Integer>> res, List<Integer> path, int curSum, int idx) {
        if (curSum == target) {
            res.add(new ArrayList<>(path));
            return;
        }
        if (curSum > target || idx >= candidates.length) {
            return;
        }
        for (int i = idx; i < candidates.length; i++) {
            path.add(candidates[i]);
            helper_1(candidates, target, res, path, curSum + candidates[i], i + 1);
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
