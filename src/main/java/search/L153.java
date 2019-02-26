package search;


@SuppressWarnings("Duplicates")
public class L153 {
    /******************************第二遍********************************************/
    /*
    * 坑 1: 下面的解法参照了discuss的答案, 记一下
    *       if the array is indeed rotated by some pivot, there are only 2 possibilities
    *           a[mid] > a[left] && a[mid] > a[right]:
    *                   meaning we are on the bigger part, the smaller part is on our right, so go right
    *           a[mid] < a[left] && a[mid] < a[right]:
    *                   meaning we are on the smaller part, to find the smallest element, go left
    *       if the array is not rotated (actually one rotating cycle completed),
    *       we just need to go left, in this case a[mid] < a[right] always holds.
    *
    *       combining the cases above, we conclude that
    *           if a[mid] > a[right], go right; if a[mid] < a[right], go left.
    * 坑 2: 这道题想的时候, 容易掉坑里:
    *           从最简单的常规情况入手: 单调递增的时候, 如果 nums[mid] < nums[right] 的话, 则保留左半部分, 否则保留右半部分
    *           之后再分析正常rotate的情况:
    *               a[mid] > a[left] && a[mid] > a[right]: 则最小值在右半部分
    *               a[mid] < a[left] && a[mid] < a[right]: 则最小值在左半部分
    * 坑 3: 把这道题和33题联系起来看, 第一遍的解题思路是正确的
    * */
    class SecondTime {
        public int findMin(int[] nums) {
            int left = 0;
            int right = nums.length - 1;
            while (left + 1 < right) {
                int mid = left + (right - left) / 2;
                if (nums[mid] < nums[right]) {
                    right = mid;
                } else {
                    left = mid;
                }
            }
            return Math.min(nums[left], nums[right]);
        }
    }
    /******************************第一遍********************************************/
    /*
     * 坑 1: 这道题是一维旋转数组的简化版本, 思路是一样的, 以 mid 作为判断的标准, 找到单调递增的那一段, 然后将搜索范围减少一半
     *       比如 nums[mid] <= nums[right] ---> right = mid;
     * */
    class FirstClass{
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
}
