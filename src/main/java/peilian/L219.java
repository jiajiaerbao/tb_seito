package peilian;

@SuppressWarnings("Duplicates")
public class L219 {
    /******************************第一遍********************************************/
    public boolean containsNearbyDuplicate(int[] nums, int k) {
        if (nums == null || nums.length <= 1) {
            return false;
        }
        int prev = nums[0];
        for (int i = 0; i < nums.length; i++) {
            while (i < nums.length - 1 && nums[i] == nums[i + 1]) {
                i++;
            }
            if (Math.abs(prev - nums[i]) <= k) {
                return true;
            }
            prev = nums[i];
        }
        return false;
    }
}
