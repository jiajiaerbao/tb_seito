package peilian;

import java.util.Arrays;

@SuppressWarnings("Duplicates")
public class L217 {
    /******************************第一遍********************************************/
    /*
    * 坑 1: 这里先排序后check好, 这样的话, 空间复杂度降到最低
    * */
    public boolean containsDuplicate(int[] nums) {
        if (nums == null || nums.length <= 1) {
            return false;
        }
        Arrays.sort(nums);
        for (int i = 0; i < nums.length - 1; i++) {
            if (nums[i] == nums[i + 1]) {
                return true;
            }
        }
        return false;
    }
}
