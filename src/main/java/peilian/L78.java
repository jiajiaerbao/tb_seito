package peilian;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("Duplicates")
public class L78 {
    /******************************第一遍********************************************/
    /*
    * 坑 1: 坑BOSS, 把当前的结果List<Integer>放入到结果集List<List<Integer>>里面的时候, 一定要deep copy
    * 坑 2: helper不需要base case, 因为当 idx==nums.length 的时候, curList里面的结果也需要保存(是从上一层传下来的)
    *
    * */
    public List<List<Integer>> subsets(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        //corner case
        if (nums == null || nums.length == 0) {
            return res;
        }
        helper(nums, res, 0, new ArrayList<>());
        return res;
    }

    private void helper(int[] nums, List<List<Integer>> res, int idx, List<Integer> curList) {
        /*if (idx == nums.length) { //这里如果改成 idx > nums.length 的话, 可以通过,
            return;                 //但是没有意义, idx最多可以走到 idx==nums.length的位置
        }*/
        res.add(new ArrayList<>(curList));
        for (int i = idx; i < nums.length; i++) {
            curList.add(nums[i]);
            helper(nums, res, i + 1, curList);
            curList.remove(curList.size() - 1);
        }
    }
}
