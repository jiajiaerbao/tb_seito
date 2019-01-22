package search;

@SuppressWarnings("Duplicates")
public class L69 {
    public int mySqrt(int x) {
        /*
        corner case 有两点:
            1. 输入是否小于零
            2. 如果等于1的话, 也当做一个corner case来判断, 直接返回1
        */
        if (x < 0) {
            return -1;
        }
        if(x == 1){
            return 1;
        }
        //考虑到x可能是 2^31 - 1, 这里用long, 避免越界的情况
        long left = 0;
        long right = x / 2;
        while (left + 1 < right) {
            long mid = left + (right - left) / 2;
            long midVal = mid * mid;
            if (x < midVal) {
                right = mid;
            } else if (x > midVal) {
                left = mid;
            } else {
                return (int)mid;
            }
        }
        if (right * right <= x) {
            return (int)right;
        }
        return (int)left;
    }
}
