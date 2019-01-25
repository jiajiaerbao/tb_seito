package bit;

@SuppressWarnings("Duplicates")
public class L260 {
    public int[] singleNumber(int[] nums) {
        if (nums == null || nums.length == 0) {
            return new int[2];
        }
        int xor = 0;            //找到 不同的两个数 A 和 B 的不同的位置, 比如 [01000100000]
        for (int n : nums) {
            xor ^= n;
        }
        int mask = 1;            //bit 操作的术语, 这里找到了 最右边的那个 不同的 1
        while ((xor & mask) == 0) {
            mask = mask << 1;
        }
        int group1 = 0;
        int group2 = 0;
        for (int n : nums) {
            if ((n & mask) == 0) {
                group1 ^= n;
            } else {
                group2 ^= n;
            }
        }
        return new int[]{group1, group2};
    }
}
