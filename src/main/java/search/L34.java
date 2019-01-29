package search;

/*
* 坑 1: 用了两次 binary search, 在第二次用 binary search 的时候, 别忘了重新设置 left 和 right
* */
@SuppressWarnings("Duplicates")
public class L34 {
    public int[] searchRange(int[] nums, int target) {
        //corner case
        if (nums == null || nums.length == 0) {
            return new int[]{-1, -1};
        }
        int leftBound = -1, rightBound = -1;
        int left = 0, right = nums.length - 1;
        //get most left
        while (left + 1 < right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] < target) {
                left = mid;
            } else if (nums[mid] > target) {
                right = mid;
            } else {
                right = mid;
            }
        }
        leftBound = nums[left] == target ? left : right;
        if (nums[leftBound] != target) {
            return new int[]{-1, -1};
        }
        left = 0;
        right = nums.length - 1;
        //get most right
        while (left + 1 < right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] < target) {
                left = mid;
            } else if (nums[mid] > target) {
                right = mid;
            } else {
                left = mid;
            }
        }
        rightBound = nums[right] == target ? right : left;
        return new int[]{leftBound, rightBound};
    }
}
