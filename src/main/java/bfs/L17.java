package bfs;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class L17 {
    /*
    * 这道题用定长搜索的模板: 还是不熟练
    * 对于模板而言:
    *   queue: res
    *   pathLength: digits.length
    *   size: res.size()
    *   converts(cur): get(digits.charAt(i))
    *
    *   关键是你不理解为什么要这样写!!!!
    *       从结果集的角度考虑: 你先建立一个空的结果集, 用来保存结果
    *       这里的定长是说对于结果而言, 每一个独立的结果都是定长的, 长度是 digits.length
    *       在基于digits.length的for loop里面:
    *           对于当前的位置, 也就是基于digits.length的for loop里面的i, 把所有的可能性全部加到结果集res当中
    *           然后跳到digits.length的下一位, 继续相同的步骤
    * */
    public List<String> letterCombinations(String digits) {
        List<String> res = new LinkedList<>();
        //corner case
        if (digits.length() == 0) {
            return res;
        }
        //这里只是让res开一个头
        res.add("");
        //pathLength: digits的length: 这里定长的意思就是最后输出的结果的长度
        for (int i = 0; i < digits.length(); i++) {
            int size = res.size();
            //对queue里面的每一个元素, 循环取出第一个元素, 然后添加一个字母后, 再加到 res 的末尾
            //到底添加多少个字母是由外层的for(pathLength)来决定的
            for (int k = 0; k < size; k++) {
                String cur = res.get(0);
                res.remove(0);
                List<Character> list = get(digits.charAt(i));
                //这里是next=convert(cur)
                for (char ch : list) {
                    res.add(cur + ch);
                }
            }
        }
        return res;
    }


    public List<Character> get(char c) {
        List<Character> res = new ArrayList<>();
        switch (c) {
            case '2':
                res.add('a');
                res.add('b');
                res.add('c');
                break;
            case '3':
                res.add('d');
                res.add('e');
                res.add('f');
                break;
            case '4':
                res.add('g');
                res.add('h');
                res.add('i');
                break;
            case '5':
                res.add('j');
                res.add('k');
                res.add('l');
                break;
            case '6':
                res.add('m');
                res.add('n');
                res.add('o');
                break;
            case '7':
                res.add('p');
                res.add('q');
                res.add('r');
                res.add('s');
                break;
            case '8':
                res.add('t');
                res.add('u');
                res.add('v');
                break;
            case '9':
                res.add('w');
                res.add('x');
                res.add('y');
                res.add('z');
                break;
        }
        return res;
    }
}
