package twoPointersSlidingWindow;


/*
* 坑 1: count[26]是用于保存sliding window里面的字符的, 不要初始化, 进window一个字符, update一下count
* 坑 2: 每当有一个新的字符进入到sliding window之后,
*           更新当前在window里面出现次数最多的字符 ----> 可以得到window内最多字符的个数 ----> 不同字符的个数
* 坑 3: 这里不用end, 因为window的右边界始终跟i相同
* 坑 4: 当前window里面不同字符个数就是 需要replace的次数
* 坑 5: 坑BOSS: 时刻update当前window里面出现次数最多的char:
*          当有一个新字符进入window的时候, i++的时候
*          当有一个字符从window移出去的时候, ount[startChar - 'A']--;
* 坑 6: window里面不同字符出现的次数的表示: i - start + 1 - count[mostChar - 'A']
* */
@SuppressWarnings("Duplicates")
public class L424 {
    public int characterReplacement(String s, int k) {
        //corner case
        if (s == null || s.length() == 0 || k < 0) {
            return 0;
        }
        int[] count = new int[26];
        char mostChar = '\0';
        int start = 0;
        int res = 0;
        for (int i = 0; i < s.length(); i++) {
            char cur = s.charAt(i);
            count[cur - 'A']++;
            if (mostChar == '\0' || count[cur - 'A'] > count[mostChar - 'A']) {
                mostChar = cur;
            }
            while (i - start + 1 - count[mostChar - 'A'] > k) {
                char startChar = s.charAt(start);
                start++;
                count[startChar - 'A']--;
                for (char ch = 'A'; ch <= 'Z'; ch++) {
                    if (count[ch - 'A'] > count[mostChar - 'A']) {
                        mostChar = ch;
                    }
                }
            }
            res = Math.max(res, i - start + 1);
        }
        return res;
    }
}
