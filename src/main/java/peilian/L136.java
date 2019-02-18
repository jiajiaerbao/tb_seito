package peilian;

@SuppressWarnings("Duplicates")
public class L136 {
    public int singleNumber(int[] nums) {
        int sum = 0;
        for (int n : nums) {
            sum ^= n;
        }
        return sum;
    }
}
