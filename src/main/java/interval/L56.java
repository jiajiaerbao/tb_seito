package interval;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

/*
* 坑 1: 先把所有的interval按照 start 排序
* 坑 2: 如果没有overlap的话, 则 1st.end < 2nd.start
* 坑 3: 坑BOSS: 如果有overlap的话, 取1st和2nd的最长的end, 把cur合并到prev上面, 但是这时不加入结果集
* 坑 4: 画出图形来, 逻辑清楚了, 代码就好写了
* */
@SuppressWarnings("Duplicates")
public class L56 {
    public List<Interval> merge(List<Interval> intervals) {
        List<Interval> res = new ArrayList<>();
        //corner case
        if (intervals == null || intervals.size() == 0) {
            return res;
        }
        PriorityQueue<Interval> minHeap = new PriorityQueue<>(
                (interval1, interval2) -> interval1.start - interval2.start
        );
        for (Interval interval : intervals) {
            minHeap.offer(interval);
        }
        Interval prev = minHeap.poll();
        while (!minHeap.isEmpty()) {
            Interval cur = minHeap.poll();
            if (prev.end < cur.start) {
                res.add(prev);
                prev = cur;
            } else {
                //把 cur merge 到 prev上面
                prev = new Interval(prev.start, Math.max(prev.end, cur.end));
            }
        }
        res.add(prev);
        return res;
    }
}