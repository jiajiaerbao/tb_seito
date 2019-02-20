package trie;

import java.util.*;
import java.util.stream.Collectors;

@SuppressWarnings("Duplicates")
public class L642 {
    /******************************第二遍********************************************/
    /*
    * 坑 1: 定义TrieNode的时候, 需要定义一个field: List<Pair>, 用来存放当前trieNode的top3的String和其count
    * 坑 2: 需要一个全局变量用来存储所有的 input str 和 其count, 这里的逻辑都是关联在一起的:
    *          因为是autoComplete系统, 需要定义一个StringBuilder来保存input的char, 直到遇到#, 代表一次输入完成
    *          所有的输入都要用全局的Map来保存起来, 用来记录到目前为止, 该str已经被输入了的次数
    * 坑 3: top3的比较逻辑封装在 Pair的compareTo里面, 调用一次Collections.sort(List<Pair>)即实现了自动排序
    * 坑 4: insert逻辑里面有坑: 每一次输入一个新的str的时候, 都要更新沿途的TrieNode的Top3:
    *           如果已经存在, 则更新count(直接调用countMap里面的count)
    *           如果不存在, 则加到list里面
    *              最后sort一下, 去掉最小的那个
    * 坑 5: 输入显示的逻辑:
    *           如果输入的是 '#' 的话, 则完成了一次 str的输入, 调用insert
    *           如果输入的不是 '#', 返回最后一个字母所在的trieNode的top3
    * 坑 6: 因为有一个全局的countMap, 所以每次都是先更新countMap, 然后再更新各个trieNode的Pair的count
    * */
    class AutocompleteSystem_2 {
        private class TrieNode {
            char val;
            boolean isLeaf;
            TrieNode[] next;
            List<Pair> list;

            public TrieNode(char val) {
                this.val = val;
                isLeaf = false;
                next = new TrieNode[256];
                list = new ArrayList<>();
            }
        }

        private TrieNode root;
        private StringBuilder sb;
        private Map<String, Integer> countMap;

        private class Pair implements Comparable<Pair> {
            public String searchWords;
            public int count;

            public Pair(String str, int cnt) {
                searchWords = str;
                count = cnt;
            }

            @Override
            public int compareTo(Pair that) {
                if (this.count != that.count) {
                    return that.count - this.count;
                } else {
                    return this.searchWords.compareTo(that.searchWords);
                }
            }
        }

        public AutocompleteSystem_2(String[] sentences, int[] times) {
            //corner case
            if (sentences == null || times == null || sentences.length != times.length) {
                return;
            }
            root = new TrieNode('\0');
            sb = new StringBuilder();
            countMap = new HashMap<>();
            for (int i = 0; i < sentences.length; i++) {
                //这里要先更新countMap再更新Trie, 因为trieNode的List<Pair>的Pair的count是从countMap的到的
                countMap.put(sentences[i], times[i]);
                insert(root, sentences[i]);
            }
        }

        private void insert(TrieNode root, String str) {
            TrieNode cur = root;
            for (char curCh : str.toCharArray()) {
                if (cur.next[curCh] == null) {
                    cur.next[curCh] = new TrieNode(curCh);
                }
                //因为最开始的cur是root, 没有实际意义, 所以是先把cur指向下一层的node, 再更新pair
                cur = cur.next[curCh];
                //下面的逻辑是坑: 更新沿途TrieNode的list
                boolean updateCnt = false;
                for (Pair pair : cur.list) {
                    if (pair.searchWords.equals(str)) {
                        pair.count = countMap.get(str);
                        updateCnt = true;
                    }
                }
                if (!updateCnt) {
                    cur.list.add(new Pair(str, countMap.get(str)));

                }
                Collections.sort(cur.list);
                if (cur.list.size() > 3) {
                    cur.list.remove(cur.list.size() - 1);
                }

            }
            cur.isLeaf = true;
        }

        public List<String> input(char c) {
            if (c == '#') {
                String newStr = sb.toString();
                countMap.put(newStr, countMap.getOrDefault(newStr, 0) + 1);
                insert(root, newStr);
                sb.setLength(0);
                return new ArrayList<>();
            } else {
                sb.append(c);
                return findPrefix(sb.toString(), root);
            }
        }

        public List<String> findPrefix(String str, TrieNode root) {
            TrieNode cur = root;
            for (char ch : str.toCharArray()) {
                if (cur.next[ch] == null) {
                    return new ArrayList<>();
                }
                cur = cur.next[ch];
            }
            return cur.list.stream().map(p -> p.searchWords).collect(Collectors.toList());
        }
    }

    /******************************第一遍********************************************/
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
    class AutocompleteSystem_1 {
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

        public void AutocompleteSystem_1(String[] sentences, int[] times) {
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
    }

    public static void main(String[] args) {
        L642 l642 = new L642();
        /*l642.AutocompleteSystem(new String[]{"i love you","island","iroman","i love leetcode"}, new int[]{5,3,2,2});
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
        l642.input('#');*/
    }
}
