package graph;

import java.util.*;

/*
* 坑 1: 结果集 List<String>: 这里需要建立一个 LinkedList<String>, 因为每次都是在index==0的位置加新节点
* 坑 2: 题目要求的起始机场是 "JFK"
* 坑 3: 这道题用的是欧拉回路的解法 (Euler circuit solution) (和拓扑解法一模一样), 还有DFS解法(没看)
* */
@SuppressWarnings("Duplicates")
public class L332 {
    public List<String> findItinerary(String[][] tickets) {
        //corner case
        if (tickets == null || tickets.length == 0 || tickets[0] == null || tickets[0].length == 0) {
            return new LinkedList<>();
        }
        List<String> res = new LinkedList<>();
        Map<String, PriorityQueue<String>> graph = createGraph(tickets);
        String startAirPort = "JFK";
        eulerCircuit(startAirPort, graph, res);

        return res;
    }

    private Map<String, PriorityQueue<String>> createGraph(String[][] tickets) {
        Map<String, PriorityQueue<String>> graph = new HashMap<>();
        for (String[] entry : tickets) {
            PriorityQueue<String> minHeap = graph.getOrDefault(entry[0], new PriorityQueue<>());
            minHeap.offer(entry[1]);
            graph.put(entry[0], minHeap);
        }
        return graph;
    }
    //Euler circuit solution
    private void eulerCircuit(String cur, Map<String, PriorityQueue<String>> graph, List<String> res) {
        PriorityQueue<String> minHeap = graph.get(cur);
        while (minHeap != null && !minHeap.isEmpty()) {
            String next = minHeap.poll();
            eulerCircuit(next, graph, res);
        }
        res.add(0, cur);
    }
    //Topological sort
    private void topoSort(String cur, Map<String, PriorityQueue<String>> graph, List<String> res) {
        PriorityQueue<String> minHeap = graph.get(cur);
        while (minHeap != null && !minHeap.isEmpty()) {
            String next = minHeap.poll();
            eulerCircuit(next, graph, res);
        }
        res.add(0, cur);
    }
}
