package interval;

import java.util.Arrays;

@SuppressWarnings("Duplicates")
public class L252 {
    /******************************第一遍********************************************/
    /*
    * 坑 1: 按照 先start, 再end 的顺序排序
    *       用prev指向前一个, cur指向当前, 然后比较 prev.end 是否小于 cur.start
    *       不要忘记更新prev
    * */
    public boolean canAttendMeetings(Interval[] intervals) {
        if (intervals == null || intervals.length == 0) {
            return true;
        }
        Arrays.sort(intervals, (int1, int2) -> {
            if (int1.start != int2.start) {
                return int1.start - int2.start;
            } else {
                return int1.end - int2.end;
            }
        });

        Interval prev = intervals[0];
        for (int i = 1; i < intervals.length; i++) {
            Interval cur = intervals[i];
            //这里是坑, 比较的是 前一个点的终点 和 后一个点的起点
            if (prev.end > cur.start) {
                return false;
            }
            //这里忘记了, 调试了两个小时
            prev = cur;
        }
        return true;
    }
}
