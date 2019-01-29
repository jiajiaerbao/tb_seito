package trie;

import java.util.*;
import java.util.stream.Collectors;

/*
* 坑 1: 总结大概思路:
*       Pair: String, Count, 用来保存每个String 和 其出现的次数
*             改写了compareTo, 用来比较 不同的 Pair
*       TrieNode: 除了正常的 char, TrieNode[], isLeaf 之外;
*                 还有 List<Pair> 用来保存 当前 TrieNode 的 count前三个 String
* 坑 2: Trie的逻辑
*       insert的话, 注意这里仅仅是小写字母加上空格, 所以可以改写空格的index为27, 这样就可以开辟一个27大小的array了
*       注意: 先把 cur 向下定位到 ch 所对应的 TrieNode 之后, 再 update List<Pair>
* 坑 3: 这里定义了一个全局字典:
*       Map<String: Input, Integer: Count> 来记录所有的字符串出现的次数
*       因为每个TrieNode只是记录了目前为止的count前三个, 这里需要一个全局变量来记录到目前为止各个字符串出现的次数
*
* */
@SuppressWarnings("Duplicates")
public class L642 {
    private TrieNode root;
    private StringBuilder sb;
    private Map<String, Integer> countMap;

    private class Pair implements Comparable<Pair> {
        public String searchWords;
        public int count;

        public Pair(String str, int times) {
            this.searchWords = str;
            this.count = times;
        }

        @Override
        public int compareTo(Pair that) {
            return this.count == that.count ? this.searchWords.compareTo(that.searchWords) : (that.count - this.count);
        }
    }

    private class TrieNode {
        public char ch;
        public TrieNode[] nexts;
        public boolean isLeaf;
        public List<Pair> pairs;

        public TrieNode(char ch) {
            this.ch = ch;
            this.nexts = new TrieNode[27];
            this.pairs = new ArrayList<>();
            this.isLeaf = false;
        }
    }

    public void AutocompleteSystem(String[] sentences, int[] times) {
        //corner case
        if (sentences == null || times == null || sentences.length != times.length) {
            return;
        }
        root = new TrieNode('\0');
        sb = new StringBuilder();
        countMap = new HashMap<>();
        for (int i = 0; i < sentences.length; i++) {
            countMap.put(sentences[i], times[i]);
            insert(sentences[i], root);
        }
    }

    public void insert(String str, TrieNode root) {
        TrieNode cur = root;
        for (char ch : str.toCharArray()) {
            int idx;
            if (ch >= 'a' && ch <= 'z') {
                idx = ch - 'a';
            } else {
                idx = 26;
            }
            if (cur.nexts[idx] == null) {
                cur.nexts[idx] = new TrieNode(ch);
            }
            //这里是坑:  先把 cur 向下定位到 ch 所对应的 TrieNode 之后, 再 update List<Pair>
            cur = cur.nexts[idx];

            //开始: 对于str, 在Trie上面遍历过的所有的点, 对这些点都要更新 str 的频率
            boolean findOne = false;
            for (Pair pair : cur.pairs) {
                if (pair.searchWords.equals(str)) {
                    pair.count = countMap.get(str);
                    findOne = true;
                }
            }
            if (!findOne) {
                cur.pairs.add(new Pair(str, countMap.get(str)));
            }
            Collections.sort(cur.pairs);
            if (cur.pairs.size() > 3) {
                cur.pairs.remove(cur.pairs.size() - 1);
            }
            //结束: 对于str, 在Trie上面遍历过的所有的点, 对这些点都要更新 str 的频率
        }
        cur.isLeaf = true;
    }

    public List<String> findPrefix(String str, TrieNode root) {
        TrieNode cur = root;
        for (char ch : str.toCharArray()) {
            int idx;
            if (ch >= 'a' && ch <= 'z') {
                idx = ch - 'a';
            } else {
                idx = 26;
            }
            if (cur.nexts[idx] == null) {
                return new ArrayList<>();
            }
            cur = cur.nexts[idx];
        }
        return cur.pairs.stream().map(p -> p.searchWords).collect(Collectors.toList());
    }

    public List<String> input(char c) {
        if (c == '#') {
            String newStr = sb.toString();
            countMap.put(newStr, countMap.getOrDefault(newStr, 0) + 1);
            insert(newStr, root);
            sb.setLength(0);
            return new ArrayList<>();
        } else {
            sb.append(c);
            return findPrefix(sb.toString(), root);
        }
    }
    public static void main(String[] args){
        L642 l642 = new L642();
        l642.AutocompleteSystem(new String[]{"i love you","island","iroman","i love leetcode"}, new int[]{5,3,2,2});
        l642.input('i');
        l642.input(' ');
        l642.input('a');
        l642.input('#');
        l642.input('i');
        l642.input(' ');
        l642.input('a');
        l642.input('#');
        l642.input('i');
        l642.input(' ');
        l642.input('a');
        l642.input('#');
    }
}
