package search;

public class Lint183 {
    public int woodCut(int[] L, int k) {
        //corner case
        if (L == null || L.length == 0 || k <= 0) {
            return 0;
        }
        int min = 1;
        int max = L[0];
        for (int i : L) {
            max = Math.max(max, i);
        }
        while (min + 1 < max) {
            int mid = min + (max - min) / 2;
            if (getCount(L, mid) < k) {
                max = mid;
            } else if (getCount(L, mid) > k) {
                min = mid;
            } else {
                min = mid;
            }
        }
        if (getCount(L, max) >= k) {
            return max;
        }
        if (getCount(L, min) >= k) {
            return min;
        }
        return 0;
    }

    private int getCount(int[] L, int len) {
        int res = 0;
        for (int i : L) {
            res += i / len;
        }
        return res;
    }
}
