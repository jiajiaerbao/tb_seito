package trie;

import iterator.TreeNode;

/*
* 坑 1: 因为出现了 模糊匹配 点. , 所以用 dfs, 这时把 search 的逻辑 都封装到 dfs 里面, 就不要在search里面加其他的逻辑了
* 坑 2: 坑 BOSS:
*       dfs的base case写的不对, 正确的逻辑如下:
*           如果没找到 当前 字母, 则返回 false: cur == null
*           如果所有字母都找到了, 则返回最后一个节点的 isLeaf 的状态
* */
@SuppressWarnings("Duplicates")
public class L211 {
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
    public void WordDictionary() {
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
