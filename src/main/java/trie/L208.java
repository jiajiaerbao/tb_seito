package trie;

import tree.TreeNode;

@SuppressWarnings("Duplicates")
public class L208 {
    /******************************第二遍********************************************/
    /*
    * 坑 1: 在trie的search, insert, startWith里面, 不要直接遍历root, 定义一个临时变量cur=root, 用cur来遍历
    * */
    private class Trie_2 {
        private class TrieNode {
            char val;
            boolean isLeaf;
            TrieNode[] next;

            public TrieNode(char val) {
                this.val = val;
                isLeaf = false;
                next = new TrieNode[256];
            }
        }

        private TrieNode root;

        /**
         * Initialize your data structure here.
         */
        public Trie_2() {
            root = new TrieNode('\0');
        }

        /**
         * Inserts a word into the trie.
         */
        public void insert(String word) {
            //千万不要改变root的值, 这里用一个cur来进行遍历
            TrieNode cur = root;
            for (int i = 0; i < word.length(); i++) {
                char curCh = word.charAt(i);
                if (cur.next[curCh] == null) {
                    cur.next[curCh] = new TrieNode(curCh);
                }
                cur = cur.next[curCh];
            }
            cur.isLeaf = true;
        }

        /**
         * Returns if the word is in the trie.
         */
        public boolean search(String word) {
            TrieNode cur = root;
            for (int i = 0; i < word.length(); i++) {
                if (cur.next[word.charAt(i)] == null) {
                    return false;
                }
                cur = cur.next[word.charAt(i)];
            }
            return cur.isLeaf;
        }

        /**
         * Returns if there is any word in the trie that starts with the given prefix.
         */
        public boolean startsWith(String prefix) {
            TrieNode cur = root;
            for (int i = 0; i < prefix.length(); i++) {
                if (cur.next[prefix.charAt(i)] == null) {
                    return false;
                }
                cur = cur.next[prefix.charAt(i)];
            }
            return true;
        }
    }

    /******************************第一遍********************************************/
    /*
     * 坑 1: 首先要自己定义一个TrieNode, 里面要有256个children, 一个isLeaf来指定自己是不是叶子节点
     *       具体有多少个children要根据编码方式来定
     *
     * */
    private class Trie_1 {
        private TrieNode root;

        private class TrieNode {
            Character ch;
            TrieNode[] nexts;
            Boolean isLeaf;

            public TrieNode(Character ch) {
                this.ch = ch;
                nexts = new TrieNode[26];
                isLeaf = false;
            }
        }
        /**
         * Initialize your data structure here.
         */
        public void Trie_1() {
            root = new TrieNode('\0');
        }

        /**
         * Inserts a word into the trie.
         */
        public void insert(String word) {
            TrieNode cur = root;
            for (Character ch : word.toCharArray()) {
                if (cur.nexts[ch - 'a'] == null) {          //每当进入一次for loop, cur指向的是上一个char, cur.nexts[ch - 'a']是指向当前的char
                    cur.nexts[ch - 'a'] = new TrieNode(ch);
                }
                cur = cur.nexts[ch - 'a'];              //退出for loop之前, cur顺次指向当前char的
            }
            cur.isLeaf = true;
        }

        /**
         * Returns if the word is in the trie.
         */
        public boolean search(String word) {
            TrieNode cur = root;
            for (Character ch : word.toCharArray()) {
                if (cur.nexts[ch - 'a'] == null) {
                    return false;
                }
                cur = cur.nexts[ch - 'a'];
            }
            return cur.isLeaf;
        }

        /**
         * Returns if there is any word in the trie that starts with the given prefix.
         */
        public boolean startsWith(String prefix) {
            TrieNode cur = root;
            for (Character ch : prefix.toCharArray()) {
                if (cur.nexts[ch - 'a'] == null) {
                    return false;
                }
                cur = cur.nexts[ch - 'a'];
            }
            return true;
        }
    }
}