package peilian;

@SuppressWarnings("Duplicates")
public class L191 {
    public int hammingWeight(int n) {
        int cnt = 0;
        int cur = n;
        for (int i = 0; i < 32; i++) {
            if ((cur & 1) == 1) {
                cnt++;
            }
            cur = cur >> 1;
        }
        return cnt;
    }
}
