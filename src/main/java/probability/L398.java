package probability;


import java.util.Arrays;
import java.util.Random;

/*
* 坑 1: 复制array的写法注意:
*       myNums = Arrays.copyOf(nums, nums.length);
* 坑 2: 这里的代码是改写水库采样的代码
*       采样的对象是 nums[i]==target, 所以在for loop遍历所有的值的时候, 首先filter掉所有的非target的值
* 坑 3: int rand = new Random().nextInt(count); 的意思是:
*       这里的概率是 以第一次出现的target作为pool, 后面出现的target落到pool里面的概率, 比如 第二/三次 出现的 target, 落到第 0 次 pool 里面的概率
 * */
@SuppressWarnings("Duplicates")
public class L398 {
    private int[] myNums;

    public void Solution(int[] nums) {
        myNums = Arrays.copyOf(nums, nums.length);
    }

    public int pick(int target) {
        int res = -1;
        int k = 1;
        int[] pool = new int[k];
        int count = 0;
        for (int i = 0; i < myNums.length; i++) {
            if (myNums[i] != target) {
                continue;
            }
            count++;
            if (i < k) {                        //比如[1], target==1的时候, 不能跳出, 要继续执行
                pool[i] = myNums[i];
                //continue;
            }
            int rand = new Random().nextInt(count);
            if (rand < k) {                     //如果落到了 pool 里面, 则记录当前target的 index
                pool[rand] = target;
                res = i;
            }
        }
        return res;
    }
}
