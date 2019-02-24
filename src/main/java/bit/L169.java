package bit;

@SuppressWarnings("Duplicates")
public class L169 {
    /******************************第二遍********************************************/
    class SecondTime {
        public int majorityElement(int[] nums) {
            int res = 0;
            if (nums == null || nums.length == 0) {
                return res;
            }
            int[] cnt = new int[32];
            for (int n : nums) {
                for (int i = 0; i < 32; i++) {
                    if ((n & 1) == 1) {
                        cnt[i]++;
                    }
                    n = n >> 1;
                }
            }
            int size = nums.length / 2;
            for (int i = 0; i < 32; i++) {
                if (cnt[i] > size) {
                    //这两种写法都可以
                    /*res = res | (1 << i);*/
                    res += 1 << i;
                }
            }
            return res;
        }
    }

    /******************************第一遍********************************************/
    class FirstTime{
        public int majorityElement(int[] nums) {
            if (nums == null || nums.length == 0) {
                return 0;
            }
            int[] cnt = new int[32];
            for (int n : nums) {
                for (int i = 0; i < 32; i++) {
                    if ((n & 1) == 1) {
                        cnt[i]++;
                    }
                    n = n >> 1;
                }
            }
            int res = 0;
            for (int i = 0; i < 32; i++) {
                if (cnt[i] > nums.length / 2) {        //跟137的唯一区别就是这里, 这就是做题归纳总结的好处
                    res += 1 << i;
                }
            }
            return res;
        }
    }

}
