package interval;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@SuppressWarnings("Duplicates")
public class L253 {
    /******************************第二遍********************************************/
    /*
    * 坑 1: 这道题你在上一个视频就没有听明白, 重新听了一遍视频之后, 弄懂了
    * 坑 2: 思路要清晰:
    *           把interval按照左右端点进行拆分, 总共拆成2n个端点
    *           每个端点保留该端点的值: val, 和该端点是否是起点: isStart
    *           处理tie的情况, 如果两个端点重合(val相等), 终点要放在起点前面(return this.isStart ? 1 : -1;)
    * */
    class SecondTime{
        private class MyEndPoint implements Comparable<MyEndPoint> {
            public int val;
            public boolean isStart;

            public MyEndPoint(int val, boolean isStart) {
                this.val = val;
                this.isStart = isStart;
            }

            @Override
            public int compareTo(MyEndPoint that) {
                //break tie
                if (this.val == that.val) {
                    return this.isStart ? 1 : -1;
                } else {
                    return this.val - that.val;
                }
            }
        }

        public int minMeetingRooms(Interval[] intervals) {
            //corner case
            if (intervals == null || intervals.length == 0) {
                return 0;
            }
            //construct my Endpoint based on intervals
            List<MyEndPoint> list = new ArrayList<>();
            for (Interval interval : intervals) {
                list.add(new MyEndPoint(interval.start, true));
                list.add(new MyEndPoint(interval.end, false));
            }
            Collections.sort(list);
            int maxSize = 0;
            int curSize = 0;
            for (MyEndPoint myEndPoint : list) {
                if (myEndPoint.isStart) {
                    curSize++;
                } else {
                    curSize--;
                }
                maxSize = Math.max(maxSize, curSize);
            }
            return maxSize;
        }
    }
    /******************************第一遍********************************************/
    class FirstTime{
        /*
         * 坑 1: 新建一个EndPoint, 只是放interval的边界点, 区分边界点是左边界还是右边界
         *       对于一个边界(interval), 只放左边界端点和右边界端点
         * 坑 2: 即使你在EndPoint class上面写了Comparable, 你还是需要调用Collections.sort来进行排序
         * 坑 3: 如果出现了两个边界点的时间相等, 出现了TIE, 那么先减后加, 即先减掉 end, 再加上 start (看everNote)
         * */
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

}
