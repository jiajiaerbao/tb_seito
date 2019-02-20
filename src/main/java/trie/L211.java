package trie;

import iterator.TreeNode;
import sun.text.normalizer.Trie;

/*
* 坑 1: 因为出现了 模糊匹配 点. , 所以用 dfs, 这时把 search 的逻辑 都封装到 dfs 里面, 就不要在search里面加其他的逻辑了
* 坑 2: 坑 BOSS:
*       dfs的base case写的不对, 正确的逻辑如下:
*           如果没找到 当前 字母, 则返回 false: cur == null
*           如果所有字母都找到了, 则返回最后一个节点的 isLeaf 的状态
* */
@SuppressWarnings("Duplicates")
public class L211 {
    /******************************第二遍********************************************/
    /*
    * 坑 1: 这里的 . 代表着多个option, 分叉, 用DFS, 把search的逻辑都封装到DFS里面
    * 坑 2: 坑BOSS, DFS 写的不熟练, DFS传入到下一层的 TrieNode, Index都代表什么意思, 一定要弄清楚
    * */
    class WordDictionary_2 {
        class TrieNode {
            char val;
            TrieNode[] next;
            boolean isLeaf;

            public TrieNode(char val) {
                this.val = val;
                next = new TrieNode[256];
                isLeaf = false;
            }
        }

        private TrieNode root;

        /**
         * Initialize your data structure here.
         */
        public WordDictionary_2() {
            root = new TrieNode('\0');
        }

        /**
         * Adds a word into the data structure.
         */
        public void addWord(String word) {
            TrieNode cur = root;
            for (char ch : word.toCharArray()) {
                if (cur.next[ch] == null) {
                    cur.next[ch] = new TrieNode(ch);
                }
                cur = cur.next[ch];
            }
            cur.isLeaf = true;
        }

        /**
         * Returns if the word is in the data structure. A word could contain the dot character '.' to represent any one letter.
         */
        public boolean search(String word) {
            return helper(word, root, 0);
        }

        //这个DFS是这道题的坑
        private boolean helper(String word, TrieNode cur, int idx) {
            if (cur == null) {
                return false;
            }
            //这里是坑, 当idx等于length的时候, 相当于已经遍历完了word, 而cur是idx-1对应的位置, check isLeaf的状态即可
            if (idx == word.length()) {
                return cur.isLeaf;
            }
            char ch = word.charAt(idx);
            if (ch == '.') {
                for (TrieNode next : cur.next) {
                    if (helper(word, next, idx + 1)) {
                        return true;
                    }
                }
            } else {
                return helper(word, cur.next[ch], idx + 1);
            }
            return false;
        }
    }

    /******************************第一遍********************************************/
    class WordDictionary_1 {
        private TrieNode root;

        private class TrieNode {
            public Character ch;
            public TrieNode[] nexts;
            boolean isLeaf;

            public TrieNode(Character ch) {
                this.ch = ch;
                this.nexts = new TrieNode[256];
                this.isLeaf = false;
            }
        }

        /**
         * Initialize your data structure here.
         */
        public void WordDictionary_1() {
            root = new TrieNode('\0');
        }

        /**
         * Adds a word into the data structure.
         */
        public void addWord(String word) {
            TrieNode cur = root;
            for (Character ch : word.toCharArray()) {
                if (cur.nexts[ch - 'a'] == null) {
                    cur.nexts[ch - 'a'] = new TrieNode(ch);
                }
                cur = cur.nexts[ch - 'a'];
            }
            cur.isLeaf = true;
        }

        /**
         * Returns if the word is in the data structure. A word could contain the dot character '.' to represent any one letter.
         */
        public boolean search(String word) {
        /*把下面这些逻辑都封装到 helper 里面 !!!!
        TrieNode cur = root;
        for (Character ch : word.toCharArray()) {
            if (cur.nexts[ch - 'a'] == null) {
                return false;
            }
            cur = cur.nexts[ch - 'a'];
        }
        return cur.isLeaf;*/
            return helper(root, 0, word);
        }

        public boolean helper(TrieNode cur, int idx, String word) {
            if (cur == null) {
                return false;
            }
            if (idx == word.length()) {
                return cur.isLeaf;
            }
            Character ch = word.charAt(idx);
            if (ch == '.') {
                for (TrieNode next : cur.nexts) {
                    if (helper(next, idx + 1, word)) {
                        return true;
                    }
                }
            } else {
                return helper(cur.nexts[ch - 'a'], idx + 1, word);
            }
            return false;
        }
    }
}
