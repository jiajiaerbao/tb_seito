package twoPointersSlidingWindow;

/*
* 坑 1: 坑BOSS, 你的逻辑有问题:
*       for loop里面是对cur进行判断的, 里面的if也要以cur作为判断条件依据
* 坑 2: 逻辑有问题, 当出现了第三种字母的时候, 更新lastIndex较小的那个
*       这时需要事先保存当前window的start, 即start = lastIndex, 然后再更新lastIndex
* */
@SuppressWarnings("Duplicates")
public class L159 {
    public int lengthOfLongestSubstringTwoDistinct(String s) {
        //corner case
        if (s == null || s.length() == 0) {
            return 0;
        }
        int lastIndex1st = -1;
        int lastIndex2nd = -1;
        int start = 0;
        char char1st = '\0';
        char char2nd = '\0';
        int maxLen = 0;
        for (int i = 0; i < s.length(); i++) {
            char cur = s.charAt(i);
            if (cur == char1st) {
                lastIndex1st = i;
            } else if (cur == char2nd) {
                lastIndex2nd = i;
            } else {
                if (lastIndex1st == -1) {
                    char1st = cur;
                    lastIndex1st = i;
                } else if (lastIndex2nd == -1) {
                    char2nd = cur;
                    lastIndex2nd = i;
                } else {
                    if (lastIndex1st < lastIndex2nd) {
                        start = lastIndex1st + 1;
                        char1st = cur;
                        lastIndex1st = i;
                    } else {
                        start = lastIndex2nd + 1;
                        char2nd = cur;
                        lastIndex2nd = i;

                    }
                }
            }
            maxLen = Math.max(maxLen, i - start + 1);
        }
        return maxLen;
    }
}
