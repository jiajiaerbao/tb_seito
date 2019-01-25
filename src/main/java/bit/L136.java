package bit;

/*
* 坑 1: 异或出现在for loop里面, 每加一个数都要进行一次异或操作
*
* */
@SuppressWarnings("Duplicates")
public class L136 {
    public int singleNumber(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        int res = 0;
        for (int num : nums) {
            res ^= num;
        }
        return res;
    }
}
