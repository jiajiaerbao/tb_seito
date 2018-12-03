package dfsNormal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class L90 {
    /*
     * 坑 1: 加入结果集的时间点是每当有一个新的元素加入到path里面的时候, 是在 for loop 里面, 而不是在base case 的地方, 因为不是定长的
     * 坑 2: 如何去掉重复元素: 这里跟你之前的做法类似: 在for loop里面, 第一次遇见的元素, 加入到结果集里面, 之后遇到的相同的元素, 全部跳过 (因为在 call helper之前, 已经排序了)
     * 坑 3: 在for loop里面, 每一次call helper的时候, 都是以当前位置 i+1 作为出发点的, 而不是 cur+1, 你已经不是第一次犯这个错误了, 因为你还没有完全理解dfs!!!!
     * */
    public List<List<Integer>> subsetsWithDup(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        res.add(new ArrayList<>());
        //corner case
        if (nums == null || nums.length == 0) {
            return res;
        }
        Arrays.sort(nums);
        helper(nums, res, 0, new ArrayList<>());
        return res;
    }

    private void helper(int[] nums, List<List<Integer>> res, int cur, List<Integer> curRes) {
        if (cur == nums.length) {
            return;
        }
        for (int i = cur; i < nums.length; i++) {
            //只能保证在开头 (i == index) 时, 选择连续重复的数字
            if (i != cur && nums[i] == nums[i - 1]) {
                continue;
            }
            curRes.add(nums[i]);
            res.add(new ArrayList<>(curRes));
            helper(nums, res, i + 1, curRes);
            curRes.remove(curRes.size() - 1);
        }
    }
}
