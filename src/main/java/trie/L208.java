package trie;


/*
* 坑 1: 首先要自己定义一个TrieNode, 里面要有256个children, 一个isLeaf来指定自己是不是叶子节点
*       具体有多少个children要根据编码方式来定
*
* */
@SuppressWarnings("Duplicates")
public class L208 {
    private TrieNode root;
    private class TrieNode{
        Character ch;
        TrieNode[] nexts;
        Boolean isLeaf;
        public TrieNode(Character ch){
            this.ch = ch;
            nexts = new TrieNode[26];
            isLeaf = false;
        }

    }
    /** Initialize your data structure here. */
    public void Trie() {
        root = new TrieNode('\0');
    }

    /** Inserts a word into the trie. */
    public void insert(String word) {
        TrieNode cur = root;
        for(Character ch: word.toCharArray()){
            if(cur.nexts[ch-'a'] == null){          //每当进入一次for loop, cur指向的是上一个char, cur.nexts[ch - 'a']是指向当前的char
                cur.nexts[ch-'a'] = new TrieNode(ch);
            }
            cur = cur.nexts[ch - 'a'];              //退出for loop之前, cur顺次指向当前char的
        }
        cur.isLeaf = true;
    }

    /** Returns if the word is in the trie. */
    public boolean search(String word) {
        TrieNode cur = root;
        for(Character ch : word.toCharArray()){
            if(cur.nexts[ch - 'a'] == null){
                return false;
            }
            cur = cur.nexts[ch-'a'];
        }
        return cur.isLeaf;
    }

    /** Returns if there is any word in the trie that starts with the given prefix. */
    public boolean startsWith(String prefix) {
        TrieNode cur = root;
        for(Character ch : prefix.toCharArray()){
            if(cur.nexts[ch - 'a'] == null){
                return false;
            }
            cur = cur.nexts[ch-'a'];
        }
        return true;
    }
}