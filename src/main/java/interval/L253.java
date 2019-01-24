package interval;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/*
* 坑 1: 新建一个EndPoint, 只是放interval的边界点, 区分边界点是左边界还是右边界
*       对于一个边界(interval), 只放左边界端点和右边界端点
* 坑 2: 即使你在EndPoint class上面写了Comparable, 你还是需要调用Collections.sort来进行排序
* 坑 3: 如果出现了两个边界点的时间相等, 出现了TIE, 那么先减后加, 即先减掉 end, 再加上 start (看everNote)
* */
@SuppressWarnings("Duplicates")
public class L253 {
    public int minMeetingRooms(Interval[] intervals) {
        if (intervals == null || intervals.length == 0) {
            return 0;
        }
        int res = 0;
        List<EndPoint> list = new ArrayList<>();
        for (Interval interval : intervals) {
            list.add(new EndPoint(interval.start, false));
            list.add(new EndPoint(interval.end, true));
        }
        Collections.sort(list);
        int poolSize = 0;
        for (EndPoint endPoint : list) {
            if (!endPoint.isEnd) {
                poolSize++;
            } else {
                poolSize--;
            }
            res = Math.max(poolSize, res);
        }
        return res;
    }

    private class EndPoint implements Comparable<EndPoint> {
        int val;
        boolean isEnd;

        public EndPoint(int val, boolean isEnd) {
            this.val = val;
            this.isEnd = isEnd;
        }

        @Override
        public int compareTo(EndPoint that) {
            if (this.val != that.val) {
                return this.val - that.val;
            } else {
                return this.isEnd ? -1 : 1;
            }
        }
    }
}
