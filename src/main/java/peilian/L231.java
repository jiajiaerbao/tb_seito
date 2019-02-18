package peilian;

@SuppressWarnings("Duplicates")
public class L231 {
    /*
    * 坑 1: 思路不清晰的时候, 通过画图来找出规律,
    *       比如 2^n (0 <= n <= MAX_VAL), 也就是 n 里面只有一个bit是1, 其他的都是零
    * 坑 2: 注意一下corner case
    * */
    public boolean isPowerOfTwo(int n) {
        if (n <= 0) {
            return false;
        }
        int cur = n;
        int cnt = 0;
        for (int i = 0; i < 32; i++) {
            if (cnt > 1) {
                return false;
            }
            if ((cur & 1) == 1) {
                cnt++;
            }
            cur = cur >> 1;
        }
        return cnt == 1;
    }
}
