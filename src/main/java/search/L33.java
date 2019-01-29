package search;

/*
* 坑 1: 以 mid 作为判断的基准:
*       找出 mid 左边或者右边 中, 单调递增或者单调递减的那一段: while loop里面第一层if...else
*       以那一段作为基准进行 binary search: 第二次 if...else
* 坑 2: 因为是找出是否有相等的元素, 所以 mid == target 要拿出来单独判断
* */
@SuppressWarnings("Duplicates")
public class L33 {
    public int search(int[] nums, int target) {
        if (nums == null || nums.length == 0) {
            return -1;
        }
        int left = 0;
        int right = nums.length - 1;
        while (left + 1 < right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] == target) {
                return mid;
            } else if (nums[left] < nums[mid]) {
                if (nums[left] <= target && target <= nums[mid]) {
                    right = mid;
                } else {
                    left = mid;
                }
            } else {
                if (nums[mid] <= target && target <= nums[right]) {
                    left = mid;
                } else {
                    right = mid;
                }
            }
        }
        if (nums[left] == target) {
            return left;
        }
        if (nums[right] == target) {
            return right;
        }
        return -1;
    }
}
