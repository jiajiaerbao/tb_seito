package bit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@SuppressWarnings("Duplicates")
public class L187 {
    /******************************第二遍********************************************/
    /*
    * 坑 1: 思路要清晰:
    *           从左向右一个字母一个字母地扫描, 构建一个fixed size大小的window,
    *                   如果没达到size, 则continue终止下面的语句, 继续构建window
    *           每个字母有四种可能性, 需要两个bit, 10个需要二十个bit,
    *                   每次向fixed window添加一个新字母的时候, window向左移动两个bit, 然后保留最右面的二十个bit (& 0xFFFFF) (0x表示16进制, 每一个表示4个bit)
    *                   然后与新加进来的int (通过字母转换得到)进行或 |
    *           Map<所有Window的组合, Window的状态(第一次遇见, 第二次遇见, 第三次及大于第三次遇见)>
    *               第一次遇见: null
    *               第二次遇见: false
    *               第三次及以上: true
    *           通过判断该window在map里面的状态, 来判断是否需要加入到result list里面
    *               由于result list里面是String, 所以需要通过 i 来获得subString, 比如 [i-9, i+1)
    * */
    class SecondTime{
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
                } else if (!flag) {
                    res.add(s.substring(i - 9, i + 1));
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
    /******************************第一遍********************************************/
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
    class FirstTime{
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
}
