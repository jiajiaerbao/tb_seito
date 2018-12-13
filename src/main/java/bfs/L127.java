package bfs;

import java.util.*;

@SuppressWarnings("Duplicates")
public class L127 {
    /*
     * 坑 1: 加入到 Queue里面 和 设置 visited 最好放在一起, 这样不容易出错
     * 坑 2: 对于char[] chars, 转化成String需要这样写: String newWord = String.valueOf(chars);
     * 坑 3: len 的初始值用一个简单的例子试一下, 一定要小心 len++ 是对应于while(!que.isEmpty())的loop
     * */
    public int ladderLength(String beginWord, String endWord, List<String> wordList) {
        //corner case
        if (beginWord == null || beginWord.length() == 0 ||
                endWord == null || endWord.length() == 0 ||
                wordList == null || wordList.size() == 0) {
            return 0;
        }
        //用HashSet来实现O(1)的查询, 提高查询效率
        Set<String> dic = new HashSet<>(wordList);

        Queue<String> que = new LinkedList<>();
        que.offer(beginWord);

        Set<String> visited = new HashSet<>();

        int minLen = 1;
        while (!que.isEmpty()) {
            int size = que.size();
            while (size-- > 0) {
                String cur = que.poll();

                List<String> nextWords = getNextWords(cur, dic, visited);
                for (String nextWord : nextWords) {
                    //这里在进入下一层之前, 直接在Queue里面进行判断, 更快
                    if (nextWord.equals(endWord)) {
                        return minLen + 1;
                    }
                    //当加入到Queue的时候, 已经touch到了, 这时设置visited
                    que.offer(nextWord);            //加入Queue
                    visited.add(nextWord);          //设置为已经visited过了是成对出现的
                }
            }
            minLen++;
        }
        return 0;
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


    public int ladderLength2(String beginWord, String endWord, List<String> wordList) {
        //corner case
        if (beginWord == null || endWord == null || beginWord.length() == 0 || endWord.length() == 0 || wordList == null || wordList.size() == 0) {
            return -1;
        }
        Set<String> dict = new HashSet<>();
        for (String word : wordList) {
            dict.add(word);
        }
        return findShortestPath(beginWord, endWord, dict);
    }

    private int findShortestPath(String beginWord, String endWord, Set<String> dict) {
        int len = 1;
        Queue<String> que = new LinkedList<>();
        que.offer(beginWord);
        while (!que.isEmpty()) {
            int size = que.size();
            while (size-- > 0) {
                String cur = que.poll();
                List<String> nexts = getNextWords(cur, dict);
                //System.out.println("test" + nexts);
                for (String next : nexts) {
                    if (endWord.equals(next)) {
                        return len + 1;
                    }
                    //if(dict.contains(next)){
                    que.offer(next);
                    dict.remove(next);
                    //}
                }
            }
            len++;
        }
        return 0;
    }

    private List<String> getNextWords(String cur, Set<String> dict) {
        List<String> res = new ArrayList<>();
        char[] chars = cur.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            char temp = chars[i];
            for (char newCh = 'a'; newCh <= 'z'; newCh++) {
                chars[i] = newCh;
                String newWord = String.valueOf(chars);
                //System.out.println("newWord: " + newWord);
                if (dict.contains(newWord)) {
                    res.add(newWord);
                }
            }
            chars[i] = temp;
        }
        return res;
    }













































}
