package dfsNormal;

import java.util.ArrayList;
import java.util.List;

public class L216 {
    /*
    * 坑 1: 每个数字只能用一次, 而且数字的集合是从 1 开始, 到 9 结束
    * 坑 2: 结果的数字集合的所有数字的总长度是 k, 所有数字的和是 n
    * 坑 3: 在for loop里面, 因为你已经处理过 i 了, 所以进入下一层for loop的时候, 需要从 i+1  开始
    * */
    public List<List<Integer>> combinationSum3(int k, int n) {
        List<List<Integer>> res = new ArrayList<>();
        //corner case
        if (n <= 0) {
            return res;
        }
        helper(res, k, n, new ArrayList<>(), 0, 1);
        return res;
    }

    private void helper(List<List<Integer>> res, int k, int n, List<Integer> curPath, int curSum, int curIdx) {
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
            helper(res, k, n, curPath, curSum + i, i + 1);
            curPath.remove(curPath.size() - 1);
        }
    }
}
