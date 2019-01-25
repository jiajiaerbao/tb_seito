package bit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
* 坑 1: 两个 bit 能表示的四个状态是: 0, 1, 2, 3
* 坑 2: window = ((window << 2) & 0xFFFFF) | val;
*           每次window 向左移动 两个bit, 保留 最左面的20个bit, 然后加上 下一个字母 所对应的状态
* 坑 3: 最开始 没达到 10个字母的时候, 直接跳过后面的逻辑
*       if (i < 9) {
*           continue;
*       }
* 坑 4: 对于一个新得到的val, 在map里面, 要先找, 再放到map里面
*       true 表示已经加到了res里面; false表示还没有加到res里面
* */
@SuppressWarnings("Duplicates")
public class L187 {
    public List<String> findRepeatedDnaSequences(String s) {
        List<String> res = new ArrayList<>();
        //corner case
        if (s == null || s.length() < 10) {
            return res;
        }
        Map<Integer, Boolean> map = new HashMap<>();
        int window = 0;
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            int val = convert(ch);
            window = ((window << 2) & 0xFFFFF) | val;
            if (i < 9) {
                continue;
            }
            Boolean flag = map.get(window);
            if (flag == null) {
                map.put(window, false);
            } else {
                if (!flag) {
                    res.add(s.substring(i - 9, i + 1));
                }
                map.put(window, true);
            }
        }
        return res;
    }

    private int convert(char ch) {
        switch (ch) {
            case 'A':
                return 0;
            case 'C':
                return 1;
            case 'G':
                return 2;
            case 'T':
                return 3;
        }
        return -1;
    }
}
