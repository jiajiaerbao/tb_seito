package interval;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

@SuppressWarnings("Duplicates")
public class L218 {
    private class EndPoint implements Comparable<EndPoint> {
        int val;
        int height;
        boolean isEnd;

        public EndPoint(int val, int height, boolean isEnd) {
            this.val = val;
            this.height = height;
            this.isEnd = isEnd;
        }

        @Override
        public int compareTo(EndPoint that) {
            if (this.val != that.val) {
                return this.val - that.val;
            } else {
                if (!this.isEnd && !that.isEnd) {         //当都是左边界的时候, 高的come first
                    return that.height - this.height;
                } else if (this.isEnd && that.isEnd) {    //当都是右边界的时候, 低的come first
                    return this.height - that.height;
                } else {                                 //当一个是左边界, 一个是右边界的时候, 左边界的come first
                    if (!this.isEnd) {
                        return 1;
                    } else {
                        return -1;
                    }
                }
            }
        }
    }

    public List<int[]> getSkyline(int[][] buildings) {
        List<int[]> res = new ArrayList<>();
        if (buildings == null || buildings.length == 0) {
            return res;
        }
        List<EndPoint> list = new ArrayList<>();
        for (int[] building : buildings) {
            list.add(new EndPoint(building[0], building[2], false));
            list.add(new EndPoint(building[1], building[2], true));
        }
        Collections.sort(list);
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>((i1, i2) -> i2 - i1);
        for (EndPoint endPoint : list) {
            if (!endPoint.isEnd) {
                if (maxHeap.isEmpty() || endPoint.height > maxHeap.peek()) {
                    res.add(new int[]{endPoint.val, endPoint.height});
                }
                maxHeap.offer(endPoint.height);
            } else {
                maxHeap.remove(endPoint.height);
                if (maxHeap.isEmpty() || maxHeap.peek() < endPoint.height) {
                    res.add(new int[]{endPoint.val, maxHeap.isEmpty() ? 0 : maxHeap.peek()});
                }
            }
        }
        return res;
    }
}
