package bit;

/*
* 坑 1: 因为是连续的数字里面, 丢了一个, 所以 nums 里面多出来的是 nums.length 那个数字
*       在 res 里面事先指定 nums.length 的话, 最后会被异或掉
* */
@SuppressWarnings("Duplicates")
public class L268 {
    public int missingNumber(int[] nums) {
        int res = nums.length;
        for (int i = 0; i < nums.length; i++) {
            res ^= i;
            res ^= nums[i];
        }
        return res;
    }
}


