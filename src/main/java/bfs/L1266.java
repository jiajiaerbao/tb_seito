package bfs;

import java.util.*;

public class L1266 {

    /*
    * 这道题是126的简化版本, 127的加强版本
    * 不要求返回所有的最短路径, 只是返回任意一条最短路径即可
    * 具体做法:
    *       在127的基础上, 用 Map<String, String>把所有的边都存起来, 注意: 要反着存, next --> cur
    *       然后 reverse 一下即可
    * */

    /*
     * 加入到 Queue里面 和 设置 visited 最好放在一起, 这样不容易出错
     * */
    public List<String> findAnyoneLadder(String beginWord, String endWord, List<String> wordList) {
        List<String> res = new ArrayList<>();
        //corner case
        if (beginWord == null || beginWord.length() == 0 ||
                endWord == null || endWord.length() == 0 ||
                wordList == null || wordList.size() == 0) {
            return res;
        }
        //用HashSet来实现O(1)的查询, 提高查询效率
        Set<String> dic = new HashSet<>(wordList);

        Queue<String> que = new LinkedList<>();
        que.offer(beginWord);

        Set<String> visited = new HashSet<>();

        /*
         * 这里是变化点: 保存图的边
         * */
        Map<String, String> graph = new HashMap<>();

        while (!que.isEmpty()) {
            int size = que.size();
            while (size-- > 0) {
                String cur = que.poll();

                List<String> nextWords = getNextWords(cur, dic, visited);
                for (String nextWord : nextWords) {
                    //这里在进入下一层之前, 直接在Queue里面进行判断, 更快
                    if (nextWord.equals(endWord)) {
                        //return minLen + 1;
                        List<String> result = printGraph(graph, endWord);
                        Collections.reverse(result);
                        return result;
                    }
                    //当加入到Queue的时候, 已经touch到了, 这时设置visited
                    que.offer(nextWord);            //加入Queue
                    visited.add(nextWord);          //设置为已经visited过了是成对出现的
                    /*
                    * 这里是变化点: 保存各个边
                    * */
                    graph.put(nextWord, cur);
                }
            }
        }
        return res;
    }

    private List<String> getNextWords(final String cur, final Set<String> wordList, final Set<String> visited) {
        List<String> res = new ArrayList<>();
        char[] cc = cur.toCharArray();
        for (int i = 0; i < cc.length; i++) {
            char temp = cc[i];
            for (char c = 'a'; c <= 'z'; c++) {
                cc[i] = c;
                String nextWord = String.valueOf(cc);
                //下一个变化一个字母得到的单词 (不包括本身), 在词典里面, 还没有被访问过
                if (c != temp && wordList.contains(nextWord) && !visited.contains(nextWord)) {
                    res.add(nextWord);
                }
            }
            cc[i] = temp;                           //这里要注意: 当改变了一个单词的字母之后, 要记得 set back 回来, 否则就不对了
        }
        return res;
    }

    /*
     * 这里是变化点: 因为从 endWord 只有一条边能连通 startWord
     * */
    private List<String> printGraph(final Map<String, String> graph, final String endWord){
        List<String> res = new ArrayList<>();
        String cur = endWord;
        while (graph.containsKey(cur)){
            cur = graph.get(cur);
            res.add(cur);
        }
        res.add(endWord);
        return res;
    }
}
