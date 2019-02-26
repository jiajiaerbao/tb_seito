package peilian;

@SuppressWarnings("Duplicates")
public class L190 {
    /******************************第一遍********************************************/
    /*
    * 坑 1: bit运算:
    *           res每次向左移动一位;
    *           如果cur是1的话, 则加上;
    *           cur 每次向右移动一位, 用来反转
    *
    * */
    class FirstTime{
        public int reverseBits(int n) {
            int res = 0;
            for (int i = 0; i < 32; i++) {
                int temp = (n >> i) & 1;
                //注意这里是31, 不是32
                res = res | (temp << (31 - i));
            }
            return res;
        }
    }
}
