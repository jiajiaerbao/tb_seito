package dp;


import java.util.Arrays;

/*
* 坑 1: 坑BOSS: 搜索问题, 分叉, 还是分叉没搞懂
*       对于每一个店(点) i: 抢 OR 不抢
*           抢: 下一个只能从 i+2 开始
*         不抢: 可以从 i+1 开始
*       dp[i] = Math.max(dp[i-1], dp[i-2] + nums[i-1])
* 坑 2: 注意边界条件
*
* 坑 3: 根据状态转移公式, 确定 index 的大小依赖关系: 大的依赖于小的
* 坑 4: 改写成pruning之后, 注意base case, 去掉冗余的base case
* */
@SuppressWarnings("Duplicates")
public class L198 {
    /*****************************     dp      ***********************/
    public int rob(int[] nums) {
        //corner case
        if (nums == null || nums.length == 0) {
            return 0;
        }
        int[] dp = new int[nums.length];
        if (nums.length == 1) {
            return nums[0];
        }

        dp[0] = nums[0];
        dp[1] = Math.max(nums[0], nums[1]);

        for (int i = 2; i < nums.length; i++) {
            dp[i] = Math.max(dp[i - 1], dp[i - 2] + nums[i]);
        }
        return dp[nums.length - 1];
    }

    /*****************************  dfs+pruning  ************************/

    public int rob2(int[] nums) {
        //corner case
        if (nums == null || nums.length == 0) {
            return 0;
        }
        int[] mem = new int[nums.length];
        Arrays.fill(mem, -1);
        return dfs(nums, nums.length - 1, mem);
    }

    private int dfs(int[] nums, int idx, int[] mem) {
        if (idx < 0) {
            return 0;
        }
        /*if (idx == 0) {
            return nums[0];
        }
        if (idx == 1) {
            return Math.max(nums[0], nums[1]);
        }*/
        if (mem[idx] != -1) {
            return mem[idx];
        }
        mem[idx] = Math.max(dfs(nums, idx - 1, mem), dfs(nums, idx - 2, mem) + nums[idx]);
        return mem[idx];
    }
}
