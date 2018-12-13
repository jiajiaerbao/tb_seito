package bfs;

import java.util.*;

@SuppressWarnings("Duplicates")
public class L1266 {
    /*
    * 这道题是126的简化版本, 127的加强版本
    * 不要求返回所有的最短路径, 只是返回任意一条最短路径即可
    * 坑 1: 在127的基础上, 用 Map<String, String>把所有的边都存起来, 注意: 要反着存, next --> cur
    *       然后 reverse 一下即可
    * 坑 2: 加入到 Queue里面 和 设置 visited 最好放在一起, 这样不容易出错
    * 坑 3: 做第二遍的时候, 忘记了查环, 每次touch到了next之后, 记得要从dict里面remove掉
    * 坑 4: 在构建path, 返回任意一条路径的时候, 不需要用dfs, 直接用while loop反着退到beginWord即可, 在while loop里面, 如果碰到了beginWord的话, 立即break出来
    * */
    public List<List<String>> findAnyoneLadder(String beginWord, String endWord, List<String> wordList) {
        List<List<String>> res = new ArrayList<>();
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
                        //把最后的endWord加到graph里面
                        graph.put(nextWord, cur);
                        List<String> result = printGraph(graph, endWord, beginWord);
                        res.add(result);
                        return res;
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
    private List<String> printGraph(final Map<String, String> graph, final String endWord, final String beginWord) {
        //System.out.println("endWord:" + endWord + ";\nbeginWord:" + beginWord + ";\ngraph:" + graph);
        List<String> res = new ArrayList<>();
        String cur = endWord;
        while (graph.containsKey(cur)) {
            //这里是防止出环, 当出现了end(也就是beginWord)的时候, 加到path之后立即退出while loop
            if (cur.equals(beginWord)) {
                break;
            }
            cur = graph.get(cur);
            //这里要在最开始的地方加, 相当于倒序
            res.add(0, cur);
        }
        //这个是在最后加
        res.add(endWord);
        return res;
    }



    /**************************************************************************/

    public List<List<String>> findAnyoneLadder2(String beginWord, String endWord, List<String> wordList) {
        List<List<String>> res = new ArrayList<>();
        if (beginWord == null || beginWord.length() == 0 ||
                endWord == null || endWord.length() == 0 ||
                wordList == null || wordList.size() == 0) {
            return res;
        }
        Set<String> dict = new HashSet<>(wordList);
        return bfs(beginWord, endWord, dict);
    }

    private List<List<String>> bfs(String beginWord, String endWord, Set<String> dict) {
        List<List<String>> res = new ArrayList<>();

        Queue<String> que = new LinkedList<>();
        que.offer(beginWord);

        Map<String, String> graph = new HashMap<>();

        while (!que.isEmpty()) {
            int size = que.size();
            while (size-- > 0) {
                String cur = que.poll();
                List<String> nexts = getNexts(cur, dict);
                for (String next : nexts) {
                    que.offer(next);
                    graph.put(next, cur);
                    //System.out.println("next:" + next + "; cur:" + cur);
                    //千万不要忘记查环
                    dict.remove(next);
                    //这个判断是否出现endWord, 并且输出任意一条最短路径的逻辑要放在for loop最后来实现
                    //因为前面有构建graph的语句, 必须要把 到endWord的那条边 放到graph里面才能完整地建立出来路径
                    if (next.equals(endWord)) {
                        getOnePath(next, beginWord, res, graph);
                        return res;
                    }
                }
            }
        }
        return res;
    }
    private List<String> getNexts(String cur, Set<String> dict) {
        List<String> res = new ArrayList<>();
        char[] chars = cur.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            char temp = chars[i];
            for (char ch = 'a'; ch <= 'z'; ch++) {
                chars[i] = ch;
                String newWord = String.valueOf(chars);
                //取出自身的单词
                if (!newWord.equals(cur) && dict.contains(newWord)) {
                    res.add(newWord);
                }
            }
            chars[i] = temp;
        }
        return res;
    }
    private void getOnePath(String cur, String end, List<List<String>> res,  Map<String, String> graph) {
        List<String> path = new ArrayList<>();
        //这里的graph的边的方向也是从endWord到beginWord, 所以当从endWord走到begWord的时候, 即构建好了一条路径
        String next = cur;
        while (next != null){
            //这里是防止出环, 当出现了end(也就是beginWord)的时候, 加到path之后立即退出while loop
            if(next.equals(end)){
                path.add(0, next);
                break;
            }
            //这里别忘了在 list 的开头加入节点
            path.add(0, next);
            next = graph.get(next);
        }
        res.add(new ArrayList<>(path));
    }










































}
