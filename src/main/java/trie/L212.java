package trie;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("Duplicates")
public class L212 {
    /******************************第一遍********************************************/
    /*
    * 坑 1: 大概思路: 把words保存在trie里面, 用来处理逐个字符输入的情况: 建立TrieNode, 把所有的word都插入到trie里面, 最后通过root来进行查询
    *                2D board的话, 用DFS进行搜索: visited, List<String> result, StringBuilder path
    * 坑 2: 坑BOSS:  最重要的就是理解 trie 的root 是一个 dummyNode, 不承担任何信息, 所以dfs的base需要check ndoe.nextNode[...]
    *                DFS的搜索, 传入的 [i, j] 所对应的字符存在于 trieNode.next[[i, j]] 的里面
    *                当发现一个trieNode的位置可以形成单词了之后, 设置该TrieNdoe的isLeaf = false, 否则会出现重复搜索
    * */
    class FirstTime {
        private class TrieNode {
            char val;
            boolean isLeaf;
            TrieNode[] nextNodes;

            public TrieNode(char val) {
                this.val = val;
                isLeaf = false;
                nextNodes = new TrieNode[256];
            }
        }
        public List<String> findWords(char[][] board, String[] words) {
            List<String> res = new ArrayList<>();
            //corner case
            if (board == null || board.length == 0 || board[0] == null || board[0].length == 0 ||
                    words == null || words.length == 0) {
                return res;
            }
            //construct trie
            TrieNode root = new TrieNode('\0');
            for (String word : words) {
                insert(root, word);
            }
            //search 2d board, each cell may be a start point
            boolean[][] visited = new boolean[board.length][board[0].length];
            for (int i = 0; i < board.length; i++) {
                for (int j = 0; j < board[0].length; j++) {
                    dfs(root, board, i, j, visited, res, new StringBuilder());
                }
            }
            return res;
        }
        //2D board + trie的DFS: 做了三个小时
        private void dfs(TrieNode node, char[][] board,
                         int idxI, int idxJ,
                         boolean[][] visited, List<String> res, StringBuilder path) {
            int row = board.length;
            int col = board[0].length;
            //这里的corner case是判断 node.nextNodes[board[idxI][idxJ]] 是否为空, 即
            //board[idxI][idxJ] 所对应的字符在 node.nextNodes[...] 的位置
            if (idxI < 0 || idxI >= row || idxJ < 0 || idxJ >= col ||
                    visited[idxI][idxJ] || node.nextNodes[board[idxI][idxJ]] == null) {
                return;
            }
            //board[idxI][idxJ] 所对应于 node.nextNodes[...]
            TrieNode next = node.nextNodes[board[idxI][idxJ]];
            //set status
            visited[idxI][idxJ] = true;
            path.append(board[idxI][idxJ]);

            //表明对于本次搜索的起点 [i ,j], 在Trie上面, 该TrieNode的地方已经找到单词了, 不再继续生成单词
            if (next.isLeaf) {
                res.add(path.toString());
                next.isLeaf = false;
            }

            dfs(next, board, idxI, idxJ + 1, visited, res, path);
            dfs(next, board, idxI, idxJ - 1, visited, res, path);
            dfs(next, board, idxI + 1, idxJ, visited, res, path);
            dfs(next, board, idxI - 1, idxJ, visited, res, path);
            //set back
            visited[idxI][idxJ] = false;
            path.deleteCharAt(path.length() - 1);
        }

        private void insert(TrieNode root, String word) {
            TrieNode cur = root;
            for (char ch : word.toCharArray()) {
                if (cur.nextNodes[ch] == null) {
                    cur.nextNodes[ch] = new TrieNode(ch);
                }
                cur = cur.nextNodes[ch];
            }
            cur.isLeaf = true;
        }
    }
}
