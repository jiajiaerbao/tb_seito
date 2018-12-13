package bfs;

import java.util.*;

@SuppressWarnings("Duplicates")
public class L126 {
    //坑 1: 如何表示图: 用HashMap<V, List<V>>来表示!!!!
    //坑 2: 见BFS笔记:
    //          如果在第一次遇见 c 的时候, 把 c 删掉的话, 会出现丢解的问题, 比如删除了 c 之后, 导致 b 跟 c 的边无法加到解集合中
    //          一层一层地删除访问过的元素
    //坑 3: 坑BOSS: 思路根本不记得了 --> 因为不理解: bfs, 一层一层地扫描, 反着存到graph(next, cur), 当发现了endWord之后一定要在处理完该层之后再生成所有path, 用dfs从endWord开始反着生成path
    //坑 4: 用DFS构建路径的不理解:
    //          a. graph是<next, List<cur>>, graph的边是反着指的
    //          b. 在调用函数 search(List<List<String>> res, List<String> one, String cur, String end, Map<String, List<String>> graph)的时候
    //             是search(res, one, endWord, beginWord, graph): cur的位置放endWord, end的位置放beginWord, 因为边是反着指的, 所有边的起始点是endWord, 所有边的终点是beginWord
    //          c.
    //          d. 在getPaths的for loop里面, 把next也就是应该是cur的点, 放在开始位置: path.add(0, next)
    //坑 5: bfs while loop里面卡了三个小时:
    //          a. 第二遍写法的查环是通过从字典里面删除word来实现的
    //          b. 构建graph的时候
    //          c. 设置一个flag: 如果在当前层发现了endWord, 不立即返回, 一定要把当前层的所有节点都处理完之后, 再生成所有的path, 防止丢解
    //          d. 第一次(成功)加到thisLevelVisited的时候, 才加入到queue里面
    //          e. 千万不要忘记更新graph
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


    /************************************************************/

    public List<List<String>> findLadders(String beginWord, String endWord, List<String> wordList) {
        //corner case
        if (beginWord == null || beginWord.length() == 0 ||
                endWord == null || endWord.length() == 0 ||
                wordList == null || wordList.size() == 0) {
            return new ArrayList<>();
        }
        Set<String> dic = new HashSet<>(wordList);
        return getAllPaths(beginWord, endWord, dic);
    }

    private List<List<String>> getAllPaths(String beginWord, String endWord, Set<String> dict) {
        List<List<String>> res = new ArrayList<>();
        Queue<String> que = new LinkedList<>();
        que.offer(beginWord);
        boolean flag = false;
        Map<String, List<String>> graph = new HashMap<>();
        while (!que.isEmpty()) {
            //每当进入到下一层的时候, 重新建立一个HashSet用来保存当前层所有已经访问过的节点
            Set<String> thisLevelVisited = new HashSet<>();
            int size = que.size();
            while (size-- > 0) {
                String cur = que.poll();
                List<String> nexts = getNexts(cur, dict);
                //System.out.println("nexts:" + nexts);
                for (String next : nexts) {
                    if (next.equals(endWord)) {
                        flag = true;
                    }
                    //第一次碰到的点: 放入到queue里面, 在图里面新建一个<Node, List<Neighbors>>, 放入该层的visitedSet里面
                    if (!thisLevelVisited.contains(next)) {
                        que.offer(next);
                        graph.put(next, new ArrayList<>());
                        thisLevelVisited.add(next);
                    }
                    //****这里是不是需要加到 if 里面? 不要
                    //      因为这里的key是next, 需要加的节点是cur: graph.get(next).add(cur)
                    //      也就是说即使next已经touch过了, 也要新建立一条从next到不同cur的边(同一个queue里面有不同的cur)
                    List<String> neighbors = graph.get(next);
                    neighbors.add(cur);
                    graph.put(next, neighbors);
                }
                //System.out.println("cur:" + cur + ";   graph:" + graph.get(cur));
            }
            if (flag) {
                //****为什么要在list里面加endWord?
                //      因为endWord在graph里面是所有边的起点, 如果在刚开始的时候,不加到list里面的话, 遍历到后面之后就没办法加了
                //      dfs是deep copy最后的list, 所以初始的endWord一直都会在list里面的
                List<String> list = new ArrayList<>();
                list.add(endWord);
                getPaths(endWord, beginWord, list, res, graph);
                return res;
            }
            //从dict里面去掉所有已经touch过的单词, 起到查环的作用
            dict.removeAll(thisLevelVisited);
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
                if (!newWord.equals(cur) && dict.contains(newWord)) {
                    res.add(newWord);
                }
            }
            chars[i] = temp;
        }
        return res;
    }

    private void getPaths(String cur, String end, List<String> path, List<List<String>> res, Map<String, List<String>> graph) {
        if (cur.equals(end)) {
            res.add(new ArrayList<>(path));
            return;
        }
        for (String next : graph.get(cur)) {
            path.add(0, next);
            getPaths(next, end, path, res, graph);
            path.remove(0);
        }
    }

































}
