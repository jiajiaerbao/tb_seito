package probability;


import java.util.Arrays;
import java.util.Random;

@SuppressWarnings("Duplicates")
public class L384 {
    /******************************第二遍********************************************/
    /*
    * 坑 1: 这道题是随机抽样问题, 具体解释看everNote
    *           [0, i) 表示当前区间
    *           i 表示下一个将要放进来的数, shuffleIdx表示去随机数之后, 应该放进去的位置
    *           如果 shuffleIdx==i, 则表示直接添加在末尾, 变成 [0, i+1) 即可
    *           如果 shuffleIdx < i,则需要swap
    *           注意, 这里 1 <= i <= nums.length, 即 i 属于 [1, len]
    *               [0, 1)   表示  0
    *               [0, len) 表示 [0, len-1]
    * 坑 2: 坑 BOSS: 这道题的解法不准确, 这里直接交换即可, 不用判断 k 是否在 i 的范围内, 不要跟水库采样弄混了
    * */
    class SecondTime{
        class Solution {
            private int[] oriArr;

            public Solution(int[] nums) {
                oriArr = Arrays.copyOf(nums, nums.length);
            }

            /**
             * Resets the array to its original configuration and return it.
             */
            public int[] reset() {
                return oriArr;
            }

            /**
             * Returns a random shuffling of the array.
             */
            public int[] shuffle() {
                int[] res = new int[oriArr.length];
                for (int i = 1; i <= oriArr.length; i++) {
                    int shuffleIdx = new Random().nextInt(i);
                    if (shuffleIdx == i - 1) {
                        res[shuffleIdx] = oriArr[i - 1];
                    } else {
                        int temp = res[shuffleIdx];
                        res[shuffleIdx] = oriArr[i - 1];
                        res[i - 1] = temp;
                    }
                }
                return res;
            }
        }
    }
    /******************************第一遍********************************************/
    /*
     * 坑 1: 因为涉及到了reset, 所以需要把input的array保存在单独的一个变量里面
     *       这里是 private int[] myNums;
     * 坑 2: 坑BOSS: 这里直接交换即可, 不用判断 k 是否在 i 的范围内, 不要跟水库采样弄混了
     * 坑 3: 获取随机数:
     *       new Random().nextInt(i+1); 是获取 [ 0, i ] 之间的随机数
     * 坑 4: swap的逻辑是
     *       新来了一个 [i] 位置上的值, 本来要放到 temp[i] 的位置上, 但是需要shuffle, 所以需要跟 随机数 [r] 进行交换:
     *       获取一个随机数 r = [0, i], 把 r 的值 移动到 temp[i] 的位置
     *       然后用 nums[i] 覆盖掉 temp[r]
     * */
    class FirstTime{
        private int[] myNums;

        public void Solution(int[] nums) {
            myNums = new int[nums.length];
            for (int i = 0; i < nums.length; i++) {
                myNums[i] = nums[i];
            }
        }

        /**
         * Resets the array to its original configuration and return it.
         */
        public int[] reset() {
            return myNums;
        }

        /**
         * Returns a random shuffling of the array.
         */
        public int[] shuffle() {
            int[] temp = new int[myNums.length];

            for (int i = 0; i < myNums.length; i++) {
                int randomInt = new Random().nextInt(i + 1);
                temp[i] = temp[randomInt];
                temp[randomInt] = myNums[i];
            }
            return temp;
        }
    }
}
