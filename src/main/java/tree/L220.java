package tree;

import java.util.TreeSet;

@SuppressWarnings("Duplicates")
public class L220 {
    /******************************第二遍********************************************/
    /*
    * 坑 1: 这里注意越界, 不能用Integer, 只能用Long
    * 坑 2: ums[i] +/- t 的时候, 一定要把其中的一个变为Long.valueOf(...)之后再计算, 否则会发生越界
    * */
    public boolean containsNearbyAlmostDuplicate_2(int[] nums, int k, int t) {
        //corner case
        if (nums == null || nums.length == 0 || t < 0 || k < 0) {
            return false;
        }
        TreeSet<Long> set = new TreeSet<>();
        for (int i = 0; i < nums.length; i++) {
            if (i > k) {
                set.remove(Long.valueOf(nums[i - k - 1]));
            }
            long upper = Long.valueOf(nums[i]) + t;
            long lower = Long.valueOf(nums[i]) - t;
            Long max = set.floor(upper);
            if (max != null && max >= lower) {
                return true;
            }
            set.add(Long.valueOf(nums[i]));
        }
        return false;
    }
    /******************************第一遍********************************************/
    /*
     * 1. 这里仅仅是问true/false, 如果没有两个元素的值相同的话, 用TreeSet即可
     * 2. 如果nums array里面有两个元素的值相同, 需要用 TreeMap<Value, Count of this value>
     * 3. 如果让你返回index, 需要用 TreeMap<Value, List<Index of this value>>
     * 4. sliding window的解法还是不熟练, 关键是keep一个window size的集合, 然后慢慢从起点移动到终点
     * 5. sliding window: 移动的时候, 先从window里面remove, 在向window里面insert新元素
     * 6. 坑BOSS: sliding window: 移动的时候, 千万不要忘了 add 下一个元素 (删除-->增加)
     *            sliding window:
     *                   从 sliding window 里面删除 已经出 window 的元素 (要先删除, 否是逻辑就不对了)
     *                   进行业务逻辑判断
     *                   把 新进入 sliding window 里面的 new 元素加进去
     * 7. 这道题的input 都是 int, 但是 nums[i] + t 有可能越界, 所以需要用 nums[i] + Long.valueOf(t) 来进行转换, 结果存入到long里面
     * */
    public boolean containsNearbyAlmostDuplicate_1(int[] nums, int k, int t) {
        //i, j: Math.abs(i - j) <= k, Math.abs(nums[i] - nums[j]) <= t
        //nums[i] - t == nums[j]
        //nums[i] + t == nums[j]
        //sliding window: keep t+1 size window

        //corner case
        if (nums == null || nums.length == 0 || t < 0 || k < 0) {
            return false;
        }
        TreeSet<Long> set = new TreeSet<>();
        for (int i = 0; i < nums.length; i++) {
            if (i > k) {
                set.remove(Long.valueOf(nums[i - k - 1]));
            }
            long upperBound = nums[i] + Long.valueOf(t);
            long lowerBound = nums[i] - Long.valueOf(t);
            Long maxAvail = set.floor(upperBound);
            if (maxAvail != null && maxAvail >= lowerBound) {
                return true;
            }
            set.add(Long.valueOf(nums[i]));
        }
        return false;
    }
    public static void main(String[] args){
        L220 l220 = new L220();
        l220.containsNearbyAlmostDuplicate_1(new int[]{0,2147483647}, 1, 2147483647);
    }
}
