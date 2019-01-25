package bit;

/*
* 坑 1: 第一个for loop, 这里是定义了一个 int val = n; 这样的话容易出错, 最好按照下面这样写
*        for (int n : nums) {
*            for (int i = 0; i < 32; i++) {
*                if ((n & 1) == 1) {
*                    cnt[i]++;
*                }
*                n = n >> 1;
*            }
*        }
* 坑 2:
* */
@SuppressWarnings("Duplicates")
public class L137 {
    public int singleNumber(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        int[] cnt = new int[32];
        for (int n : nums) {
            int val = n;
            for (int i = 0; i < 32; i++) {
                if ((val & 1) == 1) {           //对于该bit位, 如果是 1 则 cnt加1
                    cnt[i]++;
                }
                val = val >> 1;                 //处理完一位bit之后, 把下一个bit的移动到右边
            }
        }
        int res = 0;
        for (int i = 0; i < 32; i++) {
            if (cnt[i] % 3 == 1) {
                res += (1 << i);
            }
        }
        return res;
    }
}
