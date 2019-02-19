package peilian;

import java.util.HashSet;
import java.util.Set;

@SuppressWarnings("Duplicates")
public class L219 {
    /******************************第一遍********************************************/
    /*
    * 坑 1: 坑 BOSS, 算法哥的题如果都弄懂了的话, 就够了, 比如这里, 你完全可以用L220的套路来解219题, 而且是直接秒掉的那种
    *       关键是反复做题 加上 反复看视频, 丰富你的笔记, 掌握算法哥的解题套路和思想, 这才是能够持续找到工作的根本
    * */
    public boolean containsNearbyDuplicate(int[] nums, int k) {
        if (nums == null || nums.length == 0 || k <= 0) {
            return false;
        }
        Set<Integer> set = new HashSet<>();
        for (int i = 0; i < nums.length; i++) {
            if (i > k) {
                set.remove(nums[i - k - 1]);
            }
            if (set.contains(nums[i])) {
                return true;
            }
            set.add(nums[i]);
        }
        return false;
    }
}
