package bfs;

import java.util.*;

public class L126 {
    //如何表示图: 用HashMap<V, List<V>>来表示!!!!
    //见BFS笔记:
    //  如果在第一次遇见 c 的时候, 把 c 删掉的话, 会出现丢解的问题, 比如删除了 c 之后, 导致 b 跟 c 的边无法加到解集合中
    //  一层一层地删除访问过的元素
    public List<List<String>> findLadders(String beginWord, String endWord, List<String> wordList) {
        List<List<String>> res = new ArrayList<>();

        //corner case
        //这里省略了

        //建立一个hashSet用来实现O(1)的查询, 要比List快得多
        Set<String> dict = new HashSet<>(wordList);

        //BFS 用的 Queue
        Queue<String> que = new LinkedList<>();
        que.offer(beginWord);

        //用来判断是否找到了一个条路径
        //建立一个flag用来表示: 对某一层遍历完之后, 是否找到了Endword.
        //这里的隐含条件就是当找到了Endword之后, 即使继续遍历, 也无法找到最短路径了, 因为最短路径只可能出现在flag第一次设置为true的那一层
        //当继续向下一层移动的时候, 路径长度就加 1 了
        boolean findEndWord = false;

        //用于查环, 标准做法, 这里可以和dic混用, 但是我不考虑那种做法
        Set<String> visited = new HashSet<>();

        //这时卡壳的地方: 建图还是不熟练
        //用Map来建图: I want to create a map to represent a graph, key is vertex, value is its neighbors
        Map<String, List<String>> graph = new HashMap<>();

        //BFS模板
        while (!que.isEmpty()){
            int size = que.size();
            //这是这道题的特殊地方, 对于每一层的 visited 过的节点, 当遍历完这一层之后, 再设置为visited
            Set<String> visitedByThisLevel = new HashSet<>();
            while (size-- > 0){
                String cur = que.poll();
                //找出所有出现在dict里面的候选词
                List<String> nextWords = getNextWords(cur, dict);
                for(String nextWord : nextWords){
                    //已经找到
                    if(nextWord.equals(endWord)){
                        findEndWord = true;
                    }
                    //这个地方是你卡壳的地方
                    if(!visited.contains(nextWord)){
                        List<String> neighbors = graph.getOrDefault(nextWord, new ArrayList<>());
                        //这里要注意: 只有在当前层第一次访问到的 node 才加入到 queue 里面
                        if(neighbors.size() == 0){
                            que.offer(nextWord);
                            visitedByThisLevel.add(nextWord);
                        }
                        neighbors.add(cur);
                        graph.put(nextWord, neighbors);
                    }
                }
            }
            //注意: 每一层遍历结束之后, 在都设置到visited里面去
            visited.addAll(visitedByThisLevel);
            //找到了一个之后, 加到解集合里面
            if(findEndWord){
                List<String> one = new LinkedList<>();
                one.add(endWord);
                search(res, one, endWord, beginWord, graph);
                return res;
            }
        }
        return res;
    }
    private List<String> getNextWords(final String curWord, final Set<String> dict) {
        List<String> res = new ArrayList<>();
        char[] cc = curWord.toCharArray();
        for (int i = 0; i < cc.length; i++) {
            char temp = cc[i];
            for (char c = 'a'; c <= 'z'; c++) {
                cc[i] = c;
                String nextWord = String.valueOf(cc);
                //这里找到所有的在字典里面, 但是不是该单词本身的所有词
                if (c != temp && dict.contains(nextWord)) {
                    res.add(nextWord);
                }
            }
            cc[i] = temp;
        }
        //System.out.println("curWord: " + curWord + "     nextWords:: " + res);
        return res;
    }
    private void search(List<List<String>> res, List<String> one, String cur, String end, Map<String, List<String>> graph) {
        if (cur.equals(end)) {
            List<String> copy = new LinkedList<String>(one);
            res.add(copy);
            return;
        }
        List<String> next = graph.get(cur);
        for (String n : next) {
            one.add(0, n);
            search(res, one, n, end, graph);
            one.remove(0);
        }
    }
}
