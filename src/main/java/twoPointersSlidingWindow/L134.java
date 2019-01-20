package twoPointersSlidingWindow;

/*
* 坑 1: 坑BOSS, 当 status>=0 的时候, 邮箱里面还有剩余的油, 这时尽可能地开到下一站
*              当 status<0 的时候, 当前的start不满足条件, start先向前进的相反方向移动一位
* 坑 2: 跳出while的时候, start == end, 这时
*               如果 status<0 的话, 则返回-1
*               如果 status>=0 的话, 则有解, start就是解, start如果是len, 则表示起点, 返回 0
* */
@SuppressWarnings("Duplicates")
public class L134 {
    public int canCompleteCircuit(int[] gas, int[] cost) {
        if (gas == null || cost == null || gas.length != cost.length) {
            return 0;
        }
        int len = gas.length;
        int start = len;
        int end = 0;
        int status = 0;
        while (start > end) {
            if (status >= 0) {
                status += gas[end] - cost[end];
                end++;
            } else {
                start--;
                status += gas[start] - cost[start];
            }
        }
        if(status < 0){
            return  -1;
        }
        if(start == len){
            return 0;
        }else {
            return start;
        }
    }
}
