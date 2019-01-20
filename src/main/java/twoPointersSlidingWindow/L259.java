package twoPointersSlidingWindow;

import java.util.Arrays;


/*
* 坑 1: two pointer的变形, 前提一定是sort好的, 所以先要自己sort一下
* */
@SuppressWarnings("Duplicates")
public class L259 {
    public int threeSumSmaller(int[] nums, int target) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        Arrays.sort(nums);
        int res = 0;
        for (int i = 0; i < nums.length; i++) {
            int remain = target - nums[i];
            int left = i + 1, right = nums.length - 1;
            while (left < right) {
                if (nums[left] + nums[right] < remain) {
                    res += right - left;
                    left++;
                } else {
                    right--;
                }
            }
        }
        return res;
    }
}
