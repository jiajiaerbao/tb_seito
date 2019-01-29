package search;

/*
* 坑 1: 坑BOSS, 这道题要往BinarySearch上面靠, 下面是抄的答案
*       在binary search里面, 对于比较的基准 mid, 目标是 mid 要大于 mid-1 和 mid+1
*       不满足要求的时候, 要移动mid:
*           mid-1 > mid, right = mid
*           mid < mid+1, left = mid
*           其他情况, left/right = mid
*       最后出 while loop的时候, 找峰值:
*           left > right, 则 return left
*           left < right, 则 return right
* 坑 2: 这里的left和right的取值范围 根据题意来定, leetCode是取 0, len-1
* */
@SuppressWarnings("Duplicates")
public class L162 {
    public int findPeakElement(int[] nums) {
        //corner case
        if (nums == null || nums.length == 0) {
            return -1;
        }
        int left = 0;
        int right = nums.length - 1;
        while (left + 1 < right) {
            int mid = left + (right - left) / 2;
            if (nums[mid - 1] > nums[mid]) {
                right = mid;
            } else if (nums[mid] < nums[mid + 1]) {
                left = mid;
            } else {
                right = mid;
            }
        }
        if (nums[left] > nums[right]) {
            return left;
        } else {
            return right;
        }
    }
}
