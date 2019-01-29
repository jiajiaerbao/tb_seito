package search;


/*
* 坑 1: 这道题是一维旋转数组的简化版本, 思路是一样的, 以 mid 作为判断的标准, 找到单调递增的那一段, 然后将搜索范围减少一半
*       比如 nums[mid] <= nums[right] ---> right = mid;
* */
@SuppressWarnings("Duplicates")
public class L153 {
    public int findMin(int[] nums) {
        if (nums == null || nums.length == 0) {
            return -1;
        }
        int left = 0;
        int right = nums.length - 1;
        while (left + 1 < right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] <= nums[right]) {
                right = mid;
            } else {
                left = mid;
            }
        }
        return nums[left] < nums[right] ? nums[left] : nums[right];
    }
}
