package dp;


import org.omg.Messaging.SYNC_WITH_TRANSPORT;

/**
 * 坑 1: key 的 index 的移动方向是固定的(只是增大), 而 ring 的 index 可以向左/向右移动, 所以要先从 key 的 index 开始遍历
 * 坑 2: base case 只跟 key 的 index 有关系, 当 key 的 index touch 到 key 的 length 的时候, 结束
 * 坑 3: 逻辑要清晰:
 *          只有当 idxR 和 idxK 的字母相同的时候, 按一下(加 1)
 *          每当找到相同字母之后, ring的位置不动, key 的 index 加 1, call 下一层dfs
 *          只有当 idxR 和 idxK 不同的时候, 才顺时针或者逆时针旋转, 直到找到相同的字母为止
 * 坑 4: 顺时针和逆时针旋转的时候, 如何获得 index?
 *       通过单独定义的 getIndex 来获取
 * 坑 5: 每次顺时针或者逆时针旋转的时候, count 都需要 加 1 (转动的count, 不是 按一下的count)
 * 坑 6: 变量命名要注意, ringIdx和keyIdx 要比 idxR和idxK 好, 不容易出错
 */
@SuppressWarnings("Duplicates")
public class L514 {
    public int findRotateSteps(String ring, String key) {
        if (ring == null || key == null || ring.length() == 0 || key.length() == 0) {
            return -1;
        }
        int lenR = ring.length();
        int lenK = key.length();
        return dfs(ring, 0, key, 0, new int[lenK][lenR]);
    }

    private int dfs(String ring, int ringIdx, String key, int keyIdx, int[][] mem) {
        if (keyIdx == key.length()) {
            return 0;
        }
        /*if (ringIdx == ring.length()) {
            System.out.println("key: " + key + "; key.length(): " + key.length() + "; keyIdx:" + keyIdx + ";");
            System.out.println("ring: " + ring + "; ring.length(): " + ring.length() + "; ringIdx:" + ringIdx + ";");
        }*/
        if (mem[keyIdx][ringIdx] != 0) {
            return mem[keyIdx][ringIdx];
        }
        if (ring.charAt(ringIdx) == key.charAt(keyIdx)) {
            mem[keyIdx][ringIdx] = dfs(ring, ringIdx, key, keyIdx + 1, mem) + 1;
            return mem[keyIdx][ringIdx];
        }
        int cnt = 1;
        int left = ringIdx - 1;
        while (left != ringIdx) {
            left = get(left, ring.length());
            if (ring.charAt(left) == key.charAt(keyIdx)) {
                break;
            }
            left--;
            cnt++;
        }
        int minLeft = dfs(ring, left, key, keyIdx + 1, mem) + cnt;

        cnt = 1;
        int right = ringIdx + 1;
        while (right != ringIdx) {
            right = get(right, ring.length());
            if (ring.charAt(right) == key.charAt(keyIdx)) {
                break;
            }
            right++;
            cnt++;
        }
        int minRight = dfs(ring, right, key, keyIdx + 1, mem) + cnt;
        mem[keyIdx][ringIdx] = Math.min(minLeft, minRight) + 1;
        return mem[keyIdx][ringIdx];
    }

    private int get(int idx, int len) {
        int cur = idx;
        while (cur < 0) {
            cur += len;
        }
        return cur % len;
    }

    public static void main(String args[]) {
        L514 l711 = new L514();
        String ring = "kmgjj";
        String key = "mmmkkkjjjjjjggg";
        int res = l711.findRotateSteps(ring, key);
        System.out.println(res);
    }


    /*public int findRotateSteps(String ring, String key) {
        *//*
        * dp[i, j] <-- min( dp[ 0-len-1, j-1] ) + 1
        *
        *
        * *//*

        int len1 = ring.length();
        int len2 = key.length();
        int[][] dp = new int[len1][len2];
        dp[0][0] = 0;
        for(int i = 0; i <= len1; i++){
            for(int j = 0; j <= len2; j++){
                dp[i][j] = Math.min(dp[i-1][j-1], dp[i][j]) + 1;
            }
        }
    }*/
}
