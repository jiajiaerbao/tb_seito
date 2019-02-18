package dfsNormal;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("Duplicates")
public class L46 {
    /******************************第一遍********************************************/
    /*
    * 坑 1: 坑BOSS, 这道题按照九章算法的讲法来做吧, 而且你的搜索树也能画的明白
    *
    * */
    public List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        if (nums == null) {
            return res;
        }
        helper(nums, new boolean[nums.length], new ArrayList<>(), res);
        return res;
    }

    public void helper(int[] nums, boolean[] visited, List<Integer> path, List<List<Integer>> res) {
        if (path.size() == nums.length) {
            res.add(new ArrayList<>(path));
            return;
        }
        for (int i = 0; i < nums.length; i++) {
            if (visited[i]) {
                continue;
            }
            path.add(nums[i]);
            visited[i] = true;
            helper(nums, visited, path, res);
            visited[i] = false;
            path.remove(path.size() - 1);
        }
    }
}
