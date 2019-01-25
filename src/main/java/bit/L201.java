package bit;

/*
* 坑 1: 退出while loop之后, m = n 都变成了 右移 i 位的 prefix, 而且 m == n
*       最后只要把 m/n 左移 i 位 即可
*
* */
@SuppressWarnings("Duplicates")
public class L201 {
    public int rangeBitwiseAnd(int m, int n) {
        int i = 0;
        while (m != n) {
            m = m >> 1;
            n = n >> 1;
            i++;
        }
        return m << i;
    }
}
