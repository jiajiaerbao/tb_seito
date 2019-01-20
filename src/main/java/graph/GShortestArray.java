package graph;

import java.util.*;

/*
* 坑 1: 这是一道G家面经题
* 坑 2: Time Complexity: O(N)
* 坑 3: Space Complexity: O(N)
* */
public class GShortestArray {
    static final int INITIAL = 0;
    static final int PROCESSING = 1;
    static final int DONE = 2;

    public List<Integer> shortestArray(List<List<Integer>> lists) {
        if (lists == null) {
            throw new IllegalArgumentException();
        }
        List<Integer> res = new ArrayList<>();
        HashMap<Integer, Integer> visited = new HashMap<>();
        HashMap<Integer, HashSet<Integer>> graph = createGraph(lists, visited);
        for (List<Integer> list : lists) {
            for (int cur : list) {
                dfs(graph, cur, visited, res);
            }
        }
        return res;
    }

    private void dfs(HashMap<Integer, HashSet<Integer>> graph, int cur, HashMap<Integer, Integer> visited, List<Integer> res) {
        Integer status = visited.get(cur);
        if (status == DONE) {
            return;
        } else if (status == PROCESSING) {
            res.add(0, cur);
            return;
        }
        visited.put(cur, PROCESSING);
        HashSet<Integer> nexts = graph.get(cur);
        if (nexts != null) {
            for (int next : graph.get(cur)) {
                dfs(graph, next, visited, res);
            }
        }
        visited.put(cur, DONE);
        res.add(0, cur);
    }

    private HashMap<Integer, HashSet<Integer>> createGraph(List<List<Integer>> lists, HashMap<Integer, Integer> visited) {
        HashMap<Integer, HashSet<Integer>> graph = new HashMap<>();
        for (List<Integer> list : lists) {
            for (int i = 0; i < list.size() - 1; i++) {
                int from = list.get(i);
                int to = list.get(i + 1);
                HashSet<Integer> nexts = graph.getOrDefault(from, new HashSet<>());
                nexts.add(to);
                graph.put(from, nexts);
                visited.put(from, INITIAL);
                visited.put(to, INITIAL);
            }
        }
        return graph;
    }
    public static void main(String[] args){
        GShortestArray gShortestArray = new GShortestArray();
        List<List<Integer>> lists =
                Arrays.asList(
                        Arrays.asList(1, 2, 3),
                        Arrays.asList(2, 5, 7),
                        Arrays.asList(2, 7, 5)
                );
        List<Integer> res = gShortestArray.shortestArray(lists);
        System.out.println(res);

        lists =
                Arrays.asList(
                        Arrays.asList(1, 2, 3, 2),
                        Arrays.asList(2, 5, 7),
                        Arrays.asList(2, 7, 5)
                );
        res = gShortestArray.shortestArray(lists);
        System.out.println(res);

        lists =
                Arrays.asList(
                        Arrays.asList(1, 2, 3, 2),
                        Arrays.asList(2, 5, 7),
                        Arrays.asList(7, 5, 2)
                );
        res = gShortestArray.shortestArray(lists);
        System.out.println(res);

        lists =
                Arrays.asList(
                        Arrays.asList(1, 2, 3, 3),
                        Arrays.asList(2, 5, 7),
                        Arrays.asList(7, 5, 2)
                );
        res = gShortestArray.shortestArray(lists);
        System.out.println(res);
    }
}
