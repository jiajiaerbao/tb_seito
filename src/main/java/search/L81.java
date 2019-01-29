package search;

/*
* 坑 1: 坑BOSS: 这道题的坑是会有多个相等的元素:
*           mid == target, 则直接返回 true
*           left < mid 时:
*               left ~ mid 是单调递增的
*               这时判断target, 需要把 左右边界(left/right)都包含在内:
*                   left <= target < mid
*            mid < right 时:
*               mid ~ right 是单调递增的
*               这时判断target, 需要把 左右边界(left/right)都包含在内:
*                   mid < target <= right
*           如果 mid 和 left/right相等的话,
*               移动 不是 target 的那一端
                if (nums[left] != target) {
                    left++;
                } else {
                    right--;
                }
* 坑 2: 坑BOSS, 在 第二层 if 判断里面, target 要跟 左右边界进行比较,
*           if (nums[left] <= target && target < nums[mid]) {
*           if (nums[mid] < target && target <= nums[right]) {
* */
@SuppressWarnings("Duplicates")
public class L81 {
    public boolean search(int[] nums, int target) {
        if (nums == null || nums.length == 0) {
            return false;
        }
        int left = 0;
        int right = nums.length - 1;
        while (left + 1 < right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] == target) {
                return true;
            } else if (nums[left] < nums[mid]) {
                if (nums[left] <= target && target < nums[mid]) {
                    right = mid;
                } else {
                    left = mid;
                }
            } else if (nums[mid] < nums[right]) {
                if (nums[mid] < target && target <= nums[right]) {
                    left = mid;
                } else {
                    right = mid;
                }
            } else {
                if (nums[left] != target) {
                    left++;
                } else {
                    right--;
                }
            }
        }
        if (nums[left] == target || nums[right] == target) {
            return true;
        }
        return false;
    }
}
