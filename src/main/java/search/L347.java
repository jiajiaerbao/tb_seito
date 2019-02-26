package search;

import java.util.*;

@SuppressWarnings("Duplicates")
public class L347 {
    /******************************第一遍********************************************/
    /*
    * 坑 1: 这道题算法哥是用findK来解的, wrap了一个class
    * 坑 2: 老刘建议用minHeap来解
    * */
    public List<Integer> topKFrequent(int[] nums, int k) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int num : nums) {
            int cnt = map.getOrDefault(num, 0);
            map.put(num, cnt + 1);
        }
        PriorityQueue<Integer> minHeap = new PriorityQueue<>((n1, n2) -> map.get(n1) - map.get(n2));
        for (int num : map.keySet()) {
            if (minHeap.size() < k) {
                minHeap.offer(num);
            } else {
                minHeap.offer(num);
                minHeap.poll();
            }
        }
        List<Integer> res = new ArrayList<>();
        while (!minHeap.isEmpty()) {
            res.add(minHeap.poll());
        }
        Collections.reverse(res);
        return res;
    }
}
