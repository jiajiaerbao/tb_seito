package stack;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Stack;

@SuppressWarnings("Duplicates")
public class L239 {
    /******************************第一遍********************************************/
    /*
    * 坑 1: Deque里面放的是index, 而不是具体的值
    *       所以比较的时候, 用的是 nums[deque.peekLast()] 和 nums[deque.peekFirst()]
    * 坑 2: Deque里面始终保持这最多 k 个 index:
    *           i >= k 的时候, 已经遍历了 大于 N个数了, 这时check Deque里面保存的index是否超过k个
    *           deque.peekFirst() <= i-k, Deque里面的第一个index小于 i-k, 说明多余k个了
    * 坑 3: 因为你的res[len-k+1], 所以在for loop里面, 直接用res[i-k+1]即可
    * */
    public int[] maxSlidingWindow(int[] nums, int k) {
        //corner case
        if (nums == null || nums.length == 0) {
            //空解的写法
            return new int[0];
        }
        int len = nums.length;
        int[] res = new int[len - k + 1];
        Deque<Integer> deque = new LinkedList<>();
        for (int i = 0; i < len; i++) {
            //检查deque里面的index范围是否超出了k个
            if (i >= k && deque.peekFirst() <= i - k) {
                deque.pollFirst();
            }
            //deque.peekLast() <= nums[i]: 相等的时候, 用后一个覆盖掉前一个
            while (!deque.isEmpty() && nums[deque.peekLast()] <= nums[i]) {
                deque.pollLast();
            }
            //这里是放index
            deque.offerLast(i);
            if (i >= k - 1) {
                res[i - k + 1] = nums[deque.peekFirst()];
            }
        }
        return res;
    }
}
