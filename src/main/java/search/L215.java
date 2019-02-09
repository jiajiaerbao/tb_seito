package search;

import java.util.PriorityQueue;

/*
* 坑 1: 这道题用minHeap来做, 因为只要保存到目前为止的最大的 k 个 即可
*       这样的话, 在后面的follow up的时候, 空间复杂度只是跟 k 有关系
* */
@SuppressWarnings("Duplicates")
public class L215 {
    public int findKthLargest(int[] nums, int k) {
        PriorityQueue<Integer> minHeap = new PriorityQueue<>((a, b) -> a - b);
        for (int num : nums) {
            if (minHeap.size() < k) {
                minHeap.offer(num);
            } else {
                int top = minHeap.peek();
                if (num > top) {
                    minHeap.poll();
                    minHeap.offer(num);
                }
            }
        }
        return minHeap.peek();
    }
}
