package dfsNormal;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("Duplicates")
public class L216 {
    /******************************第二遍********************************************/
    /*
    * 坑 1: 关键是明确 cur 和 curSum 的含义:
    *           在进入到下一层 helper 函数之前, curSum 和 cur 都已经在上一层计算过了
    *           所以下一层是从 cur+1 开始计算
    * */
    public List<List<Integer>> combinationSum3_2(int k, int n) {
        List<List<Integer>> res = new ArrayList<>();
        if (k <= 0 || n < 0) {
            return res;
        }
        helper_2(k, n, res, new ArrayList<>(), 0, 0);
        return res;
    }

    private void helper_2(int k, int n, List<List<Integer>> res, List<Integer> path, int curSum, int cur) {
        if (path.size() == k && curSum == n) {
            res.add(new ArrayList<>(path));
            return;
        }
        if (path.size() > k || curSum > n) {
            return;
        }
        for (int i = cur + 1; i <= 9; i++) {
            path.add(i);
            helper_2(k, n, res, path, curSum + i, i);
            path.remove(path.size() - 1);
        }
    }

    /******************************第一遍********************************************/
    /*
    * 坑 1: 每个数字只能用一次, 而且数字的集合是从 1 开始, 到 9 结束
    * 坑 2: 结果的数字集合的所有数字的总长度是 k, 所有数字的和是 n
    * 坑 3: 在for loop里面, 因为你已经处理过 i 了, 所以进入下一层for loop的时候, 需要从 i+1  开始
    * */
    public List<List<Integer>> combinationSum3_1(int k, int n) {
        List<List<Integer>> res = new ArrayList<>();
        //corner case
        if (n <= 0) {
            return res;
        }
        helper_1(res, k, n, new ArrayList<>(), 0, 1);
        return res;
    }

    private void helper_1(List<List<Integer>> res, int k, int n, List<Integer> curPath, int curSum, int curIdx) {
        if (curSum == n && curPath.size() == k) {
            res.add(new ArrayList<>(curPath));
            return;
        }
        if (curSum > n || curPath.size() > k) {
            return;
        }
        int size = curPath.size();
        for (int i = curIdx; i < 10; i++) {
            curPath.add(i);
            helper_1(res, k, n, curPath, curSum + i, i + 1);
            curPath.remove(curPath.size() - 1);
        }
    }
}
