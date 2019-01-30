package twoPointersSlidingWindow;

/*
* 坑 1: 这道题是考查 开闭区间
*       [ 0....len-1 ]
*       [ 0....start....i....end....len-1 ]
*            0            1            未知        2
*       [0, start]    (start, i)    [i, end)   [end, len-1]
*       初始值: start = -1, 表示 [0, start] 的区间为空
*               end = len, 表示 [end, len-1] 的区间为空
*                  i = 0
* 坑 2: while loop里面的结束条件是 i < end, i 碰到了 end, 程序就结束了
* */

@SuppressWarnings("Duplicates")
public class L75 {
    public void sortColors(int[] nums) {
        //corner case
        if (nums == null || nums.length == 0) {
            return;
        }
        int i = 0;
        int start = -1;
        int end = nums.length;
        while (i < end) {
            if (nums[i] == 0) {
                nums[i++] = 1;
                nums[++start] = 0;
            } else if (nums[i] == 1) {
                i++;
            } else if (nums[i] == 2) {
                nums[i] = nums[--end];
                nums[end] = 2;
            }
        }
    }
}
